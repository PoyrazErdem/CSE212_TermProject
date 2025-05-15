
public class User {
	private String userName;
	private String password;
	
	public User(String userName,String password){
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String username) {			
		userName = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String a) {
		password = a;
	}
	
	public void passwordValidator() throws InvalidPasswordException {
		char[] passwordArray = password.toCharArray();
		for(char c : passwordArray) {
			if(c == ' ') {
				throw new InvalidPasswordException("The password cant have spaces inbetween");
			}
		}
	}
}
