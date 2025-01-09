package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // 2 baris, 2 kolom, spasi 10px antar tombol

        // judul di bagian atas
        JLabel titleLabel = new JLabel("Selamat datang Admin", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 51, 102));

        // Set posisi dan jarak untuk titleLabel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20); // Menambahkan jarak di sekitar title
        mainPanel.add(titleLabel, gbc);

        JButton pendaftaranButton = createButton("Pendaftaran");
        JButton pengelolaanSampahButton = createButton("Pengelolaan Sampah");
        JButton konversiPoinButton = createButton("Konversi Poin");
        JButton dropboxButton = createButton("Dropbox");

        // Set posisi dan jarak untuk tombol-tombol
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 10, 20); // Jarak untuk tombol
        mainPanel.add(pendaftaranButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(pengelolaanSampahButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(konversiPoinButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(dropboxButton, gbc);

        pendaftaranButton.addActionListener(e -> showPendaftaran());
        pengelolaanSampahButton.addActionListener(e -> showPengelolaanSampah());
        konversiPoinButton.addActionListener(e -> showKonversiPoin());
        dropboxButton.addActionListener(e -> showDropbox());

        // Menambahkan panel utama ke frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Font tombol
        button.setBackground(new Color(173, 216, 230)); // Warna latar belakang tombol
        button.setForeground(new Color(0, 51, 102)); // Warna teks tombol
        button.setFocusPainted(false); // Menghilangkan garis fokus tombol
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2)); // Border tombol
        // Menentukan ukuran tombol
        button.setPreferredSize(new Dimension(200, 80));

        return button;
    }

    private void showPendaftaran() {
        JFrame pendaftaranFrame = new JFrame("Pendaftaran");
        pendaftaranFrame.setSize(300, 200);
        pendaftaranFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pendaftaranFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton masyarakatButton = createButton("Masyarakat");
        JButton kurirButton = createButton("Kurir");

        panel.add(masyarakatButton);
        panel.add(kurirButton);

        masyarakatButton.addActionListener(e -> {
            this.dispose();
            pendaftaranFrame.dispose();
            SwingUtilities.invokeLater(() -> {
                MasyarakatView masyarakatView = new MasyarakatView();
                masyarakatView.setVisible(true);
            });
        });

        kurirButton.addActionListener(e -> {
            showKurir(pendaftaranFrame, panel);
        });

        pendaftaranFrame.add(panel);
        pendaftaranFrame.setVisible(true);
    }

    private void showKurir(JFrame pendaftaranFrame, JPanel panel) {
        panel.removeAll();

        JButton persetujuanButton = createButton("Persetujuan");
        JButton verifikasiButton = createButton("Verifikasi");

        panel.add(persetujuanButton);
        panel.add(verifikasiButton);

        // Atur ulang layout panel
        panel.revalidate();
        panel.repaint();

        persetujuanButton.addActionListener(evt -> {
            pendaftaranFrame.dispose();
            SwingUtilities.invokeLater(() -> {
                KurirView kurirView = new KurirView();
                kurirView.setVisible(true);
            });
        });

        verifikasiButton.addActionListener(evt -> {
            this.dispose();
            pendaftaranFrame.dispose();
            SwingUtilities.invokeLater(() -> {
                BerkasView berkasView = new BerkasView();
                berkasView.setVisible(true);
            });
        });
    }

    private void showPengelolaanSampah() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            SampahView sampahView = new SampahView();
            sampahView.setVisible(true);
        });
    }

    private void showKonversiPoin() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            KonversiPoin konversiPoin = new KonversiPoin();
            konversiPoin.setVisible(true);
        });
    }

    private void showDropbox() {
        this.dispose();
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
