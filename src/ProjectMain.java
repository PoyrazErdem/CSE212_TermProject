import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ProjectMain {

	public static void main(String[] args) {
		ArrayList<User> list = UserManegment.Load();
		for(User user : list) {
			System.out.println(user.getUserName());
		}
		/*
		SwingUtilities.invokeLater(() -> {
		    JFrame dummy = new JFrame();
		    new UserRecognition(dummy);
		});
		*/
        new UserRecognition2();
	}

}
