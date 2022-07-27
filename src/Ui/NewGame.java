package Ui;

import Cards.CharacterCard;
import Cards.CharacterSkill;
import Cards.PlayerCard;
import JsonHandler.CreateScenario;
import JsonHandler.PlayerCardFactory;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NewGame extends JPanel {
    Menu menu;
    // JPanel BG;
    PaintContent cityBG;
    PaintContent schoolBG;
    PaintContent hospitalBG;
    PaintContent shrineBG;
    PaintContent dataBG;
    Dimension backgroundSize;
    Dimension dataSize;
    JPanel contentPanel;
    JLabel inforLabel;
    JPanel characterInforPanel;
    JLabel goodwillInforLabel;
    JLabel paraInforLabel;
    JLabel intrigueInforLabel;
    Image cardImage;
    JLabel informer;
    JPanel protagonistHandPanel;
    JPanel mastermindHandPanel;
    int day = 1;
    int loop = 4;
    CreateScenario scenario;
    int language;
    NewGame currentNewGame;
    int checkKeyPersonState = 1;

    int cardPlayedCount = 0;
    Boolean isMastermind = false;
    Boolean playerCardClicked = false;
    PlayerCardFactory playerCardFactory;
    JLabel incidentInfor1;
    JLabel incidentInfor2;
    int phaseCount = 0;             // 0 - playing card, 1 - mastermind abilities (hidden role), 2 - goodwill abilities (character skill - for both master and prota, 3 - incident (mastermind), 4 - day end
    JPanel characterSkillPanel;
    boolean isOpenCharacterSkillPanel = false;
    JButton skill1;
    JButton skill2;
    int currentSKill = 0;
    boolean skillActivated = false;
    CharacterSkill characterSkill;
    CharacterCard characterCardPerform;
    CharacterCard characterCardTarget;
    CharacterCard currentCharacter;
    boolean otherCharacterSkillPanelOpen = false;
    boolean policeSkill1used;
    boolean policeSkill2used;
    boolean shrineMaidenSkill2Used = false;
    boolean isBrain = true;
    boolean isConspiracyTheorist = false;
    boolean isSomeoneDie = false;
    boolean brainIsAlive = true;
    boolean conspiracyTheoristIsAlive = true;
    JPanel archivePanel;
    JButton archiveToggleBtn;
    int archiveToggle = 0;
    JLabel mastermindHiddenRoleSkillInforLabel;
    JPanel doctorSkillPanel;
    JButton doctorAddBtn;
    JButton doctorRemoveBtn;
    boolean doctorIsAdding;
    JPanel hiddenRolePanel;
    JLabel hiddenRoleLabel;
    int peopleInShrine = 1;
    int peopleInSchool = 2;
    int peopleInHospital = 1;
    int peopleInCity = 2;
    boolean isPhase1 = false;
    JPanel mastermindAbilitiesPanel;
    JButton mastermindYesBtn;
    JButton mastermindNoBtn;
    JLabel mastermindHiddenRoleLabel;
    boolean mastermindSkillActivated = false;
    JPanel gameResultPanel;
    JLabel gameResultLabel;
    JButton mainMenuBtn;
    JPanel exitPanel;
    JLabel exitLabel;
    JButton yesExitBtn;
    JButton noExitBtn;
    private boolean isMurderHappen = false;
    private boolean isSuicideHappen = false;

    public NewGame(Menu menu) throws IOException {
        currentNewGame = this;
        this.language = menu.GetLanguage();
        contentPanel = new JPanel();
        contentPanel.setVisible(true);
        contentPanel.setBounds(0, 0, menu.frameSize.width, menu.frameSize.height);
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        this.add(contentPanel);

        this.setLayout(null);

        //size
        backgroundSize = new Dimension(menu.frameSize.width / 5 * 2, menu.frameSize.height / 2);
        dataSize = new Dimension(menu.frameSize.width / 5, menu.frameSize.height);

        //draw
        if (language == 1) {
            cityBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/city.png").getImage(), backgroundSize);
            schoolBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/school.png").getImage(), backgroundSize);
            hospitalBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/hospital.png").getImage(), backgroundSize);
            shrineBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/shrine.png").getImage(), backgroundSize);
            dataBG = new PaintContent(new ImageIcon("res/Turn/JP/day1loop4.png").getImage(), dataSize);
        } else {
            cityBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/EN/city.png").getImage(), backgroundSize);
            schoolBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/EN/school.png").getImage(), backgroundSize);
            hospitalBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/EN/hospital.png").getImage(), backgroundSize);
            shrineBG = new PaintContent(new ImageIcon("res/tragedy_commons_5th/board/EN/shrine.png").getImage(), backgroundSize);
            dataBG = new PaintContent(new ImageIcon("res/Turn/EN/day1loop4.png").getImage(), dataSize);
        }

        //Set layout
        cityBG.setLayout(new BoxLayout(cityBG, BoxLayout.X_AXIS));
        hospitalBG.setLayout(new BoxLayout(hospitalBG, BoxLayout.X_AXIS));
        shrineBG.setLayout(new BoxLayout(shrineBG, BoxLayout.X_AXIS));
        schoolBG.setLayout(new BoxLayout(schoolBG, BoxLayout.X_AXIS));

        // Add bottom margin to board layout
        cityBG.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 0));
        hospitalBG.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 0));
        shrineBG.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 0));
        schoolBG.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 0));

        //Set Position
        cityBG.setBounds(menu.frameSize.width / 5, menu.frameSize.height / 2, backgroundSize.width, backgroundSize.height);
        schoolBG.setBounds(menu.frameSize.width / 5 * 3, menu.frameSize.height / 2, backgroundSize.width, backgroundSize.height);
        hospitalBG.setBounds(menu.frameSize.width / 5, 0, backgroundSize.width, backgroundSize.height);
        shrineBG.setBounds(menu.frameSize.width / 5 * 3, 0, backgroundSize.width, backgroundSize.height);
        dataBG.setBounds(0, 0, dataSize.width, dataSize.height);

        //Archive
        archivePanel = new JPanel();
        archivePanel.setBounds(0, menu.frameSize.height / 4 * 3, menu.frameSize.width, menu.frameSize.height / 4);
        archivePanel.setLayout(new BoxLayout(archivePanel, BoxLayout.X_AXIS));
        archivePanel.setBackground(Color.GRAY);
        contentPanel.add(archivePanel);
        archivePanel.setVisible(false);

        archiveToggleBtn = new JButton("Archive");
        archiveToggleBtn.setBounds(menu.frameSize.width / 8 * 2, 0, menu.frameSize.width / 7, menu.frameSize.height / 10);
        archiveToggleBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.height / 35));
        archiveToggleBtn.setForeground(Color.WHITE);
        archiveToggleBtn.setOpaque(false);
        archiveToggleBtn.setContentAreaFilled(false);
        archiveToggleBtn.setBorderPainted(false);
        contentPanel.add(archiveToggleBtn);
        archiveToggleBtn.setEnabled(false);

        //Action
        Action yesAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.container.setVisible(true);
                menu.containerRoot.setVisible(true);
                currentNewGame.setVisible(false);
                menu.loadGameBtn.setEnabled(true);
                exitPanel.setVisible(false);
            }
        };

        Action noAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanel.setVisible(false);
            }
        };

        // Exit to menu panel
        int exitPanelWidth = menu.frameSize.width / 3;
        int exitPanelHeight = menu.frameSize.height / 3;
        exitPanel = new JPanel();
        exitPanel.setBounds(menu.frameSize.width / 2 - exitPanelWidth / 2, menu.frameSize.height / 2 - exitPanelHeight / 2, exitPanelWidth, exitPanelHeight);

        exitLabel = new JLabel("Exit to Main Menu");
        exitLabel.setBounds(exitPanelWidth / 100 * 29, exitPanelHeight / 8, exitPanelWidth / 2, exitPanelHeight / 5);
        exitLabel.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.width / 60));

        yesExitBtn = new JButton("Yes");
        yesExitBtn.setBounds(exitPanelWidth / 6, exitPanelHeight / 3 * 2, exitPanelWidth / 3, exitPanelHeight / 4);
        yesExitBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.width / 35));
        yesExitBtn.setOpaque(false);
        yesExitBtn.setContentAreaFilled(false);
        yesExitBtn.setBorderPainted(false);
        yesExitBtn.addActionListener(yesAction);

        noExitBtn = new JButton("No");
        noExitBtn.setBounds(exitPanelWidth / 2, exitPanelHeight / 3 * 2, exitPanelWidth / 3, exitPanelHeight / 4);
        noExitBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.width / 35));
        noExitBtn.setOpaque(false);
        noExitBtn.setContentAreaFilled(false);
        noExitBtn.setBorderPainted(false);
        noExitBtn.addActionListener(noAction);

        exitPanel.setLayout(null);
        exitPanel.add(exitLabel);
        exitPanel.add(yesExitBtn);
        exitPanel.add(noExitBtn);
        exitPanel.add(exitLabel);
        exitPanel.setVisible(false);
        contentPanel.add(exitPanel);

        //Mastermind hand
        mastermindHandPanel = new JPanel();
        mastermindHandPanel.setBounds(0, menu.frameSize.height / 4 * 3, menu.frameSize.width, menu.frameSize.height / 4);
        mastermindHandPanel.setLayout(new BoxLayout(mastermindHandPanel, BoxLayout.X_AXIS));
        mastermindHandPanel.setBackground(Color.GRAY);
        contentPanel.add(mastermindHandPanel);
        mastermindHandPanel.setVisible(false);
        PlayerCardFactory playerCardFactory = new PlayerCardFactory(language);
        if (language == 1) {
            playerCardFactory.CreateFromJSONFile("playerCardJP.txt");
        } else if (language == 0) {
            playerCardFactory.CreateFromJSONFile("playerCardEN.txt");
        }
        for (int i = playerCardFactory.GetPlayerCardList().size() - 1; i >= 0; i--) {
            if (playerCardFactory.GetPlayerCardList().get(i).GetCardName().equals("protagonistC_forbidmovement")) {
                break;
            }
            final int final_i = i;
            BufferedImage playerCardImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(i).GetImageURL())), backgroundSize.width / 20, backgroundSize.height / 3);
            playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel().setIcon(new ImageIcon(playerCardImg));
            mastermindHandPanel.add(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
            playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel().setPreferredSize(new Dimension(backgroundSize.width / 30, backgroundSize.height / 3));
            int finalI = i;
            playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    BufferedImage inforImg = null;
                    try {
                        inforImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(final_i).GetImageURL())), backgroundSize.width / 2, backgroundSize.height);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inforLabel.setIcon(new ImageIcon(inforImg));
                    inforLabel.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    BufferedImage inforImg = null;
                    try {
                        inforImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(final_i).GetImageURL())), backgroundSize.width / 2, backgroundSize.height);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inforLabel.setIcon(new ImageIcon(inforImg));
                    inforLabel.setVisible(false);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardClicked() == false && playerCardClicked == false && playerCardFactory.GetPlayerCardList().get(finalI).GetPlayedState() == false) {
                        playerCardFactory.GetPlayerCardList().get(finalI).SetPlayerCardClicked(true);
                        playerCardClicked = true;    //check if any card chosen
                        BufferedImage tempImg = null;
                        try {
                            tempImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(final_i).GetImageURL())), backgroundSize.width / 10, backgroundSize.height / 2);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardLabel().setIcon(new ImageIcon(tempImg));
                    } else if (playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardClicked() == true && playerCardClicked == true) {
//                        System.out.println("turn off");
                        playerCardFactory.GetPlayerCardList().get(finalI).SetPlayerCardClicked(false);
                        playerCardClicked = false;
                        playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardLabel().setIcon(new ImageIcon(playerCardImg));
                    }
                }
            });
        }


        // Protagonist hand
        protagonistHandPanel = new JPanel();
        protagonistHandPanel.setBounds(0, menu.frameSize.height / 4 * 3, menu.frameSize.width, menu.frameSize.height / 4);
        protagonistHandPanel.setLayout(new BoxLayout(protagonistHandPanel, BoxLayout.X_AXIS));
        protagonistHandPanel.setBackground(Color.GRAY);
        contentPanel.add(protagonistHandPanel);
        protagonistHandPanel.setVisible(false);
        for (int i = 0; i < playerCardFactory.GetPlayerCardList().size(); i++) {
            if (playerCardFactory.GetPlayerCardList().get(i).GetCardName().equals("mastermind_add1para1")) {
                break;
            }
            final int final_i = i;
            BufferedImage playerCardImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(i).GetImageURL())), backgroundSize.width / 20, backgroundSize.height / 3);
            playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel().setIcon(new ImageIcon(playerCardImg));
            protagonistHandPanel.add(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
            playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel().setPreferredSize(new Dimension(backgroundSize.width / 30, backgroundSize.height / 3));
            int finalI = i;
            playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    BufferedImage inforImg = null;
                    try {
                        inforImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(final_i).GetImageURL())), backgroundSize.width / 2, backgroundSize.height);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inforLabel.setIcon(new ImageIcon(inforImg));
                    inforLabel.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    BufferedImage inforImg = null;
                    try {
                        inforImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(final_i).GetImageURL())), backgroundSize.width / 2, backgroundSize.height);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inforLabel.setIcon(new ImageIcon(inforImg));
                    inforLabel.setVisible(false);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardClicked() == false && playerCardClicked == false && playerCardFactory.GetPlayerCardList().get(finalI).GetPlayedState() == false) {
                        playerCardFactory.GetPlayerCardList().get(finalI).SetPlayerCardClicked(true);
                        playerCardClicked = true;    //check if any card chosen
                        BufferedImage tempImg = null;
                        try {
                            tempImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(final_i).GetImageURL())), backgroundSize.width / 10, backgroundSize.height / 2);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardLabel().setIcon(new ImageIcon(tempImg));
                    } else if (playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardClicked() == true && playerCardClicked == true) {
                        playerCardFactory.GetPlayerCardList().get(finalI).SetPlayerCardClicked(false);
                        playerCardClicked = false;
                        playerCardFactory.GetPlayerCardList().get(finalI).GetPlayerCardLabel().setIcon(new ImageIcon(playerCardImg));
                    }
                }
            });
        }

        //Ui
        JButton endTurnBtn = new JButton("End Turn");
        JButton startTurnBtn = new JButton("Start Turn");
        JButton nextBtn = new JButton("Next");
        endTurnBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        //set font
        endTurnBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.height / 35));
        startTurnBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.height / 35));
        nextBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.height / 35));

        //set color
        endTurnBtn.setForeground(Color.WHITE);
        startTurnBtn.setForeground(Color.WHITE);
        nextBtn.setForeground(Color.WHITE);

        endTurnBtn.setVisible(true);
        startTurnBtn.setVisible(true);
        nextBtn.setVisible(true);

        //hide btn background
        endTurnBtn.setOpaque(false);
        endTurnBtn.setContentAreaFilled(false);
        endTurnBtn.setBorderPainted(false);

        startTurnBtn.setOpaque(false);
        startTurnBtn.setContentAreaFilled(false);
        startTurnBtn.setBorderPainted(false);

        nextBtn.setOpaque(false);
        nextBtn.setContentAreaFilled(false);
        nextBtn.setBorderPainted(false);

        //add to main panel
        this.add(endTurnBtn);
        this.add(startTurnBtn);
        this.add(nextBtn);

        //set bounds
        endTurnBtn.setBounds(menu.frameSize.width / 8 * 5, 0, menu.frameSize.width / 10, menu.frameSize.height / 10);
        startTurnBtn.setBounds(menu.frameSize.width / 8 * 3, 0, menu.frameSize.width / 7, menu.frameSize.height / 10);
        nextBtn.setBounds(menu.frameSize.width / 8 * 4, 0, menu.frameSize.width / 10, menu.frameSize.height / 10);
        Action startTurnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mastermindHandPanel.setVisible(true);
                isMastermind = true;
                startTurnBtn.setEnabled(false);

                // Set this round position
                for (int j = 0; j < scenario.GetCharacterList().size(); j++) {
                    scenario.GetCharacterList().get(j).SetThisRoundPos(scenario.GetCharacterList().get(j).GetCurrentPos());
                }
            }
        };

        Action archiveToggleBtnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archiveToggle == 0) {
                    archiveToggle = 1;
                    archivePanel.setVisible(true);
                } else if (archiveToggle == 1) {
                    archiveToggle = 0;
                    archivePanel.setVisible(false);
                }
            }
        };

        Action nextAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSomeoneDie == false) {
                    for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                        if (scenario.GetCharacterList().get(i).GetAlive() == 0) {
                            isSomeoneDie = true;
                        }
                    }
                    if (isSomeoneDie) {
                        archiveToggleBtn.setEnabled(true);
                    }
                }
                if (isSomeoneDie == true) {
                    for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                        if (scenario.GetCharacterList().get(i).GetAlive() == 0) {
                            switch (scenario.GetCharacterList().get(i).GetThisRoundPos()) {
                                case 1:
                                    hospitalBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInHospital -= 1;
                                    break;
                                case 2:
                                    shrineBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInShrine -= 1;
                                    break;
                                case 3:
                                    cityBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInCity -= 1;
                                    break;
                                case 4:
                                    schoolBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInSchool -= 1;
                                    break;
                            }
                            archivePanel.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                            scenario.GetCharacterList().get(i).SetCurrentPos(0);
                        }
                    }
                }

                //doing next phase action
                phaseCount += 1;
                currentNewGame.Next(playerCardFactory);

                if (phaseCount == 2 && isPhase1 == false) {
                    isPhase1 = true;
                    phaseCount = 1;
                    if (!brainIsAlive) {
                        if (conspiracyTheoristIsAlive) {
                            mastermindHiddenRoleLabel.setText("Do you want to use Conspiracy Theorist's skill?");
                            mastermindAbilitiesPanel.setVisible(true);
                            isBrain = false;
                            isConspiracyTheorist = true;
                            nextBtn.setEnabled(false);
                        }
                    } else if (brainIsAlive) {
                        mastermindAbilitiesPanel.setVisible(true);
                        nextBtn.setEnabled(false);
                    }
                }

                if (phaseCount != 1) {
                    isPhase1 = false;
                }

                if (phaseCount == 3) {
                    if (day == 2) {
                        for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                            if (scenario.GetCharacterList().get(i).GetIncident().equals("Murder")) {
                                characterCardPerform = scenario.GetCharacterList().get(i);
                                break;
                            }
                        }
                        if (characterCardPerform.GetParanoiaCount() >= characterCardPerform.GetParanoiaThreshold()) {
                            if (GetPeopleIn(characterCardPerform.GetCurrentPos()) == 2) {
                                for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                                    if (scenario.GetCharacterList().get(i).GetCurrentPos() == characterCardPerform.GetCurrentPos() && !scenario.GetCharacterList().get(i).GetCardName().equals(characterCardPerform.GetCardName())) {
                                        scenario.GetCharacterList().get(i).KillCharacter();
                                        JOptionPane.showMessageDialog(menu, scenario.GetCharacterList().get(i).GetCardName() + " is murdered!!!");
                                        System.out.println(scenario.GetCharacterList().get(i).GetAlive());
                                        isMurderHappen = true;
                                        break;
                                    }
                                }
                            } else if (GetPeopleIn(characterCardPerform.GetCurrentPos()) > 2) {
                                mastermindHiddenRoleSkillInforLabel.setText("Choose a character in " + characterCardPerform.GetCurrentPosName() + " to murder except " + characterCardPerform.GetCardName());
                                nextBtn.setEnabled(false);
                                mastermindHiddenRoleSkillInforLabel.setVisible(true);
                                skillActivated = true;
                            }
                        }
                    } else if (day == 3) {
                        for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                            if (scenario.GetCharacterList().get(i).GetIncident().equals("Suicide")) {
                                if (scenario.GetCharacterList().get(i).GetParanoiaCount() >= scenario.GetCharacterList().get(i).GetParanoiaThreshold()) {
                                    scenario.GetCharacterList().get(i).KillCharacter();
                                    isSuicideHappen = true;
                                    JOptionPane.showMessageDialog(menu, "Someone commit suicide!!!");
                                }
                            }
                        }
                    }
                }

                for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                    if (scenario.GetCharacterList().get(i).GetRole().equals("Key Person") && scenario.GetCharacterList().get(i).GetAlive() == 0) {
                        switch (scenario.GetCharacterList().get(i).GetThisRoundPos()) {
                            case 1:
                                hospitalBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                peopleInHospital -= 1;
                                break;
                            case 2:
                                shrineBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                peopleInShrine -= 1;
                                break;
                            case 3:
                                cityBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                peopleInCity -= 1;
                                break;
                            case 4:
                                schoolBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                peopleInSchool -= 1;
                                break;
                        }
                        archivePanel.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                        scenario.GetCharacterList().get(i).SetCurrentPos(0);
                        mastermindHiddenRoleSkillInforLabel.setVisible(false);
                        JOptionPane.showMessageDialog(menu, "Loop end!");
                        phaseCount = 4;
                    }
                }
                if (phaseCount == 4) {
                    endTurnBtn.setEnabled(true);
                    nextBtn.setEnabled(false);
                }
                startTurnBtn.setEnabled(false);
            }
        };

        Action endTurnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // transfer dead person to archive
                if (isSomeoneDie == true) {
                    for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                        if (scenario.GetCharacterList().get(i).GetAlive() == 0) {
                            switch (scenario.GetCharacterList().get(i).GetThisRoundPos()) {
                                case 1:
                                    hospitalBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInHospital -= 1;
                                    break;
                                case 2:
                                    shrineBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInShrine -= 1;
                                    break;
                                case 3:
                                    cityBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInCity -= 1;
                                    break;
                                case 4:
                                    schoolBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                    peopleInSchool -= 1;
                                    break;
                            }
                            archivePanel.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                            scenario.GetCharacterList().get(i).SetCurrentPos(0);
                        }
                    }
                }
                //set infor
                for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                    if (scenario.GetCharacterList().get(i).GetRole().equals("Brain")) {
                        if (scenario.GetCharacterList().get(i).GetAlive() == 0) {
                            brainIsAlive = false;
                        }
                    }
                    if (scenario.GetCharacterList().get(i).GetRole().equals("Conspiracy Theorist")) {
                        if (scenario.GetCharacterList().get(i).GetAlive() == 0) {
                            conspiracyTheoristIsAlive = false;
                        }
                    }
                }
                currentNewGame.Update(playerCardFactory);
                endTurnBtn.setEnabled(false);
                nextBtn.setEnabled(false);
                startTurnBtn.setEnabled(true);
                phaseCount = 0;
            }
        };

        Action doctorAddAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (characterCardTarget.GetCurrentPos() == characterCardPerform.GetCurrentPos()) {
                    doctorIsAdding = true;
                    doctorSkillPanel.setVisible(false);
                    characterSkill.SetIsAdd(doctorIsAdding);
                    skillActivated = false;
                    characterSkill.ActiveGoodwillSkill();
                }
            }
        };

        Action doctorRemoveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (characterCardTarget.GetCurrentPos() == characterCardPerform.GetCurrentPos()) {
                    doctorIsAdding = false;
                    doctorSkillPanel.setVisible(false);
                    characterSkill.SetIsAdd(doctorIsAdding);
                    skillActivated = false;
                    characterSkill.ActiveGoodwillSkill();
                }
            }
        };

        Action skill1Action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCharacter.GetSkill1Played() == false) {
                    if (currentCharacter.GetCardName().equals("Office Worker")) {
                        hiddenRolePanel.setVisible(true);
                        currentCharacter.SetSkill1Played(true);
                        hiddenRoleLabel.setText("Office Worker's role: " + currentCharacter.GetRole());
                        characterSkillPanel.setVisible(false);
                    } else if (currentCharacter.GetCardName().equals("Police")) {
                        characterSkill = new CharacterSkill(currentCharacter, characterCardTarget, currentNewGame, currentSKill);
                        System.out.println("check 1");
                        currentSKill = 1;
                        characterSkill.ActiveGoodwillSkill();
                        characterSkillPanel.setVisible(false);
                    } else {
                        skillActivated = true;
                        currentSKill = 1;
                        characterCardPerform = currentCharacter;
                    }
                    characterSkillPanel.setVisible(false);
                    skill1.setEnabled(false);
                }
            }
        };

        Action skill2Action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCharacter.GetSkill2Played() == false) {
                    characterSkillPanel.setVisible(false);
                    skillActivated = true;
                    currentSKill = 2;
                    characterCardPerform = currentCharacter;
                    skill2.setEnabled(false);
                }
            }
        };

        Action okBtnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hiddenRolePanel.setVisible(false);
                hiddenRoleLabel.setVisible(false);
                incidentInfor1.setVisible(false);
                incidentInfor2.setVisible(false);
            }
        };

        Action mastermindYesBtnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBrain == true) {
                    for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                        if (scenario.GetCharacterList().get(i).GetRole().equals("Brain")) {
                            characterCardPerform = scenario.GetCharacterList().get(i);
                            break;
                        }
                    }
                    mastermindHiddenRoleSkillInforLabel.setText("Chose a character in " + characterCardPerform.GetCurrentPosName() + " to add 1 intrigue");
                    mastermindHiddenRoleSkillInforLabel.setVisible(true);
                    mastermindHiddenRoleLabel.setText("Do you want to use Conspiracy Theorist's skill?");
                    mastermindSkillActivated = true;
                    mastermindAbilitiesPanel.setVisible(false);
                    nextBtn.setEnabled(false);
                    if (!conspiracyTheoristIsAlive) {
                        nextBtn.setEnabled(true);
                    }
                }
                if (isBrain == false && isConspiracyTheorist == true) {
                    for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                        if (scenario.GetCharacterList().get(i).GetRole().equals("Conspiracy Theorist")) {
                            characterCardPerform = scenario.GetCharacterList().get(i);
                            break;
                        }
                    }
                    mastermindHiddenRoleSkillInforLabel.setText("Chose a character in " + characterCardPerform.GetCurrentPosName() + "  to add 1 paranoia");
                    mastermindHiddenRoleSkillInforLabel.setVisible(true);
                    mastermindSkillActivated = true;
                    mastermindAbilitiesPanel.setVisible(false);
                    mastermindHiddenRoleLabel.setText("Do you want to use Brain's skill?");
                    nextBtn.setEnabled(false);
                }
            }
        };

        Action mainMenuBtnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.container.setVisible(true);
                menu.loadGameBtn.setEnabled(false);
                menu.containerRoot.setVisible(true);
                currentNewGame.setVisible(false);
                menu.loadGameBtn.setEnabled(false);
                contentPanel.setVisible(false);

                menu.repaint();
            }
        };

        Action mastermindNoBtnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBrain == true) {
                    isBrain = false;
                    isConspiracyTheorist = true;
                    mastermindHiddenRoleLabel.setText("Do you want to use Conspiracy Theorist's skill?");
                    if (!conspiracyTheoristIsAlive) {
                        nextBtn.setEnabled(true);
                        mastermindHiddenRoleLabel.setText("Do you want to use Brain's skill?");
                        mastermindAbilitiesPanel.setVisible(false);
                        isConspiracyTheorist = false;
                    }
                } else if (isConspiracyTheorist == true && conspiracyTheoristIsAlive) {
                    isConspiracyTheorist = false;
                    mastermindHiddenRoleLabel.setText("Do you want to use Brain's skill?");
                    mastermindAbilitiesPanel.setVisible(false);
                    nextBtn.setEnabled(true);
                }
            }
        };

        //add Action
        archiveToggleBtn.addActionListener(archiveToggleBtnAction);
        endTurnBtn.addActionListener(endTurnAction);
        startTurnBtn.addActionListener(startTurnAction);
        nextBtn.addActionListener(nextAction);

        //Information
        inforLabel = new JLabel();
        this.add(inforLabel);
        inforLabel.setBounds(menu.frameSize.width / 5 * 4, 0, backgroundSize.width / 2, backgroundSize.height);
        inforLabel.setVisible(false);
        characterInforPanel = new JPanel();
        this.add(characterInforPanel);
        characterInforPanel.setBounds(menu.frameSize.width / 5 * 4, backgroundSize.height, backgroundSize.width / 5, backgroundSize.height / 10);
        characterInforPanel.setLayout(new BoxLayout(characterInforPanel, BoxLayout.Y_AXIS));
        goodwillInforLabel = new JLabel();
        paraInforLabel = new JLabel();
        intrigueInforLabel = new JLabel();
        characterInforPanel.add(goodwillInforLabel);
        characterInforPanel.add(paraInforLabel);
        characterInforPanel.add(intrigueInforLabel);
        characterInforPanel.setVisible(false);

        //Add skill trigger panel
        characterSkillPanel = new JPanel();
        int characterSkillPanelWidth = menu.frameSize.width / 3;
        int characterSkillPanelHeight = menu.frameSize.height / 3;
        characterSkillPanel.setBounds(menu.frameSize.width / 2 - characterSkillPanelWidth / 2, menu.frameSize.height / 2 - characterSkillPanelHeight / 2, characterSkillPanelWidth, characterSkillPanelHeight);
        skill1 = new JButton("Skill 1");
        skill1.addActionListener(skill1Action);
        skill2 = new JButton("Skill 2");
        skill2.addActionListener(skill2Action);
        skill1.setVisible(true);
        skill2.setVisible(true);
        skill1.setEnabled(false);
        skill2.setEnabled(false);
        characterSkillPanel.setVisible(false);
        characterSkillPanel.setLayout(null);
        characterSkillPanel.setMinimumSize(new Dimension(characterSkillPanelWidth, characterSkillPanelHeight / 2));
        characterSkillPanel.add(skill1);
        characterSkillPanel.add(skill2);

        skill1.setBounds(characterSkillPanelWidth / 6 + characterSkillPanelWidth / 30, characterSkillPanelHeight / 2, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        skill2.setBounds(characterSkillPanelWidth / 2 + characterSkillPanelWidth / 50, characterSkillPanelHeight / 2, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);

        // Doctor's skill special trigger
        doctorSkillPanel = new JPanel();
        doctorSkillPanel.setLayout(null);
        doctorSkillPanel.setBounds(menu.frameSize.width / 2 - characterSkillPanelWidth / 2, menu.frameSize.height / 2 - characterSkillPanelHeight / 2, characterSkillPanelWidth, characterSkillPanelHeight);
        doctorAddBtn = new JButton("Add Paranoia");
        doctorAddBtn.addActionListener(doctorAddAction);
        doctorRemoveBtn = new JButton("Remove paranoia");
        doctorRemoveBtn.addActionListener(doctorRemoveAction);
        doctorAddBtn.setBounds(characterSkillPanelWidth / 6 + characterSkillPanelWidth / 30, characterSkillPanelHeight / 2, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        doctorRemoveBtn.setBounds(characterSkillPanelWidth / 2 + characterSkillPanelWidth / 50, characterSkillPanelHeight / 2, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        doctorSkillPanel.setVisible(false);
        doctorSkillPanel.add(doctorAddBtn);
        doctorSkillPanel.add(doctorRemoveBtn);

        // Show hidden role through gw skill
        hiddenRolePanel = new JPanel();
        hiddenRolePanel.setLayout(null);
        hiddenRolePanel.setBounds(menu.frameSize.width / 2 - characterSkillPanelWidth / 2, menu.frameSize.height / 2 - characterSkillPanelHeight / 2, characterSkillPanelWidth, characterSkillPanelHeight);
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(okBtnAction);
        okBtn.setBounds(characterSkillPanelWidth / 3, characterSkillPanelHeight / 4 * 3, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        hiddenRolePanel.add(okBtn);
        hiddenRolePanel.setVisible(false);
        hiddenRoleLabel = new JLabel();
        hiddenRoleLabel.setBounds(characterSkillPanelWidth / 3, characterSkillPanelHeight / 4, characterSkillPanelWidth / 2, characterSkillPanelHeight / 4);
        hiddenRolePanel.add(hiddenRoleLabel);
        hiddenRoleLabel.setVisible(false);

        gameResultPanel = new JPanel();
        gameResultPanel.setLayout(null);
        gameResultPanel.setBounds(menu.frameSize.width / 2 - exitPanelWidth / 2, menu.frameSize.height / 2 - exitPanelHeight / 2, exitPanelWidth, exitPanelHeight);
        gameResultLabel = new JLabel();
        gameResultLabel.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.width / 60));
        gameResultLabel.setBounds(exitPanelWidth / 100 * 29, exitPanelHeight / 8, exitPanelWidth / 2, exitPanelHeight / 5);
        mainMenuBtn = new JButton("Main Menu");
        mainMenuBtn.addActionListener(mainMenuBtnAction);
        mainMenuBtn.setBounds(characterSkillPanelWidth / 3, characterSkillPanelHeight / 4 * 3, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        mainMenuBtn.setFont(new Font("Algerian", Font.PLAIN, menu.frameSize.width / 80));
        mainMenuBtn.setOpaque(false);
        mainMenuBtn.setContentAreaFilled(false);
        mainMenuBtn.setBorderPainted(false);
        gameResultPanel.add(gameResultLabel);
        gameResultPanel.add(mainMenuBtn);
        gameResultPanel.setVisible(false);
        contentPanel.add(gameResultPanel);

        incidentInfor1 = new JLabel();
        incidentInfor1.setBounds(characterSkillPanelWidth / 3, characterSkillPanelHeight / 4, characterSkillPanelWidth / 2, characterSkillPanelHeight / 4);
        incidentInfor1.setVisible(false);
        hiddenRolePanel.add(incidentInfor1);
        incidentInfor2 = new JLabel();
        incidentInfor2.setBounds(characterSkillPanelWidth / 3, characterSkillPanelHeight / 4 * 2, characterSkillPanelWidth / 2, characterSkillPanelHeight / 4);
        incidentInfor2.setVisible(false);
        hiddenRolePanel.add(incidentInfor2);

        // Resolve using hidden skill
        mastermindAbilitiesPanel = new JPanel();
        mastermindAbilitiesPanel.setLayout(null);
        mastermindAbilitiesPanel.setBounds(menu.frameSize.width / 2 - characterSkillPanelWidth / 2, menu.frameSize.height / 2 - characterSkillPanelHeight / 2, characterSkillPanelWidth, characterSkillPanelHeight);
        mastermindYesBtn = new JButton("Yes");
        mastermindYesBtn.setBounds(characterSkillPanelWidth / 6 + characterSkillPanelWidth / 30, characterSkillPanelHeight / 2, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        mastermindYesBtn.addActionListener(mastermindYesBtnAction);
        mastermindNoBtn = new JButton("No");
        mastermindNoBtn.setBounds(characterSkillPanelWidth / 2 + characterSkillPanelWidth / 30, characterSkillPanelHeight / 2, characterSkillPanelWidth / 4, characterSkillPanelHeight / 4);
        mastermindNoBtn.addActionListener(mastermindNoBtnAction);
        mastermindHiddenRoleLabel = new JLabel();
        mastermindHiddenRoleLabel.setBounds(characterSkillPanelWidth / 3, characterSkillPanelHeight / 4, characterSkillPanelWidth / 2, characterSkillPanelHeight / 4);
        mastermindHiddenRoleLabel.setText("Do you want to use Brain's skill?");
        mastermindAbilitiesPanel.add(mastermindYesBtn);
        mastermindAbilitiesPanel.add(mastermindNoBtn);
        mastermindAbilitiesPanel.add(mastermindHiddenRoleLabel);
        mastermindAbilitiesPanel.setVisible(false);

        mastermindHiddenRoleSkillInforLabel = new JLabel();
        mastermindHiddenRoleSkillInforLabel.setBounds(menu.frameSize.width / 2 - characterSkillPanelWidth / 2, menu.frameSize.height / 2 - characterSkillPanelHeight / 2, menu.frameSize.width, characterSkillPanelHeight);
        mastermindHiddenRoleSkillInforLabel.setForeground(Color.RED);
        mastermindHiddenRoleSkillInforLabel.setFont(new Font("Algerian", Font.PLAIN, characterSkillPanelWidth / 30));
        mastermindHiddenRoleSkillInforLabel.setVisible(false);

        contentPanel.add(mastermindHiddenRoleSkillInforLabel);
        contentPanel.add(mastermindAbilitiesPanel);
        contentPanel.add(hiddenRolePanel);
        contentPanel.add(doctorSkillPanel);
        contentPanel.add(characterSkillPanel);

        //add character
        scenario = new CreateScenario("The First Script", language);    //insert chosen scenario here
        scenario.CreateFromJSONFile();
        for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
            final int final_i = i;
            if (scenario.GetCharacterList().get(i).GetStartingPos() == 1) {
                hospitalBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
            } else if (scenario.GetCharacterList().get(i).GetStartingPos() == 2) {
                shrineBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
            } else if (scenario.GetCharacterList().get(i).GetStartingPos() == 3) {
                cityBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
            } else if (scenario.GetCharacterList().get(i).GetStartingPos() == 4) {
                schoolBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
            }
            BufferedImage imgResize = null;
            try {
                imgResize = Scalr.resize(ImageIO.read(new File(scenario.GetCharacterList().get(i).GetImageURL())), backgroundSize.width / 6, backgroundSize.height / 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            scenario.GetCharacterList().get(i).GetCharacterLabel().setIcon(new ImageIcon(imgResize));
            int finalI = i;
            int finalI1 = i;
            scenario.GetCharacterList().get(i).GetCharacterLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    BufferedImage inforImg = null;
                    try {
                        inforImg = Scalr.resize(ImageIO.read(new File(scenario.GetCharacterList().get(final_i).GetImageURL())), backgroundSize.width / 2, backgroundSize.height);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inforLabel.setIcon(new ImageIcon(inforImg));
                    inforLabel.setVisible(true);
                    goodwillInforLabel.setText("Goodwill: " + scenario.GetCharacterList().get(final_i).GetGoodwillCount());
                    paraInforLabel.setText("Paranoia: " + scenario.GetCharacterList().get(final_i).GetParanoiaCount());
                    intrigueInforLabel.setText("Intrigue: " + scenario.GetCharacterList().get(final_i).GetIntrigueCount());
                    characterInforPanel.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    BufferedImage inforImg = null;
                    try {
                        inforImg = Scalr.resize(ImageIO.read(new File(scenario.GetCharacterList().get(final_i).GetImageURL())), backgroundSize.width / 2, backgroundSize.height);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inforLabel.setIcon(new ImageIcon(inforImg));
                    inforLabel.setVisible(false);
                    characterInforPanel.setVisible(false);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (phaseCount == 0) {
                        if (scenario.GetCharacterList().get(finalI).GetAlive() > 0) {
                            for (int j = 0; j < playerCardFactory.GetPlayerCardList().size(); j++) {
                                if (playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardClicked()) {
                                    Point coordinate = scenario.GetCharacterList().get(finalI).GetCharacterLabel().getLocationOnScreen();
                                    if (isMastermind && scenario.GetCharacterList().get(finalI).GetMastermindCardPlayed() == false) {
                                        playerCardFactory.GetPlayerCardList().get(j).SetPlayerCardClicked(false);
                                        playerCardClicked = false;
                                        mastermindHandPanel.remove(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                                        BufferedImage tempImg = null;
                                        try {
                                            tempImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(j).GetImageURL())), backgroundSize.width / 30, backgroundSize.height / 3);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        currentNewGame.ResolveCard(scenario.GetCharacterList().get(finalI), playerCardFactory.GetPlayerCardList().get(j));
                                        playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setIcon(new ImageIcon(tempImg));
                                        playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setVisible(true);
                                        playerCardFactory.GetPlayerCardList().get(j).SetCardUsed(true);
                                        contentPanel.add(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                                        if (menu.currentDisplayIndex == 1) {
                                            if (menu.currentResolutionIndex == 0) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x - 640, coordinate.y + backgroundSize.height / 5 - 360, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                            if (menu.currentResolutionIndex == 1) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x - 533, coordinate.y + backgroundSize.height / 5 - 300, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                            if (menu.currentResolutionIndex == 2) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x - 320, coordinate.y + backgroundSize.height / 5 - 180, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                            if (menu.currentResolutionIndex >= 3) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x, coordinate.y + backgroundSize.height / 5, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                        } else if (menu.currentDisplayIndex == 0) {
                                            playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x, coordinate.y + backgroundSize.height / 5, backgroundSize.width / 5, backgroundSize.height / 3);
                                        }
                                        mastermindHandPanel.repaint();
                                        playerCardFactory.GetPlayerCardList().get(j).SetPlayedState(true);
                                        scenario.GetCharacterList().get(finalI).SetMastermindCardPlayed(true);
                                        cardPlayedCount += 1;
                                        //Chơi đủ 3 lá mastermind
                                        if (cardPlayedCount == 3 && isMastermind) {
                                            cardPlayedCount = 0;
                                            isMastermind = false;
                                            mastermindHandPanel.setVisible(false);
                                            protagonistHandPanel.setVisible(true);
                                        }
                                    } else if (isMastermind == false && scenario.GetCharacterList().get(finalI).GetProtagonistCardPlayed() == false) {
                                        playerCardFactory.GetPlayerCardList().get(j).SetPlayerCardClicked(false);
                                        playerCardClicked = false;
                                        protagonistHandPanel.remove(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                                        BufferedImage tempImg = null;
                                        try {
                                            tempImg = Scalr.resize(ImageIO.read(new File(playerCardFactory.GetPlayerCardList().get(j).GetImageURL())), backgroundSize.width / 30, backgroundSize.height / 3);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        currentNewGame.ResolveCard(scenario.GetCharacterList().get(finalI), playerCardFactory.GetPlayerCardList().get(j));
                                        playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setIcon(new ImageIcon(tempImg));
                                        playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setVisible(true);
                                        playerCardFactory.GetPlayerCardList().get(j).SetCardUsed(true);
                                        contentPanel.add(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                                        if (menu.currentDisplayIndex == 1) {
                                            if (menu.currentResolutionIndex == 0) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x - 640, coordinate.y + backgroundSize.height / 5 - 330, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                            if (menu.currentResolutionIndex == 1) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x - 533, coordinate.y + backgroundSize.height / 5 - 270, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                            if (menu.currentResolutionIndex == 2) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x - 320, coordinate.y + backgroundSize.height / 5 - 150, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                            if (menu.currentResolutionIndex >= 3) {
                                                playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x, coordinate.y + backgroundSize.height / 5 + 30, backgroundSize.width / 5, backgroundSize.height / 3);
                                            }
                                        } else if (menu.currentDisplayIndex == 0) {
                                            playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel().setBounds(coordinate.x, coordinate.y + backgroundSize.height / 5 + 30, backgroundSize.width / 5, backgroundSize.height / 3);
                                        }
                                        protagonistHandPanel.repaint();
                                        playerCardFactory.GetPlayerCardList().get(j).SetPlayedState(true);
                                        scenario.GetCharacterList().get(finalI).SetProtagonistCardPlayed(true);
                                        cardPlayedCount += 1;
                                        //Chơi đủ 3 lá mastermind
                                        if (cardPlayedCount == 3 && isMastermind == false) {
                                            cardPlayedCount = 0;
                                            protagonistHandPanel.setVisible(false);
                                            startTurnBtn.setEnabled(false);
                                            nextBtn.setEnabled(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    }
                    else if (phaseCount == 1) { // hidden role
                        characterSkill = new CharacterSkill(characterCardPerform, characterCardTarget, currentNewGame, currentSKill);
                        if (isBrain == true && mastermindSkillActivated == true) {
                            if (scenario.GetCharacterList().get(finalI).GetCurrentPos() == characterCardPerform.GetCurrentPos()) {
                                characterSkill.BrainSKill(scenario.GetCharacterList().get(finalI));
                                isBrain = false;
                                mastermindSkillActivated = false;
                                isConspiracyTheorist = true;
                                mastermindHiddenRoleSkillInforLabel.setVisible(false);
                                mastermindAbilitiesPanel.setVisible(true);
                                nextBtn.setEnabled(false);
                                if (!conspiracyTheoristIsAlive) {
                                    nextBtn.setEnabled(true);
                                    mastermindAbilitiesPanel.setVisible(false);
                                }
                            }
                        } else if (isBrain == false && mastermindSkillActivated == true && isConspiracyTheorist == true && conspiracyTheoristIsAlive) {
                            if (scenario.GetCharacterList().get(finalI).GetCurrentPos() == characterCardPerform.GetCurrentPos()) {
                                characterSkill.ConspiracyTheoristSkill(scenario.GetCharacterList().get(finalI));
                                isBrain = true;
                                mastermindSkillActivated = false;
                                isConspiracyTheorist = false;
                                mastermindHiddenRoleSkillInforLabel.setVisible(false);
                                nextBtn.setEnabled(true);
                            }
                        }
                    } else if (phaseCount == 2) {  // goodwill abi
                        if (skillActivated == false) {
                            if (isOpenCharacterSkillPanel == false && otherCharacterSkillPanelOpen == false) {
                                nextBtn.setEnabled(false);
                                currentCharacter = scenario.GetCharacterList().get(finalI);
                                characterSkillPanel.setVisible(true);
                                isOpenCharacterSkillPanel = true;
                                otherCharacterSkillPanelOpen = true;
                                if (scenario.GetCharacterList().get(finalI).GetGoodwillCount() >= scenario.GetCharacterList().get(finalI).GetGoodwillThreshold1()) {
                                    if (scenario.GetCharacterList().get(finalI).GetGoodwillCount() >= scenario.GetCharacterList().get(finalI).GetGoodwillThreshold2()) {
                                        skill1.setEnabled(true);
                                        skill2.setEnabled(true);
                                    } else {
                                        skill1.setEnabled(true);
                                        skill2.setEnabled(false);
                                    }
                                }
                            } else if (isOpenCharacterSkillPanel) {
                                nextBtn.setEnabled(true);
                                characterSkillPanel.setVisible(false);
                                isOpenCharacterSkillPanel = false;
                                otherCharacterSkillPanelOpen = false;
                            }
                            if (currentCharacter.GetSkill1Played() == true) {
                                skill1.setEnabled(false);
                            }
                            if (currentCharacter.GetSkill2Played() == true) {
                                skill2.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Doctor")) {
                                skill2.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Shrine Maiden")) {
                                skill1.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Shrine Maiden") && shrineMaidenSkill2Used) {
                                skill2.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Doctor") && GetPeopleIn(currentCharacter.GetCurrentPos()) < 2) {
                                skill1.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Boy Student") && GetPeopleIn(currentCharacter.GetCurrentPos()) < 2) {
                                skill1.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Girl Student") && GetPeopleIn(currentCharacter.GetCurrentPos()) < 2) {
                                skill1.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Police") && policeSkill1used) {
                                skill1.setEnabled(false);
                            }
                            if (currentCharacter.GetCardName().equals("Police") && policeSkill2used) {
                                skill2.setEnabled(false);
                            }
                        } else if (skillActivated) {
                            nextBtn.setEnabled(true);
                            characterCardTarget = scenario.GetCharacterList().get(finalI);
                            characterSkill = new CharacterSkill(characterCardPerform, characterCardTarget, currentNewGame, currentSKill);
                            if (characterCardPerform.GetCardName().equals("Doctor")) {
                                if (characterCardTarget.GetCurrentPos() == characterCardPerform.GetCurrentPos() && !characterCardTarget.GetCardName().equals("Doctor")) {
                                    doctorSkillPanel.setVisible(true);
                                }
                            } else if (characterCardPerform.GetCardName().equals("Police")) {
                                if (characterCardTarget.GetCurrentPos() == characterCardPerform.GetCurrentPos() && !characterCardTarget.GetCardName().equals("Police")) {
                                    characterSkill.ActiveGoodwillSkill();
                                    skillActivated = false;
                                }
                            } else if (characterCardPerform.GetCardName().equals("Shrine Maiden")) {
                                characterSkill.ActiveGoodwillSkill();
                                if (characterCardTarget.GetCurrentPos() == characterCardPerform.GetCurrentPos()) {
                                    hiddenRoleLabel.setText(characterCardTarget.GetCardName() + "'s role: " + characterSkill.ShrineMaidenSkill2());
                                    hiddenRoleLabel.setVisible(true);
                                    hiddenRolePanel.setVisible(true);
                                    shrineMaidenSkill2Used = true;
                                }
                                skillActivated = false;
                            } else {
                                characterSkill.ActiveGoodwillSkill();
                                skillActivated = false;
                            }
                        }

                        System.out.println(GetPeopleIn(currentCharacter.GetCurrentPos()));
                    } else if (phaseCount == 3) { // incident
                        currentCharacter = scenario.GetCharacterList().get(finalI);
                        if (isMurderHappen == false && skillActivated == true) {
                            if (!currentCharacter.GetCardName().equals(characterCardPerform.GetCardName()) && currentCharacter.GetCurrentPos() == characterCardPerform.GetCurrentPos()) {
                                currentCharacter.KillCharacter();
                                System.out.println(currentCharacter.GetAlive());
                                JOptionPane.showMessageDialog(menu, currentCharacter.GetCardName() + " is murdered!!!");
                                skillActivated = false;
                                isMurderHappen = true;
                                nextBtn.setEnabled(true);
                                mastermindHiddenRoleSkillInforLabel.setVisible(true);
                            }
                        }
                    }
                }
            });
        }

        //add contents to container
        this.add(cityBG);
        this.add(schoolBG);
        this.add(hospitalBG);
        this.add(shrineBG);
        this.add(dataBG);

        //Set Visible
        shrineBG.setVisible(true);
        cityBG.setVisible(true);
        hospitalBG.setVisible(true);
        dataBG.setVisible(true);
        schoolBG.setVisible(true);
        this.setVisible(false);

        //Action
        Action exitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanel.setVisible(true);
            }
        };

        //add keyboard handler
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "things?");
        this.getActionMap().put("things?", exitAction);
    }

    public Menu GetMenu() {
        return menu;
    }

    public JPanel GetHiddenRolePanel() {
        return hiddenRolePanel;
    }

    public JLabel GetIncidentInfor1() {
        return incidentInfor1;
    }

    public JLabel GetIncidentInfor2() {
        return incidentInfor2;
    }

    public boolean GetPoliceSkill1Used() {
        return policeSkill1used;
    }

    public boolean GetPoliceSkill2Used() {
        return policeSkill2used;
    }

    public void SetPoliceSkill1Used(boolean newState) {
        this.policeSkill1used = newState;
    }

    public void SetPoliceSkill2Used(boolean newState) {
        this.policeSkill2used = newState;
    }

    public boolean GetShrineMaidenSkill2Used() {
        return shrineMaidenSkill2Used;
    }

    public void SetShrineMaidenSkill2Used(boolean newState) {
        this.shrineMaidenSkill2Used = newState;
    }

    public boolean GetIsMurderHappen() {
        return isMurderHappen;
    }

    public boolean GetIsSuicideHappen() {
        return isSuicideHappen;
    }

    // Đưa vào thẻ bài rồi lấy tên, duyệt tên để quyết định kĩ năng sử dụng
    public void ResolveCard(CharacterCard characterCard, PlayerCard playerCard) {
        if (playerCard.GetCardName().equals("protagonistA_add1para") || playerCard.GetCardName().equals("protagonistB_add1para") || playerCard.GetCardName().equals("protagonistC_add1para") || playerCard.GetCardName().equals("mastermind_add1para1") || playerCard.GetCardName().equals("mastermind_add1para2")) {
            playerCard.AddParanoia(characterCard, 1);                            //Add 1 para
        }
        if (playerCard.GetCardName().equals("protagonistA_remove1para") || playerCard.GetCardName().equals("protagonistB_remove1para") || playerCard.GetCardName().equals("protagonistC_remove1para") || playerCard.GetCardName().equals("mastermind_remove1para")) {
            playerCard.AddParanoia(characterCard, -1);                           //remove 1 para
        }
        if (playerCard.GetCardName().equals("mastermind_forbidpara")) {
            playerCard.ForbidParanoia((characterCard));                                //forbid para
        }
        if (playerCard.GetCardName().equals("protagonistA_add1goodwill") || playerCard.GetCardName().equals("protagonistB_add1goodwill") || playerCard.GetCardName().equals("protagonistC_add1goodwill")) {
            playerCard.AddGoodwill(characterCard, 1);                            //add 1 goodwill
        }
        if (playerCard.GetCardName().equals("protagonistA_add2goodwill") || playerCard.GetCardName().equals("protagonistB_add2goodwill") || playerCard.GetCardName().equals("protagonistC_add2goodwill")) {
            playerCard.AddGoodwill(characterCard, 2);                            //add 2 goodwill                once per loop
        }
        if (playerCard.GetCardName().equals("mastermind_forbidgoodwill")) {
            playerCard.ForbidGoodwill(characterCard);                                  //forbid goodwill
        }
        if (playerCard.GetCardName().equals("mastermind_add1intrigue")) {
            playerCard.AddIntrigue(characterCard, 1);                    //add 1 intrigue
        }
        if (playerCard.GetCardName().equals("mastermind_add2intrigue")) {
            playerCard.AddIntrigue(characterCard, 2);        //add 2 intrigue       once per loop
        }
        if (playerCard.GetCardName().equals("protagonistA_forbidintrigue") || playerCard.GetCardName().equals("protagonistB_forbidintrigue") || playerCard.GetCardName().equals("protagonistC_forbidintrigue")) {
            playerCard.ForbidIntrigue(characterCard);        //forbid intrigue
        }
        if (playerCard.GetCardName().equals("protagonistA_verticalmovement") || playerCard.GetCardName().equals("protagonistB_verticalmovement") || playerCard.GetCardName().equals("protagonistC_verticalmovement") || playerCard.GetCardName().equals("mastermind_verticalmovement")) {
            playerCard.VerticalMovement(characterCard);        //vertical move
        }
        if (playerCard.GetCardName().equals("protagonistA_horizontalmovement") || playerCard.GetCardName().equals("protagonistB_horizontalmovement") || playerCard.GetCardName().equals("protagonistC_horizontalmovement") || playerCard.GetCardName().equals("mastermind_horizontalmovement")) {
            playerCard.HorizontalMovement(characterCard);        //horizontal move
        }
        if (playerCard.GetCardName().equals("mastermind_diagonalmovement")) {
            playerCard.DiagonalMovement(characterCard);        //diagonal move         once per loop
        }
        if (playerCard.GetCardName().equals("protagonistA_forbidmovement") || playerCard.GetCardName().equals("protagonistB_forbidmovement") || playerCard.GetCardName().equals("protagonistC_forbidmovement")) {
            playerCard.ForbidMovement(characterCard);        //forbid move               once per loop
        }
    }

    public void Next(PlayerCardFactory playerCardFactory) {
        if (phaseCount == 1 && isPhase1 == false) {
            for (int j = playerCardFactory.GetPlayerCardList().size() - 1; j >= 0; j--) {
                contentPanel.remove(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                mastermindHandPanel.remove(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                if (playerCardFactory.GetPlayerCardList().get(j).GetCardUsed() != true || playerCardFactory.GetPlayerCardList().get(j).GetReUsable()) {
                    if (playerCardFactory.GetPlayerCardList().get(j).GetCardName().equals("protagonistC_forbidmovement")) {
                        break;
                    }
                    mastermindHandPanel.add(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                }
            }

            // Xử lí tay prota
            for (int i = 0; i < playerCardFactory.GetPlayerCardList().size(); i++) {
                contentPanel.remove(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
                protagonistHandPanel.remove(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
                if (playerCardFactory.GetPlayerCardList().get(i).GetCardUsed() != true || playerCardFactory.GetPlayerCardList().get(i).GetReUsable()) {
                    if (playerCardFactory.GetPlayerCardList().get(i).GetCardName().equals("mastermind_add1para1")) {
                        break;
                    }
                    protagonistHandPanel.add(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
                }
            }

            // Xử lí di chuyển
            for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
//            System.out.println(scenario.GetCharacterList().get(i).GetCardName()+" "+scenario.GetCharacterList().get(i).GetCurrentPos());
                if (scenario.GetCharacterList().get(i).GetIsMoving() == true) {
                    if (scenario.GetCharacterList().get(i).GetForbidMovement() == false) {
//                    switch (scenario.GetCharacterList().get(i).GetTempPos()) {
                        switch (scenario.GetCharacterList().get(i).GetThisRoundPos()) {
                            case 1:
                                hospitalBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("hospital-1");
                                peopleInHospital -= 1;
                                break;
                            case 2:
                                shrineBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("Shrine-1");
                                peopleInShrine -= 1;
                                break;
                            case 3:
                                cityBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("city-1");
                                peopleInCity -= 1;
                                break;
                            case 4:
                                schoolBG.remove(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("school-1");
                                peopleInSchool -= 1;
                                break;
                        }
                        if (scenario.GetCharacterList().get(i).GetCurrentPos() == scenario.GetCharacterList().get(i).GetForbidPos()) {
                            scenario.GetCharacterList().get(i).SetCurrentPos(scenario.GetCharacterList().get(i).GetTempPos());
                        }
                        switch (scenario.GetCharacterList().get(i).GetCurrentPos()) {
                            case 1:
                                hospitalBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("hospital+1");
                                peopleInHospital += 1;
                                break;
                            case 2:
                                shrineBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("shrine+1");
                                peopleInShrine += 1;
                                break;
                            case 3:
                                cityBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("city+1");
                                peopleInCity += 1;
                                break;
                            case 4:
                                schoolBG.add(scenario.GetCharacterList().get(i).GetCharacterLabel());
                                System.out.println("school+1");
                                peopleInSchool += 1;
                                break;
                        }
                    }
                }
            }
        }

        //Repaint
        this.repaint();
        hospitalBG.repaint();
        shrineBG.repaint();
        cityBG.repaint();
        schoolBG.repaint();
        dataBG.repaint();
        contentPanel.repaint();
        protagonistHandPanel.repaint();
        mastermindHandPanel.repaint();

        // Xử lí phase 4 (day-end)
        if (phaseCount == 4) {
            System.out.println("phaseCount + anyhthing to check");
            System.out.println("hospital " + GetPeopleIn(1));
            System.out.println("shrine" + GetPeopleIn(2));
            System.out.println("city " + GetPeopleIn(3));
            System.out.println("school " + GetPeopleIn(4));
            for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
                if (scenario.GetCharacterList().get(i).GetRole().equals("Serial Killer") && GetPeopleIn(scenario.GetCharacterList().get(i).GetCurrentPos()) == 2) {
                    for (int j = 0; i < scenario.GetCharacterList().size(); j++) {
                        if (!scenario.GetCharacterList().get(j).GetRole().equals("Serial Killer") && scenario.GetCharacterList().get(j).GetCurrentPos() == scenario.GetCharacterList().get(i).GetCurrentPos()) {
                            scenario.GetCharacterList().get(j).KillCharacter();
                            break;
                        }
                    }
                }
            }
            characterSkill = new CharacterSkill(currentCharacter, characterCardTarget, currentNewGame, currentSKill);
            characterSkill.KillerSkill();
        }
    }

    public void Update(PlayerCardFactory playerCardFactory) {
        //Repaint
        this.repaint();
        hospitalBG.repaint();
        shrineBG.repaint();
        cityBG.repaint();
        schoolBG.repaint();
        dataBG.repaint();
        contentPanel.repaint();
        protagonistHandPanel.repaint();
        mastermindHandPanel.repaint();

        //check alive
        for (int i = 0; i < scenario.GetCharacterList().size(); i++) {
            if (scenario.GetCharacterList().get(i).GetRole().equals("Key Person")) {
                checkKeyPersonState = scenario.GetCharacterList().get(i).GetAlive();
                System.out.println("current alive?: " + scenario.GetCharacterList().get(i).GetAlive());
                System.out.println("gan alive: " + checkKeyPersonState);
                break;
            }
        }
        if (day == 4 && checkKeyPersonState != 0) {
            gameResultLabel.setText("Protagonist Win");
            gameResultPanel.setVisible(true);
        }

        if (day < 4 && checkKeyPersonState != 0) {
            day += 1;
            for (int i = 0; i < playerCardFactory.GetPlayerCardList().size(); i++) {
                playerCardFactory.GetPlayerCardList().get(i).ResetDay();
                playerCardClicked = false;
                isBrain = true;
                isConspiracyTheorist = false;
            }
            for (int j = 0; j < scenario.GetCharacterList().size(); j++) {
                scenario.GetCharacterList().get(j).ResetDay();
            }
        } else if (day == 4 || checkKeyPersonState == 0) {
            if (loop > 1) {
                day = 1;
                loop -= 1;

                brainIsAlive = true;
                conspiracyTheoristIsAlive = true;
                isBrain = true;
                isSomeoneDie = false;
                isConspiracyTheorist = false;
                isMurderHappen = false;
                isSuicideHappen = false;
                policeSkill1used = false;
                policeSkill2used = false;
                shrineMaidenSkill2Used = false;
                archiveToggleBtn.setEnabled(false);
                peopleInSchool = 2;
                peopleInCity = 2;
                peopleInShrine = 1;
                peopleInHospital = 1;

                //reset playercard
                for (int i = 0; i < playerCardFactory.GetPlayerCardList().size(); i++) {
                    playerCardFactory.GetPlayerCardList().get(i).ResetLoop();
                    playerCardClicked = false;
                }

                //add playercard to hand
                for (int i = 0; i < playerCardFactory.GetPlayerCardList().size(); i++) {
                    contentPanel.remove(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
                    protagonistHandPanel.remove(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
                    if (playerCardFactory.GetPlayerCardList().get(i).GetCardUsed() != true || playerCardFactory.GetPlayerCardList().get(i).GetReUsable()) {
                        if (playerCardFactory.GetPlayerCardList().get(i).GetCardName().equals("mastermind_add1para1")) {
                            break;
                        }
                        protagonistHandPanel.add(playerCardFactory.GetPlayerCardList().get(i).GetPlayerCardLabel());
                    }
                }
                for (int j = playerCardFactory.GetPlayerCardList().size() - 1; j >= 0; j--) {
                    contentPanel.remove(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                    mastermindHandPanel.remove(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                    if (playerCardFactory.GetPlayerCardList().get(j).GetCardUsed() != true || playerCardFactory.GetPlayerCardList().get(j).GetReUsable()) {
                        if (playerCardFactory.GetPlayerCardList().get(j).GetCardName().equals("protagonistC_forbidmovement")) {
                            break;
                        }
                        mastermindHandPanel.add(playerCardFactory.GetPlayerCardList().get(j).GetPlayerCardLabel());
                    }
                }

                //reset character position
                for (int j = 0; j < scenario.GetCharacterList().size(); j++) {
                    scenario.GetCharacterList().get(j).ResetLoop();
                    switch (scenario.GetCharacterList().get(j).GetTempPos()) {
                        case 0:
                            archivePanel.remove(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 1:
                            hospitalBG.remove(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 2:
                            shrineBG.remove(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 3:
                            cityBG.remove(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 4:
                            schoolBG.remove(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                    }
                    switch (scenario.GetCharacterList().get(j).GetCurrentPos()) {
                        case 1:
                            hospitalBG.add(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 2:
                            shrineBG.add(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 3:
                            cityBG.add(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                        case 4:
                            schoolBG.add(scenario.GetCharacterList().get(j).GetCharacterLabel());
                            break;
                    }
                }

            } else if (loop == 1 && checkKeyPersonState == 0) { //final guess
                gameResultLabel.setText("Mastermind Win");
                gameResultPanel.setVisible(true);
            }
        }
        if (language == 1) {
            if (this.day == 4) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day4loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day4loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day4loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day4loop1.png").getImage());
                }
            }
            if (this.day == 3) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day3loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day3loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day3loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day3loop1.png").getImage());
                }
            }
            if (this.day == 2) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day2loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day2loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day2loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day2loop1.png").getImage());
                }
            }
            if (this.day == 1) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day1loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day1loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day1loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/JP/day1loop1.png").getImage());
                }
            }
            if (this.loop == 0) {
                dataBG.SetImage(new ImageIcon("res/Turn/JP/badend.png").getImage());
            }
        } else if (language == 0) {
            if (this.day == 4) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day4loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day4loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day4loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day4loop1.png").getImage());
                }
            }
            if (this.day == 3) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day3loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day3loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day3loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day3loop1.png").getImage());
                }
            }
            if (this.day == 2) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day2loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day2loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day2loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day2loop1.png").getImage());
                }
            }
            if (this.day == 1) {
                if (this.loop == 4) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day1loop4.png").getImage());
                }
                if (this.loop == 3) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day1loop3.png").getImage());
                }
                if (this.loop == 2) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day1loop2.png").getImage());
                }
                if (this.loop == 1) {
                    dataBG.SetImage(new ImageIcon("res/Turn/EN/day1loop1.png").getImage());
                }
            }
            if (this.loop == 0) {
                dataBG.SetImage(new ImageIcon("res/Turn/EN/badend.png").getImage());
            }
        }
    }

    public int GetCurrentSkill() {
        return currentSKill;
    }

    public int GetPeopleIn(int Pos) {
        switch (Pos) {
            case 1:
                return peopleInHospital;
            case 2:
                return peopleInShrine;
            case 3:
                return peopleInCity;
            case 4:
                return peopleInSchool;
        }
        return 0;
    }

    public CreateScenario GetScenario() {
        return scenario;
    }
}