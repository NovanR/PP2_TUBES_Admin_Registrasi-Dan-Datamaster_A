package view;

import controller.DropboxController;
import model.Dropbox;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DropboxView extends JFrame {

    private JTable dropboxTable;
    private JScrollPane scrollPane;

    private DropboxController dropboxController;

    public DropboxView() {
        dropboxController = new DropboxController();
        setTitle("Dropbox List");
        setLayout(new BorderLayout());

        // Table setup
        dropboxTable = new JTable();
        scrollPane = new JScrollPane(dropboxTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data into the table
        loadDropboxData();

        // Frame settings
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void loadDropboxData() {
        List<Dropbox> dropboxes = dropboxController.getAllDropbox();

        // Prepare data for JTable
        String[] columnNames = {"ID", "Nama TPS", "No. HP", "Alamat TPS"};
        Object[][] data = new Object[dropboxes.size()][4];

        for (int i = 0; i < dropboxes.size(); i++) {
            Dropbox dropbox = dropboxes.get(i);
            data[i][0] = dropbox.getIdTps();
            data[i][1] = dropbox.getNamaTps();
            data[i][2] = dropbox.getNoHpTps();
            data[i][3] = dropbox.getAlamatTps();
        }

        // Set data to table
        dropboxTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DropboxView view = new DropboxView();
            view.setVisible(true);
        });
    }
}
