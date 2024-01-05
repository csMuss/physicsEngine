package physicsEngine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

class DemoSquare extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int DELAY = 8;
	private static final int CUBE_SIZE = 50;

	private DrawInfoText drawText;
	private double time = 0;
	protected double horizontalSpeed = 0; // 100;
	protected double horizontalSpeedBounce = horizontalSpeed;

	private static PhysicsCalculations physCalcs = new PhysicsCalculations();
	private KeyboardListener keyBoard;

	private Color amber = new Color(255, 191, 0);

	// Movement
	private boolean isJumping = false;
	private double jumpVelocity = 0;

	private boolean isDropping = false;
	private double dropVelocity = 0;

	private boolean isMovingLeft = false;
	private double leftVelocity = 0;

	private boolean isMovingRight = false;
	private double rightVelocity = 0;

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

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(amber);
		drawText.draw(g2);
		g2.fillRect((int) x, (int) y, CUBE_SIZE, CUBE_SIZE);
	}

	// Starters
	public void initiateJump() {
		isJumping = true;
		jumpVelocity = -250;
	}

	public void initiateDrop() {
		isDropping = true;
		dropVelocity = 250;
	}

	public void initiateLeftMove() {
		isMovingLeft = true;
		leftVelocity = -250;
	}

	public void initiateRightMove() {
		isMovingRight = true;
		rightVelocity = 250;
	}

	// Stoppers
	public void stopJump() {
		isJumping = false;
		jumpVelocity = 0;
	}

	public void stopDrop() {
		isDropping = false;
		dropVelocity = 0;
	}

	public void stopLeftMove() {
		isMovingLeft = false;
		leftVelocity = 0;
	}

	public void stopRightMove() {
		isMovingRight = false;
		rightVelocity = 0;
	}

	protected void updatePositions() {
		// Update horizontal position
		if (isJumping) {
			y += jumpVelocity * (DELAY / 1000.0);
			jumpVelocity += PhysicsCalculations.GRAVITY * (DELAY / 1000.0);
		} else {
			applyDampingToVelocity("jump");
		}

		if (isDropping) {
			y += dropVelocity * (DELAY / 1000.0);
		} else {
			applyDampingToVelocity("drop");
		}

		if (isMovingLeft) {
			x += leftVelocity * (DELAY / 1000.0);
		} else {
			applyDampingToVelocity("left");
		}

		if (isMovingRight) {
			x += rightVelocity * (DELAY / 1000.0);
		} else {
			applyDampingToVelocity("right");
		}
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

	private void applyDampingToVelocity(String movementType) {
		double dampingFactor = 0.05; 
		
		switch (movementType) {
		case "jump":
			jumpVelocity *= dampingFactor;
			if (Math.abs(jumpVelocity) < 1)
				jumpVelocity = 0;
			break;
		case "drop":
			dropVelocity *= dampingFactor;
			if (Math.abs(dropVelocity) < 1)
				dropVelocity = 0;
			break;
		case "left":
			leftVelocity *= dampingFactor;
			if (Math.abs(leftVelocity) < 1)
				leftVelocity = 0;
			break;
		case "right":
			rightVelocity *= dampingFactor;
			if (Math.abs(rightVelocity) < 1)
				rightVelocity = 0;
			break;
		}
	}

	protected void resetSimulation() {
		time = 0;
		horizontalSpeedBounce = horizontalSpeed;
		x = physCalcs.calculateXFalling((this.getWidth() / 2) - 50, time, horizontalSpeed);
		y = physCalcs.calculateYFalling(30, PhysicsCalculations.GRAVITY, time);
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
