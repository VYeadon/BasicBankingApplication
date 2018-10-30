package userWindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DetailedStatementWindow 
{
	JPanel panel;
	JPanel upperPanel;
	JPanel lowerPanel;
	JFrame window;
	
	public DetailedStatementWindow()
	{
		createStatementWindow();
	}
	
	public void createStatementWindow()
	{
		// Creates Jframe window
		window = new JFrame("Detailed Bank Statement ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates main Jpanel 
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));	
		window.add(panel, BorderLayout.CENTER);
		
		// Creating upper panel containg options to choose account
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(2,2));
		window.add(upperPanel, BorderLayout.NORTH);
		
		// Lower pane holds the return option
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,2));
		window.add(lowerPanel, BorderLayout.SOUTH);
		
		createStatementWindowObjects();
		
		window.setSize(400,400);
		window.setVisible(true);
	}
	
	public void createStatementWindowObjects()
	{
		//JTextArea statement = new JTextArea()
	}
	
}
