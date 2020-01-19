import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false; 				// Game active or not
	
	private int totalBrick = 21;
	
	private int score = 0; 						// Score 
	
	private Timer timer; 						// Timer setting 
	private int delay = 8;
	
	private int playerX = 310; 					// Slider initial position
	
	private int ballposX = 120;					// Ball initial position in x-axis
	private int ballposY = 350;					// In y-axis
	private int ballXdir = -1; 					// Ball direction setting
	private int ballYdir = -2;
	
	private MapGenerator mapGenerator; 
	
	public Gameplay() {							// Constructor
		mapGenerator = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this); 
		timer.start();
	}
	
	public void paint(Graphics g) {
		// Background 
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// Drawing brick 
		mapGenerator.draw((Graphics2D)g);
		
		// Border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// score
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		// Paddle 
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// Ball 
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		// Won 
		if ( totalBrick <= 0 ) {
			play = false;
			ballXdir = 0; 
			ballYdir = 0; 
			g.setColor(Color.yellow);
			g.setFont(new Font("Consolas", Font.BOLD, 30));
			g.drawString("You won! Scores: " + score, 190, 300);
			
			g.setFont(new Font("Consolas", Font.BOLD, 20));
			g.drawString("Press enter to restart...", 230, 350);
		}
		
		// Game over 
		if ( ballposY > 570 ) {
			play = false;
			ballXdir = 0; 
			ballYdir = 0; 
			g.setColor(Color.yellow);
			g.setFont(new Font("Consolas", Font.BOLD, 30));
			g.drawString("Game Over! Scores: " + score, 190, 300);
			 
			g.setFont(new Font("Consolas", Font.BOLD, 20));
			g.drawString("Press enter to restart...", 230, 350);
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if ( play ) {
			if ( new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)) ) 
				ballYdir = -ballYdir;
			
			A: for ( int i = 0; i < mapGenerator.map.length; i++ ) {
				for ( int j = 0; j < mapGenerator.map[0].length; j++ ) {
					if ( mapGenerator.map[i][j] > 0 ) {
						int brickX = j * mapGenerator.brickWidth + 80; 
						int brickY = i * mapGenerator.brickHeight + 50;
						int brickWidth = mapGenerator.brickWidth; 
						int brickHeight = mapGenerator.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight); 
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20); 
						Rectangle brickRect = rect;
						
						if ( ballRect.intersects(brickRect) ) {
							mapGenerator.setBrickValue(0, i, j); 
							totalBrick--;
							score += 5;
							
							if ( ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width ) 
								ballXdir = -ballXdir; 
							else 
								ballYdir = -ballYdir;
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir; 
			ballposY += ballYdir; 
			
			if ( ballposX < 0 ) 
				ballXdir = -ballXdir;
			if ( ballposY < 0 ) 
				ballYdir = -ballYdir;
			if ( ballposX > 670 ) 
				ballXdir = -ballXdir;
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {	// If right key pressed
			if ( playerX >= 600 ) 
				playerX = 600; 
			else 
				moveRight();
		} 
		
		if ( e.getKeyCode() == KeyEvent.VK_LEFT ) {		// If left key pressed
			if ( playerX <= 0 ) 
				playerX = 0; 
			else 
				moveLeft();
		}
		
		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( !play ) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1; 
				ballYdir = -2;
				playerX = 310; 
				score = 0;
				totalBrick = 21;
				mapGenerator = new MapGenerator(3, 7);
				
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true; 									// Game start
		playerX += 20; 									// Moving right 20 pixels
	}
	
	public void moveLeft() {
		play = true; 									// Game start
		playerX -= 20;									// Moving left 20 pixels
	}	
}
