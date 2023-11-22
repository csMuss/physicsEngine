package fun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PhysicsEngine {
	
	public static void main(String args[]) {

		createJFrame();
		
	}
	
	private static void createJFrame() {
		JFrame frame = new JFrame("Physics Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(800,600);
		frame.setResizable(false);
		
		PhysicsCalcAndShapeDrawing physDemo = new PhysicsCalcAndShapeDrawing();
		frame.add(physDemo);
		frame.setVisible(true);
	}

}

class PhysicsCalcAndShapeDrawing extends JPanel {
	
	private static final long serialVersionUID = 1L;

    final double GRAVITY = 9.81; // Positive, as the calculation accounts for direction
    private static final int DELAY = 16; // Approximately 60 frames per second
    private double t = 0; // Time variable
    private double horizontalSpeed = 100; // Example horizontal speed

    Timer timer = new Timer(DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            t += DELAY / 1000.0; // Increment time in seconds
            repaint(); // Trigger the repaint to update the position
        }
    });

    public PhysicsCalcAndShapeDrawing() {
        timer.start(); // Start the timer
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.DARK_GRAY);
        g2.setColor(Color.CYAN);

        // Calculate the new positions
        double x = calculateXFalling(0, GRAVITY, t, horizontalSpeed);
        double y = calculateYFalling(0, GRAVITY, t);
        
        // Get the size of the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Cube size
        int cubeSize = 50;
        
        // Check if the cube is beyond the right boundary
        if (x + cubeSize > panelWidth) {
            x = panelWidth - cubeSize;
        }

        // Check if the cube is beyond the bottom boundary
        if (y + cubeSize > panelHeight) {
            y = panelHeight - cubeSize;
            // Stops when its out of bounds
            timer.stop();
        }

        g2.fillRect((int) x, (int) y, 50, 50); // Draw the falling object
    }

    public double calculateXFalling(double startX, double gravitationAttraction, double time, double horizontalSpeed) {
        return startX + time * horizontalSpeed;
    }

    public double calculateYFalling(double startY, double gravitationAttraction, double time) {
        return startY + 0.5 * gravitationAttraction * time * time;
    }
}

