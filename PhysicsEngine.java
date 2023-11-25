package physicsEngine;

import javax.swing.JFrame;

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
		
		DemoSquare physDemo = new DemoSquare();
		frame.add(physDemo);
		frame.setVisible(true);
	}

}
