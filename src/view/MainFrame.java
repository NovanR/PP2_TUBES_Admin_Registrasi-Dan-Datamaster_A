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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));

        // Buttons
        JButton pendaftaranButton = new JButton("Pendaftaran");
        JButton pengelolaanSampahButton = new JButton("Pengelolaan Sampah");
        JButton konversiPoinButton = new JButton("Konversi Poin");
        JButton dropboxButton = new JButton("Dropbox");

        // Add buttons to panel
        mainPanel.add(pendaftaranButton);
        mainPanel.add(pengelolaanSampahButton);
        mainPanel.add(konversiPoinButton);
        mainPanel.add(dropboxButton);

        // Add actions for buttons
        pendaftaranButton.addActionListener(e -> showPendaftaran());
        pengelolaanSampahButton.addActionListener(e -> showPengelolaanSampah());
        konversiPoinButton.addActionListener(e -> showKonversiPoin());
        dropboxButton.addActionListener(e -> showDropbox());

        // Add panel to frame
        add(mainPanel);
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
        JOptionPane.showMessageDialog(this, "Dropbox clicked");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
