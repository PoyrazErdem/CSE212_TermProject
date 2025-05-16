import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserRecognition2 extends JDialog {

	private static JPanel mainPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel buttonPanel;
	
	private JTextField usernameTxtField;
	private JPasswordField passwordTxtField;
	private JLabel usernameTxtLabel;
	private JLabel passwordTxtLabel;
	private JButton OKButton;
	
	public UserRecognition2() {
		
		setTitle("Login to PangGame");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(350, 140);
		setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JLabel welcomeTxt = new JLabel("Welcome to Pang, Please enter your username and password");
		mainPanel.add(welcomeTxt);
		
		usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		usernameTxtLabel = new JLabel("Username: ");
		usernameTxtField = new JTextField(10);
		usernamePanel.add(usernameTxtLabel);
		usernamePanel.add(usernameTxtField);
		
		passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordTxtLabel = new JLabel("Password: ");
		passwordTxtField = new JPasswordField(10);
		passwordPanel.add(passwordTxtLabel);
		passwordPanel.add(passwordTxtField);
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		OKButton = new JButton("Login");
		buttonPanel.add(OKButton);
		
		mainPanel.add(usernamePanel);
		mainPanel.add(passwordPanel);
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
		setVisible(true);
	}
}
