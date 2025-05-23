import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private  Image playerImage;
	private int playerX = 100;
	private int playerY = 300;
	
	public GamePanel() {
		setSize(800,600);
		setBackground(Color.BLACK);
		setFocusable(true);
		
		playerImage = new ImageIcon("/assests/Player.png").getImage();
		
		setVisible(true);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(playerImage, playerX, playerY, this);
	}
}
