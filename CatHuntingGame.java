package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CatHuntingGame extends JPanel implements ActionListener {
    private int catX = 300, catY = 300; // 고양이 위치
    private int coinX, coinY; // 코인 위치
    private int score = 0; // 코인 획득 개수
    private Timer gameTimer; // 게임 루프 타이머
    private Timer countdownTimer; // 30초 제한 타이머
    private int timeLeft = 30; // 남은 시간
    private JButton confirmButton; // 확인 버튼
    private Random random = new Random();

    private ImageIcon catWalkRight, catWalkLeft; // 고양이 이미지 2개 (걷는 이미지)
    private ImageIcon currentCatImage;  // 현재 고양이 이미지
    private ImageIcon coinImage;  // 코인 이미지
    private boolean isMovingRight = false;  // 고양이가 오른쪽인지 왼쪽인지 구분
    private ActionListener coinListener;

    public CatHuntingGame(ActionListener coinListener) {
        this.coinListener = coinListener;

        setPreferredSize(new Dimension(800, 600));

        try {
            catWalkRight = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/고양이/오 걷.jpg");
            catWalkLeft = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/고양이/왼 걷.jpg");
            coinImage = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/고양이/코인.png");
            currentCatImage = catWalkRight;
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + e.getMessage());
        }

        setFocusable(true);
        setLayout(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (catY > 0) catY -= 20;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (catY < getHeight() - 70) catY += 20;
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                }
                repaint();
            }
        });

        generateCoin();

        gameTimer = new Timer(30, this);
        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                endGame();
            }
            repaint();
        });

        confirmButton = new JButton("확인");
        confirmButton.setVisible(false);
        confirmButton.addActionListener(e -> closeGameWindow());
        add(confirmButton);
        setupConfirmButton();

        startGame();
    }

    private void setupConfirmButton() {
        int buttonWidth = 120;
        int buttonHeight = 50;
        int panelWidth = Math.max(getWidth(), 800);
        int panelHeight = Math.max(getHeight(), 600);
        confirmButton.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
    }

    private void startGame() {
        score = 0;
        timeLeft = 30;
        confirmButton.setVisible(false);
        generateCoin();
        gameTimer.start();
        countdownTimer.start();
        repaint();
    }

    private void generateCoin() {
        int panelWidth = Math.max(getWidth(), 100);
        int panelHeight = Math.max(getHeight(), 100);
        coinX = random.nextInt(panelWidth - 50);
        coinY = random.nextInt(panelHeight - 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(currentCatImage.getImage(), catX, catY, 150, 70, this);
        g.drawImage(coinImage.getImage(), coinX, coinY, 50, 50, this);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Time Left: " + timeLeft + "s", 10, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (new Rectangle(catX, catY, 150, 70).intersects(new Rectangle(coinX, coinY, 50, 50))) {
            score += 100;
            generateCoin();
        }
        repaint();
    }

    private void moveRight() {
        catX += 20;
        isMovingRight = true;
        updateCatImage();
    }

    private void moveLeft() {
        catX -= 20;
        isMovingRight = false;
        updateCatImage();
    }

    private void updateCatImage() {
        currentCatImage = isMovingRight ? catWalkRight : catWalkLeft;
    }

    private void endGame() {
        gameTimer.stop();
        countdownTimer.stop();
        confirmButton.setVisible(true);
        JOptionPane.showMessageDialog(this, "Game Over! You collected " + score + " coins!");
        if (coinListener != null) {
            coinListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(score)));
        }
    }

    private void closeGameWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose(); // 게임창 닫기
        }
    }
}
