package Ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Menu extends JFrame implements ActionListener{

    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
    }

    public int menuState=0;           // 0 - main menu /  1 - exitPanel
    public int exitPanelCommandNum = -1;
    public int commandNum=-1;
    JPanel exitPanel;
    JPanel container;
    JFrame frame;
    Dimension frameSize;
    int currentLanguageIndex;
    int currentResolutionIndex;
    int currentDisplayIndex;
    Menu currentObject = this;
    Config config;
    Option option;
    NewGame newGame;
    JPanel containerRoot;
    JButton loadGameBtn;

    public Menu() {
        config = new Config();
        config.loadConfig();        //load config
        currentLanguageIndex = config.GetLanguageIndex();
        currentResolutionIndex = config.GetResolutionIndex();
        currentDisplayIndex = config.GetDisplayIndex();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image image = new ImageIcon("res/tragedy_commons_5th/logo/logo.png").getImage();

        //set resolution
        frame = new JFrame();
        if (currentResolutionIndex == 0) {
            frameSize = new Dimension(640, 360);
        }
        if (currentResolutionIndex == 1) {
            frameSize = new Dimension(854, 480);
        }
        if (currentResolutionIndex == 2) {
            frameSize = new Dimension(1280, 720);
        }
        if (currentResolutionIndex == 3) {
            frameSize = new Dimension(1920, 1080);
        }
        if (currentResolutionIndex == 4) {
            frameSize = new Dimension(2560, 1440);
        }
        if (currentResolutionIndex == 5) {
            frameSize = new Dimension(screenSize.width, screenSize.height);
        }

        // Fulscreen, borderless windowed
        switch (currentDisplayIndex) {
            case 0:
                frameSize = new Dimension(screenSize.width, screenSize.height);
                frame.setUndecorated(true);
                break;
            case 1:
                frame.setUndecorated(true);
                break;
        }

        frame.setSize(frameSize);
        containerRoot = new PaintBackGround(image, frameSize);
        container= new JPanel();

        //track chosen button using keyboard
        JLabel newGamePointer = new JLabel(">");
        newGamePointer.setBounds(frameSize.width /10 - frameSize.width/1000, frameSize.height / 6 * 5, frameSize.width / 40, frameSize.height / 15);
        newGamePointer.setFont(new Font ("Algerian",Font.PLAIN,frameSize.width/30));
        newGamePointer.setVisible(false);

        JLabel loadGamePointer = new JLabel(">");
        loadGamePointer.setBounds(frameSize.width / 25*7 - frameSize.width/1000, frameSize.height / 6 * 5, frameSize.width / 40, frameSize.height / 15);
        loadGamePointer.setFont(new Font ("Algerian",Font.PLAIN,frameSize.width/30));
        loadGamePointer.setVisible(false);

        JLabel editorPointer = new JLabel(">");
        editorPointer.setBounds(frameSize.width / 125*59 - frameSize.width/1000, frameSize.height / 6 * 5, frameSize.width / 40, frameSize.height / 15);
        editorPointer.setFont(new Font ("Algerian",Font.PLAIN,frameSize.width/30));
        editorPointer.setVisible(false);

        JLabel settingsPointer = new JLabel(">");
        settingsPointer.setBounds(frameSize.width / 5*3 - frameSize.width/1000, frameSize.height / 6 * 5, frameSize.width / 40, frameSize.height / 15);
        settingsPointer.setFont(new Font ("Algerian",Font.PLAIN,frameSize.width/30));
        settingsPointer.setVisible(false);

        JLabel exitPointer = new JLabel(">");
        exitPointer.setBounds(frameSize.width / 100*77 - frameSize.width/1000, frameSize.height / 6 * 5, frameSize.width / 40, frameSize.height / 15);
        exitPointer.setFont(new Font ("Algerian",Font.PLAIN,frameSize.width/30));
        exitPointer.setVisible(false);

        //Exit to desktop panel
        exitPanel = new JPanel();
        exitPanel.setLayout(null);
        int exitPanelWidth = frameSize.width/3;
        int exitPanelHeight = frameSize.height/3;

        JLabel exitToDesktop = new JLabel("Exit To Desktop");
        exitToDesktop.setFont(new Font ("Algerian", Font.PLAIN, frameSize.width/50)); //import x y coordinate
        exitToDesktop.setBounds(exitPanelWidth/100*29,exitPanelHeight/8,exitPanelWidth/2,exitPanelHeight/5);

        JButton yesBtn = new JButton("Yes");
        yesBtn.setBounds(exitPanelWidth/6,exitPanelHeight/3*2,exitPanelWidth/3,exitPanelHeight/4);
        yesBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 35));

        JButton noBtn = new JButton("No");
        noBtn.setBounds(exitPanelWidth/2,exitPanelHeight/3*2,exitPanelWidth/3,exitPanelHeight/4);
        noBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 35));

        exitPanel.setBounds(frameSize.width/2-exitPanelWidth/2,frameSize.height/2-exitPanelHeight/2,exitPanelWidth,exitPanelHeight);
        exitPanel.setVisible(false);
        exitPanel.setBackground(Color.LIGHT_GRAY);

        //exit to desktop panel pointer
        JLabel noPointer = new JLabel(">");
        noPointer.setBounds(exitPanelWidth/2 - exitPanelWidth/60, exitPanelHeight/3*2, exitPanelWidth/20, exitPanelHeight/4);
        noPointer.setFont(new Font ("Algerian",Font.PLAIN,exitPanelWidth/13));
        noPointer.setVisible(false);

        JLabel yesPointer = new JLabel(">");
        yesPointer.setBounds(exitPanelWidth/6 - exitPanelWidth/60, exitPanelHeight/3*2, exitPanelWidth/20, exitPanelHeight/4);
        yesPointer.setFont(new Font ("Algerian",Font.PLAIN,exitPanelWidth/13));
        yesPointer.setVisible(false);

        //add component to exitPanel
        exitPanel.add(yesBtn);
        exitPanel.add(noBtn);
        exitPanel.add(exitToDesktop);
        exitPanel.add(noPointer);
        exitPanel.add(yesPointer);

        //Button
        JButton newGameBtn = new JButton("New Game");
        newGameBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 45));

        JButton editorBtn = new JButton("EDITOR");
        editorBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 45));

        JButton optionsBtn = new JButton("SETTINGS");
        optionsBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 45));

        JButton exitBtn = new JButton("EXIT");
        exitBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 45));

        loadGameBtn = new JButton("Load Game");
        loadGameBtn.setFont(new Font("Algerian", Font.PLAIN, frameSize.width / 45));
        loadGameBtn.setEnabled(false);

        // put japanese here to change in game language
        if (currentLanguageIndex == 1) {
            newGameBtn.setText("START");
            editorBtn.setText("EDITOR");
            optionsBtn.setText("SETTING");
            exitBtn.setText("EXIT");
        }

        //Action Perform
        Action noAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesPointer.setVisible(false);
                noPointer.setVisible(false);
                exitPanelCommandNum=-1;
                menuState = 0;
                exitPanel.setVisible(false);
                exitPointer.setVisible(true);
                newGameBtn.setEnabled(true);
                loadGameBtn.setEnabled(true);
                editorBtn.setEnabled(true);
                optionsBtn.setEnabled(true);
            }
        };

        Action yesAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        };

        Action newGameAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newGame = new NewGame(currentObject);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                containerRoot.add(newGame);
                newGame.setVisible(true);
                container.setVisible(false);
            }
        };

        Action loadAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame.setVisible(true);
                container.setVisible(false);
            }
        };

        Action editorAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(frameSize);
            }
        };

        Action optionsAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = new Option(currentObject);
                containerRoot.add(option);
                container.setVisible(false);
                option.setVisible(true);
                BasicInternalFrameUI bi = (BasicInternalFrameUI) option.getUI();
                bi.setNorthPane(null);
            }
        };

        Action exitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanel.setVisible(true);
                menuState = 1;
                commandNum = -1;
                newGamePointer.setVisible(false);
                loadGamePointer.setVisible(false);
                editorPointer.setVisible(false);
                settingsPointer.setVisible(false);
                exitPointer.setVisible(false);
                newGameBtn.setEnabled(false);
                loadGameBtn.setEnabled(false);
                editorBtn.setEnabled(false);
                optionsBtn.setEnabled(false);
            }
        };

        // Di chuyyen con tro trong menu
        Action adjustMenuPointerUp = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandNum--;
                if (commandNum<0){
                    commandNum = 4;
                }
                if (commandNum == 0 ){
                    newGamePointer.setVisible(true);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 1 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(true);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 2 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(true);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 3 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(true);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 4 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(true);
                }
            }
        };

        Action adjustMenuPointerDown = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandNum++;
                if (commandNum>4){
                    commandNum = 0;
                }
                if (commandNum == 0 ){
                    newGamePointer.setVisible(true);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 1 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(true);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 2 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(true);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 3 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(true);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 4 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(true);
                }
            }
        };

        Action adjustMenuPointerLeft = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandNum--;
                if (commandNum<0){
                    commandNum = 4;
                }
                if (commandNum == 0 ){
                    newGamePointer.setVisible(true);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 1 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(true);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 2 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(true);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 3 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(true);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 4 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(true);
                }
            }
        };

        Action adjustMenuPointerRight = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandNum++;
                if (commandNum>4){
                    commandNum = 0;
                }
                if (commandNum == 0 ){
                    newGamePointer.setVisible(true);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 1 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(true);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 2 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(true);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 3 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(true);
                    exitPointer.setVisible(false);
                }
                if (commandNum == 4 ){
                    newGamePointer.setVisible(false);
                    loadGamePointer.setVisible(false);
                    editorPointer.setVisible(false);
                    settingsPointer.setVisible(false);
                    exitPointer.setVisible(true);
                }
            }
        };

        Action adjustExitPanelPointerUp = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanelCommandNum--;
                if (exitPanelCommandNum<0){
                    exitPanelCommandNum = 1;
                }
                if (exitPanelCommandNum == 0){
                    noPointer.setVisible(true);
                    yesPointer.setVisible(false);
                }
                if (exitPanelCommandNum == 1){
                    noPointer.setVisible(false);
                    yesPointer.setVisible(true);
                }
            }
        };

        Action adjustExitPanelPointerDown = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanelCommandNum++;
                if (exitPanelCommandNum>1){
                    exitPanelCommandNum = 0;
                }
                if (exitPanelCommandNum == 0){
                    noPointer.setVisible(true);
                    yesPointer.setVisible(false);
                }
                if (exitPanelCommandNum == 1){
                    noPointer.setVisible(false);
                    yesPointer.setVisible(true);
                }
            }
        };

        Action adjustExitPanelPointerLeft = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanelCommandNum--;
                if (exitPanelCommandNum<0){
                    exitPanelCommandNum = 1;
                }
                if (exitPanelCommandNum == 0){
                    noPointer.setVisible(true);
                    yesPointer.setVisible(false);
                }
                if (exitPanelCommandNum == 1){
                    noPointer.setVisible(false);
                    yesPointer.setVisible(true);
                }
            }
        };

        Action adjustExitPanelPointerRight = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPanelCommandNum++;
                if (exitPanelCommandNum>1){
                    exitPanelCommandNum = 0;
                }
                if (exitPanelCommandNum == 0){
                    noPointer.setVisible(true);
                    yesPointer.setVisible(false);
                }
                if (exitPanelCommandNum == 1){
                    noPointer.setVisible(false);
                    yesPointer.setVisible(true);
                }
            }
        };

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuState == 1){
                    if(exitPanelCommandNum == 0){
                        noBtn.doClick();
                        System.out.println(exitPanelCommandNum);
                    }
                    if(exitPanelCommandNum == 1) {
                        yesBtn.doClick();
                        System.out.println(exitPanelCommandNum);
                    }
                }
                if(menuState == 0){
                    if(commandNum == 0){
                        newGameBtn.doClick();
                    }
                    if(commandNum == 1){
                        loadGameBtn.doClick();
                    }
                    if(commandNum == 2){
                        editorBtn.doClick();
                    }
                    if(commandNum == 3){
                        optionsBtn.doClick();
                    }
                    if(commandNum == 4){
                        exitBtn.doClick();
                    }
                }
            }
        };

        //assign action perform to button
        noBtn.addActionListener(noAction);
        yesBtn.addActionListener(yesAction);
        newGameBtn.addActionListener(newGameAction);
        loadGameBtn.addActionListener(loadAction);
        editorBtn.addActionListener(editorAction);
        editorBtn.setEnabled(false); //chuc nang chua su dung
        optionsBtn.addActionListener(optionsAction);
        exitBtn.addActionListener(exitAction);

        container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(0,frameSize.width/6,frameSize.height/15,0));

        //add component to container
        container.add(newGamePointer);
        container.add(Box.createRigidArea(new Dimension(5, 0)));
        container.add(newGameBtn);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(loadGamePointer);
        container.add(Box.createRigidArea(new Dimension(5, 0)));
        container.add(loadGameBtn);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(editorPointer);
        container.add(Box.createRigidArea(new Dimension(5, 0)));
        container.add(editorBtn);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(settingsPointer);
        container.add(Box.createRigidArea(new Dimension(5, 0)));
        container.add(optionsBtn);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(exitPointer);
        container.add(Box.createRigidArea(new Dimension(5, 0)));
        container.add(exitBtn);

        containerRoot.setLayout(new BorderLayout());
        containerRoot.add(container,BorderLayout.PAGE_END);
        frame.add(exitPanel);

        //transparent
        newGameBtn.setOpaque(false);
        newGameBtn.setContentAreaFilled(false);
        newGameBtn.setBorderPainted(false);

        editorBtn.setOpaque(false);
        editorBtn.setContentAreaFilled(false);
        editorBtn.setBorderPainted(false);

        optionsBtn.setOpaque(false);
        optionsBtn.setContentAreaFilled(false);
        optionsBtn.setBorderPainted(false);

        exitBtn.setOpaque(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setBorderPainted(false);

        loadGameBtn.setOpaque(false);
        loadGameBtn.setContentAreaFilled(false);
        loadGameBtn.setBorderPainted(false);

        yesBtn.setOpaque(false);
        yesBtn.setContentAreaFilled(false);
        yesBtn.setBorderPainted(false);

        noBtn.setOpaque(false);
        noBtn.setContentAreaFilled(false);
        noBtn.setBorderPainted(false);

        // add container to frame and adjust things
        frame.add(containerRoot);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("LOOPER");
        if (currentLanguageIndex == 1){
            frame.setTitle("惨劇RoopeR");
        }
        Image titleIcon = new ImageIcon("res\\tragedy_commons_5th\\extra\\icon.png").getImage();
        frame.setIconImage(titleIcon);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        //bind key to button
        container.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),"dothings");
        container.getActionMap().put("dothings", enterAction);
        container.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"up");
        container.getActionMap().put("up",adjustMenuPointerUp);
        container.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"down");
        container.getActionMap().put("down",adjustMenuPointerDown);
        container.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"left");
        container.getActionMap().put("left",adjustMenuPointerLeft);
        container.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"right");
        container.getActionMap().put("right",adjustMenuPointerRight);
        exitPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"up");
        exitPanel.getActionMap().put("up",adjustExitPanelPointerUp);
        exitPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"down");
        exitPanel.getActionMap().put("down",adjustExitPanelPointerDown);
        exitPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"left");
        exitPanel.getActionMap().put("left",adjustExitPanelPointerLeft);
        exitPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"right");
        exitPanel.getActionMap().put("right",adjustExitPanelPointerRight);
    }

    public int GetLanguage(){
        return currentLanguageIndex;
    }
}
