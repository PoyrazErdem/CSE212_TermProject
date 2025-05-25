import java.awt.Color;
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
    private BufferedImage spriteSheet;
    private int playerX = 100;
    private int currentFrame = 0;
    private int frameWidth = 32;
    private int frameHeight = 34;
    private int frameCount = 4; 
    private int initialX = 11;
    private int gapbetweenchar = 3;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean facingLeft = false;
    private boolean aKeyHeld = false;
    private boolean dKeyHeld = false;
    
    private BufferedImage idleFrame;
    
    private BufferedImage shootingFrame;
    private boolean shooting = false;
    
    private BufferedImage hookSheet;
    private BufferedImage[] hooks = new BufferedImage[58];
    private int initialHookX = 8;
    private int initialHookY = 1383;
    private int hookWidth = 9;
    private int hookLenght = 1560 - 1383;
    
    private Timer timer;
    private Timer movertimer;
    
    private BufferedImage backgroundSheet;
    private BufferedImage[] backgrounds = new BufferedImage[4];
    public static int backgroundWidth = 397-8;
    private int backgroundHeight = 215-8;
    
    private BufferedImage foregroundSheet;
    private BufferedImage[] foregrounds = new BufferedImage[4];
    private int foregroundWidth = 392 - 8;
    private int foregroundHeight = 251 - 44;

    public GamePanel() {
        
    	setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new keyHandler());
        setBackground(Color.BLACK);

        
        try {
            spriteSheet = ImageIO.read(getClass().getResource("/assets/Player.png"));
            backgroundSheet = ImageIO.read(getClass().getResource("/assets/Backgrounds.png"));
            foregroundSheet = ImageIO.read(getClass().getResource("/assets/Foreground.png"));
            hookSheet = ImageIO.read(getClass().getResource("/assets/Items__Weapons.png"));

            frames = new BufferedImage[frameCount];
            for(int i = 0; i < frameCount; i++) {
                if (i == 0) {
                	frames[i] = spriteSheet.getSubimage(initialX, 1, frameWidth, frameHeight);
                }
                else frames[i] = spriteSheet.getSubimage(i * frameWidth + initialX + gapbetweenchar, 1, frameWidth, frameHeight);
                // frameWidth += 1;
            }
            
            idleFrame = spriteSheet.getSubimage(initialX, 111, frameWidth, frameHeight);
            shootingFrame = spriteSheet.getSubimage(43, 111, frameWidth, frameHeight);
            
            backgrounds[0] = backgroundSheet.getSubimage(8, 8, backgroundWidth, backgroundHeight);
            backgrounds[1] = backgroundSheet.getSubimage(8, 1088, backgroundWidth, backgroundHeight);
            backgrounds[2] = backgroundSheet.getSubimage(8, 2816, backgroundWidth, backgroundHeight);		
            backgrounds[3] = backgroundSheet.getSubimage(8, 4760, backgroundWidth, backgroundHeight);	
            
            foregrounds[0] = foregroundSheet.getSubimage(8, 44, foregroundWidth, foregroundHeight);
            foregrounds[1] = foregroundSheet.getSubimage(8, 260, foregroundWidth, foregroundHeight);
            foregrounds[2] = foregroundSheet.getSubimage(399, 692, foregroundWidth, foregroundHeight);
            foregrounds[3] = foregroundSheet.getSubimage(8, 1123, foregroundWidth, foregroundHeight);
            
            
            for(int i = 0; i < 23; i++) {
            	hooks[i] = hookSheet.getSubimage(initialHookX + (8 * i), initialHookY + (2 * i), hookWidth, hookLenght + (2 * i));
            }
            for(int a = 23; a < 46; a++) {
            	int i = 0;
            	hooks[a] = hookSheet.getSubimage(initialHookX + (8 * i), initialHookY + 57 + (2 * i), hookWidth, hookLenght + 52 + (2 * i));
            	i++;
            }
        	for(int b = 46; b < 58; b++) {
        		int i = 0;
        		hooks[b] = hookSheet.getSubimage(initialHookX + (8 * i), initialHookY + 57 + 60 + (2 * i), hookWidth, hookLenght + 52 + 51 + (2 * i));
        		i++;
        	}
            
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
        g2d.drawImage(backgrounds[3], 0, 0, backgroundWidth * 3 , backgroundHeight * 3, null);
        g2d.drawImage(foregrounds[3], 0, 0, foregroundWidth * 3 , foregroundHeight * 3, null);
        
        BufferedImage spriteToDraw;
        if (shooting && shootingFrame != null) {
            spriteToDraw = shootingFrame;
            for(int i = 0; i < hooks.length; i++) {
            	g2d.drawImage(hooks[i], playerX + 15, 500 + 30, hookWidth * 2, hookLenght * 2, null);
            }
        } 
        else if ((movingLeft || movingRight) && frames != null && frames[currentFrame] != null) {
            spriteToDraw = frames[currentFrame];
        } 
        else {
            spriteToDraw = idleFrame;
        }

        // Flip horizontally if facing left
        if (facingLeft) {
            g2d.drawImage(spriteToDraw, playerX + 96, 500, -96, 102, null);
        } 
        else {
            g2d.drawImage(spriteToDraw, playerX, 500, 96, 102, null);
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
    