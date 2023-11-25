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
    private double horizontalSpeedBounce = horizontalSpeed;
    
    Color amber = new Color(255, 191, 0);
    
    double x = calculateXFalling(0, time, horizontalSpeed);
    double y = calculateYFalling(30, GRAVITY, time);
    
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
        // Stops the timer
        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
            timer.stop();
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
        double x = calculateXFalling(0, time, horizontalSpeed);
        double y = calculateYFalling(30, GRAVITY, time);
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
            timer.stop();
        }
        
        // Bounce off of the left side of the bounds
        if(flipHorizontalLeft == true) {
        	if(horizontalSpeedBounce >= 1) {
        		// number divided by slows down the bounce dividing by double the horizontal speed
        		// gives you a rebound speed close to the inital horizontal speed
        		horizontalSpeedBounce = horizontalSpeedBounce - horizontalSpeedBounce / (2 * horizontalSpeedBounce);
        	}
        	x = calculateXFalling(0, time, horizontalSpeedBounce);
        }
        
        // Bounce off of the right side of the bounds
        if(flipHorizontalRight == true) {
           	if(horizontalSpeedBounce <= 1) {
        		horizontalSpeedBounce++;
        	}
        	x = calculateXFalling(0, time, horizontalSpeedBounce);
        }
        
        //Drawing the square
        g2.fillRect((int) x, (int) y, 50, 50); // Draw the falling object
    }

	public double calculateXFalling(double startX, double time, double horizontalSpeed) {
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

