package view;

import controller.MasyarakatController;
import model.Masyarakat;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.awt.Font;

public class MasyarakatView extends JFrame {

    private MasyarakatController controller;

    public MasyarakatView() {
        controller = new MasyarakatController();
        setTitle("Data Masyarakat");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private JTable getTable() {
        // OTOMATIS REFRESH
        JPanel mainPanel = (JPanel) getContentPane().getComponent(0);
        JScrollPane scrollPane = (JScrollPane) mainPanel.getComponent(1);
        return (JTable) scrollPane.getViewport().getView();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Data Masyarakat", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image", "Status" };
        List<Masyarakat> masyarakatList = controller.getAllMasyarakat();
        Object[][] data = new Object[masyarakatList.size()][8];

        for (int i = 0; i < masyarakatList.size(); i++) {
            Masyarakat masyarakat = masyarakatList.get(i);
            data[i][0] = masyarakat.getIdMasyarakat();
            data[i][1] = masyarakat.getNamaMasyarakat();
            data[i][2] = masyarakat.getJenisKelamin();
            data[i][3] = masyarakat.getTanggalLahir();
            data[i][4] = masyarakat.getNoHP();
            data[i][5] = masyarakat.getAlamat();
            data[i][6] = masyarakat.getImage();
            data[i][7] = masyarakat.getStatus();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JButton addButton = new JButton("Tambah");
        addButton.addActionListener(e -> addMasyarakat());

        JButton updateButton = new JButton("Edit");
        updateButton.addActionListener(e -> updateMasyarakat(table));

        JButton deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(e -> deleteMasyarakat(table));

        JButton exportPdfButton = new JButton("Export PDF");
        exportPdfButton.addActionListener(e -> exportToPdf());

        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        JPanel footerPanel = new JPanel();
        footerPanel.add(addButton);
        footerPanel.add(updateButton);
        footerPanel.add(deleteButton);
        footerPanel.add(exportPdfButton);
        footerPanel.add(kembaliButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // ADD MASYARAKAT
    private void addMasyarakat() {
        // menambah data baru
        JTextField namaField = new JTextField();
        JRadioButton lakiButton = new JRadioButton("Laki-laki");
        JRadioButton perempuanButton = new JRadioButton("Perempuan");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiButton);
        genderGroup.add(perempuanButton);

        JTextField tanggalLahirField = new JTextField();
        JTextField noHPField = new JTextField();
        JTextField alamatField = new JTextField();
        JTextField imageField = new JTextField();

        JRadioButton terimaButton = new JRadioButton("Terima");
        JRadioButton tolakButton = new JRadioButton("Tolak");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(terimaButton);
        statusGroup.add(tolakButton);

        Object[] message = {
                "Nama:", namaField,
                "Jenis Kelamin:", lakiButton, perempuanButton,
                "Tanggal Lahir (YYYY-MM-DD):", tanggalLahirField,
                "No HP:", noHPField,
                "Alamat:", alamatField,
                "Image Path:", imageField,
                "Status:", terimaButton, tolakButton
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        UIManager.put("OptionPane.cancelButtonText", "Batal");
        int option = JOptionPane.showConfirmDialog(null, message, "Add Masyarakat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String jenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String status = terimaButton.isSelected() ? "DISETUJUI" : tolakButton.isSelected() ? "DITOLAK" : "";

            if (!namaField.getText().matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf dan spasi!");
                return;
            }

            // Validasi tanggal lahir
            String tanggalLahirInput = tanggalLahirField.getText();
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(tanggalLahirInput);
                if (date.getMonthValue() < 1 || date.getMonthValue() > 12) {
                    JOptionPane.showMessageDialog(null, "Bulan yang dimasukkan tidak valid (harus antara 1-12)!");
                    return;
                }
            } catch (java.time.format.DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Format tanggal lahir tidak valid! Gunakan format YYYY-MM-DD.");
                return;
            }

            // Validasi nomor HP
            if (!noHPField.getText().matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(null, "Nomor HP hanya boleh mengandung angka!");
                return;
            }

            if (jenisKelamin.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan lengkapi semua pilihan!");
                return;
            }

            Masyarakat masyarakat = new Masyarakat(
                    0, // ID akan digenerate oleh database
                    namaField.getText(),
                    jenisKelamin,
                    java.sql.Date.valueOf(tanggalLahirField.getText()),
                    noHPField.getText(),
                    alamatField.getText(),
                    imageField.getText(),
                    status);
            controller.addMasyarakat(masyarakat);
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
        }

        refreshTable(getTable());
    }

    // UPDATE
    private void updateMasyarakat(JTable table) {
        int selectedRow = table.getSelectedRow();
        UIManager.put("OptionPane.okButtonText", "OK");
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan diupdate.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        JTextField namaField = new JTextField((String) table.getValueAt(selectedRow, 1));
        namaField.setEnabled(false);

        JRadioButton lakiButton = new JRadioButton("Laki-laki");
        lakiButton.setEnabled(false);

        JRadioButton perempuanButton = new JRadioButton("Perempuan");
        perempuanButton.setEnabled(false);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiButton);
        genderGroup.add(perempuanButton);

        String jenisKelamin = (String) table.getValueAt(selectedRow, 2);
        if ("Laki-laki".equals(jenisKelamin)) {
            lakiButton.setSelected(true);
        } else if ("Perempuan".equals(jenisKelamin)) {
            perempuanButton.setSelected(true);
        }

        JTextField tanggalLahirField = new JTextField(table.getValueAt(selectedRow, 3).toString());
        tanggalLahirField.setEnabled(false);

        JTextField noHPField = new JTextField((String) table.getValueAt(selectedRow, 4));
        noHPField.setEnabled(false);

        JTextField alamatField = new JTextField((String) table.getValueAt(selectedRow, 5));
        alamatField.setEnabled(false);

        JTextField imageField = new JTextField((String) table.getValueAt(selectedRow, 6));
        imageField.setEnabled(false);

        JRadioButton terimaButton = new JRadioButton("DITERIMA");
        JRadioButton tolakButton = new JRadioButton("DITOLAK");
    

        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(terimaButton);
        statusGroup.add(tolakButton);

        String currentStatus = table.getValueAt(selectedRow, 7).toString();
        if ("DITERIMA".equalsIgnoreCase(currentStatus)) {
            terimaButton.setSelected(true);
        } else if ("DITOLAK".equalsIgnoreCase(currentStatus)) {
            tolakButton.setSelected(true);
        }

        Object[] message = {
                "Nama:", namaField,
                "Jenis Kelamin:", lakiButton, perempuanButton,
                "Tanggal Lahir (YYYY-MM-DD):", tanggalLahirField,
                "No HP:", noHPField,
                "Alamat:", alamatField,
                "Image Path:", imageField,
                "Status:", terimaButton, tolakButton
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        UIManager.put("OptionPane.cancelButtonText", "Batal");
        int option = JOptionPane.showConfirmDialog(null, message, "Update Masyarakat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newJenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String newStatus = terimaButton.isSelected() ? "DITERIMA" : tolakButton.isSelected() ? "DITOLAK" : "";

            // Validasi input nama
            if (!namaField.getText().matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf dan spasi!");
                return;
            }

            // Validasi tanggal lahir
            String tanggalLahirInput = tanggalLahirField.getText();
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(tanggalLahirInput);
                if (date.getMonthValue() < 1 || date.getMonthValue() > 12) {
                    JOptionPane.showMessageDialog(null, "Bulan yang dimasukkan tidak valid (harus antara 1-12)!");
                    return;
                }
            } catch (java.time.format.DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Format tanggal lahir tidak valid! Gunakan format YYYY-MM-DD.");
                return;
            }

            // Validasi nomor HP
            if (!noHPField.getText().matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(null, "Nomor HP hanya boleh mengandung angka!");
                return;
            }

            if (newJenisKelamin.isEmpty() || newStatus.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan lengkapi semua pilihan!");
                return;
            }

            Masyarakat masyarakat = new Masyarakat(
                    id,
                    namaField.getText(),
                    newJenisKelamin,
                    java.sql.Date.valueOf(tanggalLahirField.getText()),
                    noHPField.getText(),
                    alamatField.getText(),
                    imageField.getText(),
                    newStatus);
            controller.updateMasyarakat(masyarakat);
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }

        refreshTable(table);
    }

    // REFRESH
    private void refreshTable(JTable table) {
        List<Masyarakat> masyarakatList = controller.getAllMasyarakat();
        Object[][] data = new Object[masyarakatList.size()][8];

        for (int i = 0; i < masyarakatList.size(); i++) {
            Masyarakat masyarakat = masyarakatList.get(i);
            data[i][0] = masyarakat.getIdMasyarakat();
            data[i][1] = masyarakat.getNamaMasyarakat();
            data[i][2] = masyarakat.getJenisKelamin();
            data[i][3] = masyarakat.getTanggalLahir();
            data[i][4] = masyarakat.getNoHP();
            data[i][5] = masyarakat.getAlamat();
            data[i][6] = masyarakat.getImage();
            data[i][7] = masyarakat.getStatus();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {
                "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image", "Status"
        }));
    }

