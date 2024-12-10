package Project;

import javax.swing.*;
import java.awt.*;

public class cat_ending {

    public void determineCatJob(
        int theology, int affection, int fatigue, int combatPower, int mzPower,
        int knowledge, int wildPower, int morality, int grace, int coins
    ) {
        String job = "";
        String imagePath = "";
        String backgroundImg = "C:/Users/leeya/OneDrive/Desktop/고양이/직업배경.png";

        // 직업 결정 로직
        if (theology >= 70 && fatigue >= 70) {
            job = "사이비교주 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/사이비 교주 고양이.png";
        } else if (affection >= 80 && mzPower >= 80) {
            job = "아이돌 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/아이돌 고양이.png";
        } else if (combatPower >= 70 && wildPower >= 70) {
            job = "사냥꾼 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/사냥꾼 고양이.png";
        } else if (mzPower >= 90 && theology < 70 && combatPower < 70 && knowledge < 70) {
            job = "엠지고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/엠지 고양이.png";
        } else if (knowledge >= 80 && coins < 100) {
            job = "대도고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/도둑 고양이.png";
        } else if (wildPower >= 80 && combatPower >= 80 && coins < 100) {
            job = "현상금 사냥꾼 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/현상꾼 고양이.png";
        } else if (combatPower >= 70 && knowledge >= 70) {
            job = "마법용사 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/마법용사_누끼_아이폰.png";
        } else if (knowledge >= 80 && morality >= 80) {
            job = "작가 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/작가 고양이.png";
        } else if (knowledge >= 90 && morality >= 90) {
            job = "마왕 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/마왕 고양이.png";
        } else if (cat_base.fatigue >= 80) {
            job = "가출 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/가출 고양이.png";
        } else if (grace >= 80 && morality >= 80) {
            job = "성직자 고양이";
            imagePath = "C:/Users/leeya/OneDrive/Desktop/고양이/성직자 고양이.png";
        }

        // 팝업 호출
        if (!job.isEmpty()) {
            showEndingPopup(job, imagePath, backgroundImg);
        }
    }

    private void showEndingPopup(String job, String imagePath, String backgroundImg) {
        JDialog endingDialog = new JDialog();
        endingDialog.setTitle("고양이 직업 엔딩");
        endingDialog.setSize(800, 600);
        endingDialog.setLocationRelativeTo(null); // 화면 중앙 배치
        endingDialog.setModal(true); // 모달 설정
        endingDialog.setLayout(null);

        // JLayeredPane 생성
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);

        // 배경 패널 생성
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image background = new ImageIcon(backgroundImg).getImage();
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    System.err.println("배경 이미지 로드 실패.");
                    e.printStackTrace();
                }
            }
        };
        backgroundPanel.setBounds(0, 0, 800, 600);

        // 축하 메시지
        JLabel messageLabel = new JLabel("축하합니다! 당신의 고양이는 \"" + job + "\"(이/가) 되었습니다!");
        messageLabel.setBounds(50, 10, 700, 50);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        // 고양이 이미지 표시
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(200, 100, 400, 400);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.err.println("고양이 이미지 로드 실패: " + imagePath);
            e.printStackTrace();
        }

        // 게임 종료 버튼
        JButton exitButton = new JButton("게임 종료");
        exitButton.setBounds(350, 520, 100, 30);
        exitButton.addActionListener(e -> {
            System.exit(0); // 모든 창 종료
        });

        // 컴포넌트 추가
        layeredPane.add(backgroundPanel, Integer.valueOf(0));
        layeredPane.add(messageLabel, Integer.valueOf(1));
        layeredPane.add(imageLabel, Integer.valueOf(1));
        layeredPane.add(exitButton, Integer.valueOf(1));

        endingDialog.add(layeredPane);
        endingDialog.setVisible(true);
    }
}