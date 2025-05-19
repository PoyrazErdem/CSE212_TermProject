public class ProjectMain {

	public static void main(String[] args) {
		User.users = UserManegment.Load();
		for(User user : User.users) {
			System.out.println(user.getUserName());
		}
        
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        mainMenuScreen.setTitle("PANG");
        mainMenuScreen.setDefaultCloseOperation(3);
        mainMenuScreen.setSize(450, 250);
        mainMenuScreen.setResizable(false);
        mainMenuScreen.setLocationRelativeTo(null);
        mainMenuScreen.setVisible(true);
	}

}
