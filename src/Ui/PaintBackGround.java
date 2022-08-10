package Ui;
import javax.swing.*;
import java.awt.*;

public class PaintBackGround extends JPanel {   //drawing background of menu and setting
    private final Image image;
    private final Dimension frameSize;
    public PaintBackGround(Image image, Dimension frameSize) {
        this.image = image;
        this.frameSize = frameSize;
        setOpaque(false);
        setBackground(Color.white);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage (image, 0, 0, frameSize.width, frameSize.height, null);
        super.paintComponent(g);
    }
}
