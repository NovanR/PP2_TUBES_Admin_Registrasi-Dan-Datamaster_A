package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // Panel utama dengan layout GridLayout (2 baris dan 2 kolom)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 baris, 2 kolom, spasi 10px antar tombol

        // Membuat tombol dengan fungsi dan gaya menarik
        JButton pendaftaranButton = createButton("Pendaftaran");
        JButton pengelolaanSampahButton = createButton("Pengelolaan Sampah");
        JButton konversiPoinButton = createButton("Konversi Poin");
        JButton dropboxButton = createButton("Dropbox");

        // Menambahkan tombol ke panel utama
        mainPanel.add(pendaftaranButton);
        mainPanel.add(pengelolaanSampahButton);
        mainPanel.add(konversiPoinButton);
        mainPanel.add(dropboxButton);

        // Menambahkan aksi untuk tombol
        pendaftaranButton.addActionListener(e -> showPendaftaran());
        pengelolaanSampahButton.addActionListener(e -> showPengelolaanSampah());
        konversiPoinButton.addActionListener(e -> showKonversiPoin());
        dropboxButton.addActionListener(e -> showDropbox());

        // Menambahkan panel utama ke frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        // Membuat tombol dengan teks dan gaya
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Font tombol
        button.setBackground(new Color(173, 216, 230)); // Warna latar belakang tombol
        button.setForeground(new Color(0, 51, 102)); // Warna teks tombol
        button.setFocusPainted(false); // Menghilangkan garis fokus tombol
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2)); // Border tombol
        button.setPreferredSize(new Dimension(150, 50)); // Ukuran tombol

        return button;
    }

    private void showPendaftaran() {
        JFrame pendaftaranFrame = new JFrame("Pendaftaran");
        pendaftaranFrame.setSize(300, 200);
        pendaftaranFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pendaftaranFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton masyarakatButton = new JButton("Masyarakat");
        JButton kurirButton = new JButton("Kurir");

        panel.add(masyarakatButton);
        panel.add(kurirButton);

        masyarakatButton.addActionListener(e -> {
            pendaftaranFrame.dispose();
            SwingUtilities.invokeLater(() -> {
                MasyarakatView masyarakatView = new MasyarakatView();
                masyarakatView.setVisible(true);
            });
        });

        kurirButton.addActionListener(e -> JOptionPane.showMessageDialog(pendaftaranFrame, "Kurir clicked"));

        pendaftaranFrame.add(panel);
        pendaftaranFrame.setVisible(true);
    }

    private void showPengelolaanSampah() {
        SwingUtilities.invokeLater(() -> {
            SampahView sampahView = new SampahView();
            sampahView.setVisible(true);
        });
    }

    private void showKonversiPoin() {
        JOptionPane.showMessageDialog(this, "Konversi Poin clicked");
    }

    private void showDropbox() {
        SwingUtilities.invokeLater(() -> {
            DropboxView dropboxView = new DropboxView();
            dropboxView.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
