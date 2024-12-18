package view;

import controller.DropboxController;
import model.Dropbox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DropboxView extends JFrame {

    private DropboxController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtIdTps, txtNamaTps, txtNoHpTps, txtAlamatTps;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

    public DropboxView() {
        controller = new DropboxController();
        initializeUI();
        loadDataToTable();
    }

    private void initializeUI() {
        // Set up JFrame
        setTitle("Dropbox Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Table Model and JTable
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama TPS", "No HP", "Alamat"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Input Form Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data"));

        txtIdTps = new JTextField();
        txtNamaTps = new JTextField();
        txtNoHpTps = new JTextField();
        txtAlamatTps = new JTextField();

        inputPanel.add(new JLabel("ID TPS:"));
        inputPanel.add(txtIdTps);
        inputPanel.add(new JLabel("Nama TPS:"));
        inputPanel.add(txtNamaTps);
        inputPanel.add(new JLabel("No HP TPS:"));
        inputPanel.add(txtNoHpTps);
        inputPanel.add(new JLabel("Alamat TPS:"));
        inputPanel.add(txtAlamatTps);

        // Buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        // Add components to JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        btnAdd.addActionListener(e -> addDropbox());
        btnUpdate.addActionListener(e -> updateDropbox());
        btnDelete.addActionListener(e -> deleteDropbox());
        btnRefresh.addActionListener(e -> loadDataToTable());

        // Show JFrame
        setVisible(true);
    }

    private void loadDataToTable() {
        // Clear table
        tableModel.setRowCount(0);

        // Get data from controller
        List<Dropbox> dropboxList = controller.getAllDropbox();
        if (dropboxList != null) {
            for (Dropbox dropbox : dropboxList) {
                tableModel.addRow(new Object[]{
                        dropbox.getIdTps(),
                        dropbox.getNamaTps(),
                        dropbox.getNoHpTps(),
                        dropbox.getAlamatTps()
                });
            }
        }
    }

    private void addDropbox() {
        String idTps = txtIdTps.getText();
        String namaTps = txtNamaTps.getText();
        String noHpTps = txtNoHpTps.getText();
        String alamatTps = txtAlamatTps.getText();

        if (idTps.isEmpty() || namaTps.isEmpty() || noHpTps.isEmpty() || alamatTps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Dropbox dropbox = new Dropbox(idTps, namaTps, noHpTps, alamatTps);
        controller.createDropbox(dropbox);
        loadDataToTable();
    }

    private void updateDropbox() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diperbarui!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idTps = tableModel.getValueAt(selectedRow, 0).toString();
        String namaTps = txtNamaTps.getText();
        String noHpTps = txtNoHpTps.getText();
        String alamatTps = txtAlamatTps.getText();

        if (namaTps.isEmpty() || noHpTps.isEmpty() || alamatTps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Dropbox dropbox = new Dropbox(idTps, namaTps, noHpTps, alamatTps);
        controller.updateDropbox(dropbox);
        loadDataToTable();
    }

    private void deleteDropbox() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idTps = tableModel.getValueAt(selectedRow, 0).toString();
        controller.deleteDropbox(idTps);
        loadDataToTable();
    }
}
