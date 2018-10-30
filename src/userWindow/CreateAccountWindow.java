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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateAccountWindow
{
	JPanel button;
	JFrame window;
	JPanel lowerPanel;
	
	public CreateAccountWindow()
	{
		createAccountWindow();
	}
	
	public void createAccountWindow()
	{
		// Creates Jframe window
		window = new JFrame("Bank: Create Account ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates Jpanel 
		button= new JPanel();
		button.setLayout(new GridLayout(4,2));	
		window.add(button, BorderLayout.CENTER);
		
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,2));
		window.add(lowerPanel, BorderLayout.SOUTH);
		
		createAccountWindowObjects();
		
		window.setSize(400,400);
		window.setVisible(true);
	}
	
	public void createAccountWindowObjects()
	{
		EventListenerMainWindow buttonListener = new EventListenerMainWindow(window);
		
		//-------------------CENTRE PANEL
		JLabel name = new JLabel("Name: ");
		JTextField nameField = new JTextField("");
		
		JLabel address = new JLabel("Address: ");
		JTextField addressField = new JTextField("");
	
		JButton submit = new JButton("Submit");
		JLabel filler = new JLabel("");
		
		JLabel AcNoLabel = new JLabel("Your new account number");
		JTextField AcNo = new JTextField("");
		
		button.add(name);
		button.add(nameField);
		button.add(address);
		button.add(addressField);
		button.add(submit);
		button.add(filler);
		button.add(AcNoLabel);
		button.add(AcNo);
		
		submit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					BankSQLConnection SQLObj = new BankSQLConnection(nameField.getText(), addressField.getText());
					
					ResultSet IDList = SQLObj.getIDCount();
					
					int IDLength = 0;
					// Gets the length of the account number list
					try 
					{
						while(IDList.next())
						{
							IDLength = IDList.getInt(1);
						}
					} 
					catch (SQLException e1)
					{
						e1.printStackTrace();
					}
					
					AcNo.setText(Integer.toString(IDLength));
				}
			}										);
		
		// -------------------LOWER PANEL
		JButton goBack = new JButton("Return");
		
		lowerPanel.add(goBack);
		
		goBack.addActionListener(buttonListener);
	}
}

