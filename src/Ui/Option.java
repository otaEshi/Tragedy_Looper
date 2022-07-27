package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Option extends JInternalFrame {
    JComboBox languageBox;
    JPanel optionPanel;
    JButton cancelBtn;
    JButton applyBtn;
    JComboBox resolutionBox;
    Dimension optionFrameSize;
    int currentLanguageIndex;
    int currentResolutionIndex;
    int currentDisplayIndex;
    Dimension tempSize;
    Menu menu;

    public Option(Menu menu) {
        Image image = new ImageIcon("res\\settingBG.png").getImage();                                                //option Background
        this.menu = menu;
        optionFrameSize = menu.frameSize;
        optionPanel = new PaintBackGround(image, optionFrameSize);
        optionPanel.setLayout(null);

        JLabel languageLabel = new JLabel("Language");
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setFont(new Font("Imprint MT Shadow", Font.PLAIN, optionFrameSize.width / 40));
        languageLabel.setBounds(optionFrameSize.width / 6 * 2, optionFrameSize.height / 9 * 2, 250, 60);

        String[] language = {"English", "Japanese"};
        languageBox = new JComboBox(language);
        languageBox.setBounds(optionFrameSize.width / 6 * 3, optionFrameSize.height / 4, 100, 30);
        languageBox.setBorder(BorderFactory.createEmptyBorder());
        languageBox.setSelectedIndex(menu.currentLanguageIndex);
        languageBox.setOpaque(false);

        cancelBtn = new JButton("CANCEL");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Imprint MT Shadow", Font.PLAIN, optionFrameSize.width / 40));
        cancelBtn.setBounds(optionFrameSize.width / 4, optionFrameSize.height / 5 * 4, optionFrameSize.width / 5, optionFrameSize.height / 15);
        cancelBtn.setOpaque(false);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);

        applyBtn = new JButton("APPLY");
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setFont(new Font("Imprint MT Shadow", Font.PLAIN, optionFrameSize.width / 40));
        applyBtn.setBounds(optionFrameSize.width / 4 * 2, optionFrameSize.height / 5 * 4, optionFrameSize.width / 5, optionFrameSize.height / 15);
        applyBtn.setOpaque(false);
        applyBtn.setContentAreaFilled(false);
        applyBtn.setBorderPainted(false);

        JLabel resolutionLabel = new JLabel("Resolution");
        resolutionLabel.setForeground(Color.WHITE);
        resolutionLabel.setFont(new Font("Imprint MT Shadow", Font.PLAIN, optionFrameSize.width / 45));
        resolutionLabel.setBounds(optionFrameSize.width / 6 * 2, optionFrameSize.height / 5 * 2, 250, 30);

        String[] resolution = {"640 x 360", "854 x 480", "1280 x 720", "1920 x 1080", "2560 x 1440", "Screen size"};
        resolutionBox = new JComboBox(resolution);
        resolutionBox.setBounds(optionFrameSize.width / 6 * 3, optionFrameSize.height / 5 * 2, 100, 30);
        resolutionBox.setOpaque(false);
        resolutionBox.setSelectedIndex(menu.currentResolutionIndex);

        JLabel displayLabel = new JLabel("Display Mode");
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Imprint MT Shadow", Font.PLAIN, optionFrameSize.width / 45));
        displayLabel.setBounds(optionFrameSize.width / 6 * 2, optionFrameSize.height / 20 * 11, 250, 50);

        String[] display = {"Fullscreen", "Borderless Windowed"};
        JComboBox displayBox = new JComboBox(display);
        displayBox.setBounds(optionFrameSize.width / 6 * 3, optionFrameSize.height / 25 * 14, 150, 30);
        displayBox.setSelectedIndex(menu.currentDisplayIndex);
        displayBox.setOpaque(false);

        //Action perform
        Action cancelAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
                menu.frame.dispose();
            }
        };

        Action applyAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check resolution
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int chosenSize = resolutionBox.getSelectedIndex();
                if (chosenSize == 0) {
                    tempSize = new Dimension(640, 360);
                }
                if (chosenSize == 1) {
                    tempSize = new Dimension(854, 480);
                }
                if (chosenSize == 2) {
                    tempSize = new Dimension(1280, 720);
                }
                if (chosenSize == 3) {
                    tempSize = new Dimension(1920, 1080);
                }
                if (chosenSize == 4) {
                    tempSize = new Dimension(2560, 1440);
                }
                if (chosenSize == 5) {
                    tempSize = new Dimension(screenSize.width, screenSize.height);
                }

                if(tempSize.width<=screenSize.width && tempSize.height<=screenSize.height) {
                    currentLanguageIndex = languageBox.getSelectedIndex();
                    currentResolutionIndex = resolutionBox.getSelectedIndex();
                    currentDisplayIndex = displayBox.getSelectedIndex();
                    menu.config.saveConFig(currentLanguageIndex, currentResolutionIndex, currentDisplayIndex);
                    new Menu();
                    menu.frame.dispose();
                } else {                                                                //Out of bounds
                    JOptionPane.showMessageDialog(null, "Please choose a valid resolution", "Out of Bounds", JOptionPane.INFORMATION_MESSAGE );
                }
            }
        };

        cancelBtn.addActionListener(cancelAction);
        applyBtn.addActionListener(applyAction);

        //Bind key to apply and cancel
        cancelBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "cancel");
        cancelBtn.getActionMap().put("cancel", cancelAction);
        applyBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "apply");
        applyBtn.getActionMap().put("apply", applyAction);

        optionPanel.add(languageBox);
        optionPanel.add(cancelBtn);
        optionPanel.add(applyBtn);
        optionPanel.add(this.resolutionBox);
        optionPanel.add(languageLabel);
        optionPanel.add(resolutionLabel);
        optionPanel.add(displayLabel);
        optionPanel.add(displayBox);
        optionPanel.setFocusable(true);
        this.add(optionPanel);
    }
}

