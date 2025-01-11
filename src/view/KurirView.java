package view;

import controller.KurirController;
import model.Kurir;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.awt.Font;

public class KurirView extends JFrame {

    private KurirController controller;

    public KurirView() {
        controller = new KurirController();
        setTitle("Data Kurir");
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
        JLabel headerLabel = new JLabel("Data Kurir", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "ID", "No Ktp", "No SIM", "NPWP", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP",
                "Alamat", "Image", "status" };
        List<Kurir> kurirList = controller.getAllKurir();
        Object[][] data = new Object[kurirList.size()][11];

        for (int i = 0; i < kurirList.size(); i++) {
            Kurir kurir = kurirList.get(i);
            data[i][0] = kurir.getIdKurir();
            data[i][1] = kurir.getNoKtp();
            data[i][2] = kurir.getNoSim();
            data[i][3] = kurir.getNpwp();
            data[i][4] = kurir.getNamaKurir();
            data[i][5] = kurir.getJenisKelamin();
            data[i][6] = kurir.getTanggalLahir();
            data[i][7] = kurir.getNoHP();
            data[i][8] = kurir.getAlamat();
            data[i][9] = kurir.getImage();
            data[i][10] = kurir.getStatus();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JButton addButton = new JButton("Tambah");
        addButton.addActionListener(e -> addKurir());

        JButton updateButton = new JButton("Edit");
        updateButton.addActionListener(e -> updateKurir(table));

        JButton deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(e -> deleteKurir(table));

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

    // ADD KURIR
    private void addKurir() {
        JTextField noKtpField = new JTextField();
        JTextField noSimField = new JTextField();
        JTextField npwpField = new JTextField();
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

        JRadioButton terimaButton = new JRadioButton("DISETUJUI");
        JRadioButton tolakButton = new JRadioButton("DITOLAK");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(terimaButton);
        statusGroup.add(tolakButton);

        Object[] message = {
                "No KTP:", noKtpField,
                "No SIM:", noSimField,
                "NPWP:", npwpField,
                "Nama:", namaField,
                "Jenis Kelamin:", lakiButton, perempuanButton,
                "Tanggal Lahir (YYYY-MM-DD):", tanggalLahirField,
                "No HP:", noHPField,
                "Alamat:", alamatField,
                "Image Path:", imageField
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        int option = JOptionPane.showConfirmDialog(null, message, "Add Kurir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String jenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String status = terimaButton.isSelected() ? "DISETUJUI" : tolakButton.isSelected() ? "DITOLAK" : "";

            if (jenisKelamin.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan lengkapi semua pilihan!");
                return;
            }

            // Validasi Nama
            if (!namaField.getText().matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf dan spasi!");
                return;
            }

            // Validasi tanggal lahir
            try {
                java.sql.Date.valueOf(tanggalLahirField.getText()); // Validasi format tanggal
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Tanggal lahir harus dalam format YYYY-MM-DD!");
                return;
            }

            // Validasi Nomor HP
            if (!noHPField.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Nomor HP hanya boleh mengandung angka!");
                return;
            }

            Kurir kurir = new Kurir(
                    0, // ID otomatis
                    Integer.parseInt(noKtpField.getText()), // No KTP
                    Integer.parseInt(noSimField.getText()), // No SIM
                    Integer.parseInt(npwpField.getText()), // NPWP
                    namaField.getText(),
                    jenisKelamin,
                    java.sql.Date.valueOf(tanggalLahirField.getText()),
                    noHPField.getText(),
                    alamatField.getText(),
                    imageField.getText(),
                    "PENDING");
            controller.addKurir(kurir);
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
        }
        refreshTable(getTable());
    }

    // UPDATE
    private void updateKurir(JTable table) {
        UIManager.put("OptionPane.okButtonText", "OK");
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan diupdate.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        JTextField noKtpField = new JTextField(String.valueOf(table.getValueAt(selectedRow, 1)));
        JTextField noSimField = new JTextField(String.valueOf(table.getValueAt(selectedRow, 2)));
        JTextField npwpField = new JTextField(String.valueOf(table.getValueAt(selectedRow, 3)));
        JTextField namaField = new JTextField((String) table.getValueAt(selectedRow, 4));
        JRadioButton lakiButton = new JRadioButton("Laki-laki");
        JRadioButton perempuanButton = new JRadioButton("Perempuan");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(lakiButton);
        genderGroup.add(perempuanButton);

        String jenisKelamin = (String) table.getValueAt(selectedRow, 5);
        if ("Laki-laki".equals(jenisKelamin)) {
            lakiButton.setSelected(true);
        } else if ("Perempuan".equals(jenisKelamin)) {
            perempuanButton.setSelected(true);
        }

        JTextField tanggalLahirField = new JTextField(table.getValueAt(selectedRow, 6).toString());
        JTextField noHPField = new JTextField((String) table.getValueAt(selectedRow, 7));
        JTextField alamatField = new JTextField((String) table.getValueAt(selectedRow, 8));
        JTextField imageField = new JTextField((String) table.getValueAt(selectedRow, 9));

        JRadioButton terimaButton = new JRadioButton("DISETUJUI");
        JRadioButton tolakButton = new JRadioButton("DITOLAK");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(terimaButton);
        statusGroup.add(tolakButton);

        String currentStatus = table.getValueAt(selectedRow, 10).toString();
        if ("DISETUJUI".equalsIgnoreCase(currentStatus)) {
            terimaButton.setSelected(true);
        } else if ("DITOLAK".equalsIgnoreCase(currentStatus)) {
            tolakButton.setSelected(true);
        }

        Object[] message = {
                "No KTP:", noKtpField,
                "No SIM:", noSimField,
                "NPWP:", npwpField,
                "Nama:", namaField,
                "Jenis Kelamin:", lakiButton, perempuanButton,
                "Tanggal Lahir (YYYY-MM-DD):", tanggalLahirField,
                "No HP:", noHPField,
                "Alamat:", alamatField,
                "Image Path:", imageField,
                "Status:", terimaButton, tolakButton
        };

        UIManager.put("OptionPane.okButtonText", "Simpan");
        int option = JOptionPane.showConfirmDialog(null, message, "Update Kurir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newJenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String newStatus = terimaButton.isSelected() ? "DISETUJUI" : tolakButton.isSelected() ? "DITOLAK" : "";

            if (newJenisKelamin.isEmpty() || newStatus.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan lengkapi semua pilihan!");
                return;
            }

            // Validasi Nama
            if (!namaField.getText().matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf dan spasi!");
                return;
            }

            // Validasi tanggal lahir
            try {
                java.sql.Date.valueOf(tanggalLahirField.getText()); // Validasi format tanggal
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Tanggal lahir harus dalam format YYYY-MM-DD!");
                return;
            }

            // Validasi Nomor HP
            if (!noHPField.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Nomor HP hanya boleh mengandung angka!");
                return;
            }

            // Validasi angka untuk noKTP, noSIM, dan NPWP
            try {
                int noKtp = Integer.parseInt(noKtpField.getText());
                int noSim = Integer.parseInt(noSimField.getText());
                int npwp = Integer.parseInt(npwpField.getText());

                // Membuat objek Kurir
                Kurir kurir = new Kurir(
                        id,
                        noKtp,
                        noSim,
                        npwp,
                        namaField.getText(),
                        newJenisKelamin,
                        java.sql.Date.valueOf(tanggalLahirField.getText()),
                        noHPField.getText(),
                        alamatField.getText(),
                        imageField.getText(),
                        newStatus);

                // Update data ke database
                controller.updateKurir(kurir);
                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "No KTP, No SIM, dan NPWP harus berupa angka!");
                return;
            }
        }

        refreshTable(table);
    }

    // REFRESH
    private void refreshTable(JTable table) {
        List<Kurir> kurirList = controller.getAllKurir();
        Object[][] data = new Object[kurirList.size()][11];

        for (int i = 0; i < kurirList.size(); i++) {
            Kurir kurir = kurirList.get(i);
            data[i][0] = kurir.getIdKurir();
            data[i][1] = kurir.getNoKtp();
            data[i][2] = kurir.getNoSim();
            data[i][3] = kurir.getNpwp();
            data[i][4] = kurir.getNamaKurir();
            data[i][5] = kurir.getJenisKelamin();
            data[i][6] = kurir.getTanggalLahir();
            data[i][7] = kurir.getNoHP();
            data[i][8] = kurir.getAlamat();
            data[i][9] = kurir.getImage();
            data[i][10] = kurir.getStatus();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {
                "ID", "No KTP", "No SIM", "NPWP", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image",
                "Status"
        }));
    }

    // DELETE
    private void deleteKurir(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan dihapus.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?",
                "Delete Kurir", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            controller.deleteKurir(id);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            refreshTable(table);
        }
    }

