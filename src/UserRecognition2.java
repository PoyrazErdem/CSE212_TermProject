
import javax.swing.*;
import java.awt.*;

public class UserRecognition2 extends JDialog{
	private JTextField usernameBox;
	private JPasswordField passwordBox;
	private JLabel passwordErrorMessage;
	private JButton okButton;
	private JButton cancelButton;
	
	public UserRecognition2(JFrame parent) {
		super(parent, "User Recognition",true);
		setLayout(new BorderLayout());
		setSize(400,250);
		setLocationRelativeTo(parent);
		
		JLabel label = new JLabel("Welcome to Pang, Please enter a username and password");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);
		setVisible(true);
	}
}
