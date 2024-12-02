package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// 부모 클래스 cat_base
public class cat_base {
	static int totalCoins = 0; // 전체 코인
    static JLabel coinLabel; // 코인 표시 라벨
    

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

        // 코인 라벨 추가
        coinLabel = new JLabel("Coins: " + totalCoins);
        coinLabel.setBounds(20, 20, 150, 30);
        coinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        background.add(coinLabel);
        

        // 고양이 이미지
        ImageIcon icon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/메인고양이.png");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel catLabel = new JLabel(new ImageIcon(scaledImage));
        catLabel.setBounds(frame.getWidth() / 2 - 50, frame.getHeight() / 2 - 50, 100, 100);
        background.add(catLabel);

        // 상태 정보 패널
        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(600, 10, 180, 200); // 우측 상단
        statusPanel.setLayout(new GridLayout(5, 2));

        JLabel affectionLabel = new JLabel("애정도: 0");
        JLabel knowledgeLabel = new JLabel("지식: 0");
        JLabel staminaLabel = new JLabel("체력 : 0");
        JLabel combatPowerLabel = new JLabel("전투력: 0");
        JLabel theologyLabel = new JLabel("신학: 0");
        JLabel MZLabel = new JLabel("엠지력 : 0");
        JLabel wildPowerLabel = new JLabel("야생력 : 0");
        JLabel moralityLabel = new JLabel("도덕 : 0");
        JLabel graceLabel = new JLabel("기품 : 0");
        JLabel confidenceLabel = new JLabel("자신감 : 0");
        JLabel charmLabel = new JLabel("매력 : 0");
        JLabel fatigueLabel = new JLabel("피로도 : 0");


        statusPanel.add(affectionLabel);
        statusPanel.add(knowledgeLabel);
        statusPanel.add(staminaLabel);       
        statusPanel.add(combatPowerLabel);
        statusPanel.add(fatigueLabel);
        statusPanel.add(theologyLabel);      
        statusPanel.add(MZLabel);           
        statusPanel.add(wildPowerLabel);    
        statusPanel.add(moralityLabel);     
        statusPanel.add(graceLabel);        
        statusPanel.add(confidenceLabel);   
        statusPanel.add(charmLabel);         


        background.add(statusPanel);

        // 활동 버튼
        JButton activityButton = new JButton("Activities");
        activityButton.setBounds(10, 60, 150, 40);
        activityButton.setFont(new Font("Arial", Font.BOLD, 14));
        background.add(activityButton);

        ;

        // 프레임에 추가
        frame.add(background);
        frame.setVisible(true);
        
        

     // 상태 업데이트 변수 초기화
        int affection = 0;
        int knowledge = 0;
        int stamina = 0;
        int combatPower = 0;
        int fatigue = 0;
        int theology = 0;
        int mzPower = 0;
        int wildPower = 0;
        int morality = 0;
        int grace = 0;
        int confidence = 0;
        int charm = 0;
        

        // 활동 버튼 클릭 이벤트
        activityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 활동 창 열기
                CatActivities activities = new CatActivities(
                    affection, knowledge, stamina, combatPower, fatigue, theology, mzPower, wildPower, morality, grace, confidence, charm,
                    affectionLabel, knowledgeLabel, staminaLabel, combatPowerLabel, fatigueLabel, theologyLabel, MZLabel, wildPowerLabel,
                    moralityLabel, graceLabel, confidenceLabel, charmLabel );
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

    public static int getTotalCoins() {
        return totalCoins;
    }

    // totalCoins 값을 설정하는 setter 메서드
    public static void setTotalCoins(double coins) {
        totalCoins = (int) coins;
    }
}


// 자식 클래스 CatActivities
class CatActivities extends cat_base {

	 private int affection;
	 private int knowledge;
	 private int combatPower;
	 private int fatigue;
	 private int theology;
	 private int mzPower;
	 private int wildPower;
	 private int morality;
	 private int grace;
	 private int confidence;
	 private int charm;
	 private int stamina; 
	 
