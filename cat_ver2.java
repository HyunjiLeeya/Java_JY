package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// 부모 클래스 cat_base
public class cat_ver2 {

    public static void main(String[] args) {
        // 메인 프레임 생성
        JFrame frame = new JFrame("고양이 키우기 게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // 프레임 크기 고정
        frame.setLayout(null); // 절대 위치 레이아웃

        // 배경 패널 설정
        JPanel background = new JPanel();
        background.setBackground(new Color(255, 235, 205)); // 연한 크림색
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        background.setLayout(null);

        // 인벤토리 버튼
        JButton inventoryButton = new JButton("Inventory");
        inventoryButton.setBounds(10, 10, 150, 40);
        inventoryButton.setFont(new Font("Arial", Font.BOLD, 14));
        background.add(inventoryButton);

        // 고양이 이미지
        ImageIcon icon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/cat.png");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel catLabel = new JLabel(new ImageIcon(scaledImage));
        catLabel.setBounds(frame.getWidth() / 2 - 50, frame.getHeight() / 2 - 50, 100, 100);
        background.add(catLabel);

        // 상태 정보 패널
        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(600, 10, 180, 200); // 우측 상단
        statusPanel.setLayout(new GridLayout(5, 2));

        JLabel experienceLabel = new JLabel("경험치: 0");
        JLabel affectionLabel = new JLabel("애정도: 0");
        JLabel knowledgeLabel = new JLabel("지식: 0");
        JLabel combatPowerLabel = new JLabel("전투력: 0");
        JLabel fatigueLabel = new JLabel("피로도: 0");

        statusPanel.add(experienceLabel);
        statusPanel.add(affectionLabel);
        statusPanel.add(knowledgeLabel);
        statusPanel.add(combatPowerLabel);
        statusPanel.add(fatigueLabel);

        background.add(statusPanel);

        // 활동 버튼
        JButton activityButton = new JButton("Activities");
        activityButton.setBounds(10, 60, 150, 40);
        activityButton.setFont(new Font("Arial", Font.BOLD, 14));
        background.add(activityButton);

        // 인벤토리 버튼 클릭 이벤트
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 인벤토리 창 열기
                createInventoryDialog(frame);
            }
        });

        // 프레임에 추가
        frame.add(background);
        frame.setVisible(true);

        // 상태 업데이트 변수
        int experience = 0;
        int affection = 0;
        int knowledge = 0;
        int combatPower = 0;
        int fatigue = 0;

        // 활동 버튼 클릭 이벤트
        activityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 활동 창 열기
                CatActivities activities = new CatActivities(experience, affection, knowledge, combatPower, fatigue, experienceLabel, affectionLabel, knowledgeLabel, combatPowerLabel, fatigueLabel);
                activities.openActivityDialog(frame);
            }
        });
    }

    // 인벤토리 다이얼로그 생성
    public static void createInventoryDialog(JFrame frame) {
        // 인벤토리 다이얼로그
        JDialog inventoryDialog = new JDialog(frame, "인벤토리", true);
        inventoryDialog.setSize(400, 300);
        inventoryDialog.setLayout(new BorderLayout());

        // 인벤토리 내용 표시 (예시)
        JTextArea inventoryArea = new JTextArea();
        inventoryArea.setText("고양이 사료\n고양이 장난감\n고양이 침대\n");
        inventoryArea.setEditable(false);

        // 다이얼로그에 텍스트 영역 추가
        inventoryDialog.add(new JScrollPane(inventoryArea), BorderLayout.CENTER);

        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryDialog.dispose();
            }
        });
        inventoryDialog.add(closeButton, BorderLayout.SOUTH);

        // 다이얼로그 보이기
        inventoryDialog.setVisible(true);
    }
}


// 자식 클래스 CatActivities
class CatActivities extends cat_ver2 {

    private int experience;
    private int affection;
    private int knowledge;
    private int combatPower;
    private int fatigue;

    private final String[] subjects = {
            "자연과학", "신학", "군사학", "검술", "마법",
            "문학", "예법", "무용", "미술", "격투술"
    };
    private final String[] careActions = {
            "밥주기", "간식주기", "물먹기", "목욕하기", "빗질하기", "산책하기"
    };

    private final String[] leisureActions = {
            "코인벌기", "쇼핑하기", "낮잠자기", "사교활동", "무술대회 나가기",
            "도박장에 가기", "주식하기", "운동가기"
    };

    private JLabel experienceLabel, affectionLabel, knowledgeLabel, combatPowerLabel, fatigueLabel;

    // 생성자
    public CatActivities(int experience, int affection, int knowledge, int combatPower, int fatigue, JLabel experienceLabel, JLabel affectionLabel, JLabel knowledgeLabel, JLabel combatPowerLabel, JLabel fatigueLabel) {
        this.experience = experience;
        this.affection = affection;
        this.knowledge = knowledge;
        this.combatPower = combatPower;
        this.fatigue = fatigue;

        this.experienceLabel = experienceLabel;
        this.affectionLabel = affectionLabel;
        this.knowledgeLabel = knowledgeLabel;
        this.combatPowerLabel = combatPowerLabel;
        this.fatigueLabel = fatigueLabel;
    }

