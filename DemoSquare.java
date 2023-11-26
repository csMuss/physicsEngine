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
    protected double horizontalSpeed = 100;
    protected double horizontalSpeedBounce = horizontalSpeed;
    
    private static PhysicsCalculations physCalcs = new PhysicsCalculations();
    private KeyboardListener keyBoard;
    
    private Color amber = new Color(255, 191, 0);
    private double x, y;
    
    public DemoSquare() {
        this.drawText = new DrawInfoText(this);
        setBackground(Color.BLACK);
        timer.start();
        setFocusable(true);
        
        this.keyBoard = new KeyboardListener(this);
        addKeyListener(keyBoard);
        
        // Initialize x and y position
        x = physCalcs.calculateXFalling(0, time, horizontalSpeed);
        y = physCalcs.calculateYFalling(30, PhysicsCalculations.GRAVITY, time);
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
    
    protected void updatePositions() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        boolean flipHorizontalLeft = false;
        boolean flipHorizontalRight = false;
        
        // Calculate the new positions
        x = physCalcs.calculateXFalling(0, time, horizontalSpeed);
        y = physCalcs.calculateYFalling(30, PhysicsCalculations.GRAVITY, time);
        
        // Check if the cube is beyond the right boundary, bounce back
        if (x + CUBE_SIZE > panelWidth) {
            x = panelWidth - CUBE_SIZE;
            flipHorizontalLeft = !flipHorizontalLeft;
        }
        
        // Check if the cube is beyond the left boundary, bounce back
        if (x < 0) {
            x = 0;
            flipHorizontalRight = !flipHorizontalRight;
        }
        
        // Check if the cube is beyond the bottom boundary
        if (y + CUBE_SIZE > panelHeight) {
            y = panelHeight - CUBE_SIZE;
            timer.stop();
        }
        
        // Bounce off of the left side of the bounds
        if (flipHorizontalLeft) {
            if (horizontalSpeedBounce >= 1) {
                horizontalSpeedBounce -= horizontalSpeedBounce / (2 * horizontalSpeedBounce);
            }
            x = physCalcs.calculateXFalling(0, time, horizontalSpeedBounce);
        }
        
        // Bounce off of the right side of the bounds
        if (flipHorizontalRight) {
            if (horizontalSpeedBounce <= 1) {
                horizontalSpeedBounce += horizontalSpeedBounce / (2 * horizontalSpeedBounce);
            }
            x = physCalcs.calculateXFalling(0, time, horizontalSpeedBounce);
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
