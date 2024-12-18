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
        String[] columnNames = { "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image" };
        List<Masyarakat> masyarakatList = controller.getAllMasyarakat();
        Object[][] data = new Object[masyarakatList.size()][7];

        for (int i = 0; i < masyarakatList.size(); i++) {
            Masyarakat masyarakat = masyarakatList.get(i);
            data[i][0] = masyarakat.getIdMasyarakat();
            data[i][1] = masyarakat.getNamaMasyarakat();
            data[i][2] = masyarakat.getJenisKelamin();
            data[i][3] = masyarakat.getTanggalLahir();
            data[i][4] = masyarakat.getNoHP();
            data[i][5] = masyarakat.getAlamat();
            data[i][6] = masyarakat.getImage();
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
        List<Masyarakat> masyarakatList = controller.getAllMasyarakat();
        Object[][] data = new Object[masyarakatList.size()][7];

        for (int i = 0; i < masyarakatList.size(); i++) {
            Masyarakat masyarakat = masyarakatList.get(i);
            data[i][0] = masyarakat.getIdMasyarakat();
            data[i][1] = masyarakat.getNamaMasyarakat();
            data[i][2] = masyarakat.getJenisKelamin();
            data[i][3] = masyarakat.getTanggalLahir();
            data[i][4] = masyarakat.getNoHP();
            data[i][5] = masyarakat.getAlamat();
            data[i][6] = masyarakat.getImage();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {
                "ID", "Nama", "Jenis Kelamin", "Tanggal Lahir", "No HP", "Alamat", "Image"
        }));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MasyarakatView view = new MasyarakatView();
            view.setVisible(true);
        });
    }
}
