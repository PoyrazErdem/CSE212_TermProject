import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Balloons {
    int x, y;
    int speedX, speedY;
    int sizeIndex;      // 0 = MEGA, 3 = small
    int colorIndex;     // 0 = red, 1 = blue, 2 = green
    BufferedImage sprite;
    boolean active = true;

    public Balloons(int x, int y, int colorIndex, int sizeIndex, BufferedImage[] balloonSprites) {
        this.x = x;
        this.y = y;
        this.colorIndex = colorIndex;
        this.sizeIndex = sizeIndex;
        this.sprite = balloonSprites[colorIndex * 4 + sizeIndex];

        this.speedX = 3;                         // horizontal speed
        this.speedY = getBounceVelocity();      // initial bounce up
    }

    public void update(ArrayList<Rectangle> blocks, Rectangle leftWall, Rectangle rightWall, Rectangle roof, Rectangle floor) {
        int gravity = 1;
        int scale = 3;

        int width = sprite.getWidth() * scale;
        int height = sprite.getHeight() * scale;

        x += speedX;
        y += speedY;

        speedY += gravity;

        //left wall
        if(x <= leftWall.x + leftWall.width) {
            x = leftWall.x + leftWall.width;
            speedX *= -1;
        }

        //right wall
        if(x + width >= rightWall.x) {
            x = rightWall.x - width;
            speedX *= -1;
        }
        
        //floor
        if(y + height >= floor.y) {
            y = floor.y - height;
            speedY = -getBounceVelocity(); 
        }

        //roof
        if(y <= roof.y + roof.height) {
            y = roof.y + roof.height;
            speedY *= -1;
        }

        for(Rectangle block : blocks) {
        	Rectangle balloonBorder = new Rectangle(x, y, width, height);
            
            if(balloonBorder.intersects(block)) {
                // Determine from where it's hitting
                Rectangle intersection = balloonBorder.intersection(block);

                // Vertical collision (hit top or bottom of block)
                if (intersection.height < intersection.width) {
                    if (speedY > 0 && y + height <= block.y + intersection.height) {
                        // Hitting from above (falling onto block)
                        y = block.y - height;
                        speedY = -getBounceVelocity(); // bounce
                    } else if (speedY < 0 && y >= block.y + block.height - intersection.height) {
                        // Hitting from below (rising into block)
                        y = block.y + block.height;
                        speedY = 1; // stop going up and start falling
                    }
                }

                // Horizontal collision (hit side of block)
                else {
                    if (x + width / 2 < block.x + block.width / 2) {
                        x = block.x - width; // hit left side
                    } else {
                        x = block.x + block.width; // hit right side
                    }
                    speedX *= -1; // reverse horizontal direction
                }
            }
        }      
 }

    public void draw(Graphics2D g2d) {
        if (active) {
            int scale = 3;
            g2d.drawImage(sprite, x, y, sprite.getWidth() * scale, sprite.getHeight() * scale, null);
            // g2d.setColor(Color.RED);
            // g2d.drawRect(x, y, sprite.getWidth() * scale, sprite.getHeight() * scale);
        }
    }

    private int getBounceVelocity() {
        return switch (sizeIndex) {
            case 0 -> 22;
            case 1 -> 21;
            case 2 -> 20;
            case 3 -> 18;
            default -> 10;
        };
    }

    public Rectangle getBounds() {
        int scale = 3;
        return new Rectangle(x, y, sprite.getWidth() * scale, sprite.getHeight() * scale);
    }
}
