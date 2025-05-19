import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserInterface extends JFrame {

	private static JPanel mainPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel buttonPanel;
	private JPanel errorPanel;
	
	private static JTextField usernameTxtField;
	private static JPasswordField passwordTxtField;
	private JLabel usernameTxtLabel;
	private JLabel passwordTxtLabel;
	private static JLabel errorLabel1;
	private static JLabel errorLabel2;
	private static JLabel errorLabel3;
	private JButton OKButton;
	private JButton CancelButton;
	
	public UserInterface() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //Box layout kullan
		JLabel welcomeTxt = new JLabel("Welcome to Pang, Please enter your username and password");	//mesaj	
		
		
		usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //user input ortala
		usernameTxtLabel = new JLabel("Username: ");
		usernameTxtField = new JTextField(15); 
		usernameTxtField.setToolTipText("enter your username here");
		usernamePanel.add(usernameTxtLabel);
		usernamePanel.add(usernameTxtField);
		
		
		passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordTxtLabel = new JLabel("Password: ");
		passwordTxtField = new JPasswordField(15);
		passwordTxtField.setToolTipText("enter your password here");
		passwordPanel.add(passwordTxtLabel);
		passwordPanel.add(passwordTxtField);
		
		
		errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		errorLabel1 = new JLabel("*the password can not be more than one word");
		errorLabel1.setForeground(Color.RED);
		errorLabel2 = new JLabel("*the password must be more than 8 letters");
		errorLabel2.setForeground(Color.RED);
		errorLabel3 = new JLabel("*invalid username");
		errorLabel3.setForeground(Color.RED);
		errorPanel.add(errorLabel1);
		errorPanel.add(errorLabel2);
		errorPanel.add(errorLabel3);
		errorPanel.setVisible(false);
		
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		OKButton = new JButton("OK");
		CancelButton = new JButton("Cancel");
		buttonPanel.add(OKButton);
		buttonPanel.add(CancelButton);
		JPanel buttonWrapper = new JPanel();
		buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.X_AXIS));
		buttonWrapper.add(Box.createHorizontalStrut(35)); 
		buttonWrapper.add(buttonPanel);
		
		
		welcomeTxt.setAlignmentX(CENTER_ALIGNMENT);              
		usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
		passwordPanel.setAlignmentX(CENTER_ALIGNMENT);					//hepsi merkeze ortalamak için
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		errorPanel.setAlignmentX(CENTER_ALIGNMENT);
		buttonWrapper.setAlignmentX(CENTER_ALIGNMENT);
		
		mainPanel.add(welcomeTxt);
		mainPanel.add(Box.createVerticalStrut(35)); //welcome mesajı ile username arasındaki boşluk   
		mainPanel.add(usernamePanel);    
		mainPanel.add(passwordPanel);
		mainPanel.add(errorPanel);
		mainPanel.add(Box.createVerticalStrut(35)); //Error mesajları ile butonlar arası boşluk
		mainPanel.add(buttonWrapper);
		
		
		add(mainPanel);
		
		
		ActionHandler handler = new ActionHandler();
		usernameTxtField.addActionListener(handler);
		passwordTxtField.addActionListener(handler);
		OKButton.addActionListener(handler);
		CancelButton.addActionListener(handler);
		
		 setTitle("User Interface"); //name of the window
	     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //sağ üst çarpı
	     setSize(450, 250); //boyut
	     setResizable(false); //kenardan tutup büyütemezsin
	     setLocationRelativeTo(null); //merkezde ortaya çıkması için
	     setVisible(true);
	}
	
	
	private class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent	event) {
			if(event.getSource() == usernameTxtField) {
				passwordTxtField.requestFocusInWindow();  // when "enter" is pressed automatically skip to the password input field 
			}
			else if(event.getSource() == passwordTxtField) {
				username_passwordChecker();
				OKButton.requestFocusInWindow();  // the same thing with "enter" but this time skip to OK button
			}
			else if(event.getSource() == OKButton) {
				username_passwordChecker();
			}
			else if(event.getSource() == CancelButton) {
				dispose();
			}
		}
	}
	
	public void username_passwordChecker() {
		String username = usernameTxtField.getText().trim(); //gets the username
		String password = new String(passwordTxtField.getPassword()); //getPassword returns char[], made it a String like this
		User user = new User(username, password);
		errorLabel1.setVisible(false);
		errorLabel2.setVisible(false);
		errorLabel3.setVisible(false);
		errorLabel3.setVisible(false);
		try {
			user.usernameValidator();
			user.passwordValidator();
			UserManegment.Login(username, password);
			dispose();
		}
		catch(UsernameLenghtException e) {
			errorLabel3.setVisible(true);
		}
		
		catch(InvalidPasswordException e) {
			errorLabel1.setVisible(true);
		}
		catch(PasswordLenghtException e) {
			errorLabel2.setVisible(true);
		}
		catch(WrongPasswordException e) {
			errorLabel1.setText("*wrong password for the given username");
			errorLabel1.setVisible(true);
		}
		if(errorLabel3.isVisible()) {
			errorPanel.setVisible(true);
		}
		else if(errorLabel1.isVisible() || errorLabel2.isVisible()) {
			errorPanel.setVisible(true);
		}
	}
}
