	 private void setFatigue(int value) {
		    fatigue = Math.max(value, 0); // 값이 0보다 작으면 0으로 설정
		}

    private final String[] subjects = {
            "신학 수업", "기도 수업", "마법 연습", "자연탐구", "야외생존 훈련",
            "철학 수업", "예절 교육", "체력 단련", "문학 교육", "검술훈련", "격투술", "연설 수업", "연극 수업",
            "음악 수업", "춤 수업", "수학", "지구과학", "과학실험", "예술 수업","틱톡 수업"
    };
    private final String[] careActions = {
            "밥주기", "간식주기", "물먹기", "목욕하기", "빗질하기", "산책하기", "낮잠자기","놀아주기"
    };

    private final String[] leisureActions = {
            "장애물 달리기로 코인벌기","코인 사냥하기", "슬롯머신 돌리기", "무술대회 나가기",
            "뽑기게임하기", "은행 가기" 
    };

    private JLabel affectionLabel;
    private JLabel knowledgeLabel;
    private JLabel combatPowerLabel;
    private JLabel fatigueLabel;
    private JLabel theologyLabel;
    private JLabel mzPowerLabel;
    private JLabel wildPowerLabel;
    private JLabel moralityLabel;
    private JLabel graceLabel;
    private JLabel confidenceLabel;
    private JLabel charmLabel;
    private JLabel staminaLabel;

