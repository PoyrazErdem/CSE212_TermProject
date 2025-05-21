import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HighScoreList {
	
	static final String path = "High_Score_List\\High_Score_List.txt";
	
	public void highScore() {
		String[] scores = {
	            "1st    100000    M.S    20STAGE",
	            "2nd     80000    M.K    18STAGE",
	            "3rd     70000    T.U    17STAGE",
	            "4th     60000    K.H    15STAGE",
	            "5th     50000    Y.N    13STAGE",
	            "6th     40000    Y.K    12STAGE",
	            "7th     30000    Y.F    11STAGE",
	            "8th     20000    M.N    10STAGE",
	            "9th     10000    M.M     5STAGE",
	            "10th     5000    J.O     3STAGE"
		};
		
		try {
			FileWriter writer = new FileWriter(path); // file'a yazar
			for(String score : scores) {
				writer.write(score + "\n");
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void highScore2() {
		String list = "";
		
		try {
			 BufferedReader reader = new BufferedReader(new FileReader(path));	
			 String line;
			 while((line = reader.readLine()) != null) {
				 list += line + "\n";
			 }
			 reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	    
		JOptionPane.showMessageDialog(null, list, "High Score List", JOptionPane.PLAIN_MESSAGE);
	}
}
