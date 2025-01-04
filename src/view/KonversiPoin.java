package view;

import controller.KonversiPoinController;
import model.Poin;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class KonversiPoin extends JFrame {
    private KonversiPoinController controller;
    private JTable table;
    private Object[][] data;
    private final String[] columnNames = { "ID Poin", "ID Pengguna", "ID TPS", "Kategori Sampah", "Berat (Kg)",
            "Total Poin" };

    public KonversiPoin() {
        controller = new KonversiPoinController();
        setTitle("Data Poin Masyarakat");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Data Poin Masyarakat", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table
        updateTableData(); // Inisialisasi data tabel
        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JButton addButton = new JButton("Tambah Data");
        addButton.addActionListener(e -> showAddDialog());

        JButton editButton = new JButton("Edit Data");
        editButton.addActionListener(e -> editSelectedRow());

        JButton deleteButton = new JButton("Hapus Data");
        deleteButton.addActionListener(e -> deleteSelectedRow());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());

        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.addActionListener(e -> kembaliKeMainFrame());

        JPanel footerPanel = new JPanel();
        footerPanel.add(addButton);
        footerPanel.add(editButton);
        footerPanel.add(deleteButton);
        footerPanel.add(refreshButton);
        footerPanel.add(kembaliButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateTableData() {
        List<Poin> poinList = controller.getAllPoin();
        data = new Object[poinList.size()][6];

        for (int i = 0; i < poinList.size(); i++) {
            Poin poin = poinList.get(i);
            data[i][0] = poin.getIdPoin();
            data[i][1] = poin.getIdMasyarakat();
            data[i][2] = poin.getIdTps();
            data[i][3] = poin.getIdSampah();
            data[i][4] = poin.getBerat();
            data[i][5] = poin.getPoin();
        }
    }

    private void refreshTable() {
        updateTableData(); // Ambil data terbaru dari database
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Tambah Data Poin", true);
        dialog.setLayout(new BorderLayout());

        // Panel Utama
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ID TPS
        mainPanel.add(new JLabel("ID TPS:"));
        JComboBox<Integer> idTpsCombo = new JComboBox<>(controller.getAllIdTps().toArray(new Integer[0]));
        mainPanel.add(idTpsCombo);

        // ID Pengguna
        mainPanel.add(new JLabel("ID Pengguna:"));
        JComboBox<Integer> idPenggunaCombo = new JComboBox<>(controller.getIdMasyarakat().toArray(new Integer[0]));
        mainPanel.add(idPenggunaCombo);

        // Kategori Sampah
        mainPanel.add(new JLabel("Kategori Sampah:"));
        JComboBox<Integer> kategoriSampahCombo = new JComboBox<>(controller.getAllIdSampah().toArray(new Integer[0]));
        mainPanel.add(kategoriSampahCombo);

        // Berat
        mainPanel.add(new JLabel("Berat (Kg):"));
        JTextField beratField = new JTextField();
        mainPanel.add(beratField);

        // Total Poin
        mainPanel.add(new JLabel("Total Poin:"));
        JLabel totalPoinLabel = new JLabel("0");
        mainPanel.add(totalPoinLabel);

        // Listener untuk menghitung poin
        beratField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateTotalPoin();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateTotalPoin();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calculateTotalPoin();
            }

            private void calculateTotalPoin() {
                try {
                    double berat = Double.parseDouble(beratField.getText());
                    int totalPoin = (int) (berat * 5);
                    totalPoinLabel.setText(String.valueOf(totalPoin));
                } catch (NumberFormatException e) {
                    totalPoinLabel.setText("0");
                }
            }
        });

        dialog.add(mainPanel, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Batal");
        JButton saveButton = new JButton("Simpan");

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            try {
                int idTps = (Integer) idTpsCombo.getSelectedItem();
                int idPengguna = (Integer) idPenggunaCombo.getSelectedItem();
                int kategoriSampah = (Integer) kategoriSampahCombo.getSelectedItem();
                double berat = Double.parseDouble(beratField.getText());
                int totalPoin = Integer.parseInt(totalPoinLabel.getText());

                Poin poin = new Poin();
                poin.setIdTps(idTps);
                poin.setIdMasyarakat(idPengguna);
                poin.setIdSampah(kategoriSampah);
                poin.setBerat(berat);
                poin.setPoin(totalPoin);

                controller.addPoin(poin);
                JOptionPane.showMessageDialog(dialog, "Data berhasil disimpan!");
                dialog.dispose();

                refreshTable(); // Segarkan tabel
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Terjadi kesalahan: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void editSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan diedit terlebih dahulu!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPoin = (int) table.getValueAt(selectedRow, 0);
        Poin poin = controller.getPoinById(idPoin);

        if (poin == null) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Menampilkan dialog untuk mengedit data
        JDialog dialog = new JDialog(this, "Edit Data Poin", true);
        dialog.setLayout(new BorderLayout());

        // Panel Utama untuk Input Data
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ID TPS
        mainPanel.add(new JLabel("ID TPS:"));
        JComboBox<Integer> idTpsCombo = new JComboBox<>(controller.getAllIdTps().toArray(new Integer[0]));
        idTpsCombo.setSelectedItem(poin.getIdTps());
        mainPanel.add(idTpsCombo);

        // ID Pengguna
        mainPanel.add(new JLabel("ID Pengguna:"));
        JComboBox<Integer> idPenggunaCombo = new JComboBox<>(controller.getIdMasyarakat().toArray(new Integer[0]));
        idPenggunaCombo.setSelectedItem(poin.getIdMasyarakat());
        mainPanel.add(idPenggunaCombo);

        // Kategori Sampah
        mainPanel.add(new JLabel("Kategori Sampah:"));
        JComboBox<Integer> kategoriSampahCombo = new JComboBox<>(controller.getAllIdSampah().toArray(new Integer[0]));
        kategoriSampahCombo.setSelectedItem(poin.getIdSampah());
        mainPanel.add(kategoriSampahCombo);

        // Berat
        mainPanel.add(new JLabel("Berat (Kg):"));
        JTextField beratField = new JTextField(String.valueOf(poin.getBerat()));
        mainPanel.add(beratField);

        // Total Poin
        mainPanel.add(new JLabel("Total Poin:"));
        JLabel totalPoinLabel = new JLabel(String.valueOf(poin.getPoin()));
        mainPanel.add(totalPoinLabel);

        // Listener untuk menghitung total poin berdasarkan berat
        beratField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateTotalPoin();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateTotalPoin();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calculateTotalPoin();
            }

            private void calculateTotalPoin() {
                try {
                    double berat = Double.parseDouble(beratField.getText());
                    int totalPoin = (int) (berat * 5);
                    totalPoinLabel.setText(String.valueOf(totalPoin));
                } catch (NumberFormatException e) {
                    totalPoinLabel.setText("0");
                }
            }
        });

        dialog.add(mainPanel, BorderLayout.CENTER);

        // Tombol Batal dan Simpan
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Batal");
        JButton saveButton = new JButton("Simpan");

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {
            try {
                int idTps = (Integer) idTpsCombo.getSelectedItem();
                int idPengguna = (Integer) idPenggunaCombo.getSelectedItem();
                int kategoriSampah = (Integer) kategoriSampahCombo.getSelectedItem();
                double berat = Double.parseDouble(beratField.getText());
                int totalPoin = Integer.parseInt(totalPoinLabel.getText());

                // Memperbarui objek poin dengan data yang baru
                poin.setIdTps(idTps);
                poin.setIdMasyarakat(idPengguna);
                poin.setIdSampah(kategoriSampah);
                poin.setBerat(berat);
                poin.setPoin(totalPoin);

                // Menyimpan perubahan ke database
                controller.updatePoin(poin);

                JOptionPane.showMessageDialog(dialog, "Data berhasil diperbarui!");
                dialog.dispose();

                // Refresh data tabel
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Terjadi kesalahan: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus terlebih dahulu!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPoin = (int) table.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePoin(idPoin);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            refreshTable(); // Segarkan tabel
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
            KonversiPoin view = new KonversiPoin();
            view.setVisible(true);
        });
    }
}
