package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Catgambling extends JFrame {
    private JTextField coinInputField;
    private JLabel resultLabel;
    private JLabel currentCoinsLabel;
    private JButton drawButton;
    private JButton confirmButton;
    private Random random;
    private int coins; // cat_base의 totalCoins 값을 저장
    private int winnings = 0;

    public Catgambling(JLabel coinLabel) {
        this.coins = cat_base.getTotalCoins(); // cat_base의 초기 코인 값

        setTitle("운명의 베팅 게임");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));
        random = new Random();

        Font font = new Font("맑은 고딕", Font.PLAIN, 24);

        // 상단 패널
        JPanel topPanel = new JPanel(new BorderLayout(30, 30));
        JLabel instructionLabel = new JLabel("배팅 금액을 입력하고 '뽑기' 버튼을 클릭하세요!", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));
        coinInputField = new JTextField();
        coinInputField.setFont(font);
        topPanel.add(instructionLabel, BorderLayout.NORTH);
        topPanel.add(new JLabel("  배팅 금액: ", SwingConstants.CENTER), BorderLayout.WEST);
        topPanel.add(coinInputField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 중앙 패널
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 50, 50));
        currentCoinsLabel = new JLabel("현재 금액: " + coins, SwingConstants.CENTER);
        currentCoinsLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        resultLabel = new JLabel("결과: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        centerPanel.add(currentCoinsLabel);
        centerPanel.add(resultLabel);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 패널
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        drawButton = new JButton("뽑기");
        drawButton.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        drawButton.addActionListener(e -> drawMultiplier());
        bottomPanel.add(drawButton);

        confirmButton = new JButton("확인");
        confirmButton.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        confirmButton.setEnabled(false); // 뽑기 버튼을 눌러야 활성화
        confirmButton.addActionListener(e -> {
            cat_base.setTotalCoins(coins); // totalCoins 업데이트
            cat_base.updateCoinLabel(); // coinLabel 업데이트
            dispose(); // 현재 프레임 닫기
        });
        bottomPanel.add(confirmButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void drawMultiplier() {
        String coinInput = coinInputField.getText().trim();
        try {
            int betAmount = Integer.parseInt(coinInput);
            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(this, "0코인 이상의 금액만 배팅하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (betAmount > coins) {
                JOptionPane.showMessageDialog(this, "금액이 충분하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double randomMultiplier;
            if (random.nextDouble() <= 0.25) {
                randomMultiplier = random.nextDouble();
            } else {
                randomMultiplier = 1 + random.nextDouble() * 5;
            }
            winnings = (int) Math.round(betAmount * randomMultiplier);
            coins -= betAmount; // 배팅 금액 차감
            coins += winnings; // 당첨 금액 추가

            resultLabel.setText(String.format("결과: %d (Multiplier: %.2f)", winnings, randomMultiplier));
            currentCoinsLabel.setText("현재 금액: " + coins);

            drawButton.setEnabled(false); // 뽑기 버튼 비활성화
            confirmButton.setEnabled(true); // 확인 버튼 활성화

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 숫자를 입력하세요", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void openCatgambling(JFrame parentFrame, JLabel coinLabel) {
        Catgambling catgamblingGame = new Catgambling(coinLabel);
        catgamblingGame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Catgambling app = new Catgambling(cat_base.coinLabel);
            app.setVisible(true);
        });
    }
}
