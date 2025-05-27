import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Time extends JPanel {
	private int timeleft = 90;
	private Timer timer;
	
	public Time() {
        setOpaque(false); //nobackground
        setFocusable(false);
        
        
		timer =  new Timer(1000, e ->{
			timeleft--;
			repaint();
			if(timeleft == 0) {
				timer.stop();
			}
		});
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 20));
	    g.drawString("Time: " + timeleft, 10, 20);
	}    
}
	