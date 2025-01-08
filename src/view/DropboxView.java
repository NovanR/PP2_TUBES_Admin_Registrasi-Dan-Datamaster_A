package view;

import controller.DropboxController;
import model.Dropbox;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.awt.Font;

public class DropboxView extends JFrame {

    private DropboxController controller;

    public DropboxView() {
        controller = new DropboxController();
        setTitle("Data Dropbox");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Data Dropbox", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Nama", "No HP", "Alamat"};
        JTable table = new JTable();
        refreshTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JButton addButton = new JButton("Tambah");
        addButton.addActionListener(e -> {
            addDropbox();
            refreshTable(table);
        });

        JButton updateButton = new JButton("Edit");
        updateButton.addActionListener(e -> {
            updateDropbox(table);
            refreshTable(table);
        });

        JButton deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(e -> {
            deleteDropbox(table);
            refreshTable(table);
        });

        JButton exportPdfButton = new JButton("Ekspor PDF");
        exportPdfButton.addActionListener(e -> exportToPdf());
        // JButton refreshButton = new JButton("Refresh");
        // refreshButton.addActionListener(e -> refreshTable(table));

        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        JPanel footerPanel = new JPanel();
        footerPanel.add(addButton);
        footerPanel.add(updateButton);
        footerPanel.add(deleteButton);
        footerPanel.add(exportPdfButton);

        // footerPanel.add(refreshButton);
        footerPanel.add(kembaliButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // ADD DROPBOX
    private void addDropbox() {
        JTextField namaField = new JTextField();
        JTextField noHPField = new JTextField();
        JTextField alamatField = new JTextField();

        Object[] message = {
            "Nama:", namaField,
            "No HP:", noHPField,
            "Alamat:", alamatField,};

        int option = JOptionPane.showConfirmDialog(null, message, "Add Dropbox", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Validasi Nama
            if (!namaField.getText().matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf dan spasi!");
                return;
            }

            // Validasi No HP
            if (!noHPField.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "No HP hanya boleh berupa angka!");
                return;
            }

            try {
                Dropbox dropbox = new Dropbox(
                        0, // ID akan digenerate oleh database
                        namaField.getText(),
                        noHPField.getText(),
                        alamatField.getText());
                controller.addDropbox(dropbox);
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan data: " + e.getMessage());
            }
        }
    }

    // UPDATE DROPBOX
    private void updateDropbox(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan diupdate.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        JTextField namaField = new JTextField((String) table.getValueAt(selectedRow, 1));
        JTextField noHPField = new JTextField((String) table.getValueAt(selectedRow, 2));
        JTextField alamatField = new JTextField((String) table.getValueAt(selectedRow, 3));

        Object[] message = {
            "Nama:", namaField,
            "No HP:", noHPField,
            "Alamat:", alamatField,};

        int option = JOptionPane.showConfirmDialog(null, message, "Update Dropbox", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {

            // Validasi Nama
            if (!namaField.getText().matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf dan spasi!");
                return;
            }

            // Validasi No HP
            if (!noHPField.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "No HP hanya boleh berupa angka!");
                return;
            }
            try {
                Dropbox dropbox = new Dropbox(
                        id,
                        namaField.getText(),
                        noHPField.getText(),
                        alamatField.getText());
                controller.updateDropbox(dropbox);
                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui data: " + e.getMessage());
            }
        }
    }

    // REFRESH TABLE
    private void refreshTable(JTable table) {
        try {
            List<Dropbox> dropboxList = controller.getAllDropbox();
            if (dropboxList == null) {
                JOptionPane.showMessageDialog(null, "Gagal memuat data: data null.");
                return;
            }

            Object[][] data = new Object[dropboxList.size()][4];
            for (int i = 0; i < dropboxList.size(); i++) {
                Dropbox dropbox = dropboxList.get(i);
                data[i][0] = dropbox.getIdTps();
                data[i][1] = dropbox.getNamaTps();
                data[i][2] = dropbox.getNoHpTps();
                data[i][3] = dropbox.getAlamatTps();
            }

            table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{
                "ID", "Nama", "No HP", "Alamat"
            }));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
    }

    // DELETE DROPBOX
    private void deleteDropbox(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan dihapus.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?",
                "Delete Dropbox", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                controller.deleteDropbox(id);
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }

    // PDF REPORT
    private void exportToPdf() {
        List<Dropbox> dropboxList = controller.getAllDropbox();
        if (dropboxList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
            return;
        }

        try {
            Document document = new Document(PageSize.A4);
            String outputPath = System.getProperty("user.dir") + "/data_dropbox.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            document.open();
            document.add(new Paragraph("Laporan Data Dropbox", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4); // Empat kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{1f, 2f, 2f, 2f});

            // Header tabel
            table.addCell("ID");
            table.addCell("Nama");
            table.addCell("No HP");
            table.addCell("Alamat");

            // Isi tabel
            for (Dropbox dropbox : dropboxList) {
                table.addCell(String.valueOf(dropbox.getIdTps()));
                table.addCell(dropbox.getNamaTps());
                table.addCell(dropbox.getNoHpTps());
                table.addCell(dropbox.getAlamatTps());
            }

            document.add(table);
            document.close();

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
        SwingUtilities.invokeLater(() -> {
            DropboxView view = new DropboxView();
            view.setVisible(true);
        });
    }
}
