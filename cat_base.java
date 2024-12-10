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
    protected int coin;
    static int fatigue = 0;
    static String background_img1 = "C:/Users/leeya/OneDrive/Desktop/고양이/메인 화면.png";
    static String background_img2 = "C:/Users/leeya/OneDrive/Desktop/고양이/집배경.png";
    static String currentBackgroundImg = background_img1;
    static String cat_main_img = "C:/Users/leeya/OneDrive/Desktop/고양이/메인고양이2.png";
    static String cat_edu_good = "C:/Users/leeya/OneDrive/Desktop/고양이/흥미 고양이.png";
    static String cat_edu_bad = "C:/Users/leeya/OneDrive/Desktop/고양이/노잼 고양이.png";
    static String cat_active_good = "C:/Users/leeya/OneDrive/Desktop/고양이/애정고양이.png";
    static String cat_active_bad = "C:/Users/leeya/OneDrive/Desktop/고양이/귀찮고양ㅇ.png";

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
    
    public static void updateCoinLabel() {
        if (coinLabel != null) {
            coinLabel.setText("Coins: " + totalCoins);
        }
    }
    

    public static void main(String[] args) {
        // 메인 프레임 생성
        JFrame frame = new JFrame("고양이 키우기 게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // 프레임 크기 고정
        frame.setLayout(null); // 절대 위치 레이아웃

        // 배경 패널 설정
        JPanel background = new JPanel() {
            private Image backgroundImage;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // currentBackgroundImg 사용
                backgroundImage = new ImageIcon(currentBackgroundImg).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        background.setLayout(null);
        frame.add(background);
        
        // 코인 라벨 추가
        coinLabel = new JLabel("Coins: " + totalCoins);
        coinLabel.setBounds(10, 20, 150, 30);
        coinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coinLabel.setOpaque(true); // JLabel의 배경을 표시하기 위해 불투명 설정
        coinLabel.setBackground(Color.LIGHT_GRAY); // 배경 색상을 회색으로 설정
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트를 중앙 정렬
        background.add(coinLabel);
        
        

        // 고양이 이미지
        ImageIcon icon = new ImageIcon(cat_main_img);
        Image scaledImage = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel catLabel = new JLabel(new ImageIcon(scaledImage));
        catLabel.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 -50, 300, 300);
        background.add(catLabel);
        
        

        // 상태 정보 패널
        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(570, 10, 200, 150); // 우측 상단
        statusPanel.setLayout(new GridLayout(5, 2));
        coinLabel.setOpaque(true); // JLabel의 배경을 표시하기 위해 불투명 설정
        coinLabel.setBackground(Color.LIGHT_GRAY); // 배경 색상을 회색으로 설정
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel affectionLabel = new JLabel("  애정도: 0");
        JLabel knowledgeLabel = new JLabel("  지식: 0");
        JLabel staminaLabel = new JLabel("  체력 : 0");
        JLabel combatPowerLabel = new JLabel("  전투력: 0");
        JLabel theologyLabel = new JLabel("  신학: 0");
        JLabel MZLabel = new JLabel("  엠지력 : 0");
        JLabel wildPowerLabel = new JLabel("  야생력 : 0");
        JLabel moralityLabel = new JLabel("  도덕 : 0");
        JLabel graceLabel = new JLabel("  기품 : 0");
        JLabel confidenceLabel = new JLabel("  자신감 : 0");
        JLabel charmLabel = new JLabel("  매력 : 0");
        JLabel fatigueLabel = new JLabel("  피로도 : 0");


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
                    affection, knowledge, stamina, combatPower, fatigue, // <-- 여기서 전달
                    theology, mzPower, wildPower, morality, grace, confidence, charm,
                    affectionLabel, knowledgeLabel, staminaLabel, combatPowerLabel, fatigueLabel,
                    theologyLabel, MZLabel, wildPowerLabel, moralityLabel, graceLabel, confidenceLabel, charmLabel,catLabel
                );
                activities.openActivityDialog(frame);
            }
        });

        
    }
    void changeCatIcon(String newIconPath, JFrame parentFrame, JLabel catLabel) {
        ImageIcon newIcon = new ImageIcon(newIconPath);
        Image scaledImage = newIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        catLabel.setIcon(new ImageIcon(scaledImage)); // 새로운 아이콘 설정

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon defaultIcon = new ImageIcon(cat_main_img);
                Image defaultImage = defaultIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                catLabel.setIcon(new ImageIcon(defaultImage)); // 원래 아이콘으로 복구
            }
        });
        timer.setRepeats(false); // 한 번만 실행
        timer.start();

        parentFrame.repaint(); // UI 갱신
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
	 
	 private void checkAndShowCatJob(JFrame parentFrame, JLabel catLabel) {
		    cat_ending jobEnd = new cat_ending();
		    jobEnd.determineCatJob(
		        theology, affection, fatigue, combatPower, mzPower,
		        knowledge, wildPower, morality, grace, totalCoins
		    );
		}


	 
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
	private JLabel catLabel;

    // 생성자
    public CatActivities(
    	    int affection, int knowledge, int combatPower, int fatigue,
    	    int theology, int mzPower, int wildPower, int morality, int grace,
    	    int confidence, int charm, int stamina,
    	    JLabel affectionLabel, JLabel knowledgeLabel, JLabel combatPowerLabel,
    	    JLabel fatigueLabel, JLabel theologyLabel, JLabel mzPowerLabel, JLabel wildPowerLabel,
    	    JLabel moralityLabel, JLabel graceLabel, JLabel confidenceLabel, JLabel charmLabel, JLabel staminaLabel,JLabel catLabel)
    	{
    	    this.affection = affection;
    	    this.knowledge = knowledge;
    	    this.combatPower = combatPower;
    	    this.fatigue = fatigue;
    	    this.mzPower = mzPower;
    	    this.wildPower = wildPower;
    	    this.morality = morality;
    	    this.grace = grace;
    	    this.confidence = confidence;
    	    this.charm = charm;
    	    this.stamina = stamina;

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
    	    this.catLabel = catLabel;
    	    
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
                    if (totalCoins < 30) {  // 코인이 30보다 적으면 팝업 띄움
                        JOptionPane.showMessageDialog(
                            activityDialog,
                            "코인이 부족하여 학습을 진행할 수 없습니다.",
                            "코인 부족",
                            JOptionPane.WARNING_MESSAGE
                        );
                        return;  // 학습 진행하지 않음
                    }

                    Random rand = new Random();
                    boolean isInterested = rand.nextBoolean(); // true면 흥미로워하고, false면 지루해함
                    String randomFeeling = isInterested ? "흥미로워합니다." : "지루해합니다.";
                    JOptionPane.showMessageDialog(activityDialog, "고양이가 " + randomFeeling);

                    // 피로도 증가 및 코인 차감
                    cat_base.fatigue += 1;
                    totalCoins -= 30;  
                    updateCoinLabel();

                    // 이미지 변경
                    String newIconPath = isInterested ? cat_edu_good : cat_edu_bad;
                    changeCatIcon(newIconPath, parentFrame, catLabel); // 이미지 변경 호출

                    // 학습 효과 적용
                    switch (subject) {
                        case "신학 수업":
                        case "기도 수업":
                            theology += 3; 
                            break;
                        case "마법 연습":
                            combatPower += 2; 
                            mzPower += 2; 
                            break;
                        case "자연탐구":
                            wildPower += 5; 
                            break;
                        case "야외생존 훈련":
                            wildPower += 3;
                            stamina += 2; 
                            break;
                        case "철학 수업":
                            morality += 5; 
                            knowledge += 2; 
                            break;
                        case "예절 교육":
                            grace += 6; 
                            break;
                        case "체력 단련":
                            stamina += 5; 
                            break;
                        case "문학 교육":
                            knowledge += 3; 
                            grace += 2; 
                            break;
                        case "검술훈련":
                            combatPower += 3; 
                            stamina += 2; 
                            break;
                        case "격투술":
                            combatPower += 5; 
                            confidence += 3; 
                            break;
                        case "연설 수업":
                            confidence += 5; 
                            charm += 3; 
                            break;
                        case "연극 수업":
                            charm += 5; 
                            break;
                        case "음악 수업":
                            grace += 2; 
                            charm += 2; 
                            break;
                        case "춤 수업":
                            charm += 3; 
                            stamina += 2; 
                            break;
                        case "수학":
                        case "지구과학":
                        case "과학실험":
                            knowledge += 5; 
                            break;
                        case "예술 수업":
                            grace += 3; 
                            break;
                        case "틱톡 수업":
                            mzPower += 5; 
                            break;
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
                        "애정도가 올라갑니다.", "귀찮아합니다."
                    };
                    String randomFeeling = careFeelings[rand.nextInt(careFeelings.length)];
                    JOptionPane.showMessageDialog(activityDialog, "고양이가 " + randomFeeling);

                    // 이미지 변경 로직
                    String newIconPath = randomFeeling.equals("애정도가 올라갑니다.") ? cat_active_good : cat_active_bad;
                    changeCatIcon(newIconPath, parentFrame, catLabel);

                    // 상태 업데이트
                    if (randomFeeling.equals("애정도가 올라갑니다.")) {
                        affection += 3;
                        cat_base.fatigue -= 1;
                    } else if (randomFeeling.equals("귀찮아합니다.")) {
                        cat_base.fatigue += 1;
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
                	//fatigue += 7;
                    String clickedAction = e.getActionCommand();
                    // 클릭된 버튼의 텍스트 가져오기

                    if (clickedAction.equals("장애물 달리기로 코인벌기")) {
                    	cat_base.fatigue += 7;
                        openCatRunGame(parentFrame); // 장애물 달리기 실행
                        activityDialog.dispose();

                    } else if (clickedAction.equals("코인 사냥하기")) {
                    	cat_base.fatigue += 7;
                        openCatHuntingGame(parentFrame); // 코인 사냥하기 실행
                        activityDialog.dispose();
                        
                    } else if (clickedAction.equals("은행 가기")) {
                    	cat_base.fatigue += 3;
                        openCatBank(parentFrame); // 적금실행
                        activityDialog.dispose();
                        
                    } else if (clickedAction.equals("뽑기게임하기")) {
                    	cat_base.fatigue += 10;
                        openCatgambling (parentFrame, affectionLabel); // 적금실행
                        activityDialog.dispose();

                    } else if (clickedAction.equals("무술대회 나가기")) {
                        JOptionPane.showMessageDialog(activityDialog, "현재 개발 중입니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

                    } 
                    
                    else if (clickedAction.equals("슬롯머신 돌리기")) {
                    	cat_base.fatigue += 7;  // 피로도 7 누적 증가
                        openSlotMachineGame(parentFrame); // 슬롯머신 게임 실행
                        activityDialog.dispose();
                    }
                    
                    if (cat_base.fatigue > 0) {
                        cat_base.currentBackgroundImg = cat_base.background_img2; // 집 배경으로 변경
                    } else {
                        cat_base.currentBackgroundImg = cat_base.background_img1; // 기본 배경으로 복원
                    }

                    // 배경 다시 그리기
                    parentFrame.getContentPane().repaint();

                    updateLabels(); // 상태 업데이트
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
    
    protected static void openCatgambling(JFrame parentFrame, JLabel 
Label) {
        Catgambling catgamblingGame = new Catgambling(coinLabel); 
        catgamblingGame.setVisible(true);  // 게임 팝업 표시
    }

    
    protected static void openCatBank(JFrame parentFrame) {
        CatBank catBank = new CatBank(newCoins -> {
            totalCoins = newCoins; // 적금 결과로 업데이트된 코인을 반영
            if (coinLabel != null) { // coinLabel이 초기화되었는지 확인
                coinLabel.setText("Coins: " + totalCoins);
            } else {
                System.err.println("coinLabel is not initialized.");
            }
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
    	if (cat_base.fatigue < 0) {
    		cat_base.fatigue = 0;
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
    	fatigueLabel.setText("피로도: " + cat_base.fatigue);
    	

    
    	
		checkAndShowCatJob(null, catLabel);

    }
}

