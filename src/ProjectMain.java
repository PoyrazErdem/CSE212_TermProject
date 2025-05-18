import java.util.ArrayList;

import javax.swing.JDialog;

public class ProjectMain {

	public static void main(String[] args) {
		User.users = UserManegment.Load();
		for(User user : User.users) {
			System.out.println(user.getUserName());
		}
		
        UserInterface userInterface = new UserInterface();
        userInterface .setTitle("User Interface"); //name of the window
        userInterface .setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //sağ üst çarpı
        userInterface .setSize(450, 250); //boyut
        userInterface .setModal(true); //cant close until you finish with it
        userInterface .setResizable(false); //kenardan tutup büyütemezsin
        userInterface .setLocationRelativeTo(null); //merkezde ortaya çıkması için
        userInterface .setVisible(true);
        
	}

}
