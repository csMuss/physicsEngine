package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DrawInfoText {

    private DemoSquare demoSquare; // Reference to a DemoSquare instance

    public DrawInfoText(DemoSquare demoSquare) {
        this.demoSquare = demoSquare;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.black);
        g2.setColor(new Color(255, 191, 0));
        // Draw information about the demoSquare instance
        g2.drawString("Horizontal Speed: " + demoSquare.getHorizontalSpeed(), 0, 10); 
        g2.drawString("X: " + (int) demoSquare.getXPos(), 0, 20); 
        g2.drawString("Y: " + (int) demoSquare.getYPos(), 0, 30); 
    }
}