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
	private JButton optionsButton = new JButton("OPTIONS");
	private JButton helpButton = new JButton("HELP");
	private JPanel mainPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	public MainMenuScreen(){
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel welcomeCenterAligner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		welcomeCenterAligner.add(welcomeTextLabel);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		JPanel playCenterAligner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		playCenterAligner.add(playButton);
		
		JPanel optionsCenterAligner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		optionsCenterAligner.add(optionsButton);
		
		JPanel helpCenterAligner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		helpCenterAligner.add(helpButton);
		
		buttonPanel.add(playCenterAligner);
		buttonPanel.add(Box.createVerticalStrut(0));
		buttonPanel.add(optionsCenterAligner);
		buttonPanel.add(Box.createVerticalStrut(0));
		buttonPanel.add(helpCenterAligner);
		buttonPanel.add(Box.createVerticalStrut(35));
		
		mainPanel.add(welcomeCenterAligner);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
		
		ActionHandler handler = new ActionHandler();
		playButton.addActionListener(handler);
		optionsButton.addActionListener(handler);
		helpButton.addActionListener(handler);
	}
	
	private class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent	event) {
			String skibidi = event.getActionCommand();
			if(skibidi.equals("PLAY")){
				new UserInterface();
			}
			else if(skibidi.equals("OPTIONS")) {
				
			}
			else if(skibidi.equals("HELP")) {
				new AboutMenu();
			}
		}
	}
}

























