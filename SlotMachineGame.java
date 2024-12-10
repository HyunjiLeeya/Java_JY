package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SlotMachineGame extends JDialog {
    private JLabel[] slots; // 슬롯을 표시하는 라벨
    private JButton startButton; // 시작 버튼
    private JButton stopButton;  // 멈춤 버튼
    private JButton confirmButton; // 확인 버튼
    private JTextField betAmountField; // 배팅 금액
    private JLabel coinLabel; // 보유 코인 표시
    private int coins = cat_base.getTotalCoins(); // 부모 클래스에서 totalCoins 가져오기
    private int betAmount = 10; // 기본 배팅 금액
    private String[] cardImagePaths = { 
        "C:/Users/leeya/OneDrive/Desktop/고양이/겜블링 1.png", 
        "C:/Users/leeya/OneDrive/Desktop/고양이/겜블링 2.png", 
        "C:/Users/leeya/OneDrive/Desktop/고양이/겜블링 3.png", 
        "C:/Users/leeya/OneDrive/Desktop/고양이/겜블링 4.png", 
        "C:/Users/leeya/OneDrive/Desktop/고양이/겜블링 5.png"
    }; // 카드 이미지 경로
    private boolean isSpinning = false;  // 슬롯이 회전 중인지 확인
    private Thread slotThread;  // 슬롯 회전 쓰레드
    private JLabel totalCoinLabel; // 부모 프레임에 있는 총 코인 표시

    // 부모 프레임에서 totalCoins를 받아옴
    public SlotMachineGame(JFrame parentFrame, JLabel totalCoinLabel) {
        super(parentFrame, "Slot Machine Game", true); // JDialog로 부모 프레임을 받음 (모달)
        this.totalCoinLabel = totalCoinLabel;  // 부모 프레임의 totalCoinLabel 연결
        setSize(800, 600);  // 프레임 사이즈 변경
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 다이얼로그 닫기 설정
        setLayout(new BorderLayout(20, 20));

        // 슬롯 패널 (슬롯 3개)
        JPanel slotPanel = new JPanel();
        slotPanel.setLayout(new GridLayout(1, 3, 10, 10)); // 1행 3열로 슬롯 배치
        slotPanel.setPreferredSize(new Dimension(200, 200)); // 슬롯 크기 조정
        slots = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            slots[i] = new JLabel("", JLabel.CENTER);
            slots[i].setPreferredSize(new Dimension(100, 100)); // 각 슬롯 크기 조정
            slots[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            slotPanel.add(slots[i]);
        }
        add(slotPanel, BorderLayout.CENTER);

        // 배팅 금액 및 보유 코인 표시 패널
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        betAmountField = new JTextField(String.valueOf(betAmount), 5);
        infoPanel.add(new JLabel("배팅 금액:"));
        infoPanel.add(betAmountField);

        coinLabel = new JLabel("보유 코인: " + coins);
        infoPanel.add(coinLabel);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSlotMachine();
            }
        });
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false); // 처음엔 멈춤 버튼 비활성화
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSlotMachine();
            }
        });
        buttonPanel.add(stopButton);

        // 확인 버튼 추가
        confirmButton = new JButton("확인");
        confirmButton.setVisible(false); // 게임 종료 시에만 표시
        confirmButton.addActionListener(e -> closeGameWindow());
        buttonPanel.add(confirmButton);

        add(infoPanel, BorderLayout.NORTH);  // 배팅 금액 및 보유 코인
        add(buttonPanel, BorderLayout.SOUTH); // 버튼들 배치

        // 창 위치 설정
        setLocationRelativeTo(parentFrame);
        setVisible(true);
    }

    private void startSlotMachine() {
        // 배팅 금액 확인
        betAmount = Integer.parseInt(betAmountField.getText());
        if (betAmount > coins) {
            JOptionPane.showMessageDialog(this, "Insufficient coins!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 슬롯 회전 시작
        isSpinning = true;
        stopButton.setEnabled(true); // 멈춤 버튼 활성화
        startButton.setEnabled(false); // 시작 버튼 비활성화
        slotThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int[] result = new int[3];

                // 무한 루프 시작
                while (isSpinning) {
                    for (int i = 0; i < 3; i++) {
                        result[i] = rand.nextInt(5); // 5개의 이미지 중 랜덤 선택
                        ImageIcon icon = new ImageIcon(cardImagePaths[result[i]]); // 이미지 로드
                        // 이미지 크기를 프레임에 맞게 조정
                        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(img);
                        slots[i].setIcon(icon); // 라벨에 이미지 설정
                    }

                    try {
                        Thread.sleep(5); // 슬롯 애니메이션 속도
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 슬롯 멈췄을 때 결과 확인
                calculatePayout(result);
            }
        });
        slotThread.start();
    }

    private void stopSlotMachine() {
        // 슬롯 멈추기
        isSpinning = false;
        startButton.setEnabled(false); // 시작 버튼 비활성화
        stopButton.setEnabled(false); // 멈춤 버튼 비활성화
        confirmButton.setVisible(true); // 확인 버튼 활성화
    }

    private void calculatePayout(int[] result) {
        // 카드 매칭 확인
        boolean allMatch = (result[0] == result[1] && result[1] == result[2]);
        boolean pairMatch = (result[0] == result[1] || result[1] == result[2] || result[0] == result[2]);
        String winningCard = "C:/Users/leeya/OneDrive/Desktop/고양이/겜블링 1.png"; // 특정 카드

        int payout = 0;

        if (allMatch) {
            // 3개 동일한 카드
            payout = 2 * betAmount;
            if (cardImagePaths[result[0]].equals(winningCard)) {
                // 특정 카드일 경우 5배
                payout = 5 * betAmount;
            }
        } else if (pairMatch) {
            // 2개 동일한 카드
            payout = (int)(1.5 * betAmount);
        }

        // 코인 업데이트
        if (payout > 0) {
            coins += payout;
            totalCoinLabel.setText("Coins: " + coins);  // 부모 프레임의 totalCoins 업데이트
            JOptionPane.showMessageDialog(this, "이겼습니다!" + payout + " 코인", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            coins -= betAmount;
            totalCoinLabel.setText("Coins: " + coins);  // 부모 프레임의 totalCoins 업데이트
            JOptionPane.showMessageDialog(this, " 졌습니다! " + betAmount + " 코인", "Try Again", JOptionPane.INFORMATION_MESSAGE);
        }

        // 보유 코인 업데이트
        coinLabel.setText("보유 코인: " + coins);
    }

    private void closeGameWindow() {
        dispose(); // 게임 창 닫기
    }

    public static void openSlotMachineGame(JFrame parentFrame, JLabel totalCoinLabel) {
        new SlotMachineGame(parentFrame, totalCoinLabel); // 팝업 형태로 슬롯 머신 게임 실행
    }
}
