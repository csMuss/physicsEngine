package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class DrawInfoText extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Color amber = new Color(255, 191, 0);
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		DemoSquare demoSquare = new DemoSquare();
		Graphics2D g2 = (Graphics2D) g;
		
	    this.setBackground(Color.BLACK);
	    g2.setColor(amber);
	        
        g2.drawString("Horizontal Speed: " + demoSquare.getHorizontalSpeed(), 0, 10); 
        g2.drawString("X: " + (int) demoSquare.getXPos(), 0, 20); 
        g2.drawString("Y: " + (int) demoSquare.getY(), 0, 30); 
	        
	 }
}
