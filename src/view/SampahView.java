package view;

import controller.SampahController;
import model.Sampah;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SampahView extends JFrame {

    private JTable sampahTable;
    private JButton addButton, updateButton, deleteButton, loadButton;
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

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);

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

        // Load data saat aplikasi dimulai
        loadData();

        setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SampahView::new);
    }
}
