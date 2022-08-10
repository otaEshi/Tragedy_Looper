package Ui;

import javax.swing.*;
import java.awt.*;

public class PaintContent extends  JPanel{ //drawing info, city, shrine, hospital, school panel
    private Image image;
    private final Dimension size;

    public PaintContent(Image image, Dimension size) {
        this.image = image;
        this.size = size;
        setOpaque(false);
        setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage (image, 0, 0, size.width, size.height, null);
        super.paintComponent(g);
    }

    public void SetImage(Image image){
        this.image = image;
        repaint();
    }
}

