package view;

import controller.BerkasController;
import model.BerkasKurir;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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
        loadButton = new JButton("Refresh");
        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
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

        // Load data saat aplikasi dimulai
        loadData();

        setVisible(true);
    }

    private void showInputDialog(String action, BerkasKurir berkas) {
        JComboBox<Integer> idKurirComboBox = new JComboBox<>();
        JTextField idKtpField = new JTextField();
        JTextField idSimField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Aktif", "Non-Aktif" });

        // Load ID Kurir ke ComboBox
        loadKurirData(idKurirComboBox);

        // Isi field dengan data yang dipilih
        if (berkas != null) {
            idKurirComboBox.setSelectedItem(berkas.getIdKurir());
            idKtpField.setText(berkas.getIdKtp());
            idSimField.setText(berkas.getIdSim());
            statusComboBox.setSelectedItem(berkas.getStatus());
        }

        // Panel untuk input form
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("ID Kurir:"));
        inputPanel.add(idKurirComboBox);
        inputPanel.add(new JLabel("ID KTP:"));
        inputPanel.add(idKtpField);
        inputPanel.add(new JLabel("ID SIM:"));
        inputPanel.add(idSimField);

        if ("Update".equals(action)) { // input status hanya untuk Update
            inputPanel.add(new JLabel("Status:"));
            inputPanel.add(statusComboBox);
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                action + " Berkas",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validasi data menggunakan controller
                controller.validateBerkasData(idKtpField.getText(), idSimField.getText());

                String status = "Pending"; // Default
                if ("Update".equals(action)) {
                    status = (String) statusComboBox.getSelectedItem();
                }

                BerkasKurir newBerkas = new BerkasKurir(
                        0, // ID Berkas otomatis
                        idKtpField.getText(),
                        idSimField.getText(),
                        status,
                        (Integer) idKurirComboBox.getSelectedItem());

                if ("Tambah".equals(action)) {
                    controller.insertBerkas(newBerkas);
                    JOptionPane.showMessageDialog(this, "Berkas berhasil ditambahkan");
                } else if ("Update".equals(action)) {
                    newBerkas.setIdBerkas(berkas.getIdBerkas());
                    controller.updateBerkas(newBerkas);
                    JOptionPane.showMessageDialog(this, "Berkas berhasil diperbarui.");
                }

                loadData();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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
        try {
            List<Integer> kurirIds = controller.getKurirIds();
            for (Integer id : kurirIds) {
                comboBox.addItem(id);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
