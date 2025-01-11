package view;

import controller.BerkasController;
import model.BerkasKurir;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class BerkasView extends JFrame {

    private JTable berkasTable;
    private JButton addButton, updateButton, deleteButton;
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
        updateButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");
        JButton exportButton = new JButton("Export PDF");
        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(kembaliButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> addBerkas());
        updateButton.addActionListener(e -> updateBerkas());
        deleteButton.addActionListener(e -> deleteBerkas());
        exportButton.addActionListener(e -> exportToPdf());

        // Load data saat aplikasi dimulai
        refreshTable();

        setVisible(true);
    }

    private void addBerkas() {
        JComboBox<Integer> idKurirComboBox = new JComboBox<>();
        JTextField idKtpField = new JTextField();
        JTextField idSimField = new JTextField();

        loadKurirData(idKurirComboBox);

        Object[] inputFields = {
                "ID Kurir:", idKurirComboBox,
                "ID KTP:", idKtpField,
                "ID SIM:", idSimField
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        int option = JOptionPane.showConfirmDialog(
                this,
                inputFields,
                "Tambah Berkas",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                controller.validateBerkasData(idKtpField.getText(), idSimField.getText());

                BerkasKurir berkas = new BerkasKurir(
                        0, // ID otomatis
                        idKtpField.getText(),
                        idSimField.getText(),
                        "Pending", // Default status
                        (Integer) idKurirComboBox.getSelectedItem());

                controller.insertBerkas(berkas);
                JOptionPane.showMessageDialog(this, "Berkas berhasil ditambahkan!");
                refreshTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateBerkas() {
        int selectedRow = berkasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk diupdate!");
            return;
        }

        BerkasKurir selectedBerkas = getSelectedBerkas(selectedRow);

        JComboBox<Integer> idKurirComboBox = new JComboBox<>();
        JTextField idKtpField = new JTextField(selectedBerkas.getIdKtp());
        JTextField idSimField = new JTextField(selectedBerkas.getIdSim());
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Aktif", "Non-Aktif" });

        loadKurirData(idKurirComboBox);
        idKurirComboBox.setSelectedItem(selectedBerkas.getIdKurir());
        statusComboBox.setSelectedItem(selectedBerkas.getStatus());

        Object[] inputFields = {
                "ID Kurir:", idKurirComboBox,
                "ID KTP:", idKtpField,
                "ID SIM:", idSimField,
                "Status:", statusComboBox
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        int option = JOptionPane.showConfirmDialog(
                this,
                inputFields,
                "Update Berkas",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                controller.validateBerkasData(idKtpField.getText(), idSimField.getText());

                selectedBerkas.setIdKurir((Integer) idKurirComboBox.getSelectedItem());
                selectedBerkas.setIdKtp(idKtpField.getText());
                selectedBerkas.setIdSim(idSimField.getText());
                selectedBerkas.setStatus((String) statusComboBox.getSelectedItem());

                controller.updateBerkas(selectedBerkas);
                JOptionPane.showMessageDialog(this, "Berkas berhasil diperbarui!");
                refreshTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteBerkas() {
        int selectedRow = berkasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus!");
            return;
        }

        int idBerkas = Integer.parseInt(berkasTable.getValueAt(selectedRow, 0).toString());
        int option = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus berkas ini?",
                "Hapus Berkas",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            controller.deleteBerkas(idBerkas);
            JOptionPane.showMessageDialog(this, "Berkas berhasil dihapus!");
            refreshTable();
        }
    }

    private void refreshTable() {
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

    private BerkasKurir getSelectedBerkas(int selectedRow) {
        return new BerkasKurir(
                Integer.parseInt(berkasTable.getValueAt(selectedRow, 0).toString()),
                berkasTable.getValueAt(selectedRow, 2).toString(),
                berkasTable.getValueAt(selectedRow, 3).toString(),
                berkasTable.getValueAt(selectedRow, 4).toString(),
                Integer.parseInt(berkasTable.getValueAt(selectedRow, 1).toString()));
    }

    private void loadKurirData(JComboBox<Integer> comboBox) {
        comboBox.removeAllItems();
        try {
            List<Integer> kurirIds = controller.getKurirIds();
            for (Integer id : kurirIds) {
                comboBox.addItem(id);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            document.add(new Paragraph("Laporan Data Berkas",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" "));

            // Membuat tabel untuk data berkas
            PdfPTable table = new PdfPTable(5); // Lima kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[] { 1f, 1.5f, 2f, 2f, 1.5f });

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

            document.add(table);
            document.close();

            JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan di: " +
                    outputPath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat laporan: " +
                    e.getMessage());
        }
    }

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
