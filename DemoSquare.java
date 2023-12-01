package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

class DemoSquare extends JPanel {
	
    private static final long serialVersionUID = 1L;
    private static final int DELAY = 8;
    private static final int CUBE_SIZE = 50;
    
    private DrawInfoText drawText;
    private double time = 0;
    protected double horizontalSpeed = 100; //100;
    protected double horizontalSpeedBounce = horizontalSpeed;
    
    private static PhysicsCalculations physCalcs = new PhysicsCalculations();
    private KeyboardListener keyBoard;
    
    private Color amber = new Color(255, 191, 0);
    
    private boolean isJumping = false;
    private double jumpVelocity = 0;

    
    private double x;
    private double y;
    
    public DemoSquare() {
        this.drawText = new DrawInfoText(this);
        setBackground(Color.BLACK);
        timer.start();
        setFocusable(true);
        // Initialize x and y position
        x = physCalcs.calculateXFalling((this.getWidth() / 2) - 50, time, horizontalSpeed);
        y = physCalcs.calculateYFalling(30, PhysicsCalculations.GRAVITY, time);
        // Listing keyboard
        this.keyBoard = new KeyboardListener(this);
        addKeyListener(keyBoard);
    }

    Timer timer = new Timer(DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time += DELAY / 500.0;
            repaint();
            updatePositions();
        }
    });

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(amber);
        this.drawText.draw(g2);
        g2.fillRect((int) x, (int) y, CUBE_SIZE, CUBE_SIZE);
    }
    
    public void initiateJump() {
        if (!isJumping) {
            isJumping = true;
            jumpVelocity = -150; // Initial jump velocity
        }
    }
    
    protected void updatePositions() {
        // Update horizontal position
        x += horizontalSpeedBounce * (DELAY / 100.0);

        // Left boundary check
        if (x < 0) {
            x = 0; // Reset position to boundary
            horizontalSpeedBounce = Math.abs(horizontalSpeedBounce) * 0.9; // Reverse and reduce speed
        }

        // Right boundary check
        if (x + CUBE_SIZE > getWidth()) {
            x = getWidth() - CUBE_SIZE; // Reset position to boundary
            horizontalSpeedBounce = -Math.abs(horizontalSpeedBounce) * 0.9; // Reverse and reduce speed
        }

        // Update vertical position
        y += jumpVelocity * (DELAY / 1000.0);
        jumpVelocity += PhysicsCalculations.GRAVITY * (DELAY / 1000.0); // Apply gravity

        // Top boundary check
        if (y < 0) {
            y = 0; // Reset position to boundary
            jumpVelocity = Math.abs(jumpVelocity) * 0.9; // Reverse and reduce speed
        }

        // Bottom boundary check
        if (y + CUBE_SIZE > getHeight()) {
            y = getHeight() - CUBE_SIZE; // Reset position to boundary
            jumpVelocity = -Math.abs(jumpVelocity) * 0.9; // Reverse and reduce speed
        }
    }
    
    protected void resetSimulation() {
        time = 0;
        horizontalSpeedBounce = horizontalSpeed;
        timer.start();
        repaint();
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
