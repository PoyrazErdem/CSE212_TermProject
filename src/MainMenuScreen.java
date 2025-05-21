import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuScreen extends JFrame{
	private JLabel welcomeTextLabel = new JLabel("WELCOME TO PANG");
	private JButton playButton = new JButton("PLAY");
	private JPanel mainPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JFrame that;
	
	public MainMenuScreen(){
		that = this;
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel welcomeCenterAligner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		welcomeCenterAligner.add(welcomeTextLabel);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		JPanel playCenterAligner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		playCenterAligner.add(playButton);
		
		buttonPanel.add(playCenterAligner);
		buttonPanel.add(Box.createVerticalStrut(20));
		
		mainPanel.add(welcomeCenterAligner);
		
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
		
		ActionHandler handler = new ActionHandler();
		playButton.addActionListener(handler);
	}
	
	private class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent	event) {
			String skibidi = event.getActionCommand();
			if(skibidi.equals("PLAY")){
				that.setVisible(false);
				new UserInterface(that);
			}
		}
	}
}

























