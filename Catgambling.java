package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Catgambling extends JFrame {
    private JTextField coinInputField;
    private JLabel resultLabel;  // 결과 출력
    private JLabel currentCoinsLabel;  // 현재 보유 코인 표시
    private JLabel coinLabel;  // 부모 프레임에서 전달받은 coinLabel
    private Random random;  // 랜덤 객체
    private double coins = cat_base.getTotalCoins(); // 부모 클래스에서 totalCoins 가져오기

    // 생성자에서 coinLabel을 받도록 수정
    public Catgambling(JLabel coinLabel) {
        this.coinLabel = coinLabel;  // 부모 프레임의 coinLabel 저장

        // 기본 설정
        setTitle("운명의 베팅 게임");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);  // 화면 크기를 800x600으로 설정
        setLayout(new BorderLayout(10, 10)); // 레이아웃을 BorderLayout으로 설정
        random = new Random();

        // 한글을 지원하는 폰트로 설정 (맑은 고딕)
        Font font = new Font("맑은 고딕", Font.PLAIN, 24);

        // 상단 패널: 입력 안내 및 입력 필드
        JPanel topPanel = new JPanel(new BorderLayout(30, 30));
        JLabel instructionLabel = new JLabel("배팅 금액을 입력하고 '뽑기' 버튼을 클릭하세요!", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));  // 안내 텍스트 크기
        coinInputField = new JTextField();
        coinInputField.setFont(font);  // 입력 필드 크기
        topPanel.add(instructionLabel, BorderLayout.NORTH);
        topPanel.add(new JLabel("  배팅 금액: ", SwingConstants.CENTER), BorderLayout.WEST);
        topPanel.add(coinInputField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 중앙 패널: 현재 코인 및 결과 출력
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 50, 50));
        currentCoinsLabel = new JLabel("현재 금액: " + String.format("%.2f", coins), SwingConstants.CENTER);
        currentCoinsLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));  // 현재 코인 표시 크기
        resultLabel = new JLabel("결과: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 36));  // 결과 텍스트 크기
        centerPanel.add(currentCoinsLabel);
        centerPanel.add(resultLabel);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 패널: 뽑기 버튼
        JPanel bottomPanel = new JPanel();
        JButton drawButton = new JButton("뽑기");
        drawButton.setFont(new Font("맑은 고딕", Font.BOLD, 28));  // 버튼 글씨 크기
        drawButton.setPreferredSize(new Dimension(250, 80));  // 버튼 크기
        drawButton.addActionListener(e -> drawMultiplier());
        bottomPanel.add(drawButton);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // 중앙 정렬
        add(bottomPanel, BorderLayout.SOUTH);

        // 화면이 800x600 크기에 맞도록 버튼, 텍스트 필드 크기 등 조정
        setLocationRelativeTo(null); // 창을 화면 중앙에 위치시킴
    }

    private void drawMultiplier() {
        String coinInput = coinInputField.getText().trim();
        try {
            // 사용자 입력 배팅 금액 확인
            double betAmount = Double.parseDouble(coinInput);
            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(this, "0코인 이상의 금액만 배팅하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (betAmount > coins) {
                JOptionPane.showMessageDialog(this, "금액이 충분하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 0.25의 확률로 0~1 사이의 실수를 생성
            double randomMultiplier;
            if (random.nextDouble() <= 0.25) {
                // 0~1 사이의 실수 생성
                randomMultiplier = random.nextDouble();  // 0.0 ~ 1.0
            } else {
                // 1~5 사이의 실수 생성
                randomMultiplier = 1 + random.nextDouble() * 5;  // 1.0 ~ 5.0
            }
            double winnings = betAmount * randomMultiplier;
            // 결과 및 현재 코인 표시
            resultLabel.setText(String.format("결과: %.2f (Multiplier: %.2f)", winnings, randomMultiplier));

            // 확인 창을 띄워서, 사용자에게 결과 확인을 받음
            int confirmation = JOptionPane.showConfirmDialog(this, 
                "배팅 금액: " + betAmount + "\n결과: " + winnings + "\n배팅 후 잔액: " + (coins - betAmount + winnings) + "\n확인 하시겠습니까?", 
                "결과 확인", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                // 사용자가 "확인"을 눌렀을 때, 코인 업데이트
                coins -= betAmount;  // 배팅 금액 차감
                coins += winnings;   // 뽑기 결과 추가

                // 현재 금액 업데이트
                currentCoinsLabel.setText("현재 금액: " + String.format("%.2f", coins));

                // 부모 프레임의 coinLabel 업데이트
                coinLabel.setText("현재 금액: " + String.format("%.2f", coins));

                // cat_base의 totalCoins 업데이트
                cat_base.setTotalCoins(coins);  // 부모 클래스의 totalCoins 업데이트
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 숫자를 금액을 입력하세요", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void openCatgambling(JFrame parentFrame, JLabel coinLabel) {
        Catgambling catgamblingGame = new Catgambling(coinLabel);  // coinLabel을 전달
        catgamblingGame.setVisible(true);  // 게임 팝업 표시
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Catgambling app = new Catgambling(null);  // 게임 시작
            app.setVisible(true);
        });
    }
}
