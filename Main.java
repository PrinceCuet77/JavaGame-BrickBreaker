import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();							// JFrame object creation
		Gameplay gameplay = new Gameplay(); 					// Creating Gameplay object
		frame.setBounds(50, 50, 700, 600);					// Setting the window position & height and width 
		frame.setVisible(true);									// Enable to show the frame
		frame.setTitle("Brick Breaker Game"); 					// Set the game title 
		frame.setResizable(false);								// Disable to resize the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Setting default close operation
		frame.add(gameplay);									// Add gameplay panel object to frame
	}

}
