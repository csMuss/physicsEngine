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

    final double GRAVITY = 9.81; // Positive, as the calculation accounts for direction
    
    private static final int DELAY = 8; // 8 ms delay
    private DrawInfoText drawText;
    private double time = 0; // Time variable
    private double horizontalSpeed = 100; // Example horizontal speed
    private double horizontalSpeedBounce = horizontalSpeed;
    
    Color amber = new Color(255, 191, 0);
    
    double x = calculateXFalling(0, time, horizontalSpeed);
    double y = calculateYFalling(30, GRAVITY, time);
    
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
        // Restarts the simulation
        if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            resetSimulation();
        }
        // Stops the timer
        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
            timer.stop();
        }
        if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
            // Add code to close window
        }
        // Resumes the timer
        if (e.getKeyChar() == 'e' || e.getKeyChar() == 'e') {
            timer.start();
        }
        // Makes speed faster by 10
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
        	horizontalSpeed += 5;
        }
        // Makes speed slower by 10
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
        	horizontalSpeed -= 5;
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
        }
    });
    

    private void updatePositions() {
        // Update x and y as class fields
        x = calculateXFalling(0, time, horizontalSpeed);
        y = calculateYFalling(30, GRAVITY, time);

        // Get the size of the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        // Cube size
        int cubeSize = 50;

        // Check if the cube is beyond the right boundary
        if (x + cubeSize > panelWidth) {
            x = panelWidth - cubeSize;
            horizontalSpeedBounce = -horizontalSpeedBounce; // Reverse direction
        }
        
        // Check if the cube is beyond the left boundary
        if (x < 0) {
            x = 0;
            horizontalSpeedBounce = -horizontalSpeedBounce; // Reverse direction
        }

        // Check if the cube is beyond the bottom boundary
        if (y + cubeSize > panelHeight) {
            y = panelHeight - cubeSize;     
            timer.stop();
        }
    }


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

	public double calculateXFalling(double startX, double time, double horizontalSpeed) {
        return startX + time * horizontalSpeed;
    }

    public double calculateYFalling(double startY, double gravitationAttraction, double time) {
        return startY + 0.5 * gravitationAttraction * Math.pow(time, 2);
    }
    
    private void resetSimulation() {
    	setBackground(Color.BLACK);
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

