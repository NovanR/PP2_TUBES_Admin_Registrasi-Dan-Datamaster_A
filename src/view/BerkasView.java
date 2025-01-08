package view;

import controller.BerkasController;
import model.BerkasKurir;
import model.MyBatisUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;

import java.awt.*;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.awt.Font;

public class BerkasView extends JFrame {

    private JTable berkasTable;
    private JButton addButton, updateButton, deleteButton, loadButton;
    private BerkasController controller;

    public BerkasView() {
        controller = new BerkasController();

        setTitle("Manajemen Berkas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table Panel
        berkasTable = new JTable();
        add(new JScrollPane(berkasTable), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Tambah");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Hapus");
        JButton exportButton = new JButton("Export PDF");

        loadButton = new JButton("Refresh");
        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(kembaliButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> showInputDialog("Tambah", null));
        updateButton.addActionListener(e -> {
            int selectedRow = berkasTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris untuk diupdate!");
                return;
            }
            BerkasKurir selectedBerkas = getSelectedBerkas(selectedRow);
            showInputDialog("Update", selectedBerkas);
        });
        deleteButton.addActionListener(e -> deleteBerkas());
        loadButton.addActionListener(e -> loadData());
        exportButton.addActionListener(e -> exportToPdf());

        // Load data saat aplikasi dimulai
        loadData();

        setVisible(true);
    }

    private void showInputDialog(String action, BerkasKurir berkas) {
        JTextField idBerkasField = new JTextField();
        JComboBox<Integer> idKurirComboBox = new JComboBox<>();
        JTextField idKtpField = new JTextField();
        JTextField idSimField = new JTextField();
        JRadioButton aktifRadioButton = new JRadioButton("Aktif");
        JRadioButton nonAktifRadioButton = new JRadioButton("Non-Aktif");
        ButtonGroup statusButtonGroup = new ButtonGroup();
        statusButtonGroup.add(aktifRadioButton);
        statusButtonGroup.add(nonAktifRadioButton);

        // Load ID Kurir ke ComboBox
        loadKurirData(idKurirComboBox);

        // isi field dengan data yang dipilih
        if (berkas != null) {
            idBerkasField.setText(String.valueOf(berkas.getIdBerkas()));
            idKurirComboBox.setSelectedItem(berkas.getIdKurir());
            idKtpField.setText(berkas.getIdKtp());
            idSimField.setText(berkas.getIdSim());
            if ("Aktif".equals(berkas.getStatus())) {
                aktifRadioButton.setSelected(true);
            } else {
                nonAktifRadioButton.setSelected(true);
            }
        }

        // Panel untuk input form
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("ID Berkas:"));
        inputPanel.add(idBerkasField);
        inputPanel.add(new JLabel("ID Kurir:"));
        inputPanel.add(idKurirComboBox);
        inputPanel.add(new JLabel("ID KTP:"));
        inputPanel.add(idKtpField);
        inputPanel.add(new JLabel("ID SIM:"));
        inputPanel.add(idSimField);
        inputPanel.add(new JLabel("Status:"));
        JPanel statusPanel = new JPanel();
        statusPanel.add(aktifRadioButton);
        statusPanel.add(nonAktifRadioButton);
        inputPanel.add(statusPanel);

        // Tampil dialog
        int option = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                action + " Berkas",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String status = aktifRadioButton.isSelected() ? "Aktif" : "Non-Aktif";
            BerkasKurir newBerkas = new BerkasKurir(
                    berkas == null ? 0 : Integer.parseInt(idBerkasField.getText()),
                    idKtpField.getText(),
                    idSimField.getText(),
                    status,
                    (Integer) idKurirComboBox.getSelectedItem());

            if ("Tambah".equals(action)) {
                controller.insertBerkas(newBerkas);
                JOptionPane.showMessageDialog(this, "Berkas berhasil ditambahkan.");
            } else if ("Update".equals(action)) {
                controller.updateBerkas(newBerkas);
                JOptionPane.showMessageDialog(this, "Berkas berhasil diperbarui.");
            }

            loadData();
        }
    }

    private BerkasKurir getSelectedBerkas(int selectedRow) {
        return new BerkasKurir(
                Integer.parseInt(berkasTable.getValueAt(selectedRow, 0).toString()),
                berkasTable.getValueAt(selectedRow, 2).toString(),
                berkasTable.getValueAt(selectedRow, 3).toString(),
                berkasTable.getValueAt(selectedRow, 4).toString(),
                Integer.parseInt(berkasTable.getValueAt(selectedRow, 1).toString()));
    }

    private void deleteBerkas() {
        int selectedRow = berkasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus!");
            return;
        }
        int idBerkas = Integer.parseInt(berkasTable.getValueAt(selectedRow, 0).toString());
        controller.deleteBerkas(idBerkas);
        JOptionPane.showMessageDialog(this, "Berkas berhasil dihapus.");
        loadData();
    }

    private void loadKurirData(JComboBox<Integer> comboBox) {
        comboBox.removeAllItems();
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            List<Integer> kurirIds = session.selectList("getKurirIds");
            for (Integer id : kurirIds) {
                comboBox.addItem(id);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data kurir: " + ex.getMessage());
        }
    }

    private void loadData() {
        DefaultTableModel model = new DefaultTableModel(
                new String[] { "ID Berkas", "ID Kurir", "ID KTP", "ID SIM", "Status" }, 0);
        List<BerkasKurir> berkasList = controller.getAllBerkas();
        for (BerkasKurir berkas : berkasList) {
            model.addRow(new Object[] {
                    berkas.getIdBerkas(),
                    berkas.getIdKurir(),
                    berkas.getIdKtp(),
                    berkas.getIdSim(),
                    berkas.getStatus()
            });
        }
        berkasTable.setModel(model);
    }
    
    // PDF REPORT
    // PDF REPORT
private void exportToPdf() {
    List<BerkasKurir> berkasList = controller.getAllBerkas();
    if (berkasList.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
        return;
    }

    try {
        // Membuat dokumen PDF
        Document document = new Document(PageSize.A4);
        String outputPath = System.getProperty("user.dir") + "/data_berkas.pdf";
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));

        document.open();
        document.add(new Paragraph("Laporan Data Berkas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        document.add(new Paragraph(" "));

        // Membuat tabel untuk data berkas
        PdfPTable table = new PdfPTable(5); // Lima kolom
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{1f, 1.5f, 2f, 2f, 1.5f});

        // Header tabel
        table.addCell("ID Berkas");
        table.addCell("ID Kurir");
        table.addCell("ID KTP");
        table.addCell("ID SIM");
        table.addCell("Status");

        // Isi tabel dengan data berkas
        for (BerkasKurir berkas : berkasList) {
            table.addCell(String.valueOf(berkas.getIdBerkas()));
            table.addCell(String.valueOf(berkas.getIdKurir()));
            table.addCell(berkas.getIdKtp());
            table.addCell(berkas.getIdSim());
            table.addCell(berkas.getStatus());
        }

        // Menambahkan tabel ke dokumen
        document.add(table);
        document.close();

        // Pesan keberhasilan
        JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan di: " + outputPath);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal membuat laporan: " + e.getMessage());
    }
}

    // Kembali ke main frame
    private void kembaliKeMainFrame() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BerkasView::new);
    }
}
