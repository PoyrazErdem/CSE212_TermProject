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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	
    private BufferedImage[] frames;  //char frames
    private BufferedImage spriteSheet; //the char sprites
    private int playerX = 520;
    private int currentFrame = 0; //current char frame
    private int frameWidth = 32;
    private int frameHeight = 34;
    private int frameCount = 4; 
    private int initialX = 11;
    private int gapbetweenchar = 3;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean facingLeft = false;
    private boolean isIdle = true;
    private boolean aKeyHeld = false;
    private boolean dKeyHeld = false;
    
    private BufferedImage idleFrame;
    
    private BufferedImage healthAsset;
    private int maxHealth = 3;
    private int currentHealth = maxHealth;
    
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
    	if(!facingLeft) {
    		return new Rectangle(playerX + 2, 520, 56, 80); //to be able to track the player
    	}
    	else if(facingLeft && isIdle) {
    		return new Rectangle(playerX + 20, 520, 56, 80);
    	}
    	return null;
    }
    private boolean hurt = false;
    private long hurtStartTime = 0; //when hurt pause the game
    private final int HURT_DURATION = 1000;
    private boolean gameOver = false;
    private long gameOverStartTime = 0;
    private final int GAME_OVER_DURATION = 2000;
    private boolean gameWon = false;
    private long winStartTime = 0;
    private final int WIN_DURATION = 3000;
    private boolean resetFromWin = false;
    
    public static Rectangle leftWall;
    public static Rectangle rightWall;
    public static Rectangle roof;
    public static Rectangle floor;
    private ArrayList<ArrayList<Rectangle>> levelForegroundCollision = new ArrayList<>();
    
    private int currentLevel = 0;
    private final int totalLevels = 4;
    private boolean levelTransitionActive = false;
    private String levelCompleteMessage = "LEVEL COMPLETE";
    private boolean waitingForStartClick = true;
    
    public BufferedImage balloonSheet;
    public ArrayList<Balloons> balloons = new ArrayList<>(); //holds the balloon info
    public BufferedImage[] balloonSprites = new BufferedImage[12]; //holds the sprite
    public BufferedImage redMEGA;
    public BufferedImage redL;
    public BufferedImage redM;
    public BufferedImage redS;
    public BufferedImage blueMEGA;
    public BufferedImage blueL;
    public BufferedImage blueM;
    public BufferedImage blueS;
    public BufferedImage greenMEGA;
    public BufferedImage greenL;
    public BufferedImage greenM;
    public BufferedImage greenS;
    public int megaWidth = 48;
    public int megaLenght = 40;
    public int largeWidth = 33;
    public int largeLenght = 27;
    public int midWidth = 17;
    public int midLenght = 15;
    public int smallWidth = 8;
    public int smallLenght = 7;
    private final int playerStartX = 500; // Or whatever you want

	private final int[][] balloonSpawnPoints = {
	    {300, 100}, // spawn 1
	    {800, 100}  // spawn 2
	};
	
	private String username;
	
	private int score = 0;                 // total accumulated score
	private int scoreBeforeLevel = 0;      // saved score before entering a level
	private int currentLevelScore = 0;     // temporary score gained in current level
	private int[] levelTimes = new int[totalLevels]; 
	
	private MusicandSound bgm = new MusicandSound();;
	private boolean paused = false;

    public GamePanel(String username) {
    	
    	this.username = username;
    	
    	if (bgm != null) {
    	    bgm.playMusic("/assets/tetoTenebre_Rosso.wav", true);
    	}
        
    	setFocusable(true); //allows key input
        requestFocusInWindow();
        addKeyListener(new keyHandler());
        setBackground(Color.BLACK);
        setLayout(null); //absolute layout (for safety reasons that I am not entirely know or sure of)
        
        timerPanel.setBounds(1040, 675, 150, 40);
        timerPanel.setTimeOutCallback(() -> {
            if (!gameOver && !gameWon && !levelTransitionActive && !waitingForStartClick && !hurt) {
                gameOver = true;
                gameOverStartTime = System.currentTimeMillis();
                repaint();
            }
        });
        add(timerPanel);

        try {
            spriteSheet = ImageIO.read(getClass().getResource("/assets/Player.png"));
            backgroundSheet = ImageIO.read(getClass().getResource("/assets/Backgrounds.png"));
            foregroundSheet = ImageIO.read(getClass().getResource("/assets/Foreground.png"));
            balloonSheet = ImageIO.read(getClass().getResource("/assets/Baloons.png"));

            frames = new BufferedImage[frameCount];
            for(int i = 0; i < frameCount; i++) {
                if (i == 0) {
                	frames[i] = spriteSheet.getSubimage(initialX, 1, frameWidth, frameHeight);
                }
                else frames[i] = spriteSheet.getSubimage(i * frameWidth + initialX + gapbetweenchar, 1, frameWidth, frameHeight);
                // frameWidth += 1;
            }
            
            healthAsset = spriteSheet.getSubimage(154, 44, 16, 16);
            idleFrame = spriteSheet.getSubimage(initialX, 111, frameWidth, frameHeight);
            shootingFrame = spriteSheet.getSubimage(43, 111, frameWidth, frameHeight);
            
            redMEGA = balloonSheet.getSubimage(1, 6, megaWidth, megaLenght);
            redL = balloonSheet.getSubimage(52, 13, largeWidth, largeLenght);
            redM = balloonSheet.getSubimage(86, 19, midWidth, midLenght);
            redS = balloonSheet.getSubimage(106, 23, smallWidth, smallLenght);
            blueMEGA = balloonSheet.getSubimage(1, 56, megaWidth, megaLenght);
            blueL = balloonSheet.getSubimage(52, 63, largeWidth, largeLenght);
            blueM = balloonSheet.getSubimage(86, 69, midWidth, midLenght);
            blueS = balloonSheet.getSubimage(106, 73, smallWidth, smallLenght);
            greenMEGA = balloonSheet.getSubimage(1, 105, megaWidth, megaLenght);
            greenL = balloonSheet.getSubimage(52, 112, largeWidth, largeLenght);
            greenM = balloonSheet.getSubimage(86, 118, midWidth, midLenght);
            greenS = balloonSheet.getSubimage(106, 122, smallWidth, smallLenght);
            
            balloonSprites[0] = redMEGA;
            balloonSprites[1] = redL;
            balloonSprites[2] = redM;
            balloonSprites[3] = redS;
            balloonSprites[4] = blueMEGA;
            balloonSprites[5] = blueL;
            balloonSprites[6] = blueM;
            balloonSprites[7] = blueS;
            balloonSprites[8] = greenMEGA;
            balloonSprites[9] = greenL;
            balloonSprites[10] = greenM;
            balloonSprites[11] = greenS;
            
            spawnBalloonsForLevel();
            
            backgrounds[0] = backgroundSheet.getSubimage(8, 8, backgroundWidth, backgroundHeight);
            backgrounds[1] = backgroundSheet.getSubimage(8, 1088, backgroundWidth, backgroundHeight);
            backgrounds[2] = backgroundSheet.getSubimage(8, 2816, backgroundWidth, backgroundHeight);		
            backgrounds[3] = backgroundSheet.getSubimage(8, 4760, backgroundWidth, backgroundHeight);	
            
            foregrounds[0] = foregroundSheet.getSubimage(8, 44, foregroundWidth, foregroundHeight);
            foregrounds[1] = foregroundSheet.getSubimage(8, 260, foregroundWidth, foregroundHeight);
            foregrounds[2] = foregroundSheet.getSubimage(399, 692, foregroundWidth, foregroundHeight);
            foregrounds[3] = foregroundSheet.getSubimage(8, 1123, foregroundWidth, foregroundHeight);
            
            foregroundCollisionstheMethod();
            
            timer = new Timer(100, e ->{ // how fast the char sprites come one after another
            	currentFrame = (currentFrame + 1) % frames.length;
            	repaint();
            });
            timer.start();
            
            leftWall = new Rectangle(23, 0, 1, 600);
            rightWall = new Rectangle(1133, 0, 1, 600);
            roof = new Rectangle(23, 19, 1110, 1);
            floor = new Rectangle(23, 600, 1110, 1);
            
            movertimer = new Timer(14, e -> { // how fast things move inside the game like char going right/left or projectile
            	Rectangle predictionColision; //a temp Rectangle to represent where the player would be if they moved to left or right
            	
            	if (gameWon) {
            	    long elapsed = System.currentTimeMillis() - winStartTime;
            	    if (elapsed >= WIN_DURATION) {
            	        gameWon = false;
            	        goToLevel(0);
            	    }
            	    return;
            	}
            	
            	if (hurt) {
            	    long elapsed = System.currentTimeMillis() - hurtStartTime;
            	    if (elapsed >= HURT_DURATION) {
            	        goToLevel(currentLevel); 
            	        hurt = false;
            	    }
            	    return; 
            	}

            	if (gameOver) {
            	    long elapsed = System.currentTimeMillis() - gameOverStartTime;
            	    if (elapsed >= GAME_OVER_DURATION) {
            	    	score = 0;
            	        currentHealth = maxHealth;
            	        gameOver = false;
            	        goToLevel(0);
            	    }
            	    return; 
            	}
            	
            	if(movingLeft) {
            		predictionColision = new Rectangle(playerX - 6, 500, 96, 102);
            		if (!predictionColision.intersects(leftWall)) {
                        playerX -= 5;
                    }
            	}
            	if(movingRight) {
            		predictionColision = new Rectangle(playerX - 6, 500, 96, 102);
                    if (!predictionColision.intersects(rightWall)) {
                        playerX += 5;
                    }
            	}
            	
            	if (currentProjectile != null && currentProjectile.active) {
            	    currentProjectile.update(levelForegroundCollision.get(currentLevel));

            	    for (Balloons b : balloons) {
            	        if (b.active && isCollision(currentProjectile, b)) {
            	            handleBalloonHit(b);
            	            currentProjectile.active = false;
            	            break;
            	        }
            	    }
            	} 
            	else {
            	    currentProjectile = null;
            	}
            	
            	if (!hurt && !gameOver) {
        	        Rectangle playerBounds = getPlayerBounds();
        	        for (Balloons b : balloons) {
        	            if (b.active && playerBounds.intersects(b.getBounds())) {
        	                handlePlayerHit();
        	                break;
        	            }
        	        }
        	    }
            	
            	if (!waitingForStartClick) {
            	    for (Balloons b : balloons) {
            	        b.update(levelForegroundCollision.get(currentLevel), leftWall, rightWall, roof, floor);
            	    }

            	    if (!hurt && !gameOver && !levelTransitionActive && allBalloonsCleared()) {
            	        levelTransitionActive = true; // prevent reentry before nextLevel() finishes
            	        nextLevel(); 
            	    }
            	}
            	
                repaint();
            });
            movertimer.start();
            
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    System.out.println("Mouse clicked at: (" + x + ", " + y + ")");

                    if (waitingForStartClick) {
                        waitingForStartClick = false;
                        timerPanel.startCountdown();

                        if (paused && bgm != null) {
                            bgm.resumeMusic(); 
                            paused = false;
                        }
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
        
        //g2d.setColor(Color.RED);
        //g2d.drawRect(getPlayerBounds().x, getPlayerBounds().y, getPlayerBounds().width, getPlayerBounds().height);
        
        int Healthscale = 3; 
        int healthWidth = healthAsset.getWidth() * Healthscale;
        int healthLenght = healthAsset.getHeight() * Healthscale;
        
        for (int i = 0; i < currentHealth; i++) {	
            int x = 10 + i * (healthAsset.getWidth() + 37); 
            g2d.drawImage(healthAsset, x, 660, healthWidth, healthLenght, null);
        }
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        int displayScore = score + currentLevelScore;
        String scoreText = "Score: " + displayScore;
        String userText = username;

        // Get width of each string
        int scoreWidth = g2d.getFontMetrics().stringWidth(scoreText);
        int userWidth = g2d.getFontMetrics().stringWidth(userText);

        // Center X coordinate
        int centerX = getWidth() / 2 - 10;

        // Draw username and score centered
        g2d.drawString(userText, centerX - userWidth / 2, 640);   // username above
        g2d.drawString(scoreText, centerX - scoreWidth / 2, 670); // score below 
        
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
            g2d.drawImage(spriteToDraw, playerX, 517, 80, 85, null);
        }
        
        for (Balloons b : balloons) {
            b.draw(g2d);
        }
        
        if (levelTransitionActive) {
            g2d.setColor(new Color(0, 0, 0, 150)); // dark overlay
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            int stringWidth = g2d.getFontMetrics().stringWidth(levelCompleteMessage); // gets the thickness to later use to center the text
            g2d.drawString(levelCompleteMessage, (getWidth() - stringWidth) / 2, getHeight() / 2); // centering 
        }
        
        if (waitingForStartClick && !levelTransitionActive) {
            g2d.setColor(new Color(0, 0, 0, 150)); //transparent layer
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            String msg = "CLICK TO START";
            int textWidth = g2d.getFontMetrics().stringWidth(msg);
            g2d.drawString(msg, (getWidth() - textWidth) / 2, getHeight() / 2);
        }

        
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            String msg = "GAME OVER";
            int textWidth = g2d.getFontMetrics().stringWidth(msg);
            g2d.drawString(msg, (getWidth() - textWidth) / 2, getHeight() / 2);
        }
        
        if (gameWon) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            String msg = "YOU WIN!";
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
			    nextLevel(); 
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
	    	            int projectileX = facingLeft ? playerX + 45 : playerX + 47; // if facing left true then projectileX = playerX + 45 else projectileX = playerX + 47
	    	            int projectileY = 600; 
	    	            currentProjectile = new Projectile(projectileX, projectileY);
	    	        }
	        	}
	        }
	        
	        else if (key == KeyEvent.VK_P) { // pause
	            if (!waitingForStartClick) {
	                waitingForStartClick = true;
	                timerPanel.stopCountdown();
	                
	                if (bgm != null) {
	                    bgm.stopMusic();  // stop music on pause
	                    paused = true;
	                }
	                repaint();
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
            if (levelIndex == currentLevel && !resetFromWin) {

                score = scoreBeforeLevel;
                currentLevelScore = 0;
                levelTimes[levelIndex] = 0;
            }

            scoreBeforeLevel = score;

            currentLevel = levelIndex;
            playerX = playerStartX;
            currentProjectile = null;
            spawnBalloonsForLevel();
            waitingForStartClick = true;
            timerPanel.resetCountdown();
            timerPanel.stopCountdown();

            if (resetFromWin && levelIndex == 0) {
                currentHealth = maxHealth;
                resetFromWin = false;
            }
            repaint();
        }
    }
    
    private void nextLevel() {
        int nextLevelIndex = currentLevel + 1;

        // Add current level points + time bonus
        levelTimes[currentLevel] = timerPanel.getTimeLeft();
        score += currentLevelScore;
        score += timerPanel.getTimeLeft() * 10;
        currentLevelScore = 0;

        if (nextLevelIndex >= totalLevels) {
            levelTransitionActive = true;
            levelCompleteMessage = "YOU WIN!";
            waitingForStartClick = false;

            resetFromWin = true;
            score += currentHealth * 1000;

            // Save final log before clearing points
            try (PrintWriter out = new PrintWriter(new FileWriter("scorelog.txt", true))) {
                out.print(username + ": ");
                out.print("Total Points: " + score + ", ");
                for (int i = 0; i < levelTimes.length; i++) {
                    out.print("Level " + (i + 1) + " Finish Time: " + levelTimes[i]);
                    if (i < levelTimes.length - 1) out.print(", ");
                }
                out.println();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }

            // Clear points after saving
            score = 0;
            currentLevelScore = 0;

            Timer winDelay = new Timer(3000, winEvent -> {
                goToLevel(0);
                levelTransitionActive = false;
                levelCompleteMessage = "LEVEL COMPLETE";
                repaint();
            });
            winDelay.setRepeats(false);
            winDelay.start();
        } 
        else {
            // Log progress for this level
            try (PrintWriter out = new PrintWriter(new FileWriter("scorelog.txt", true))) {
                out.print("username: " + username);
                out.print(", total points: " + score);
                for (int i = 0; i <= currentLevel; i++) {
                    out.print(", level " + (i + 1) + " finish time: " + levelTimes[i]);
                }
                out.println();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }

            levelTransitionActive = true;
            levelCompleteMessage = "LEVEL COMPLETE";
            waitingForStartClick = true;

            Timer delay = new Timer(2000, levelEvent -> {
                goToLevel(nextLevelIndex);
                levelTransitionActive = false;
                repaint();
            });
            delay.setRepeats(false);
            delay.start();
        }
        repaint();
    }

    
    private void foregroundCollisionstheMethod() {
    	int level1Width = 95;
    	int level1Lenght = 24;
    	
    	int level2HorizontalWidth = 189;
    	int level2HorizontalLenght = 46;
    	int level2VerticalWidth = 45;
    	int level2VerticalLenght = 118;
    	
    	int level3UpperY = 277;
    	int level3LowerY = 349;
    	int level3Width = 21;
    	int level3Lenght = 21;
		ArrayList<Rectangle> level0 = new ArrayList<>();
		levelForegroundCollision.add(level0);
		 
		
		ArrayList<Rectangle> level1 = new ArrayList<>();
		level1.addAll(Arrays.asList(
			new Rectangle(216, 239, level1Width, level1Lenght), new Rectangle(528, 239, level1Width, level1Lenght),
			new Rectangle(840, 239, level1Width, level1Lenght), new Rectangle(528, 394, level1Width, level1Lenght)
		));
		levelForegroundCollision.add(level1);
		 
		
		ArrayList<Rectangle> level2 = new ArrayList<>();
		level2.addAll(Arrays.asList(
			new Rectangle(194, 287, level2HorizontalWidth, level2HorizontalLenght), new Rectangle(771, 287, level2HorizontalWidth, level2HorizontalLenght),
			new Rectangle(555, 143, level2VerticalWidth, level2VerticalLenght), new Rectangle(555, 383, level2VerticalWidth, level2VerticalLenght)
		));	
		levelForegroundCollision.add(level2);
		 
		
		ArrayList<Rectangle> level3 = new ArrayList<>();
		level3.addAll(Arrays.asList(
			 new Rectangle(95, level3LowerY, level3Width, level3Lenght), new Rectangle(168, level3UpperY, level3Width, level3Lenght),
			 new Rectangle(240, level3LowerY, level3Width, level3Lenght), new Rectangle(312, level3UpperY, level3Width, level3Lenght),
			 new Rectangle(384, level3LowerY, level3Width, level3Lenght), new Rectangle(456, level3UpperY, level3Width, level3Lenght),
			 new Rectangle(528, level3LowerY, level3Width, level3Lenght), new Rectangle(600, level3UpperY, level3Width, level3Lenght),
			 new Rectangle(672, level3LowerY, level3Width, level3Lenght), new Rectangle(745, level3UpperY, level3Width, level3Lenght),
			 new Rectangle(816, level3LowerY, level3Width, level3Lenght), new Rectangle(888, level3UpperY, level3Width, level3Lenght),
			 new Rectangle(960, level3LowerY, level3Width, level3Lenght), new Rectangle(1032, level3UpperY, level3Width, level3Lenght)
		));
		levelForegroundCollision.add(level3);
    }
    
    private void spawnBalloonsForLevel() {
        balloons.clear();

        switch (currentLevel) {
            case 0:
                // 1 red LARGE
                balloons.add(new Balloons(balloonSpawnPoints[0][0], balloonSpawnPoints[0][1], 0, 1, balloonSprites)); // red LARGE
                break;

            case 1:
                // 1 red MEGA, 1 blue MID
                balloons.add(new Balloons(balloonSpawnPoints[0][0], balloonSpawnPoints[0][1], 0, 0, balloonSprites)); // red MEGA
                balloons.add(new Balloons(balloonSpawnPoints[1][0], balloonSpawnPoints[1][1], 1, 1, balloonSprites)); // blue MID
                break;

            case 2:
                // 1 red MID, 1 green MEGA
                balloons.add(new Balloons(balloonSpawnPoints[0][0], balloonSpawnPoints[0][1], 0, 1, balloonSprites)); // red MID
                balloons.add(new Balloons(balloonSpawnPoints[1][0], balloonSpawnPoints[1][1], 2, 0, balloonSprites)); // green MEGA
                break;

            case 3:
                // 1 green MEGA, 1 blue LARGE, 1 red LARGE
                balloons.add(new Balloons(balloonSpawnPoints[0][0], balloonSpawnPoints[0][1], 2, 0, balloonSprites)); // green MEGA
                balloons.add(new Balloons(balloonSpawnPoints[1][0], balloonSpawnPoints[1][1], 1, 2, balloonSprites)); // blue LARGE
                balloons.add(new Balloons(balloonSpawnPoints[0][0], balloonSpawnPoints[0][1], 0, 1, balloonSprites)); // red LARGE
                break;
        }
    }
    
    private boolean isCollision(Projectile proj, Balloons balloon) {
        return proj.getBounds().intersects(balloon.getBounds());
    }
    
    private void handleBalloonHit(Balloons b) {
        int basePoints = switch (b.sizeIndex) {
            case 0 -> 500;
            case 1 -> 400;
            case 2 -> 300;
            case 3 -> 200;
            default -> 0;
        };
        currentLevelScore += basePoints; 

        b.active = false;

        if (b.sizeIndex < 3) {
            int newSize = b.sizeIndex + 1;
            int color = b.colorIndex;

            Balloons left = new Balloons(b.x - 20, b.y, color, newSize, balloonSprites);
            Balloons right = new Balloons(b.x + 20, b.y, color, newSize, balloonSprites);

            left.speedY = b.speedY;
            right.speedY = b.speedY;
            left.speedX = -Math.abs(b.speedX);
            right.speedX = Math.abs(b.speedX);

            balloons.add(left);
            balloons.add(right);
        }
    }

    
    private void handlePlayerHit() {
        currentHealth--;

        if (currentHealth <= 0) {
            gameOver = true;
            gameOverStartTime = System.currentTimeMillis();
        } 
        else {
            hurt = true;
            hurtStartTime = System.currentTimeMillis();
        }
    }
    
    private boolean allBalloonsCleared() {
        for (Balloons b : balloons) {
            if (b.active) return false;
        }
        return true;
    }
    
    @Override
    public void removeNotify() {
        super.removeNotify();
        if (bgm != null) {
            bgm.stopMusic();
        }
    }
}
