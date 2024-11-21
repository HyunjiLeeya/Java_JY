package CatGameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatRunGame extends JPanel implements ActionListener {
    private int catY = 250; // 고양이의 Y 위치
    private int obstacleX = 800; // 장애물의 X 위치
    private boolean jumping = false; // 점프 상태
    private int jumpVelocity = 0; // 점프 속도
    private int score = 0; // 점수
    private int speed = 5; // 장애물 이동 속도
    private Timer timer; // 게임 루프 타이머

    private JFrame frame; // 게임 프레임
    private JButton retryButton; // 다시하기 버튼
    private int coins = 0; // 코인

    public CatRunGame(JFrame frame) {
        this.frame = frame;

        // 게임 타이머 설정
        timer = new Timer(20, this);
        timer.start();

        // 키보드 입력 처리
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE && !jumping) {
                    jumping = true;
                    jumpVelocity = -15; // 점프 속도
                }
            }
        });
        setFocusable(true);

        // 다시하기 버튼 설정
        retryButton = new JButton("다시하기");
        retryButton.setBounds(350, 150, 100, 50);
        retryButton.setVisible(false);
        retryButton.addActionListener(e -> resetGame());
        this.setLayout(null);
        this.add(retryButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 색상
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 고양이 그리기
        g.setColor(Color.ORANGE);
        g.fillRect(100, catY, 50, 50);

        // 장애물 그리기
        g.setColor(Color.RED);
        g.fillRect(obstacleX, 300, 50, 50);

        // 점수 표시
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 10);
        g.drawString("Coins: " + coins, 10, 30); // 코인 표시
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 장애물 이동
        obstacleX -= speed; // 속도에 따라 장애물이 이동
        if (obstacleX < -50) {
            obstacleX = 800; // 장애물 위치 초기화
            score += 10; // 장애물 통과 시 점수 증가
            increaseDifficulty(); // 난이도 증가
        }

        // 점프 처리
        if (jumping) {
            catY += jumpVelocity;
            jumpVelocity += 1; // 중력 효과
            if (catY >= 250) { // 바닥에 닿으면 점프 종료
                catY = 250;
                jumping = false;
            }
        }

        // 충돌 감지
        if (obstacleX < 150 && obstacleX > 100 && catY >= 250) {
            gameOver();
        }

        // 화면 다시 그리기
        repaint();
    }

    private void increaseDifficulty() {
        // 일정 점수마다 장애물 속도 증가
        if (score % 50 == 0 && speed < 15) { // 장애물 속도가 너무 빠르게 증가하지 않도록 제한
            speed += 1; // 장애물 속도 증가
        }
    }

    private void gameOver() {
        timer.stop(); // 게임 루프 중지
        retryButton.setVisible(true); // 다시하기 버튼 표시
        coins = calculateCoins(score); // 최종 점수에 맞는 코인 계산
        JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score + "\nCoins Earned: " + coins);
    }

    private int calculateCoins(int finalScore) {
        if (finalScore <= 30) {
            return 5; // 0~30점일 때 5코인
        } else if (finalScore <= 60) {
            return 15; // 31~60점일 때 15코인
        } else if (finalScore <= 90) {
            return 30; // 61~90점일 때 30코인
        } else {
            return 50; // 91점 이상일 때 50코인
        }
    }

    private void resetGame() {
        // 게임 상태 초기화
        catY = 250;
        obstacleX = 800;
        jumping = false;
        jumpVelocity = 0;
        score = 0;
        speed = 5; // 장애물 속도 초기화
        coins = 0; // 코인 초기화

        retryButton.setVisible(false); // 다시하기 버튼 숨기기
        timer.start(); // 게임 루프 재시작
        repaint(); // 화면 갱신
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cat Run Game");
        CatRunGame game = new CatRunGame(frame);
        frame.add(game);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

