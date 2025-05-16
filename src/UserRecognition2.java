import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public abstract class UserRecognition2 extends JDialog {

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
	private JButton OKButton;
	
	public UserRecognition2() {
		
		setTitle("User Interface"); //name of the window
		setModal(true); //cant close until you finish with it
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //sağ üst çarpı
		setResizable(false); //kenardan tutup büyütemezsin
		setSize(450, 250); //boyut
		setLocationRelativeTo(null); //parent yok
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //Box layout kullan
		JLabel welcomeTxt = new JLabel("Welcome to Pang, Please enter your username and password");	//mesaj	
		
		usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //user input ortala
		usernameTxtLabel = new JLabel("Username: ");
		usernameTxtField = new JTextField(15); 
		usernamePanel.add(usernameTxtLabel);
		usernamePanel.add(usernameTxtField);
		
		passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordTxtLabel = new JLabel("Password: ");
		passwordTxtField = new JPasswordField(15);
		passwordPanel.add(passwordTxtLabel);
		passwordPanel.add(passwordTxtField);
		
		errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		errorLabel1 = new JLabel("*the password can not be more than one word");
		errorLabel2 = new JLabel("*the password must be more than 8 letters");
		errorPanel.add(errorLabel1);
		errorPanel.add(errorLabel2);
		errorPanel.setVisible(false);
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		OKButton = new JButton("OK");
		buttonPanel.add(OKButton);
		
		welcomeTxt.setAlignmentX(CENTER_ALIGNMENT);              //hepsi merkeze ortalamak için
		usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
		passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		errorPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		mainPanel.add(welcomeTxt);
		mainPanel.add(Box.createVerticalStrut(35));    //welcome mesajı ile username arasındaki boşluk   
		mainPanel.add(usernamePanel);    
		mainPanel.add(passwordPanel);
		mainPanel.add(Box.createVerticalStrut(35)); //passord ile buton arası boşluk 
		mainPanel.add(errorPanel);
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
		setVisible(true);
		
	}
	public void passwordChecker() {
		String username = usernameTxtField.getText();
		String password = new String(passwordTxtField.getPassword()); //getPassword returns char[], made it a String like this
		User user = new User(username, password);
		try {
			user.passwordValidator();
			errorLabel1.setVisible(false);
			errorLabel2.setVisible(false);
			dispose();
		}
		catch(InvalidPasswordException e) {
			errorLabel1.setVisible(true);
		}
		catch(PasswordLenghtException e) {
			errorLabel2.setVisible(true);
		}
	}
}
