    // 활동 창 생성 메서드
    public void openActivityDialog(JFrame parentFrame) {
        JDialog activityDialog = new JDialog(parentFrame, "활동", true);
        activityDialog.setSize(600, 400);
        activityDialog.setLocationRelativeTo(parentFrame);

        JTabbedPane tabbedPane = new JTabbedPane();

        // 교육하기 탭
        JPanel educationPanel = new JPanel(new BorderLayout());
        educationPanel.add(new JLabel("교육하기 탭입니다.", SwingConstants.CENTER), BorderLayout.CENTER);

        // 2열 5행 GridLayout으로 학문 항목 배치
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(5, 2));  // 2열 5행
        for (String subject : subjects) {
            JButton subjectButton = new JButton(subject);
            subjectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 선택한 학문에 대해 고양이의 상태를 랜덤으로 출력
                    Random rand = new Random();
                    String randomFeeling = rand.nextBoolean() ? "흥미로워합니다." : "행복해합니다.";
                    JOptionPane.showMessageDialog(activityDialog, "고양이가 " + randomFeeling);

                    // 상태 업데이트
                    if (randomFeeling.equals("흥미로워합니다.") || randomFeeling.equals("행복해합니다.")) {
                        if (subject.equals("자연과학") || subject.equals("신학") || subject.equals("군사학") || subject.equals("미술") || subject.equals("문학") || subject.equals("예법")) {
                            knowledge += 3;
                        } else if (subject.equals("무용") || subject.equals("미술") || subject.equals("격투술") || subject.equals("검술") || subject.equals("마법")) {
                            combatPower += 3;
                        }
                    } else if (randomFeeling.equals("피곤해합니다.") || randomFeeling.equals("지루해합니다.")) {
                        fatigue += 2;
                    }

                    // 상태 갱신
                    updateLabels();
                }
            });
            gridPanel.add(subjectButton);
        }
        educationPanel.add(gridPanel, BorderLayout.CENTER);

        tabbedPane.addTab("교육하기", educationPanel);

        // 돌보기 탭
        JPanel carePanel = new JPanel(new BorderLayout());
        carePanel.add(new JLabel("돌보기 탭입니다.", SwingConstants.CENTER), BorderLayout.CENTER);

        // 3열 2행 GridLayout으로 돌보기 항목 배치
        JPanel careGridPanel = new JPanel();
        careGridPanel.setLayout(new GridLayout(2, 3));  // 3열 2행
        for (String action : careActions) {
            JButton careButton = new JButton(action);
            careButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 고양이 반응 랜덤 출력
                    Random rand = new Random();
                    String[] careFeelings = {
                            "행복해합니다.", "만족합니다.", "애정도가 올라갑니다.", "귀찮아합니다."
                    };
                    String randomFeeling = careFeelings[rand.nextInt(careFeelings.length)];
                    JOptionPane.showMessageDialog(activityDialog, "고양이가 " + randomFeeling);

                    // 상태 업데이트
                    if (randomFeeling.equals("행복해합니다.") || randomFeeling.equals("만족합니다.")) {
                        affection += 1;
                        fatigue -= 1;
                    } else if (randomFeeling.equals("애정도가 올라갑니다.")) {
                        affection += 3;
                        fatigue -= 1;
                    } else if (randomFeeling.equals("귀찮아합니다.")) {
                        fatigue += 1;
                    }

                    // 상태 갱신
                    updateLabels();
                }
            });
            careGridPanel.add(careButton);
        }
        carePanel.add(careGridPanel, BorderLayout.CENTER);
        tabbedPane.addTab("돌보기", carePanel);

        // 여가활동 탭
        JPanel leisurePanel = new JPanel(new BorderLayout());
        leisurePanel.add(new JLabel("여가활동 탭입니다.", SwingConstants.CENTER), BorderLayout.CENTER);

        // 2열 4행 GridLayout으로 여가활동 항목 배치
        JPanel leisureGridPanel = new JPanel();
        leisureGridPanel.setLayout(new GridLayout(4, 2));  // 2열 4행
        for (String action : leisureActions) {
            JButton leisureButton = new JButton(action);
            leisureButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 고양이 반응 랜덤 출력
                    Random rand = new Random();
                    String[] leisureFeelings = {
                            "행복해합니다.", "지루해합니다.", "흥미로워합니다.", "피곤해합니다."
                    };
                    String randomFeeling = leisureFeelings[rand.nextInt(leisureFeelings.length)];
                    JOptionPane.showMessageDialog(activityDialog, "고양이가 " + randomFeeling);

                    // 상태 업데이트
                    if (randomFeeling.equals("흥미로워합니다.") || randomFeeling.equals("행복해합니다.")) {
                        knowledge += 2;
                        combatPower += 2;
                    } else if (randomFeeling.equals("지루해합니다.")) {
                        fatigue += 2;
                    } else if (randomFeeling.equals("피곤해합니다.")) {
                        fatigue += 1;
                    }

                    // 상태 갱신
                    updateLabels();
                }
            });
            leisureGridPanel.add(leisureButton);
        }
        leisurePanel.add(leisureGridPanel, BorderLayout.CENTER);
        tabbedPane.addTab("여가활동", leisurePanel);

        // 탭을 다이얼로그에 추가
        activityDialog.add(tabbedPane);

        // 다이얼로그 표시
        activityDialog.setVisible(true);
    }

    // 상태 갱신 메서드
    private void updateLabels() {
        // 각 상태 업데이트
        experienceLabel.setText("경험치: " + experience);
        affectionLabel.setText("애정도: " + affection);
        knowledgeLabel.setText("지식: " + knowledge);
        combatPowerLabel.setText("전투력: " + combatPower);
        fatigueLabel.setText("피로도: " + fatigue);
    }
}

        		