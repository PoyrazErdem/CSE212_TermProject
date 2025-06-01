import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile {
	int x, y; //starting positions
	int maxHeight = 570; 
	int currentLength = 0; 
    int speed = 10;
    boolean active = true; //is the line still going

    public Projectile(int startX, int startY) { //set the initial x y
        this.x = startX;
        this.y = startY;
    }

    public void update(ArrayList<Rectangle> foregroundBlocks) { // possible "collision-able" blocks
    	Rectangle projectileCollision = new Rectangle(x, y - currentLength, 6, currentLength);

        for (Rectangle block : foregroundBlocks) {
            if (projectileCollision.intersects(block)) {
                active = false;
                return; 
            }
        }
    	if (y - currentLength - 30 <= 0) { // hit the roof
    	    active = false; 
    	} 
    	else {
    	    currentLength += speed;
    	}
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.CYAN); 
        g2d.setStroke(new java.awt.BasicStroke(2)); // thickness
        g2d.drawLine(x - 2, y, x - 2, y - currentLength); // vertical line
        
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new java.awt.BasicStroke(3));								//line made up of three line edges and
        g2d.drawLine(x, y, x, y - currentLength);								//a center
        
        g2d.setColor(Color.CYAN);
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.drawLine(x + 2, y, x + 2, y - currentLength);
    }
}
