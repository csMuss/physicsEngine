package physicsEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener extends KeyAdapter implements KeyListener {
	
	private DemoSquare demoSquare;
	
    public KeyboardListener(DemoSquare demoSquare) {
        this.demoSquare = demoSquare;
    }
    
	@Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	char keyChar = Character.toLowerCase(e.getKeyChar());

    	switch (keyChar) {
        // Jump 
    	case 'w':
    		demoSquare.initiateJump();
    		break;
    	// Restart simulation
    	case 'r':
    		demoSquare.resetSimulation();
    		break;
    	// Pause simulation
    	case 'p':
    	    demoSquare.timer.stop();
    	    break;
    	    // Close window
    	case 'q':
    	        // Add code to close window
    	     break;
    	    // Resume simulation
    	case 'e':
    	    demoSquare.timer.start();
    	    break;
    	    // Increase speed
    	case 'd':
    	    demoSquare.horizontalSpeed += 5;
    	    break;
    	    // Decrease speed
    	case 'a':
    	    demoSquare.horizontalSpeed -= 5;
    	    break;
    	    // Do nothing
    	default:
    		break;
    	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

}
