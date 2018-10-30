package userWindow;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainUserWindow 
{
	JPanel button;
	JFrame window;
	
	public static void main(String[] args)
	{
		MainUserWindow obj = new MainUserWindow();
	}
	public MainUserWindow()
	{
		createMainWindow();
	}
	
	public void createMainWindow()
	{
		// Creates Jframe window
		window = new JFrame("Bank: Main page ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates Jpanel 
		button= new JPanel();
		button.setLayout(new GridLayout(4,1));	
		window.add(button, BorderLayout.CENTER);
		
		createMainButtons();
		
		window.setSize(400,400);
		window.setVisible(true);
	}
	
	public void createMainButtons()
	{
		// Created but
		JButton createAc = new JButton("Create Account");
		JButton deposit = new JButton("Deposit Money");
		JButton withdraw = new JButton("Withdraw Money");
		JButton accountStatement = new JButton("Account Statement");
		
		button.add(createAc);
		button.add(deposit);
		button.add(withdraw);
		button.add(accountStatement);
		
		
		EventListenerMainWindow buttonListener = new EventListenerMainWindow(window);
		
		createAc.addActionListener(buttonListener);
		deposit.addActionListener(buttonListener);
		withdraw.addActionListener(buttonListener);
		accountStatement.addActionListener(buttonListener);
	}
}
