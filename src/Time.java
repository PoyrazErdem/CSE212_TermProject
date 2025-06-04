import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Time extends JPanel {
	private int timeleft = 100;
	private Timer timer;
	private Runnable timeOutCallback;
	
	public Time() {
        setOpaque(false); //no background
        setFocusable(false);
        
        
        timer = new Timer(1000, e -> {
            timeleft--;
            repaint();
            if (timeleft == 0) {
                timer.stop();
                if (timeOutCallback != null) {
                    timeOutCallback.run(); 
                }
            }
        });
	}
	public void startCountdown() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }
	public void stopCountdown() {
        timer.stop();
    }
	public void resetCountdown() {
        timeleft = 100;
        repaint();
    }
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Arial", Font.BOLD, 20));
	    g.drawString("Time: " + timeleft, 10, 20);
	}
	
	public int getTimeLeft() {
	    return timeleft;
	}
	
	public void setTimeOutCallback(Runnable callback) {
	    this.timeOutCallback = callback;
	}
}
	