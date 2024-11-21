package CatGameProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class CatMazeGame extends JPanel {
    private static final int SIZE = 30; // 셀 크기
    private static final int ROWS = 15; // 미로 세로 크기
    private static final int COLS = 15; // 미로 가로 크기
    private int[][] maze; // 미로 배열
    private int catX = 1, catY = 1; // 고양이의 시작 위치
    private int exitX = 13, exitY = 13; // 탈출구 위치
    private boolean gameOver = false; // 게임 종료 상태
    private JButton restartButton;
    private long gameStartTime; // 게임 시작 시간

    public CatMazeGame() {
        maze = generateMaze(); // 미로 생성
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            moveCat(0, -1);
                            break;
                        case KeyEvent.VK_DOWN:
                            moveCat(0, 1);
                            break;
                        case KeyEvent.VK_LEFT:
                            moveCat(-1, 0);
                            break;
                        case KeyEvent.VK_RIGHT:
                            moveCat(1, 0);
                            break;
                    }
                }
            }
        });

        // 게임 시작 시간 기록
        gameStartTime = System.currentTimeMillis();

        // 다시하기 버튼 설정
        restartButton = new JButton("다시 하기");
        restartButton.setBounds(200, 400, 100, 30);
        restartButton.addActionListener(e -> restartGame());
        this.setLayout(null);
        this.add(restartButton);
    }

    // 미로 생성
    private int[][] generateMaze() {
        int[][] maze = new int[ROWS][COLS];

        // 초기 값은 -1로 설정
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = -1;
                if (i % 2 == 1 && j % 2 == 1) {
                    maze[i][j] = 1; // 갈 수 있는 공간
                } else if (i == 0 || j == 0 || i == maze.length - 1 || j == maze[i].length - 1) {
                    maze[i][j] = 0; // 벽
                } else {
                    maze[i][j] = 2; // 수정 가능한 영역
                }
            }
        }

        // 오른쪽 또는 아래쪽으로만 벽을 뚫음
        int[][] move2 = { { 1, 0 }, { 0, 1 } };
        Random random = new Random();
        for (int i = 1; i < maze.length; i += 2) {
            for (int j = 1; j < maze[i].length; j += 2) {
                int k = random.nextInt(2);
                if (maze[i + move2[k][0]][j + move2[k][1]] != 0) {
                    maze[i + move2[k][0]][j + move2[k][1]] = 1; // 길을 뚫기
                } else {
                    k = Math.abs(k - 1);
                    maze[i + move2[k][0]][j + move2[k][1]] = 1; // 다른 방향으로 길 뚫기
                }
            }
        }

        // 벽을 제거하고 경로를 추가
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 2) {
                    maze[i][j] = 0;
                }
            }
        }

        return maze;
    }

    // 고양이 이동
    private void moveCat(int dx, int dy) {
        int newX = catX + dx;
        int newY = catY + dy;

        // 벽에 부딪히지 않도록 이동
        if (newX >= 0 && newX < COLS && newY >= 0 && newY < ROWS && maze[newY][newX] != 0) {
            catX = newX;
            catY = newY;
        }

        // 탈출구에 도달하면 게임 종료
        if (catX == exitX && catY == exitY) {
            gameOver = true;
            long elapsedTime = System.currentTimeMillis() - gameStartTime;
            JOptionPane.showMessageDialog(this, "탈출 성공! 게임 종료.\n시간: " + elapsedTime / 1000.0 + "초");
        }

        repaint();
    }

    // 화면 그리기
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 미로 그리기 (벽을 선으로 그리기)
        g.setColor(Color.BLACK);

        // 벽 그리기
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (maze[y][x] == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * SIZE, y * SIZE, SIZE, SIZE); // 벽은 검은색
                }
            }
        }

        // 고양이 그리기
        g.setColor(Color.ORANGE);
        g.fillRect(catX * SIZE, catY * SIZE, SIZE, SIZE);

        // 탈출구 그리기
        g.setColor(Color.GREEN);
        g.fillRect(exitX * SIZE, exitY * SIZE, SIZE, SIZE);

        // 게임 종료 메시지
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("게임 종료! 탈출 성공.", 200, 200);
        }
    }

 // 게임 리셋 함수
    private void restartGame() {
        catX = 1;
        catY = 1;
        gameOver = false;
        maze = generateMaze();
        gameStartTime = System.currentTimeMillis(); // 새로 시작할 때마다 시간 초기화
        repaint();

        // 키 입력을 받을 수 있도록 포커스 요청
        setFocusable(true);
        requestFocusInWindow();
    }


    // 메인 함수
    public static void main(String[] args) {
        JFrame frame = new JFrame("고양이 미로 탈출 게임");
        CatMazeGame game = new CatMazeGame();
        frame.add(game);
        frame.setSize(480, 480); // 게임 화면 크기
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


