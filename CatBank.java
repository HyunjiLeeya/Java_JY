package Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CatBank extends cat_base {
    private int totalCoins; // 초기 총 코인
    private int savingsAmount = 0; // 적금액
    private double interestRate = 0.01; // 10분마다 1% 이자
    private Random random = new Random();
    private Timer timer = new Timer();
    private UpdateCoinsCallback callback; // 코인 업데이트 콜백

    // GUI 컴포넌트
    private JFrame frame;
    private JLabel totalCoinsLabel;
    private JLabel savingsStatusLabel;
    private JButton startSavingsButton;
    private JButton endSavingsButton;

    // 생성자
    public CatBank(int totalCoins, UpdateCoinsCallback callback) {
        this.totalCoins = totalCoins;
        this.callback = callback;

        frame = new JFrame("적금하기");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        totalCoinsLabel = new JLabel("Total Coins: " + totalCoins);
        totalCoinsLabel.setBounds(50, 30, 300, 30);
        frame.add(totalCoinsLabel);

        savingsStatusLabel = new JLabel("적금 이력이 없습니다");
        savingsStatusLabel.setBounds(50, 70, 300, 30);
        frame.add(savingsStatusLabel);

        startSavingsButton = new JButton("적금하기");
        startSavingsButton.setBounds(50, 120, 150, 30);
        startSavingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSavings();
            }
        });
        frame.add(startSavingsButton);

        endSavingsButton = new JButton("적금깨기");
        endSavingsButton.setBounds(220, 120, 150, 30);
        endSavingsButton.setEnabled(false);
        endSavingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endSavings();
            }
        });
        frame.add(endSavingsButton);
    }

    public void showBank() {
        frame.setVisible(true);
    }

    private void startSavings() {
        if (savingsAmount > 0) {
            JOptionPane.showMessageDialog(frame, "적금이 진행중입니다");
            return;
        }

        String input = JOptionPane.showInputDialog(frame, "적금 할 금액을 입력해주세요.");
        if (input == null || input.isEmpty()) {
            return;
        }

        try {
            int amount = Integer.parseInt(input);
            if (amount <= 0 || amount > totalCoins) {
                JOptionPane.showMessageDialog(frame, "보유코인이 적습니다");
                return;
            }

            // 적금 시작
            totalCoins -= amount;
            savingsAmount = amount;
            totalCoinsLabel.setText("Total Coins: " + totalCoins);
            savingsStatusLabel.setText("잔액 : " + savingsAmount);

            startSavingsButton.setEnabled(false);
            endSavingsButton.setEnabled(true);

            // 이자 및 파산 처리 시작
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    applyInterest();
                }
            }, 600000, 600000); // 10분마다 실행

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "유효한 금액을 입력해주세요");
        }
    }

    private void endSavings() {
        if (savingsAmount == 0) {
            JOptionPane.showMessageDialog(frame, "적금이력이 없습니다");
            return;
        }

        totalCoins += savingsAmount;
        callback.onCoinsUpdated(totalCoins); // 업데이트 콜백 호출
        totalCoinsLabel.setText("Total Coins: " + totalCoins);
        savingsStatusLabel.setText("최종 금액: " + savingsAmount);

        savingsAmount = 0;
        startSavingsButton.setEnabled(true);
        endSavingsButton.setEnabled(false);

        timer.cancel(); // 타이머 종료
        timer = new Timer(); // 새로운 타이머 생성
    }

    private void applyInterest() {
        if (savingsAmount == 0) return;

        if (random.nextInt(100) == 0) { // 1% 확률로 파산
            savingsAmount = (int) (savingsAmount * 0.3); // 30%만 반환
            savingsStatusLabel.setText("은행이 파산했습니다 ! : " + savingsAmount);
            endSavings();
        } else {
            savingsAmount += savingsAmount * interestRate;
            savingsStatusLabel.setText("원금과 이자금액 : " + savingsAmount);
        }
    }

    public interface UpdateCoinsCallback {
        void onCoinsUpdated(int newTotalCoins);
    }
}
