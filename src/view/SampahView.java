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
    private JButton addButton, updateButton, deleteButton, kembaliButton, exportPdfButton;
    private SampahController controller;

    public SampahView() {
        controller = new SampahController();

        setTitle("Manajemen Sampah");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table Panel
        sampahTable = new JTable();
        add(new JScrollPane(sampahTable), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Tambah");
        updateButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");
        exportPdfButton = new JButton("Export PDF");
        kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportPdfButton);
        buttonPanel.add(kembaliButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> addSampah());
        updateButton.addActionListener(e -> updateSampah());
        deleteButton.addActionListener(e -> deleteSampah());
        exportPdfButton.addActionListener(e -> exportToPdf());

        // Load data saat aplikasi dimulai
        refreshTable();

        setVisible(true);
    }

    private void addSampah() {
        JTextField kategoriField = new JTextField();
        JTextField jenisField = new JTextField();
        JComboBox<Integer> idTpsDropdown = new JComboBox<>();

        loadTpsData(idTpsDropdown);

        Object[] inputFields = {
                "Kategori Sampah:", kategoriField,
                "Jenis Sampah:", jenisField,
                "ID TPS:", idTpsDropdown
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        int option = JOptionPane.showConfirmDialog(
                this,
                inputFields,
                "Tambah Sampah",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validasi input
                if (!kategoriField.getText().matches("[a-zA-Z\\s]+$")) {
                    JOptionPane.showMessageDialog(this, "Kategori sampah hanya boleh mengandung huruf dan spasi!");
                    return;
                }

                if (!jenisField.getText().matches("[a-zA-Z\\s]+$")) {
                    JOptionPane.showMessageDialog(this, "Jenis sampah hanya boleh mengandung huruf dan spasi!");
                    return;
                }

                if (idTpsDropdown.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Pilih ID TPS!");
                    return;
                }

                // Buat objek Sampah baru
                Sampah sampah = new Sampah(
                        0, // ID otomatis
                        kategoriField.getText(),
                        jenisField.getText(),
                        (Integer) idTpsDropdown.getSelectedItem());

                controller.insertSampah(sampah);
                JOptionPane.showMessageDialog(this, "Sampah berhasil ditambahkan!");
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void updateSampah() {
        int selectedRow = sampahTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk diupdate!");
            return;
        }

        Sampah selectedSampah = getSelectedSampah(selectedRow);

        JTextField kategoriField = new JTextField(selectedSampah.getKategoriSampah());
        JTextField jenisField = new JTextField(selectedSampah.getJenisSampah());
        JComboBox<Integer> idTpsDropdown = new JComboBox<>();

        loadTpsData(idTpsDropdown);
        idTpsDropdown.setSelectedItem(selectedSampah.getIdTps());

        Object[] inputFields = {
                "Kategori Sampah:", kategoriField,
                "Jenis Sampah:", jenisField,
                "ID TPS:", idTpsDropdown
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        int option = JOptionPane.showConfirmDialog(
                this,
                inputFields,
                "Edit Sampah",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validasi input
                if (!kategoriField.getText().matches("[a-zA-Z\\s]+$")) {
                    JOptionPane.showMessageDialog(this, "Kategori sampah hanya boleh mengandung huruf dan spasi!");
                    return;
                }

                if (!jenisField.getText().matches("[a-zA-Z\\s]+$")) {
                    JOptionPane.showMessageDialog(this, "Jenis sampah hanya boleh mengandung huruf dan spasi!");
                    return;
                }

                if (idTpsDropdown.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Pilih ID TPS!");
                    return;
                }

                // Perbarui objek Sampah
                selectedSampah.setKategoriSampah(kategoriField.getText());
                selectedSampah.setJenisSampah(jenisField.getText());
                selectedSampah.setIdTps((Integer) idTpsDropdown.getSelectedItem());

                controller.updateSampah(selectedSampah);
                JOptionPane.showMessageDialog(this, "Sampah berhasil diperbarui!");
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void deleteSampah() {
        int selectedRow = sampahTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus!");
            return;
        }

        int idSampah = Integer.parseInt(sampahTable.getValueAt(selectedRow, 0).toString());
        int option = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Hapus Sampah",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            controller.deleteSampah(idSampah);
            JOptionPane.showMessageDialog(this, "Sampah berhasil dihapus!");
            refreshTable();
        }
    }

    private void refreshTable() {
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

    private Sampah getSelectedSampah(int selectedRow) {
        return new Sampah(
                Integer.parseInt(sampahTable.getValueAt(selectedRow, 0).toString()),
                sampahTable.getValueAt(selectedRow, 1).toString(),
                sampahTable.getValueAt(selectedRow, 2).toString(),
                Integer.parseInt(sampahTable.getValueAt(selectedRow, 3).toString()));
    }

    private void loadTpsData(JComboBox<Integer> comboBox) {
        comboBox.removeAllItems();

        List<Integer> tpsList = controller.getAllTpsIds();
        for (Integer idTps : tpsList) {
            comboBox.addItem(idTps);
        }
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
            document.add(new Paragraph("Laporan Data Sampah",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4); // Empat kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[] { 1f, 2f, 2f, 1f });

            table.addCell("ID Sampah");
            table.addCell("Kategori Sampah");
            table.addCell("Jenis Sampah");
            table.addCell("ID TPS");

            for (Sampah sampah : sampahList) {
                table.addCell(String.valueOf(sampah.getIdSampah()));
                table.addCell(sampah.getKategoriSampah());
                table.addCell(sampah.getJenisSampah());
                table.addCell(String.valueOf(sampah.getIdTps()));
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
        SwingUtilities.invokeLater(SampahView::new);
    }
}
