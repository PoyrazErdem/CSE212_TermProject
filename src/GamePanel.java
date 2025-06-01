import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	
    private BufferedImage[] frames;  //char frames
    private BufferedImage spriteSheet; //the char sprites
    private int playerX = 100;
    private int currentFrame = 0; //current char frame
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
    
    private Projectile currentProjectile;
    
    private Timer timer; //sprite animation
    private Timer movertimer; //game animation
    Time timerPanel = new Time(); //timer on the right bottom
    
    private BufferedImage backgroundSheet;
    private BufferedImage[] backgrounds = new BufferedImage[4];
    public static int backgroundWidth = 397-8;
    private int backgroundHeight = 215-8;
    
    private BufferedImage foregroundSheet;
    private BufferedImage[] foregrounds = new BufferedImage[4];
    private int foregroundWidth = 392 - 8;
    private int foregroundHeight = 251 - 44;
    
    private Rectangle getPlayerBounds() {
        return new Rectangle(playerX, 500, 96, 102); //to be able to track the player
    }
    private Rectangle leftWall;
    private Rectangle rightWall;
    private ArrayList<ArrayList<Rectangle>> levelForegroundCollision = new ArrayList<>();
    
    private int currentLevel = 0;
    private final int totalLevels = 4;
    private boolean levelTransitionActive = false;
    private String levelCompleteMessage = "LEVEL COMPLETE";
    private boolean waitingForStartClick = true;

    public GamePanel() {
        
    	setFocusable(true); //allows key input
        requestFocusInWindow();
        addKeyListener(new keyHandler());
        setBackground(Color.BLACK);
        setLayout(null); //absolute layout (safety reasons that I am not entirely know or sure of)
        
        timerPanel.setBounds(1046, 640, 150, 40);
        add(timerPanel);

        
        try {
            spriteSheet = ImageIO.read(getClass().getResource("/assets/Player.png"));
            backgroundSheet = ImageIO.read(getClass().getResource("/assets/Backgrounds.png"));
            foregroundSheet = ImageIO.read(getClass().getResource("/assets/Foreground.png"));

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
            
            timer = new Timer(100, e ->{ // how fast the char sprites come one after another
            	currentFrame = (currentFrame + 1) % frames.length;
            	repaint();
            });
            timer.start();
            
            movertimer = new Timer(10, e -> { // how fast things move inside the game like char going right/left or projectile
            	Rectangle predictionColision; //a temp Rectangle to represent where the player would be if they moved to left or right
            	
            	leftWall = new Rectangle(15, 0, 5, getHeight());
                rightWall = new Rectangle(getWidth() - 20, 0, 5, getHeight());
            	
            	if(movingLeft) {
            		predictionColision = new Rectangle(playerX - 5, 500, 96, 102);
            		if (!predictionColision.intersects(leftWall)) {
                        playerX -= 5;
                    }
            	}
            	if(movingRight) {
            		predictionColision = new Rectangle(playerX + 5, 500, 96, 102);
                    if (!predictionColision.intersects(rightWall)) {
                        playerX += 5;
                    }
            	}
            	
            	if (currentProjectile != null && currentProjectile.active) {
            	    currentProjectile.update();
            	}else {
            		currentProjectile = null; // delete the projectile
            	}
            	
                repaint();
            });
            movertimer.start();
            
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                	if (waitingForStartClick) {
                        waitingForStartClick = false;
                        timerPanel.startCountdown();
                        repaint();
                    }
                }
            });

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; //to be able to mirror the char to left
        
        g2d.drawImage(backgrounds[currentLevel], 0, 0, backgroundWidth * 3 , backgroundHeight * 3, null);
        g2d.drawImage(foregrounds[currentLevel], 0, 0, foregroundWidth * 3 , foregroundHeight * 3, null);
        
        BufferedImage spriteToDraw;
        if (currentProjectile != null && currentProjectile.active) {
            currentProjectile.draw(g2d);
        }
        if (shooting && shootingFrame != null) { //if not shooting then if you shoot the frame will be shooting frame
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
            g2d.drawImage(spriteToDraw, playerX + 80, 516, -80, 85, null);
        } 
        else {
            g2d.drawImage(spriteToDraw, playerX, 516, 80, 85, null);
        }
        
        if (levelTransitionActive) {
            g2d.setColor(new Color(0, 0, 0, 150)); // dark overlay
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            int stringWidth = g2d.getFontMetrics().stringWidth(levelCompleteMessage); // gets the thickness to later use to center the text
            g2d.drawString(levelCompleteMessage, (getWidth() - stringWidth) / 2, getHeight() / 2); // centering 
        }
        
        if (waitingForStartClick) {
            g2d.setColor(new Color(0, 0, 0, 220)); //transparent layer
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            String msg = "CLICK TO START";
            int textWidth = g2d.getFontMetrics().stringWidth(msg);
            g2d.drawString(msg, (getWidth() - textWidth) / 2, getHeight() / 2);
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
			if (waitingForStartClick) {
				return;
			}
			
			else if (key == KeyEvent.VK_L) {
			    nextLevel(); // 🔧 test level transition
			}
			
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
	        	if (!shooting) {  
	        		shooting = true;
	    	        movingLeft = false;
	    	        movingRight = false;
	    	         
	    	        if (currentProjectile == null || !currentProjectile.active) {
	    	            int projectileX = facingLeft ? playerX + 45 : playerX + 47; // if facing left true then projectileX = playerX + 20 else projectileX = playerX + 70
	    	            int projectileY = 600; 
	    	            currentProjectile = new Projectile(projectileX, projectileY);
	    	        }
	        	}
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
    
    public void goToLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < totalLevels) {
            currentLevel = levelIndex;
            waitingForStartClick = true;
            timerPanel.resetCountdown();
            repaint();
        }
    }
    
    private void nextLevel() {
        levelTransitionActive = true;
        repaint();

        Timer delay = new Timer(2000, e -> {
            int nextLevelIndex = (currentLevel + 1) % totalLevels; //game loops
            goToLevel(nextLevelIndex);
            levelTransitionActive = false;
            waitingForStartClick = true;
            timerPanel.resetCountdown();
            timerPanel.stopCountdown();
            repaint();
        });
        
        delay.setRepeats(false); // run only once
        delay.start();
    }
} 
    