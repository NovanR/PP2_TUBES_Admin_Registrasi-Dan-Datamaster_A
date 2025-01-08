package view;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import controller.SampahController;
import model.Sampah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.List;

public class SampahView extends JFrame {

    private JTable sampahTable;
    private JButton addButton, updateButton, deleteButton, loadButton,kembaliButton, exportPdfButton;
    private SampahController controller;

    public SampahView() {
        controller = new SampahController();

        setTitle("Manajemen Sampah");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table Panel
        sampahTable = new JTable();
        add(new JScrollPane(sampahTable), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Tambah");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Hapus");
        loadButton = new JButton("Refresh");
        exportPdfButton = new JButton("Export PDF");
        kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exportPdfButton);
        buttonPanel.add(kembaliButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> showInputDialog("Tambah", null));
        updateButton.addActionListener(e -> {
            int selectedRow = sampahTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris untuk diupdate!");
                return;
            }
            Sampah selectedSampah = getSelectedSampah(selectedRow);
            showInputDialog("Update", selectedSampah);
        });
        deleteButton.addActionListener(e -> deleteSampah());
        loadButton.addActionListener(e -> loadData());
        exportPdfButton.addActionListener(e -> exportToPdf()); // Tambahkan listener untuk ekspor PDF

        // Load data saat aplikasi dimulai
        loadData();

        setVisible(true);
    }

    private void exportToPdf() {
        List<Sampah> sampahList = controller.getAllSampah();
        if (sampahList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
            return;
        }

        try {
            Document document = new Document(PageSize.A4);
            String outputPath = System.getProperty("user.dir") + "/data_sampah.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            document.open();
            document.add(new Paragraph("Laporan Data Sampah", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(4); // Empat kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[] {1f, 2f, 2f, 1f});

            // Header tabel
            table.addCell("ID Sampah");
            table.addCell("Kategori Sampah");
            table.addCell("Jenis Sampah");
            table.addCell("ID TPS");

            // Isi tabel
            for (Sampah sampah : sampahList) {
                table.addCell(String.valueOf(sampah.getIdSampah()));
                table.addCell(sampah.getKategoriSampah());
                table.addCell(sampah.getJenisSampah());
                table.addCell(String.valueOf(sampah.getIdTps()));
            }

            document.add(table);
            document.close();

            JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan di: " + outputPath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat laporan: " + e.getMessage());
        }
    }

    private void showInputDialog(String action, Sampah sampah) {
        JTextField idSampahField = new JTextField();
        JTextField kategoriField = new JTextField();
        JTextField jenisField = new JTextField();
        JComboBox<Integer> idTpsDropdown = new JComboBox<>();

        // Load ID TPS ke ComboBox
        loadTpsData(idTpsDropdown);

        // isi field dengan data yang dipilih
        if (sampah != null) {
            idSampahField.setText(String.valueOf(sampah.getIdSampah()));
            kategoriField.setText(sampah.getKategoriSampah());
            jenisField.setText(sampah.getJenisSampah());
            idTpsDropdown.setSelectedItem(sampah.getIdTps());
        }

        // Panel untuk input form
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("ID Sampah:"));
        inputPanel.add(idSampahField);
        inputPanel.add(new JLabel("Kategori Sampah:"));
        inputPanel.add(kategoriField);
        inputPanel.add(new JLabel("Jenis Sampah:"));
        inputPanel.add(jenisField);
        inputPanel.add(new JLabel("ID TPS:"));
        inputPanel.add(idTpsDropdown);

        // Tampil dialog
        int option = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                action + " Sampah",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int idSampah = Integer.parseInt(idSampahField.getText());
                String kategori = kategoriField.getText();
                String jenis = jenisField.getText();
                int idTps = (Integer) idTpsDropdown.getSelectedItem();

                Sampah newSampah = new Sampah(idSampah, kategori, jenis, idTps);

                if ("Tambah".equals(action)) {
                    controller.insertSampah(newSampah);
                    JOptionPane.showMessageDialog(this, "Sampah berhasil ditambahkan.");
                } else if ("Update".equals(action)) {
                    controller.updateSampah(newSampah);
                    JOptionPane.showMessageDialog(this, "Sampah berhasil diperbarui.");
                }

                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private Sampah getSelectedSampah(int selectedRow) {
        return new Sampah(
                Integer.parseInt(sampahTable.getValueAt(selectedRow, 0).toString()),
                sampahTable.getValueAt(selectedRow, 1).toString(),
                sampahTable.getValueAt(selectedRow, 2).toString(),
                Integer.parseInt(sampahTable.getValueAt(selectedRow, 3).toString()));
    }

    private void deleteSampah() {
        int selectedRow = sampahTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus!");
            return;
        }
        int idSampah = Integer.parseInt(sampahTable.getValueAt(selectedRow, 0).toString());
        controller.deleteSampah(idSampah);
        JOptionPane.showMessageDialog(this, "Sampah berhasil dihapus.");
        loadData();
    }

    private void loadData() {
        DefaultTableModel model = new DefaultTableModel(
                new String[] { "ID Sampah", "Kategori Sampah", "Jenis Sampah", "ID TPS" }, 0);
        List<Sampah> sampahList = controller.getAllSampah();
        for (Sampah sampah : sampahList) {
            model.addRow(new Object[] {
                    sampah.getIdSampah(),
                    sampah.getKategoriSampah(),
                    sampah.getJenisSampah(),
                    sampah.getIdTps()
            });
        }
        sampahTable.setModel(model);
    }

    private void loadTpsData(JComboBox<Integer> comboBox) {
        comboBox.removeAllItems();

        List<Integer> tpsList = controller.getAllTpsIds();
        for (Integer idTps : tpsList) {
            comboBox.addItem(idTps);
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
        SwingUtilities.invokeLater(SampahView::new);
    }
}
