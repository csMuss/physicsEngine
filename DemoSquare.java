package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

class DemoSquare extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 1L;

    final double GRAVITY = 9.81; // Positive, as the calculation accounts for direction
    
    private static final int DELAY = 8; // 8 ms delay
    
    private double time = 0; // Time variable
    private double horizontalSpeed = 100; // Example horizontal speed
    double horizontalSpeedBounce = horizontalSpeed;
    
    Color amber = new Color(255, 191, 0);
    
    public DemoSquare() {
        timer.start(); // Start the timer
        addKeyListener(this); // Add the key listener
        setFocusable(true); // Set focusable to true to receive key events
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this case
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Check if the pressed key is 'R'
        if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            resetSimulation();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this case
    }

    Timer timer = new Timer(DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	time += DELAY / 500.0; // Increment time in half a second, 1000 = 1 second
        	// Lower increment = faster display
            repaint(); // Trigger the repaint to update the position
        }
    });

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.BLACK);
        g2.setColor(amber);
        
        // Calculate the new positions
        double x = calculateXFalling(0, GRAVITY, time, horizontalSpeed);
        double y = calculateYFalling(30, GRAVITY, time);
        // Get the size of the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        // Cube size
        int cubeSize = 50;
       
        boolean flipHorizontalLeft = false;
        boolean flipHorizontalRight = false;
     
        // Draw text
        drawInfoText(g, x, y);

        // Check if the cube is beyond the right boundary, bounce back
        if (x + cubeSize > panelWidth) {
            x = panelWidth - cubeSize;
            flipHorizontalLeft = true;
        }
        
        // Check if the cube is beyond the left boundary, bounce back
        if (x + cubeSize < panelWidth) {
            x = 0;
            flipHorizontalRight = true;
        }

        // Check if the cube is beyond the bottom boundary
        if (y + cubeSize > panelHeight) {
            y = panelHeight - cubeSize;
            // Stops when its out of bounds
            timer.stop();            
        }
        
        // Bounce off of the left side of the bounds
        if(flipHorizontalLeft == true) {
        	if(horizontalSpeedBounce >= 1) {
        		horizontalSpeedBounce--;
        	}
        	x = calculateXFalling(0, GRAVITY, time, horizontalSpeedBounce);
        }
        
        // Bounce off of the right side of the bounds
        if(flipHorizontalRight == true) {
           	if(horizontalSpeedBounce <= 1) {
        		horizontalSpeedBounce++;
        	}
        	x = calculateXFalling(0, GRAVITY, time, horizontalSpeedBounce);
        }

        g2.fillRect((int) x, (int) y, 50, 50); // Draw the falling object
    }

    public double calculateXFalling(double startX, double gravitationAttraction, double time, double horizontalSpeed) {
        return startX + time * horizontalSpeed;
    }

    public double calculateYFalling(double startY, double gravitationAttraction, double time) {
        return startY + 0.5 * gravitationAttraction * Math.pow(time, 2);
    }
    
    private void resetSimulation() {
        time = 0; // Reset the time
        horizontalSpeedBounce = horizontalSpeed; // Reset horizontal speed
        timer.start(); // Restart the timer
        repaint(); // Repaint the panel
    }
    
    private void drawInfoText(Graphics g, double xPos, double yPos) {
    	Graphics2D g2 = (Graphics2D) g;
    	
        g2.drawString("Horizontal Speed: " + horizontalSpeedBounce, 0, 10); 
        g2.drawString("X: " + (int) xPos, 0, 20); 
        g2.drawString("Y: " + (int) yPos, 0, 30); 
    }
}

