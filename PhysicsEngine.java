package physicsEngine;

import javax.swing.JFrame;

public class PhysicsEngine {
	
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	
	public static void main(String args[]) {

		createJFrame();
		
	}
	
	private static void createJFrame() {
		JFrame frame = new JFrame("Physics Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setResizable(false);
		// Adding a physics object
		DemoSquare physDemo = new DemoSquare();
	    //DrawInfoText drawText = new DrawInfoText(physDemo);
		frame.add(physDemo);
		frame.setVisible(true);
	}
}
