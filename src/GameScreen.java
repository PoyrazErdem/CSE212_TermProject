import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameScreen extends JFrame{
	private	JMenuBar menubar;
	private JMenu gameMenu, optionsMenu, difficultyMenu ,helpMenu;
	private JMenuItem quitItem, historyItem, highScoreItem, noviceItem, intermediateItem, advencedItem, aboutItem;
	
	public GameScreen() {
		
		menubar = new JMenuBar();
		
		gameMenu = new JMenu("GAME");
		optionsMenu = new JMenu("OPTIONS");
		helpMenu = new JMenu("HELP");
		difficultyMenu = new JMenu("DIFFICULTY");
		
		quitItem = new JMenuItem("Quit");
		historyItem = new JMenuItem("History");
		highScoreItem = new JMenuItem("Highest Scores");
		noviceItem = new JMenuItem("Novice");
		intermediateItem = new JMenuItem("Intermediate");
		advencedItem = new JMenuItem("Advenced");
		aboutItem = new JMenuItem("About");
        
		gameMenu.add(quitItem);
		
		optionsMenu.add(historyItem);
		optionsMenu.add(highScoreItem);
		optionsMenu.add(difficultyMenu);
		
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
        noviceItem.addActionListener(handler);
        intermediateItem.addActionListener(handler);
        advencedItem.addActionListener(handler);
        aboutItem.addActionListener(handler);
        
        setTitle("PANG");
        setSize(1300,900);
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
	    			
	    		case "History":
	    			
	    			
	    		case "Highest Scores":
	    			HighScoreList test = new HighScoreList();
	    			test.highScore();
	    			test.highScore2();
	    			break;
	    			
	    		case "Novice":
	    			
	    			
	    		case "Intermediate":
	    			
	    			
	    		case "Advenced":
	    			
	    		
	    		case "About":
	    			setVisible(false);
	    			new AboutMenu(GameScreen.this);
	    			break;
	    	}
	    }
	}
}	

	    
	    
	    
	    
	    
	    
	    
	    