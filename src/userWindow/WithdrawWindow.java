package userWindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WithdrawWindow
{
	JPanel button;
	JFrame window;
	JPanel lowerPanel;
	
	public WithdrawWindow()
	{
		createWithdrawWindow();
	}
	
	public void createWithdrawWindow()
	{
		// Creates Jframe window
		window = new JFrame("Bank: Withdraw Money ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates main Jpanel 
		button= new JPanel();
		button.setLayout(new GridLayout(4,2));	
		window.add(button, BorderLayout.CENTER);
		
		// Creates lower panel for return
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,2));
		window.add(lowerPanel, BorderLayout.SOUTH);
		
		// Creates objects which fill the panels
		createWithdrawWindowObjects();
		
		window.setSize(400,400);
		window.setVisible(true);
	}
	
	public void createWithdrawWindowObjects()
	{
		EventListenerMainWindow buttonListener = new EventListenerMainWindow(window);
		
		// ----------------- CENTRE PANEL
		JLabel account = new JLabel("Account Number: ");
		JTextField accountField = new JTextField("");
		
		JLabel amount = new JLabel("Amount: ");
		JTextField amountField = new JTextField("");
		
		JButton enterRequest = new JButton("Submit");
		
		JLabel balance = new JLabel("Balance: ");
		JTextField balanceField = new JTextField("");
		
		button.add(account);
		button.add(accountField);
		button.add(amount);
		button.add(amountField);
		button.add(enterRequest);
		button.add(new JLabel(""));
		button.add(balance);
		button.add(balanceField);
		
		enterRequest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Error checking to see if the accoutn number that has been input is valid
				int IDLength = 0;
				ResultSet IDList = new BankSQLConnection().getIds();

				try 
				{
					// Gets the length of the account number list
					while(IDList.next())
					{
						IDLength = IDList.getInt(1);
					}
					 // If the chosen account is less than the  length
					// i.e within the range of actual accounts then the deposit function is created
					if (Integer.parseInt(accountField.getText()) <= IDLength)
					{
						new BankSQLConnection(Integer.parseInt(accountField.getText()), Integer.parseInt(amountField.getText()), 3, balanceField);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The account number you have entered does not appear in our records. ");
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}										);
		
		// -------------------LOWER PANEL
		JButton goBack = new JButton("Return");
		
		lowerPanel.add(goBack);
		
		goBack.addActionListener(buttonListener);
		
	}
}

