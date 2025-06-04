import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GameScreen extends JFrame{
	private	JMenuBar menubar;
	private JMenu gameMenu, optionsMenu, difficultyMenu ,helpMenu;
	private JMenuItem quitItem, historyItem, highScoreItem, beginnerItem ,noviceItem, intermediateItem, advencedItem, aboutItem;
	public GamePanel gamePanel;
	
	public GameScreen(String username) {
		
		menubar = new JMenuBar();
		
		gameMenu = new JMenu("GAME");
		optionsMenu = new JMenu("OPTIONS");
		helpMenu = new JMenu("HELP");
		difficultyMenu = new JMenu("DIFFICULTY");
		
		quitItem = new JMenuItem("Quit");
		historyItem = new JMenuItem("History");
		highScoreItem = new JMenuItem("High Score List");
		
		beginnerItem = new JMenuItem("Beginner");
		noviceItem = new JMenuItem("Novice");
		intermediateItem = new JMenuItem("Intermediate");
		advencedItem = new JMenuItem("Advenced");
		
		aboutItem = new JMenuItem("About");
        
		gameMenu.add(quitItem);
		
		optionsMenu.add(historyItem);
		optionsMenu.add(highScoreItem);
		optionsMenu.add(difficultyMenu);
		
		difficultyMenu.add(beginnerItem);
		difficultyMenu.add(noviceItem);
		difficultyMenu.add(intermediateItem);
		difficultyMenu.add(advencedItem);
		
		helpMenu.add(aboutItem);
        
        menubar.add(gameMenu);
        menubar.add(optionsMenu);
        menubar.add(helpMenu);
        
        setJMenuBar(menubar);
        
        MenuActionHandler handler = new MenuActionHandler();

        quitItem.addActionListener(handler);
        historyItem.addActionListener(handler);
        highScoreItem.addActionListener(handler);
        beginnerItem.addActionListener(handler);
        noviceItem.addActionListener(handler);
        intermediateItem.addActionListener(handler);
        advencedItem.addActionListener(handler);
        aboutItem.addActionListener(handler);
        
        gamePanel = new GamePanel(username);
        add(gamePanel);
        
        setTitle("PANG");
        setSize(GamePanel.backgroundWidth * 3, 840);
        setLocationRelativeTo(null);	
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
	}
	
	private class MenuActionHandler implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	String type = e.getActionCommand();

	    	switch(type) {
	    		case "Quit":
	    			System.exit(0);
	    			break;
	    			
	    		case "History":
	    			try {
	    		        File logFile = new File("scorelog.txt");
	    		        if (!logFile.exists()) {
	    		            JOptionPane.showMessageDialog(GameScreen.this, "No log file found.");
	    		            break;
	    		        }
	    		        Desktop.getDesktop().open(logFile);
	    		    } catch (IOException ex) {
	    		        ex.printStackTrace();
	    		        JOptionPane.showMessageDialog(GameScreen.this, "Failed to open log file.");
	    		    }
	    			break;
	    			
	    		case "High Score List":
	    			HighScoreList test = new HighScoreList();
	    			test.highScore();
	    			test.highScore2();
	    			break;
	    			
	    		case "Beginner":
	    			gamePanel.goToLevel(0);
	    			break;
	    			
	    		case "Novice":
	    			gamePanel.goToLevel(1);
	    			break;
	    			
	    		case "Intermediate":
	    			gamePanel.goToLevel(2);
	    			break;
	    			
	    		case "Advenced":
	    			gamePanel.goToLevel(3);
	    			break;
	    		
	    		case "About":
	    			setVisible(false);
	    			new AboutMenu(GameScreen.this);
	    			break;
	    	}
	    }
	}
}	
    