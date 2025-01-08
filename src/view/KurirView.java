package view;

import controller.KurirController;
import model.Kurir;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class KurirView extends JFrame {
    private KurirController controller;

    public KurirView() {
        controller = new KurirController();
        setTitle("Data Kurir");
        setSize(600, 400);
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
        String[] columnNames = { "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image", "status" };
        List<Kurir> kurirList = controller.getAllKurir();
        Object[][] data = new Object[kurirList.size()][8];

        for (int i = 0; i < kurirList.size(); i++) {
            Kurir kurir = kurirList.get(i);
            data[i][0] = kurir.getIdKurir();
            data[i][1] = kurir.getNamaKurir();
            data[i][2] = kurir.getJenisKelamin();
            data[i][3] = kurir.getTanggalLahir();
            data[i][4] = kurir.getNoHP();
            data[i][5] = kurir.getAlamat();
            data[i][6] = kurir.getImage();
            data[i][7] = kurir.getStatus();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addKurir());

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateKurir(table));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteKurir(table));

        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        JPanel footerPanel = new JPanel();
        footerPanel.add(addButton);
        footerPanel.add(updateButton);
        footerPanel.add(deleteButton);
        footerPanel.add(kembaliButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // ADD KURIR
    private void addKurir() {
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
        JRadioButton tolakButton = new JRadioButton("DITolak");
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

        int option = JOptionPane.showConfirmDialog(null, message, "Add Kurir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String jenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String status = terimaButton.isSelected() ? "DISETUJUI" : tolakButton.isSelected() ? "DITOLAK" : "";

            if (jenisKelamin.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan lengkapi semua pilihan!");
                return;
            }

            Kurir kurir = new Kurir(
                    0, // ID akan digenerate oleh database
                    namaField.getText(),
                    jenisKelamin,
                    java.sql.Date.valueOf(tanggalLahirField.getText()),
                    noHPField.getText(),
                    alamatField.getText(),
                    imageField.getText(),
                    status);
            controller.addKurir(kurir);
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");

            refreshTable(getTable());
        }
    }

    // UPDATE
    private void updateKurir(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang akan diupdate.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        JTextField namaField = new JTextField((String) table.getValueAt(selectedRow, 1));
        JRadioButton lakiButton = new JRadioButton("Laki-laki");
        JRadioButton perempuanButton = new JRadioButton("Perempuan");
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
        JTextField noHPField = new JTextField((String) table.getValueAt(selectedRow, 4));
        JTextField alamatField = new JTextField((String) table.getValueAt(selectedRow, 5));
        JTextField imageField = new JTextField((String) table.getValueAt(selectedRow, 6));

        JRadioButton terimaButton = new JRadioButton("DISETUJUI");
        JRadioButton tolakButton = new JRadioButton("DITOLAK");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(terimaButton);
        statusGroup.add(tolakButton);

        String currentStatus = table.getValueAt(selectedRow, 7).toString();
        if ("DISETUJUI".equalsIgnoreCase(currentStatus)) {
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

        int option = JOptionPane.showConfirmDialog(null, message, "Update Kurir", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newJenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String newStatus = terimaButton.isSelected() ? "DISETUJUI" : tolakButton.isSelected() ? "DITOLAK" : "";

            if (newJenisKelamin.isEmpty() || newStatus.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan lengkapi semua pilihan!");
                return;
            }

            Kurir kurir = new Kurir(
                    id,
                    namaField.getText(),
                    newJenisKelamin,
                    java.sql.Date.valueOf(tanggalLahirField.getText()),
                    noHPField.getText(),
                    alamatField.getText(),
                    imageField.getText(),
                    newStatus);
            controller.updateKurir(kurir);
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }

        refreshTable(getTable());
    }

    // REFRESH
    private void refreshTable(JTable table) {
        List<Kurir> kurirList = controller.getAllKurir();
        Object[][] data = new Object[kurirList.size()][8];

        for (int i = 0; i < kurirList.size(); i++) {
            Kurir kurir = kurirList.get(i);
            data[i][0] = kurir.getIdKurir();
            data[i][1] = kurir.getNamaKurir();
            data[i][2] = kurir.getJenisKelamin();
            data[i][3] = kurir.getTanggalLahir();
            data[i][4] = kurir.getNoHP();
            data[i][5] = kurir.getAlamat();
            data[i][6] = kurir.getImage();
            data[i][7] = kurir.getStatus();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {
                "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image", "Status"
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