    // PDF REPORT
    private void exportToPdf() {
        List<Kurir> kurirList = controller.getAllKurir();
        if (kurirList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
            return;
        }

        try {
            Document document = new Document(PageSize.A4);
            String outputPath = System.getProperty("user.dir") + "/data_kurir.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            document.open();
            document.add(new Paragraph("Laporan Data Kurir",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8); // Delapan kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[] { 1f, 2f, 1.5f, 2f, 2f, 2f, 2f, 1f });

            // Header tabel
            table.addCell("ID");
            table.addCell("Nama");
            table.addCell("Jenis Kelamin");
            table.addCell("Tanggal Lahir");
            table.addCell("No HP");
            table.addCell("Alamat");
            table.addCell("Image Path");
            table.addCell("Status");

            // Isi tabel
            for (Kurir kurir : kurirList) {
                table.addCell(String.valueOf(kurir.getIdKurir()));
                table.addCell(kurir.getNamaKurir());
                table.addCell(kurir.getJenisKelamin());
                table.addCell(kurir.getTanggalLahir().toString());
                table.addCell(kurir.getNoHP());
                table.addCell(kurir.getAlamat());
                table.addCell(kurir.getImage());
                table.addCell(kurir.getStatus());
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
            KurirView view = new KurirView();
            view.setVisible(true);
        });
    }
}
