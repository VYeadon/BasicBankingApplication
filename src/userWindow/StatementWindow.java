package userWindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatementWindow
{
	JPanel panel;
	JPanel upperPanel;
	JPanel lowerPanel;
	JFrame window;
	
	public StatementWindow()
	{
		createStatementWindow();
	}
	
	public void createStatementWindow()
	{
		// Creates Jframe window
		window = new JFrame("Bank Statement ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates main Jpanel 
		panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));	
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
		EventListenerMainWindow buttonListener = new EventListenerMainWindow(window);
		
		//-----------------------UPPER PANEL
		// Creates a label an an empty label to format the window
		JLabel accountNo = new JLabel("Account number: ");
		JLabel accountNoFill = new JLabel("");
		
		// Fills an array with the account numbers used to populate the comboBox
		ArrayList<String> AcNoList = populateIDList();

		JTextField accountField = new JTextField("");
		
		// Creates a new JComboBox which will hold strings
		// Adds the account nubmers to the ComboBox by converting from an ArrayList to an array
		JComboBox<String> accountFieldDropDown = new JComboBox(AcNoList.toArray());
		
		upperPanel.add(accountNo);
		upperPanel.add(accountNoFill);
		upperPanel.add(accountField);
		upperPanel.add(accountFieldDropDown);
		
		//------------------------MAIN BODY
		JLabel name = new JLabel("Name: ");
		JTextField nameField = new JTextField("");
		
		JLabel address = new JLabel("Address: ");
		JTextField addressField = new JTextField("");
		
		JLabel balance = new JLabel("Balance: ");
		JTextField balanceField = new JTextField("");
		
		JButton enterRequest = new JButton("Submit");
		JButton enterDetailed = new JButton("Detailed account statement");
		
		panel.add(name);
		panel.add(nameField);
		panel.add(address);
		panel.add(addressField);
		panel.add(balance);
		panel.add(balanceField);
		panel.add(enterRequest);
		panel.add(enterDetailed);
		
		enterRequest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try 
				{
					// If the user has input an account number in it uses that value
					if(!accountField.getText().isEmpty())
					{
						ResultSet IDList = new BankSQLConnection().getIds();
						int IDLength = 0;
						// Gets the length of the account number list
						while(IDList.next())
						{
							IDLength = IDList.getInt(1);
						}
						
						// if input ID is not in the records throw an error
						if(Integer.parseInt(accountField.getText()) > IDLength)
						{
							throw new AccountNumberOutofRange();
						}
						// Throws error as 0 is not a valid account id
						if(Integer.parseInt(accountField.getText()) == 0)
						{
							throw new AccountNumberOutofRange();
						}
							
						new BankSQLConnection(Integer.parseInt(accountField.getText()), nameField, addressField, balanceField);	
					}
					
					try
					{
						// Checks if user has selected an account number as opposed to formatting options of the ComboBox
						int value = Integer.parseInt((String)accountFieldDropDown.getSelectedItem());
						
						// If the selected ComboBox option can be converted into an int it is parsed and the results are sent back to the text box
						accountField.setText(Integer.toString(value));
						
						new BankSQLConnection(value, nameField, addressField, balanceField);
					}
					catch (NumberFormatException e1)
					{
						// Doesnt throw anything as user may have entered a value within the text box
					} 
				} 
				catch (SQLException e2) 
				{
					//e2.printStackTrace();
				} 
				catch (AccountNumberOutofRange e3) 
				{
					JOptionPane.showMessageDialog(null, "The account number you have entered does not appear in our records. ");
					//e3.printStackTrace();
				}
			}
		});	
		
		enterDetailed.addActionListener(new ActionListener()
			{
			public void actionPerformed(ActionEvent e)
			{
				DetailedStatementWindow detailedObj = new DetailedStatementWindow();
			}
	
			});
		
		// -------------------LOWER PANEL
		JButton goBack = new JButton("Return");
		
		lowerPanel.add(goBack);
		
		
		
		goBack.addActionListener(buttonListener);
	}
	
	public ArrayList<String> populateIDList()
	{
		ArrayList<String> AccountNoList = new ArrayList<String>();
		AccountNoList.add("Please choose an account number");
		AccountNoList.add("_______________________________");
		
		BankSQLConnection getIDSQL = new BankSQLConnection();
		ResultSet results = getIDSQL.getIds();
		
		try 
		{
			while (results.next())
			{
				AccountNoList.add(Integer.toString(results.getInt("Account_number")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// Connection must be closed after the results have been added to the combo box
		// Result set cannot be accesed after connection has been closed
		getIDSQL.closeSQLConnection();
		
		return AccountNoList;
	}

	public void listDepositWithdrawls()
	{
		
	}
}

