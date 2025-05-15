import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public abstract class UserManegment {
	
	static final String path = "UserData\\UserData.txt"; // text file'a giden yolun
	
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void Save(ArrayList<User> list) {
		try {
			FileWriter writer = new FileWriter(path); // file'a yazar
			for(User user : list) {
				writer.write(user.getUserName() + "," + user.getPassword());
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}