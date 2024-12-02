package CatGameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.LinkedList;

import java.util.Random;

public class CatRunGame extends JPanel implements ActionListener {
    private int catY;
    private int jumpVelocity = 0;
    private boolean jumping = false;
    private int score = 0;
    private int speed;
    private LinkedList<Integer> obstacleX = new LinkedList<>();
    private Timer timer;
    private JButton retryButton;
    private int coins = 0;
    private Random random = new Random();
    private int screenWidth, screenHeight;
    private int groundY;
    private int catSize;
    private int gravity;
    private int speedIncrementDelay = 1000;
    private Image obstacleImage;
    private Image walkingCatImage;  // 걷는 고양이 이미지
    private Image jumpingCatImage;  // 뛰는 고양이 이미지
    private int obstacleWidth = 80;
    private int obstacleHeight = 130;

    public CatRunGame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        groundY = (int) (screenHeight * 0.75);
        catSize = (int) (screenHeight * 0.1);
        gravity = screenHeight / 600;

        catY = groundY - catSize;

        speed = screenWidth / 200;

        jumpVelocity = -screenHeight / 40;

        initializeObstacles();

        timer = new Timer(20, this);
        timer.start();

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE && !jumping) {
                    jumping = true;
                    jumpVelocity = -screenHeight / 40;
                }
            }
        });
        setFocusable(true);

        retryButton = new JButton("다시하기");
        retryButton.setVisible(false);
        retryButton.setBackground(new Color(255, 255, 255));
        retryButton.setForeground(new Color(0, 0, 0));
        retryButton.setBorderPainted(true);
        retryButton.setFont(new Font("Arial", Font.BOLD, 18));

        retryButton.addActionListener(e -> resetGame());

        this.setLayout(null);
        this.add(retryButton);

        updateRetryButtonPosition();

        try {
            File imageFile = new File("C:/Users/yunni/OneDrive/바탕 화면/Uni/2학기/Computer Programming (월)/기말고사/장애물.png");
            obstacleImage = ImageIO.read(imageFile); // 장애물 이미지 로드
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            walkingCatImage = ImageIO.read(new File("C:/Users/yunni/OneDrive/바탕 화면/Uni/2학기/Computer Programming (월)/기말고사/거ㄷ.jpg"));  // 걷는 고양이 이미지 경로
            jumpingCatImage = ImageIO.read(new File("C:/Users/yunni/OneDrive/바탕 화면/Uni/2학기/Computer Programming (월)/기말고사/뛰기.jpg"));  // 뛰는 고양이 이미지 경로
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeObstacles() {
        obstacleX.clear();
        int startX = screenWidth;
        for (int i = 0; i < 5; i++) {
            startX += random.nextInt(screenWidth / 4) + screenWidth / 4;
            obstacleX.add(startX);
        }
    }

    private void updateRetryButtonPosition() {
        int buttonWidth = 200;
        int buttonHeight = 60;
        retryButton.setBounds((screenWidth - buttonWidth) / 2, (screenHeight - buttonHeight) / 2, buttonWidth, buttonHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, groundY, screenWidth, screenHeight - groundY);

        // 점프 상태에 따라 고양이 이미지를 다르게 표시
        if (jumping) {
            if (jumpingCatImage != null) {
                g.drawImage(jumpingCatImage, 100, catY, catSize, catSize, this);
            }
        } else {
            if (walkingCatImage != null) {
                g.drawImage(walkingCatImage, 100, catY, catSize, catSize, this);
            }
        }

        for (int x : obstacleX) {
            g.drawImage(obstacleImage, x, groundY - obstacleHeight, obstacleWidth, obstacleHeight, this);
        }

        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 30);
        g.drawString("Coins: " + coins, 10, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < obstacleX.size(); i++) {
            obstacleX.set(i, obstacleX.get(i) - speed);
        }

        if (!obstacleX.isEmpty() && obstacleX.getFirst() < -obstacleWidth) {
            obstacleX.removeFirst();
            int newObstacleX = obstacleX.getLast() + random.nextInt(screenWidth / 4) + screenWidth / 4;
            obstacleX.add(newObstacleX);
            score += 10;
            coins += calculateCoins(score);
        }

        if (score % speedIncrementDelay == 0 && score != 0) {
            speed += 10;
            speedIncrementDelay += 1000;
        }

        if (jumping) {
            catY += jumpVelocity;
            jumpVelocity += gravity;
            if (catY >= groundY - catSize) {
                catY = groundY - catSize;
                jumping = false;
            }
        }

        for (int x : obstacleX) {
            if (x < 150 && x + obstacleWidth > 100 && catY + catSize > groundY - obstacleHeight) {
                gameOver();
                return;
            }
        }

        repaint();
    }

    private void gameOver() {
        timer.stop();
        retryButton.setVisible(true);
        JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score + "\nCoins Earned: " + coins);
    }

    private int calculateCoins(int finalScore) {
        if (finalScore <= 30) {
            return 5;
        } else if (finalScore <= 60) {
            return 15;
        } else if (finalScore <= 90) {
            return 30;
        } else {
            return 50;
        }
    }

    private void resetGame() {
        catY = groundY - catSize;
        initializeObstacles();
        jumping = false;
        jumpVelocity = 0;
        score = 0;
        speed = screenWidth / 200;
        coins = 0;
        retryButton.setVisible(false);
        timer.start();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cat Run Game");
        CatRunGame game = new CatRunGame();
        frame.add(game);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


