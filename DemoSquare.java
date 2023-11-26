package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

class DemoSquare extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 1L;
    
    private static final int DELAY = 8; // 8 ms delay
    private DrawInfoText drawText;
    private double time = 0; // Time variable
    private double horizontalSpeed = 100; // Example horizontal speed
    private double horizontalSpeedBounce = horizontalSpeed;
    
    private static PhysicsCalculations physCalcs = new PhysicsCalculations();
    
    Color amber = new Color(255, 191, 0);
    
    double x = physCalcs.calculateXFalling(0, time, horizontalSpeed);
    double y = physCalcs.calculateYFalling(30, physCalcs.GRAVITY, time);
    
    public DemoSquare() {
	    this.drawText = new DrawInfoText(this);
    	setBackground(Color.BLACK);
        timer.start(); // Start the timer
        addKeyListener(this); // Add the key listener
        setFocusable(true); // Set focusable to true to receive key events
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	char keyChar = Character.toLowerCase(e.getKeyChar());

    	switch (keyChar) {
    	    // Restart simulation
    	    case 'r':
    	        resetSimulation();
    	        break;
    	    // Pause simulation
    	    case 'p':
    	        timer.stop();
    	        break;
    	    // Close window
    	    case 'q':
    	        // Add code to close window
    	        break;
    	    // Resume simulation
    	    case 'e':
    	        timer.start();
    	        break;
    	    // Increase speed
    	    case 'd':
    	        horizontalSpeed += 5;
    	        break;
    	    // Decrease speed
    	    case 'a':
    	        horizontalSpeed -= 5;
    	        break;
    	    // Do nothing
    	    default:
    	        break;
    	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    Timer timer = new Timer(DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	time += DELAY / 500.0; // Increment time in half a second, 1000 = 1 second
        	// Lower increment = faster display
            repaint(); // Trigger the repaint to update the position
            updatePositions(); // Trigger updates 
        }
    });

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(amber);
        // Draw Text
        this.drawText.draw(g2);
        // Drawing the square
        g2.fillRect((int) x, (int) y, 50, 50); // Draw the falling object
    }
    
    private void updatePositions() {
        // Calculate the new positions
        x = physCalcs.calculateXFalling(0, time, horizontalSpeed);
        y = physCalcs.calculateYFalling(30, physCalcs.GRAVITY, time);
        // Get the size of the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        // Cube size
        int cubeSize = 50;
       
        boolean flipHorizontalLeft = false;
        boolean flipHorizontalRight = false;
        // Check if the cube is beyond the right boundary, bounce back
        if (x + cubeSize > panelWidth) {
            x = panelWidth - cubeSize;
            flipHorizontalLeft = !flipHorizontalLeft;
        }
        
        // Check if the cube is beyond the left boundary, bounce back
        if (x + cubeSize < panelWidth) {
            x = 0;
            flipHorizontalRight = !flipHorizontalRight;
        }
        // Check if the cube is beyond the bottom boundary
        if (y + cubeSize > panelHeight) {
            y = panelHeight - cubeSize;     
            timer.stop();
        }
        
        // Bounce off of the left side of the bounds
        if(flipHorizontalLeft == true) {
        	if(horizontalSpeedBounce >= 1) {
        		// number divided by slows down the bounce dividing by double the horizontal speed
        		// gives you a rebound speed close to the inital horizontal speed
        		horizontalSpeedBounce = horizontalSpeedBounce - horizontalSpeedBounce / (2 * horizontalSpeedBounce);
        	}
        	x = physCalcs.calculateXFalling(0, time, horizontalSpeedBounce);
        }
        
        // Bounce off of the right side of the bounds
        if(flipHorizontalRight == true) {
           	if(horizontalSpeedBounce <= 1) {
           		horizontalSpeedBounce = horizontalSpeedBounce + horizontalSpeedBounce / (2 * horizontalSpeedBounce);
        	}
        	x = physCalcs.calculateXFalling(0, time, horizontalSpeedBounce);
        }
    }
    
    private void resetSimulation() {
        time = 0; // Reset the time
        horizontalSpeedBounce = horizontalSpeed; // Reset horizontal speed
        timer.start(); // Restart the timer
        repaint(); // Repaint the panel
    }
    
    // Getters
    public double getXPos() {
		return x;
	}

	public double getYPos() {
		return y;
	}
	
	public double getHorizontalSpeed() {
		return horizontalSpeed;
	}
}