    // DELETE
    private void deleteMasyarakat(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan dihapus.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?",
                "Delete Masyarakat", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            controller.deleteMasyarakat(id);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            refreshTable(table);
        }
    }

    // PDF REPORT
    private void exportToPdf() {
    List<Masyarakat> masyarakatList = controller.getAllMasyarakat();
    if (masyarakatList.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
    return;
    }

    try {
    Document document = new Document(PageSize.A4);
    String outputPath = System.getProperty("user.dir") + "/data_masyarakat.pdf";
    PdfWriter.getInstance(document, new FileOutputStream(outputPath));

    document.open();
    document.add(new Paragraph("Laporan Data Masyarakat",
    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(8); // Delapan kolom
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(10f);
    table.setWidths(new float[] { 1f, 2f, 1.5f, 1.5f, 2f, 3f, 2f, 1.5f });

    // Header tabel
    table.addCell("ID");
    table.addCell("Nama");
    table.addCell("Jenis Kelamin");
    table.addCell("Tanggal Lahir");
    table.addCell("No HP");
    table.addCell("Alamat");
    table.addCell("Image");
    table.addCell("Status");

    // Isi tabel
    for (Masyarakat masyarakat : masyarakatList) {
    table.addCell(String.valueOf(masyarakat.getIdMasyarakat()));
    table.addCell(masyarakat.getNamaMasyarakat());
    table.addCell(masyarakat.getJenisKelamin());
    table.addCell(masyarakat.getTanggalLahir().toString());
    table.addCell(masyarakat.getNoHP());
    table.addCell(masyarakat.getAlamat());
    table.addCell(masyarakat.getImage());
    table.addCell(masyarakat.getStatus());
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
            MasyarakatView view = new MasyarakatView();
            view.setVisible(true);
        });
    }
}
