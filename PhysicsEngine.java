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
		DrawInfoText drawText = new DrawInfoText();
		// The draw text has to be before adding the demo square
		// Text also has to be the same colors as the physics demo
		frame.add(drawText);
		frame.add(physDemo);
		frame.setVisible(true);
	}

}
