// Saya SAFIRA ALIYAH AZMI dengan NIM 2309209 mengerjakan Tugas Praktikum 6 dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan
// seperti yang telah dispesifikasikan. Aamiin.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Start extends JFrame {
    public Start() {
        setTitle("Flappy Bird - Start");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // panel utama dengan latar belakang (using null checks for safety)
        JPanel panel = new JPanel() {
            Image bg;
            {
                try {
                    bg = new ImageIcon("assets/background.png").getImage();
                    // If the above fails, try to load from resources
                    if (bg.getWidth(null) <= 0) {
                        bg = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
                    }
                } catch (Exception e) {
                    // Fallback: create a solid color background if image loading fails
                    System.out.println("Background image not found, using fallback color");
                }
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null && bg.getWidth(null) > 0) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback: sky blue background
                    g.setColor(new Color(135, 206, 235));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panel.setLayout(null);

        // FLAPPY BIRD Title - using drawString instead of image when image is not available
        JPanel titlePanel = new JPanel() {
            Image logoImg;
            {
                try {
                    logoImg = new ImageIcon("assets/flappy_logo.png").getImage();
                    if (logoImg.getWidth(null) <= 0) {
                        logoImg = new ImageIcon(getClass().getResource("/assets/flappy_logo.png")).getImage();
                    }
                } catch (Exception e) {
                    System.out.println("Logo image not found, will use text fallback");
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);

                if (logoImg != null && logoImg.getWidth(null) > 0) {
                    g.drawImage(logoImg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback: Draw title text
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                    // Shadow
                    g.setFont(new Font("Arial", Font.BOLD, 36));
                    g.setColor(Color.BLACK);
                    g.drawString("FLAPPY BIRD", 12, 42);

                    // Text
                    g.setColor(new Color(255, 215, 0));
                    g.drawString("FLAPPY BIRD", 10, 40);
                }
            }
        };
        titlePanel.setBounds(40, 120, 280, 70);
        panel.add(titlePanel);

        // tombol START
        JButton startButton = createButton("START");
        startButton.setBounds(115, 300, 130, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // tutup Start Menu
                launchGame(); // buka Game
            }
        });
        panel.add(startButton);

        // tombol SCORE
        JButton scoreButton = createButton("SCORE");
        scoreButton.setBounds(115, 370, 130, 50);
        scoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tampilkan highscore (dapat diimplementasikan nanti)
                JOptionPane.showMessageDialog(null, "High Score: " + getHighScore());
            }
        });
        panel.add(scoreButton);

        // copyright label
        JLabel copyrightLabel = new JLabel("(C) GEARS Studios 2013");
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        copyrightLabel.setForeground(new Color(150, 150, 150));
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        copyrightLabel.setBounds(0, 550, 360, 20);
        panel.add(copyrightLabel);

        setContentPane(panel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background orange
                g2.setColor(new Color(230, 120, 30));
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Lighter top border
                g2.setColor(new Color(250, 190, 80));
                g2.fillRect(0, 0, getWidth(), 5);

                // Darker bottom border
                g2.setColor(new Color(180, 80, 10));
                g2.fillRect(0, getHeight()-5, getWidth(), 5);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private int getHighScore() {
        // Dapat diimplementasikan untuk membaca dari file atau preferences
        return 0;
    }

    private void launchGame() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}