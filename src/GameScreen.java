import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameScreen extends JFrame{
	private	JMenuBar menubar;
	private JMenu gameMenu, optionsMenu, difficultyMenu ,helpMenu;
	private JMenuItem quitItem, historyItem, highScoreItem, begginerItem ,noviceItem, intermediateItem, advencedItem, aboutItem;
	 GamePanel gamePanel = new GamePanel();
	
	public GameScreen() {
		
		menubar = new JMenuBar();
		
		gameMenu = new JMenu("GAME");
		optionsMenu = new JMenu("OPTIONS");
		helpMenu = new JMenu("HELP");
		difficultyMenu = new JMenu("DIFFICULTY");
		
		quitItem = new JMenuItem("Quit");
		historyItem = new JMenuItem("History");
		highScoreItem = new JMenuItem("High Score List");
		
		begginerItem = new JMenuItem("Begginer");
		noviceItem = new JMenuItem("Novice");
		intermediateItem = new JMenuItem("Intermediate");
		advencedItem = new JMenuItem("Advenced");
		
		aboutItem = new JMenuItem("About");
        
		gameMenu.add(quitItem);
		
		optionsMenu.add(historyItem);
		optionsMenu.add(highScoreItem);
		optionsMenu.add(difficultyMenu);
		
		difficultyMenu.add(begginerItem);
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
        begginerItem.addActionListener(handler);
        noviceItem.addActionListener(handler);
        intermediateItem.addActionListener(handler);
        advencedItem.addActionListener(handler);
        aboutItem.addActionListener(handler);
        
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
	    			break;
	    			
	    		case "High Score List":
	    			HighScoreList test = new HighScoreList();
	    			test.highScore();
	    			test.highScore2();
	    			break;
	    			
	    		case "Begginer":
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

	    
	    
	    
	    
	    
	    
	    
	    