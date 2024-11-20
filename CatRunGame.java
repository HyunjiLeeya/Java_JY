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

    public CatRunGame() {
        Timer timer = new Timer(20, this); // 게임 루프 타이머 (20ms마다 업데이트)
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 색상
        g.setColor(Color.CYAN);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 장애물 이동
        obstacleX -= 5;
        if (obstacleX < -50) {
            obstacleX = 800;
            score += 10; // 장애물 통과 시 점수 증가
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
            JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
            System.exit(0); // 게임 종료
        }

        // 화면 다시 그리기
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cat Run Game");
        CatRunGame game = new CatRunGame();
        
        frame.add(game);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
