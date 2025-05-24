import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private BufferedImage[] frames;
    private BufferedImage idleFrame;
    private BufferedImage shootingFrame;
    private int playerX = 100;
    private int currentFrame = 0;
    private Timer timer;
    private Timer movertimer;
    private int initialX = 11;
    private int gap = 3;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean shooting = false;
    private boolean facingLeft = false;
    private boolean aKeyHeld = false;
    private boolean dKeyHeld = false;

    public GamePanel() {
        
    	setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new keyHandler());
        int frameWidth = 32;
        int frameHeight = 34;
        int frameCount = 4; // adjust based on how many frames are in the row
    	
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResource("/assets/Player.png"));

            frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                if (i == 0) {
                	frames[i] = spriteSheet.getSubimage(initialX, 1, frameWidth, frameHeight);
                }
                else frames[i] = spriteSheet.getSubimage(i * frameWidth + initialX + gap, 1, frameWidth, frameHeight);
                // frameWidth += 1;
            }
            
            idleFrame = spriteSheet.getSubimage(initialX, 111, frameWidth, frameHeight);
            shootingFrame = spriteSheet.getSubimage(43, 111, frameWidth, frameHeight);
            
            timer = new Timer(100, e ->{
            	currentFrame = (currentFrame + 1) % frames.length;
            	repaint();
            });
            timer.start();
            
            movertimer = new Timer(10, e -> {
            	if(movingLeft) {
            		playerX -= 5;
            	}
            	if(movingRight) {
            		playerX += 5;
            	}
                repaint();
            });
            movertimer.start();

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        BufferedImage spriteToDraw;
        if (shooting && shootingFrame != null) {
            spriteToDraw = shootingFrame;
        } 
        else if ((movingLeft || movingRight) && frames != null && frames[currentFrame] != null) {
            spriteToDraw = frames[currentFrame];
        } 
        else {
            spriteToDraw = idleFrame;
        }

        // Flip horizontally if facing left
        if (facingLeft) {
            g2d.drawImage(spriteToDraw, playerX + 96, 100, -96, 102, null);
        } else {
            g2d.drawImage(spriteToDraw, playerX, 100, 96, 102, null);
        }
    }
    
    private class keyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			//skibidi
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			 int key = e.getKeyCode();
	         
	         if(key == KeyEvent.VK_A) {
	        	 aKeyHeld = true;
	        	 if(!shooting) {
	        		 movingLeft = true;
		        	 facingLeft = true;
	        	 }
	         }
	         else if(key == KeyEvent.VK_D) {
	        	 dKeyHeld = true;
	        	 if(!shooting) {
	        		 movingRight = true;
		        	 facingLeft = false;
	        	 }
	         }
	         else if(key == KeyEvent.VK_SPACE) {
	        	 shooting = true;
	        	 movingLeft = false;
	        	 movingRight = false;
	         }
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
		    if(key == KeyEvent.VK_A) {
		    	aKeyHeld = false;
		        movingLeft = false;
		    } 
		    else if(key == KeyEvent.VK_D) {
		    	dKeyHeld = false;
		        movingRight = false;
		    }
		    else if(key == KeyEvent.VK_SPACE) {
		    	shooting = false;
		    	 if (aKeyHeld) {
		    	        movingLeft = true;
		    	        facingLeft = true;
		    	    }
		    	 if (dKeyHeld) {
		    	        movingRight = true;
		    	        facingLeft = false;
		    	 }
		    }
		}
    }
} 
    