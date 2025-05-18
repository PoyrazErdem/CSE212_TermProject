import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	public static ArrayList<User> users;
	
	public User(String userName,String password){
		this.username = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return username;
	}
	
	public void setUserName(String userName) {			
		username = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String a) {
		password = a;
	}
	
	public void passwordValidator() throws InvalidPasswordException, PasswordLenghtException {
		char[] passwordArray = password.toCharArray();
		for(char c : passwordArray) {
			if(c == ' ') {
				throw new InvalidPasswordException("The password cant have spaces inbetween");
			}
		}
		if(passwordArray.length < 8) {
			throw new PasswordLenghtException("The password must be more than 8 letters");
		}
	}
	
	public void usernameValidator() throws UsernameLenghtException{
		char[] usernameArray = username.toCharArray();
		if(usernameArray.length < 1) {
			throw new UsernameLenghtException("Please enter a username");
		}
	}
}
