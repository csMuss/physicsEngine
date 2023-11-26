package physicsEngine;

public class PhysicsCalculations {
	
	public static final double GRAVITY = 9.81; // Positive, as the calculation accounts for direction
	
	public double calculateXFalling(double startX, double time, double horizontalSpeed) {
        return startX + time * horizontalSpeed;
    }

    public double calculateYFalling(double startY, double gravitationAttraction, double time) {
        return startY + 0.5 * gravitationAttraction * Math.pow(time, 2);
    } 
}
