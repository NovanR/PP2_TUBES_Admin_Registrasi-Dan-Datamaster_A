package view.pendaftaran;

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

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Data Kurir", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image" };
        List<Kurir> kurirList = controller.getAllKurir();
        Object[][] data = new Object[kurirList.size()][7];

        for (int i = 0; i < kurirList.size(); i++) {
            Kurir kurir = kurirList.get(i);
            data[i][0] = kurir.getIdKurir();
            data[i][1] = kurir.getNamaKurir();
            data[i][2] = kurir.getJenisKelamin();
            data[i][3] = kurir.getTanggalLahir();
            data[i][4] = kurir.getNoHP();
            data[i][5] = kurir.getAlamat();
            data[i][6] = kurir.getImage();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable(table));
        JPanel footerPanel = new JPanel();
        footerPanel.add(refreshButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void refreshTable(JTable table) {
        List<Kurir> kurirList = controller.getAllKurir();
        Object[][] data = new Object[kurirList.size()][7];

        for (int i = 0; i < kurirList.size(); i++) {
            Kurir kurir = kurirList.get(i);
            data[i][0] = kurir.getIdKurir();
            data[i][1] = kurir.getNamaKurir();
            data[i][2] = kurir.getJenisKelamin();
            data[i][3] = kurir.getTanggalLahir();
            data[i][4] = kurir.getNoHP();
            data[i][5] = kurir.getAlamat();
            data[i][6] = kurir.getImage();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {
                "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image"
        }));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KurirView view = new KurirView();
            view.setVisible(true);
        });
    }
}