    // 생성자
    public CatActivities(
            int affection, int knowledge, int combatPower, int fatigue,
            int theology, int mzPower, int wildPower, int morality, int grace,
            int confidence, int charm, int stamina,
            JLabel affectionLabel, JLabel knowledgeLabel, JLabel combatPowerLabel,
            JLabel fatigueLabel, JLabel theologyLabel, JLabel mzPowerLabel, JLabel wildPowerLabel,
            JLabel moralityLabel, JLabel graceLabel, JLabel confidenceLabel, JLabel charmLabel, JLabel staminaLabel)
         {
            // 상태 변수 초기화
     
            this.affection = affection;
            this.knowledge = knowledge;
            this.combatPower = combatPower;
            this.fatigue = fatigue;
            this.theology = theology;
            this.mzPower = mzPower;
            this.wildPower = wildPower;
            this.morality = morality;
            this.grace = grace;
            this.confidence = confidence;
            this.charm = charm;
            this.stamina = stamina;

            // 레이블 변수 초기화
         
            this.affectionLabel = affectionLabel;
            this.knowledgeLabel = knowledgeLabel;
            this.combatPowerLabel = combatPowerLabel;
            this.fatigueLabel = fatigueLabel;
            this.theologyLabel = theologyLabel;
            this.mzPowerLabel = mzPowerLabel;
            this.wildPowerLabel = wildPowerLabel;
            this.moralityLabel = moralityLabel;
            this.graceLabel = graceLabel;
            this.confidenceLabel = confidenceLabel;
            this.charmLabel = charmLabel;
            this.staminaLabel = staminaLabel;
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
        //gridPanel.setLayout(new GridLayout(5, 2));  // 2열 5행
        for (String subject : subjects) {
            JButton subjectButton = new JButton(subject);
            subjectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	Random rand = new Random();
                	String randomFeeling = rand.nextBoolean() ? "흥미로워합니다." : "지루해합니다.";
                	JOptionPane.showMessageDialog(activityDialog, "고양이가 " + randomFeeling);

                	fatigue += 1;
                	totalCoins -=30;
                	
                	if (randomFeeling.equals("흥미로워합니다.")) {
                		ImageIcon boredIcon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/흥미로운 고양이.png");
                 	    Image boredImage = boredIcon.getImage();
                 	    Image scaledBoredImage = boredImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 원하는 크기 설정 (예: 150x150)

                 	    JLabel boredCatLabel = new JLabel(new ImageIcon(scaledBoredImage)); // 조정된 이미지로 JLabel 생성
                 	    boredCatLabel.setHorizontalAlignment(SwingConstants.CENTER);

                 	    // 다이얼로그에 이미지 추가
                 	    activityDialog.add(boredCatLabel, BorderLayout.SOUTH);
                 	    activityDialog.revalidate();
                 	    activityDialog.repaint();

                 	    // 3초 뒤에 이미지 제거
                 	    Timer timer = new Timer(3000, new ActionListener() {
                 	        @Override
                 	        public void actionPerformed(ActionEvent e) {
                 	            activityDialog.remove(boredCatLabel); // 이미지 제거
                 	            activityDialog.revalidate();
                 	            activityDialog.repaint();
                 	        }
                 	    });
                 	    timer.setRepeats(false); // 1회만 실행
                 	    timer.start();
                	    switch (subject) {
                	        case "신학 수업":
                	        case "기도 수업":
                	            theology += 3; // 신학
                	            break;
                	        case "마법 연습":
                	            combatPower += 2; // 전투
                	            mzPower += 2; // 엠지력 상승
                	            break;
                	        case "자연탐구":
                	            wildPower += 5; // 야생력
                	            break;
                	        case "야외생존 훈련":
                	            wildPower += 3;
                	            stamina += 2; // 체력
                	            break;
                	        case "철학 수업":
                	            morality += 5; // 도덕
                	            knowledge += 2; // 지력
                	            break;
                	        case "예절 교육":
                	            grace += 6; // 기품
                	            break;
                	        case "체력 단련":
                	            stamina += 5; // 체력
                	            break;
                	        case "문학 교육":
                	            knowledge += 3; // 지력
                	            grace += 2; // 기품
                	            break;
                	        case "검술훈련":
                	            combatPower += 3; // 전투력
                	            stamina += 2; // 체력
                	            break;
                	        case "격투술":
                	            combatPower += 5; // 전투력
                	            confidence += 3; // 자신감
                	            break;
                	        case "연설 수업":
                	            confidence += 5; // 자신감
                	            charm += 3; // 매력
                	            break;
                	        case "연극 수업":
                	            charm += 5; // 매력
                	            break;
                	        case "음악 수업":
                	            grace += 2; // 기품
                	            charm += 2; // 매력
                	            break;
                	        case "춤 수업":
                	            charm += 3; // 매력
                	            stamina += 2; // 체력
                	            break;
                	        case "수학":
                	        case "지구과학":
                	        case "과학실험":
                	            knowledge += 5; // 지력
                	            break;
                	        case "예술 수업":
                	            grace += 3; // 기품
                	            break;
                	        case "틱톡 수업":
                	            mzPower += 5; // 엠지력
                	            break;
                	    }
                	} else if (randomFeeling.equals("지루해합니다.")) {
                	    fatigue += 1; // 피로도 증가
                	    ImageIcon boredIcon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/지루한 고양이.png");
                	    Image boredImage = boredIcon.getImage();
                	    Image scaledBoredImage = boredImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 원하는 크기 설정 (예: 150x150)

                	    JLabel boredCatLabel = new JLabel(new ImageIcon(scaledBoredImage)); // 조정된 이미지로 JLabel 생성
                	    boredCatLabel.setHorizontalAlignment(SwingConstants.CENTER);

                	    // 다이얼로그에 이미지 추가
                	    activityDialog.add(boredCatLabel, BorderLayout.SOUTH);
                	    activityDialog.revalidate();
                	    activityDialog.repaint();

                	    // 3초 뒤에 이미지 제거
                	    Timer timer = new Timer(3000, new ActionListener() {
                	        @Override
                	        public void actionPerformed(ActionEvent e) {
                	            activityDialog.remove(boredCatLabel); // 이미지 제거
                	            activityDialog.revalidate();
                	            activityDialog.repaint();
                	        }
                	    });
                	    timer.setRepeats(false); // 1회만 실행
                	    timer.start();
                	
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

                        // 행복한 고양이 이미지 로드 및 크기 조정
                        ImageIcon happyIcon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/유잼고양이.png");
                        Image happyImage = happyIcon.getImage();
                        Image scaledHappyImage = happyImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 이미지 크기 조정
                        JLabel happyCatLabel = new JLabel(new ImageIcon(scaledHappyImage));
                        happyCatLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        // 이미지 다이얼로그에 추가
                        activityDialog.add(happyCatLabel, BorderLayout.SOUTH);
                        activityDialog.revalidate();
                        activityDialog.repaint();

                        // 3초 후에 이미지 제거
                        Timer timer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                activityDialog.remove(happyCatLabel); // 이미지 제거
                                activityDialog.revalidate();
                                activityDialog.repaint();
                            }
                        });
                        timer.setRepeats(false); // 타이머를 한 번만 실행
                        timer.start();

                    } else if (randomFeeling.equals("애정도가 올라갑니다.")) {
                        affection += 3;
                        fatigue -= 1;
                        ImageIcon happyIcon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/애정고양ㅇ이.png");
                        Image happyImage = happyIcon.getImage();
                        Image scaledHappyImage = happyImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 이미지 크기 조정
                        JLabel happyCatLabel = new JLabel(new ImageIcon(scaledHappyImage));
                        happyCatLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        // 이미지 다이얼로그에 추가
                        activityDialog.add(happyCatLabel, BorderLayout.SOUTH);
                        activityDialog.revalidate();
                        activityDialog.repaint();

                        // 3초 후에 이미지 제거
                        Timer timer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                activityDialog.remove(happyCatLabel); // 이미지 제거
                                activityDialog.revalidate();
                                activityDialog.repaint();
                            }
                        });
                        timer.setRepeats(false); // 타이머를 한 번만 실행
                        timer.start();

                    } else if (randomFeeling.equals("귀찮아합니다.")) {
                        fatigue += 1;
                        ImageIcon happyIcon = new ImageIcon("C:/Users/leeya/OneDrive/Desktop/귀찮 고양이.png");
                        Image happyImage = happyIcon.getImage();
                        Image scaledHappyImage = happyImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 이미지 크기 조정
                        JLabel happyCatLabel = new JLabel(new ImageIcon(scaledHappyImage));
                        happyCatLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        // 이미지 다이얼로그에 추가
                        activityDialog.add(happyCatLabel, BorderLayout.SOUTH);
                        activityDialog.revalidate();
                        activityDialog.repaint();

                        // 3초 후에 이미지 제거
                        Timer timer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                activityDialog.remove(happyCatLabel); // 이미지 제거
                                activityDialog.revalidate();
                                activityDialog.repaint();
                            }
                        });
                        timer.setRepeats(false); // 타이머를 한 번만 실행
                        timer.start();
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
     // 여가활동 탭
        JPanel leisurePanel1 = new JPanel(new BorderLayout());
        leisurePanel1.add(new JLabel("여가활동 탭입니다.", SwingConstants.CENTER), BorderLayout.CENTER);

        // 2열 4행 GridLayout으로 여가활동 항목 배치
     // 여가활동 탭
        JPanel leisurePanel11 = new JPanel(new BorderLayout());
        leisurePanel11.add(new JLabel("여가활동 탭입니다.", SwingConstants.CENTER), BorderLayout.CENTER);

        // 2열 4행 GridLayout으로 여가활동 항목 배치
        JPanel leisureGridPanel = new JPanel();
        leisureGridPanel.setLayout(new GridLayout(4, 2));  // 2열 4행

        for (String action : leisureActions) {
            JButton leisureButton = new JButton(action);
            leisureButton.setActionCommand(action); // 버튼의 액션 명령어 설정

            leisureButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String clickedAction = e.getActionCommand(); // 클릭된 버튼의 텍스트 가져오기

                    if (clickedAction.equals("장애물 달리기로 코인벌기")) {
                        openCatRunGame(parentFrame); // 장애물 달리기 실행
                        activityDialog.dispose();

                    } else if (clickedAction.equals("코인 사냥하기")) {
                        openCatHuntingGame(parentFrame); // 코인 사냥하기 실행
                        activityDialog.dispose();
                        
                    } else if (clickedAction.equals("은행 가기")) {
                        openCatBank(parentFrame); // 적금실행
                        activityDialog.dispose();
                        
                    } else if (clickedAction.equals("뽑기게임하기")) {
                        openCatgambling (parentFrame, affectionLabel); // 적금실행
                        activityDialog.dispose();

                    } else if (clickedAction.equals("무술대회 나가기")) {
                        JOptionPane.showMessageDialog(activityDialog, "현재 개발 중입니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

                    } else if (clickedAction.equals("슬롯머신 돌리기")) {
                        openSlotMachineGame(parentFrame); // 코인 사냥하기 실행
                        activityDialog.dispose();}
                        updateLabels(); // 다른 상태 업데이트
                    }
                
            });

            leisureGridPanel.add(leisureButton); // 버튼을 패널에 추가

    }
        leisurePanel11.add(leisureGridPanel, BorderLayout.CENTER);
        tabbedPane.addTab("여가활동", leisurePanel11);

        // 탭을 다이얼로그에 추가
        activityDialog.add(tabbedPane);

        // 다이얼로그 표시
        activityDialog.setVisible(true);
    }
 // 기존에 있던 openSlotMachineGame 메서드는 삭제하고, 새로운 메서드를 사용
    protected static void openSlotMachineGame(JFrame parentFrame) {
        SlotMachineGame.openSlotMachineGame(parentFrame, coinLabel);}
    
    protected static void openCatgambling(JFrame parentFrame, JLabel coinLabel) {
        Catgambling catgamblingGame = new Catgambling(coinLabel); 
        catgamblingGame.setVisible(true);  // 게임 팝업 표시
    }

    
    protected static void openCatBank(JFrame parentFrame) {
        CatBank catBank = new CatBank(totalCoins, newCoins -> {
            totalCoins = newCoins; // 적금 결과로 업데이트된 코인을 반영
            coinLabel.setText("Coins: " + totalCoins);
        });

        catBank.showBank(); // 은행 GUI 표시
    }

    
    
    protected static void openCatRunGame(JFrame parentFrame) {
        JFrame gameFrame = new JFrame("Cat Run Game");
        CatRunGame game = new CatRunGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // CatRunGame 종료 후 코인 업데이트
                totalCoins += Integer.parseInt(e.getActionCommand());
                coinLabel.setText("Coins: " + totalCoins); // UI 업데이트
            }
        });

        gameFrame.add(game);
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    protected void openCatHuntingGame(JFrame parentFrame) {
        JFrame gameFrame = new JFrame("Cat Hunting Game");
        CatHuntingGame game = new CatHuntingGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int earnedCoins = Integer.parseInt(e.getActionCommand());
                    totalCoins += earnedCoins; // 코인 업데이트
                    coinLabel.setText("Coins: " + totalCoins); // UI 업데이트
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid coin value: " + e.getActionCommand());
                }
            }
        });

        game.setPreferredSize(new Dimension(800, 600)); // 패널 크기 설정
        gameFrame.add(game); // CatHuntingGame 패널 추가
        gameFrame.pack(); // 패널 크기를 반영하여 창 크기 설정
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
        gameFrame.setVisible(true);
    }

	// 상태 갱신 메서드	
    private void updateLabels() {
    	if (fatigue < 0) {
            fatigue = 0;
        }
    	affectionLabel.setText("애정도: " + affection);
    	knowledgeLabel.setText("지식: " + knowledge);
    	staminaLabel.setText("체력: " + stamina);
    	combatPowerLabel.setText("전투력: " + combatPower);
    	theologyLabel.setText("신학: " + theology);
    	mzPowerLabel.setText("엠지력: " + mzPower);
    	wildPowerLabel.setText("야생력: " + wildPower);
    	moralityLabel.setText("도덕: " + morality);
    	graceLabel.setText("기품: " + grace);
    	confidenceLabel.setText("자신감: " + confidence);
    	charmLabel.setText("매력: " + charm);
    	fatigueLabel.setText("피로도: " + fatigue);

    }
}

