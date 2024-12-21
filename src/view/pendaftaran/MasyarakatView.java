package view.pendaftaran;

import controller.MasyarakatController;
import model.Masyarakat;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MasyarakatView extends JFrame {
    private MasyarakatController controller;

    public MasyarakatView() {
        controller = new MasyarakatController();
        setTitle("Data Masyarakat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
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
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addMasyarakat());

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateMasyarakat(table));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteMasyarakat(table));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable(table));

        JPanel footerPanel = new JPanel();
        footerPanel.add(addButton);
        footerPanel.add(updateButton);
        footerPanel.add(deleteButton);
        footerPanel.add(refreshButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // ADD MASYARAKAT
    private void addMasyarakat() {
        // Dialog untuk menambah data baru
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

        int option = JOptionPane.showConfirmDialog(null, message, "Add Masyarakat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String jenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String status = terimaButton.isSelected() ? "DISETUJUI" : tolakButton.isSelected() ? "DITOLAK" : "";

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
    }

    // UPDATE
    private void updateMasyarakat(JTable table) {
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

        JRadioButton terimaButton = new JRadioButton("Terima");
        JRadioButton tolakButton = new JRadioButton("Tolak");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(terimaButton);
        statusGroup.add(tolakButton);

        String currentStatus = table.getValueAt(selectedRow, 7).toString();
        if ("Terima".equalsIgnoreCase(currentStatus)) {
            terimaButton.setSelected(true);
        } else if ("Tolak".equalsIgnoreCase(currentStatus)) {
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

        int option = JOptionPane.showConfirmDialog(null, message, "Update Masyarakat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newJenisKelamin = lakiButton.isSelected() ? "Laki-laki"
                    : perempuanButton.isSelected() ? "Perempuan" : "";
            String newStatus = terimaButton.isSelected() ? "Terima" : tolakButton.isSelected() ? "Tolak" : "";

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MasyarakatView view = new MasyarakatView();
            view.setVisible(true);
        });
    }
}
