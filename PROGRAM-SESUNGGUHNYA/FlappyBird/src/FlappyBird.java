// Saya SAFIRA ALIYAH AZMI dengan NIM 2309209 mengerjakan Tugas Praktikum 6 dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan
// seperti yang telah dispesifikasikan. Aamiin.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;
    Image gameOverImage;
    Image medalImage;
    Image pauseButtonImage;

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // pipe attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;

    int gravity = 1;

    // game state
    boolean gameOver = false;
    boolean paused = false;
    int score = 0;
    int bestScore = 0;
    boolean isNewBest = false;

    // for in-game display
    Font scoreFont = new Font("Arial", Font.BOLD, 36);
    JButton pauseButton;
    JButton resumeButton;
    JButton playAgainButton;
    JButton okButton;
    JButton shareButton;

    // game over panel components
    JPanel gameOverPanel;

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);

        // load images safely with fallbacks
        loadImages();

        // Initialize player with fallback
        if (birdImage != null) {
            player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        } else {
            // Create player with null image, we'll draw a fallback shape in the paint method
            player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, null);
        }

        pipes = new ArrayList<Pipe>();

        // Pause button
        pauseButton = createButton("II", new Color(230, 120, 30));
        pauseButton.setBounds(10, 10, 40, 40);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });
        add(pauseButton);

        // Resume button (initially hidden)
        resumeButton = createButton("RESUME", new Color(230, 120, 30));
        resumeButton.setBounds(frameWidth/2 - 75, frameHeight/2 - 60, 150, 50);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });
        add(resumeButton);

        // Play Again button (initially hidden)
        playAgainButton = createButton("PLAY", new Color(230, 120, 30));
        playAgainButton.setBounds(frameWidth/2 - 75, frameHeight/2, 150, 50);
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        add(playAgainButton);

        // Initialize game over panel (will be configured in showGameOver method)
        gameOverPanel = new JPanel();
        gameOverPanel.setOpaque(false);
        gameOverPanel.setLayout(null);
        gameOverPanel.setBounds(frameWidth/2 - 120, frameHeight/2 - 100, 240, 200);
        gameOverPanel.setVisible(false);
        add(gameOverPanel);

        // Initialize OK and SHARE buttons for game over screen
        okButton = createButton("OK", new Color(230, 120, 30));
        okButton.setBounds(30, 150, 80, 40);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        shareButton = createButton("SHARE", new Color(230, 120, 30));
        shareButton.setBounds(130, 150, 80, 40);
        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code for share functionality (could be implemented later)
                JOptionPane.showMessageDialog(null, "Share functionality would go here");
            }
        });

        pipesCooldown = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver && !paused) {
                    placePipes();
                }
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    private void loadImages() {
        try {
            // Try loading from direct path first
            backgroundImage = new ImageIcon("assets/background.png").getImage();
            if (backgroundImage.getWidth(null) <= 0) {
                // If that fails, try class resource path
                backgroundImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Background image not found");
        }

        try {
            birdImage = new ImageIcon("assets/bird.png").getImage();
            if (birdImage.getWidth(null) <= 0) {
                birdImage = new ImageIcon(getClass().getResource("/assets/bird.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Bird image not found");
        }

        try {
            lowerPipeImage = new ImageIcon("assets/lowerPipe.png").getImage();
            if (lowerPipeImage.getWidth(null) <= 0) {
                lowerPipeImage = new ImageIcon(getClass().getResource("/assets/lowerPipe.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Lower pipe image not found");
        }

        try {
            upperPipeImage = new ImageIcon("assets/upperPipe.png").getImage();
            if (upperPipeImage.getWidth(null) <= 0) {
                upperPipeImage = new ImageIcon(getClass().getResource("/assets/upperPipe.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Upper pipe image not found");
        }

        try {
            gameOverImage = new ImageIcon("assets/game_over.png").getImage();
            if (gameOverImage.getWidth(null) <= 0) {
                gameOverImage = new ImageIcon(getClass().getResource("/assets/game_over.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Game over image not found");
        }

        try {
            medalImage = new ImageIcon("assets/medal.png").getImage();
            if (medalImage.getWidth(null) <= 0) {
                medalImage = new ImageIcon(getClass().getResource("/assets/medal.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Medal image not found");
        }

        try {
            pauseButtonImage = new ImageIcon("assets/pause.png").getImage();
            if (pauseButtonImage.getWidth(null) <= 0) {
                pauseButtonImage = new ImageIcon(getClass().getResource("/assets/pause.png")).getImage();
            }
        } catch (Exception e) {
            System.out.println("Pause button image not found");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Draw background with fallback color if image is missing
        if (backgroundImage != null && backgroundImage.getWidth(null) > 0) {
            g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        } else {
            // Fallback: sky blue background
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, 0, frameWidth, frameHeight);
        }

        // Draw player with fallback if image is missing
        if (player.getImage() != null && player.getImage().getWidth(null) > 0) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);
        } else {
            // Fallback: yellow bird shape
            g.setColor(Color.YELLOW);
            g.fillOval(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            g.setColor(Color.BLACK);
            g.drawOval(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            // Simple eye
            g.fillOval(player.getPosX() + player.getWidth()/4*3, player.getPosY() + player.getHeight()/3, 5, 5);
        }

        // Draw pipes with fallbacks if images are missing
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.getImage() != null && pipe.getImage().getWidth(null) > 0) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            } else {
                // Fallback: green pipes
                g.setColor(new Color(0, 150, 0));
                g.fillRect(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
                g.setColor(new Color(0, 100, 0));
                g.drawRect(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
            }
        }

        // Draw score
        if (!gameOver) {
            g.setFont(scoreFont);
            g.setColor(Color.WHITE);
            String scoreStr = String.valueOf(score);
            int scoreWidth = g.getFontMetrics().stringWidth(scoreStr);
            g.drawString(scoreStr, frameWidth/2 - scoreWidth/2, 100);
        }

        // Pause overlay
        if (paused && !gameOver) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, frameWidth, frameHeight);

            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.WHITE);
            String pausedText = "PAUSED";
            int pausedWidth = g.getFontMetrics().stringWidth(pausedText);
            g.drawString(pausedText, frameWidth/2 - pausedWidth/2, frameHeight/2 - 100);
        }
    }

    public void move() {
        if (gameOver == false && paused == false) {
            player.setVelocityY(player.getVelocityY() + gravity);
            player.setPosY(player.getPosY() + player.getVelocityY());
            if (player.getPosY() < 0) {
                player.setPosY(0);
            }

            ArrayList<Pipe> pipesToRemove = new ArrayList<>();

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

                // pengecekan skor
                if (pipe.isPassed() == false && pipe.getPosX() + pipe.getWidth() < player.getPosX() && i % 2 == 0) {
                    pipe.setPassed(true);
                    score++;
                }

                // untuk cari tabrakan dengan pipa
                Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
                Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

                if (playerRect.intersects(pipeRect)) {
                    gameOver();
                }

                if (pipe.getPosX() + pipe.getWidth() < 0) {
                    pipesToRemove.add(pipe);
                }
            }

            for (int i = 0; i < pipesToRemove.size(); i++) {
                pipes.remove(pipesToRemove.get(i));
            }

            // game over jika jatuh ke bawah
            if (player.getPosY() + player.getHeight() > frameHeight) {
                gameOver();
            }
        }
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void gameOver() {
        gameOver = true;

        // Check if it's a new best score
        if (score > bestScore) {
            bestScore = score;
            isNewBest = true;
            // Save best score here if needed
        } else {
            isNewBest = false;
        }

        showGameOverScreen();
    }

    public void showGameOverScreen() {
        // Clear the game over panel and configure it
        gameOverPanel.removeAll();
        gameOverPanel.setVisible(true);

        // Create a custom panel to draw the game over screen
        JPanel scorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw the panel background
                g.setColor(new Color(249, 214, 146));
                g.fillRect(0, 0, getWidth(), getHeight());

                // Draw GAME OVER text
                if (gameOverImage != null && gameOverImage.getWidth(null) > 0) {
                    g.drawImage(gameOverImage, 20, -50, 200, 100, null);
                } else {
                    // Fallback: text
                    g.setColor(new Color(255, 100, 0));
                    g.setFont(new Font("Arial", Font.BOLD, 24));
                    g.drawString("GAME OVER", 50, 30);
                }

                // Draw medal
                if (medalImage != null && medalImage.getWidth(null) > 0) {
                    g.drawImage(medalImage, 30, 70, 50, 50, null);
                } else {
                    // Fallback: medal shape
                    g.setColor(new Color(255, 215, 0));
                    g.fillOval(30, 70, 50, 50);
                    g.setColor(new Color(210, 105, 30));
                    g.drawOval(30, 70, 50, 50);
                }

                // Draw score text
                g.setColor(new Color(210, 115, 20));
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("SCORE", 120, 70);

                // Draw score value
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                String scoreStr = String.format("%02d", score);
                g.drawString(scoreStr, 150, 100);

                // Draw NEW BEST indicator if applicable
                if (isNewBest) {
                    g.setColor(Color.RED);
                    g.setFont(new Font("Arial", Font.BOLD, 10));
                    g.drawString("NEW", 120, 115);
                    g.drawString("BEST", 150, 115);
                }

                // Draw best score text and value
                g.setColor(new Color(210, 115, 20));
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("BEST", 120, 140);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                String bestScoreStr = String.format("%02d", bestScore);
                g.drawString(bestScoreStr, 150, 170);

                // Draw restart instruction
                g.setColor(new Color(210, 115, 20));
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("Press 'R' to restart", 60, 200);
            }
        };

        scorePanel.setLayout(null);
        scorePanel.setBounds(0, 0, 240, 220); // Increased height to accommodate restart text
        gameOverPanel.add(scorePanel);

        // Add buttons to the game over panel
        okButton.setBounds(30, 150, 80, 40);
        shareButton.setBounds(130, 150, 80, 40);
        gameOverPanel.add(okButton);
        gameOverPanel.add(shareButton);

        // Hide pause button
        pauseButton.setVisible(false);

        // Repaint to update UI
        repaint();
    }

    public void pauseGame() {
        if (!gameOver && !paused) {
            paused = true;
            resumeButton.setVisible(true);
            playAgainButton.setVisible(true);
            repaint();
        }
    }

    public void resumeGame() {
        if (paused) {
            paused = false;
            resumeButton.setVisible(false);
            playAgainButton.setVisible(false);
            repaint();
        }
    }

    public void restartGame() {
        gameOver = false;
        paused = false;
        score = 0;
        isNewBest = false;
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();

        // Reset UI
        gameOverPanel.setVisible(false);
        resumeButton.setVisible(false);
        playAgainButton.setVisible(false);
        pauseButton.setVisible(true);

        repaint();
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2.setColor(bgColor);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Lighter top border
                g2.setColor(bgColor.brighter());
                g2.fillRect(0, 0, getWidth(), 5);

                // Darker bottom border
                g2.setColor(bgColor.darker());
                g2.fillRect(0, getHeight()-5, getWidth(), 5);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver == false && paused == false) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                player.setVelocityY(-10);
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                pauseGame();
            }
        } else if (gameOver == true) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
            }
        } else if (paused == true) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
                resumeGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}