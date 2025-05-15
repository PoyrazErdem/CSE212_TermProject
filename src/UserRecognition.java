
import javax.swing.*;
import java.awt.*;

public class UserRecognition extends JDialog {
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private JButton okButton;

    public UserRecognition(JFrame parent) {
        super(parent, "User Recognition", true);
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);

        // Message
        JLabel welcome = new JLabel("Welcome to Pang. Please enter your user name and password");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcome, BorderLayout.NORTH);

        // Center panel with input fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        // Error label
        gbc.gridx = 1;
        gbc.gridy = 2;
        errorLabel = new JLabel("The password can't be more than one word");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        centerPanel.add(errorLabel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // OK Button
        okButton = new JButton("OK");
        okButton.addActionListener(e -> validatePassword());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Allow Enter key to trigger validation
        passwordField.addActionListener(e -> validatePassword());
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());

        setVisible(true);
    }

    private void validatePassword() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = new User(username, password);
        try {
            user.passwordValidator();
            errorLabel.setVisible(false);
            dispose(); // close dialog
            // You can now pass `user` to the rest of your game
        } catch (InvalidPasswordException ex) {
            errorLabel.setVisible(true);
        }
    }
}
