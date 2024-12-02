package CatGameProject;

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
    private JButton retryButton; // 다시하기 버튼
    private Random random = new Random();

    private ImageIcon catWalkRight, catWalkLeft; // 고양이 이미지 2개 (걷는 이미지)
    private ImageIcon currentCatImage;  // 현재 고양이 이미지
    private ImageIcon coinImage;  // 코인 이미지
    private boolean isMovingRight = false;  // 고양이가 오른쪽인지 왼쪽인지 구분

    public CatHuntingGame() {
        // 이미지 로드
        catWalkRight = new ImageIcon("C:/Users/yunni/OneDrive/바탕 화면/Uni/2학기/Computer Programming (월)/기말고사/오 걷.jpg"); // 오른쪽 걷기 이미지 경로
        catWalkLeft = new ImageIcon("C:/Users/yunni/OneDrive/바탕 화면/Uni/2학기/Computer Programming (월)/기말고사/왼 걷.jpg"); // 왼쪽 걷기 이미지 경로

        coinImage = new ImageIcon("C:/Users/yunni/OneDrive/바탕 화면/Uni/2학기/Computer Programming (월)/기말고사/코인.png"); // 코인 이미지 경로

        currentCatImage = catWalkRight;  // 초기 고양이 이미지는 오른쪽 걷기

        setFocusable(true);
        setLayout(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (catY > 0) catY -= 20;  // 위로 이동, 화면 밖으로 나가지 않게
                        break;
                    case KeyEvent.VK_DOWN:
                        if (catY < getHeight() - 70) catY += 20;  // 아래로 이동, 화면 밖으로 나가지 않게
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeft();  // 왼쪽 이동
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();  // 오른쪽 이동
                        break;
                }
                repaint();
            }
        });

        // 코인 초기 위치 설정
        generateCoin();

        // 게임 타이머 설정
        gameTimer = new Timer(30, this);

        // 30초 제한 타이머
        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                endGame();
            }
            repaint();
        });

        // 다시하기 버튼 설정
        retryButton = new JButton("다시 하기");
        retryButton.setVisible(false);
        retryButton.addActionListener(e -> restartGame());
        add(retryButton);
        setupRetryButton(); // 중앙 정렬 위치 설정

        startGame();
    }

    private void setupRetryButton() {
        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // 버튼을 화면 중앙에 위치
        retryButton.setBounds(screenWidth / 2 - 60, screenHeight / 2 - 25, 120, 50);
    }

    private void startGame() {
        score = 0;
        timeLeft = 30;
        retryButton.setVisible(false);
        generateCoin();
        gameTimer.start();
        countdownTimer.start();
        repaint();
    }

    private void generateCoin() {
        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // 코인 위치 X: 화면 너비 - 코인 크기
        coinX = random.nextInt(screenWidth - 50); 

        // 코인 위치 Y: 화면 높이 - 코인 크기, 작업 표시줄이 보이므로 높이를 고려하여 위치 설정
        coinY = random.nextInt(screenHeight - 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 고양이 이미지 그리기
        g.drawImage(currentCatImage.getImage(), catX, catY, 150, 70, this);

        // 코인 이미지 그리기
        g.drawImage(coinImage.getImage(), coinX, coinY, 50, 50, this);  // 코인 위치와 크기 설정

        // 점수 및 남은 시간 표시
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Time Left: " + timeLeft + "s", 10, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 고양이가 코인을 먹었는지 확인
        if (new Rectangle(catX, catY, 150, 70).intersects(new Rectangle(coinX, coinY, 50, 50))) {
            score += 100; // 점수 증가
            generateCoin(); // 새로운 코인 생성
        }

        repaint();
    }

    private void moveRight() {
        catX += 20;  // 오른쪽으로 이동
        isMovingRight = true;
        updateCatImage();  // 이미지 업데이트
    }

    private void moveLeft() {
        catX -= 20;  // 왼쪽으로 이동
        isMovingRight = false;
        updateCatImage();  // 이미지 업데이트
    }

    private void updateCatImage() {
        // 오른쪽으로 이동 시 이미지 설정
        if (isMovingRight) {
            currentCatImage = catWalkRight;  // 걷는 이미지
        } else {
            currentCatImage = catWalkLeft;  // 걷는 이미지
        }
    }

    private void endGame() {
        gameTimer.stop();
        countdownTimer.stop();
        retryButton.setVisible(true);
        JOptionPane.showMessageDialog(this, "Game Over! You collected " + score + " coins!");
    }

    private void restartGame() {
        startGame();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cat Hunting Game");
        CatHuntingGame game = new CatHuntingGame();

        frame.add(game);

        // 전체화면 설정
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 화면 최대화
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
