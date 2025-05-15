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
        JFrame dummyFrame = new JFrame(); // optional: used as a parent
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // just in case
        dummyFrame.setSize(0, 0); // invisible
        dummyFrame.setVisible(true); // needed to center dialog on it (optional)

        new UserRecognition2(null); // run your dialog

	}

}
