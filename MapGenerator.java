import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickHeight; 
	public int brickWidth;
	
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		
		// Initial all are shown
		for ( int i = 0; i < map.length; i++ ) {
			for ( int j = 0; j < map[0].length; j++ ) 
				map[i][j] = 1;
		}
		
		brickHeight = 150 / row; 
		brickWidth = 540 / col;
	}
	
	public void draw(Graphics2D g) {
		for ( int i = 0; i < map.length; i++ ) {
			for ( int j = 0; j < map[0].length; j++ ) {
				if ( map[i][j] > 0 ) {
					// Brick drawing
					g.setColor(Color.white);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					// Brick border drawing
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	// Setting brick value
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
}
