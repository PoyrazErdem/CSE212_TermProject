import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class UserManegment {
	
	static final String path = "UserData\\UserData.txt"; // text file'a giden yolun
	
	static {
	    User.users = Load();  // Load previously saved users at program startup
	}
	
	public static ArrayList<User> Load() {
		ArrayList<User> list = new ArrayList<>(); // file'a yüklemek için için arraylist oluşturulur
		File userFile = new File(path); // arraylistin içindeki username ve password bu file'a atılacak
		try {
			Scanner scanner = new Scanner(userFile); // oluşturulan file'ı satır satır gezmek için oluşturuldu
			while(scanner.hasNext()) {
				String line = scanner.nextLine(); // satırın birine line diyoruz
				String[] words = line.split(","); // virgül görünce satırı ikiye böl
				if(words.length == 2) {
					User user = new User(words[0],words[1]);
					list.add(user);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void Save(ArrayList<User> list) {
		try {
			FileWriter writer = new FileWriter(path); // file'a yazar
			for(User user : list) {
				writer.write(user.getUserName() + "," + user.getPassword()+ "\n");
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void Login(String username, String password) throws WrongPasswordException{
		for(User a : User.users){
			if(a.getUserName().equals(username)) {  // if a username matches anyone on the list
				if(!a.getPassword().equals(password)) { // but the password doesnt match the name or the user accidentally 
					                                    // typed the wrong user name but somehow still checked in as a different user
					throw new WrongPasswordException("The password and the username doesn't match, please try again");
				}
				return;
			}
		}
		Register(username, password); // if no username is being matched with given username register this user as a new player
	}
	
	public static void Register(String username, String password) {
		User user = new User(username,password);
		User.users.add(user);
		Save(User.users);
	}
}



























