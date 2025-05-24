import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	private  Image playerImage;
	private int playerX = 100;
	private int playerY = 300;
	final int frameCount = 4;
	final int spriteWidth = 24;
	final int spriteHeight = 32;
	final int animationSpeed = 150;
	final int spriteRow = 0;
	int currentFrameIndex = 0;
	private Timer walkTimer;
	private final int[][] walkingInPlaceAnimation = {
		    {0, 0},
		    {1, 0},
		    {2, 0},
		    {3, 0}
		};
	
	
	public GamePanel() {
		setSize(800,600);
		setBackground(Color.BLACK);
		setFocusable(true);
		
		playerImage = new ImageIcon(getClass().getResource("/assets/Player.png")).getImage();

		
		walkTimer = new Timer(animationSpeed, e -> {
		    currentFrameIndex = (currentFrameIndex + 1) % walkingInPlaceAnimation.length;
		    repaint();
		});
		walkTimer.start();
		
		setVisible(true);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int col = walkingInPlaceAnimation[currentFrameIndex][0];
	    int row = walkingInPlaceAnimation[currentFrameIndex][1];
		
		int sxbegin = col * spriteWidth; //where does the sprite start on x the axis on the general sprite sheet
		int sybegin = row * spriteHeight;  //where does the sprite start on y the axis on the general sprite sheet
		int sxend = sxbegin + spriteWidth;	//where does the sprite end on x the axis on the general sprite sheet
		int syend = sybegin + spriteHeight;	//where does the sprite end on y the axis on the general sprite sheet
		
		int dxstart = playerX; //where to start the sprite on x the axis 
		int dystart = playerY; //where to start the sprite on y the axis
		int dxfinish = playerX + spriteWidth * 5; //where to end the sprite on the x axis
		int dyfinish = playerY + spriteHeight * 5; //where to end the sprite on the y axis
		
		g.drawImage(playerImage, dxstart, dystart, dxfinish, dyfinish, sxbegin, sybegin, sxend, syend, this);
	}
}
