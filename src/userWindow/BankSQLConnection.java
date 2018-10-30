package userWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BankSQLConnection
{
	Connection connection;
	Statement statement;
	ResultSet results;
	String SQLStatement;
	
	int AcNo;
	String name;
	String address;
	int amount;
	
	// Constructor used when creating an account
	BankSQLConnection(String userName, String userAddress)
	{
		// these values are added during getSQLUpdate
		name = userName;
		address = userAddress;
		
		createConnectionStatement();
		getSQLUpdate(1);
		executeSQLUpdate();
		closeSQLConnection();
	}
	
	// Constructor used when depositing/withdrawing money
	// Account number dicates which account, userAmount is the amount to be withdrawn deposited
	// updateType dicates weather or not the user is withdrawing or deposting
	// balanceoutput is the field where the balance is to be outptted
	BankSQLConnection(int AccountNumber, int userAmount, int updateType, JTextField balanceOutput)
	{
		AcNo = AccountNumber;
		amount = userAmount;
		
		// Connection needs to be created so the total can be calcualted from queries
		createConnectionStatement();
		
		// Calcualtes the total balance of the user based on deposits and withdrawls
		int balance = calculateTotal(AccountNumber);
		// Dispalys the balance in the Text~Field
		balanceOutput.setText(Integer.toString(balance));
		
		// Checks to see whether the updateType is withdrawing or depsoting
		// ------------------DEPOSITING-------------------------------
		if (updateType == 2)
		{
			depositMoney(balance, userAmount, balanceOutput);
		}
		//-----------------WITHDRAWING-------------------------------
		if (updateType==3)
		{
			withdrawMoney(balance, userAmount, balanceOutput);
		}
		
	}

	// Constructor used to return bank statements of users
	BankSQLConnection(int AccountNumber, JTextField nameField, JTextField addressField, JTextField amountField)
	{
		createConnectionStatement();
		getSQLQuery(3, AccountNumber);
		executeSQLQuery();
		
		try 
		{
			//Must call results.next() to move onto records
			results.next();
			// Sets the values based on the results from  the query
			nameField.setText(results.getString("Name"));
			addressField.setText(results.getString("Address"));
			amountField.setText(Integer.toString(calculateTotal(AccountNumber)));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		closeSQLConnection();
	}
	
	// Blank Constructor
	BankSQLConnection()
	{
		
	}
	
	public ResultSet getIds()
	{
		createConnectionStatement();
		SQLStatement = "SELECT Account_number FROM account;";
		executeSQLQuery();
		return results;
	}
	
	public ResultSet getIDCount()
	{
		createConnectionStatement();
		SQLStatement = "SELECT COUNT(*) FROM account;";
		executeSQLQuery();
		return results;
	}
	
	public int calculateTotal(int id)
	{	
		// adds up the total deposited amounts
		getSQLQuery(1, id);
		executeSQLQuery();

		int totalDeposited = 0;
		
		try 
		{
			while (results.next())
			{
				totalDeposited += results.getInt("Amount");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// Adds up the total withdrawn amounts
		getSQLQuery(2, id);
		executeSQLQuery();
		
		int totalWithdrawn = 0;
		
		try 
		{
			while (results.next())
			{
				totalWithdrawn += results.getInt("Amount");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		int balance = totalDeposited - totalWithdrawn;
		return balance;

	}

	public void withdrawMoney(int balance, int userAmount, JTextField balanceOutput)
	{
		// Checks to see if the input is withdrawing
		// Checks whether there are sufficent funds inside the bank account
		if (balance < amount)
		{
			JOptionPane.showMessageDialog(null, "Sorry you have insufficent funds. ");
			closeSQLConnection();
		}
		// -----------------WITHDRAWING---------------------------------
		else
		{
			getSQLUpdate(3);
			executeSQLUpdate();
			// Dispalys the balance after the database has been updated
			balanceOutput.setText(Integer.toString(balance-userAmount));
			closeSQLConnection();
		}
	}
	
	public void depositMoney(int balance, int userAmount, JTextField balanceOutput)
	{
		getSQLUpdate(2);
		executeSQLUpdate();
		// Dispalys the balance after the database has been updated
		balanceOutput.setText(Integer.toString(balance+userAmount));
		closeSQLConnection();
	}
	
	//--------------------------FUNCTIONS TO CREATE, EXECUTE & CLOSE SQL-------------
	public void createConnectionStatement()
	{
		try 
		{
			// Needed to create a class for the mysql/jdcb driver to communicate between the two
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Sets up the connection for the server, security checks have been added on the end
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banks?autoReconnect=true&useSSL=false", "root", "Rugby123");
			//Creates a statement class to retrieve date, update class is used to edit data
			statement = connection.createStatement();
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("merd");
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void getSQLUpdate(int type)
	{
		// Case 1: Add user and details
		// Case 2: Deposit money to account
		// Case 3: Withdraw money from account
		switch(type)
		{
			case 1:
				SQLStatement = "INSERT INTO account (Name, Address) VALUES ('"  + name + "', '" +  address + "');";
				break;
			case 2:
				SQLStatement = "INSERT INTO Deposit VALUES (" + AcNo +  ", " + amount + ", '" + LocalDate.now() + "');";
				break;
			case 3:
				SQLStatement = "INSERT INTO Withdraw VALUES (" + AcNo +  ", " + amount + ", '" + LocalDate.now() + "');";	
				break;
		}
	}
	
	public void getSQLQuery(int type, int id)
	{
		// Case 1: Return updated balance
		// Case 2: Return bank statement
		// Case 3: Returns all information for one id
		switch(type)
		{
			case 1:
				SQLStatement = "SELECT amount FROM deposit WHERE Account_number = " + id + ";";
				break;
			case 2:
				SQLStatement = "SELECT amount FROM withdraw WHERE Account_number = " + id + ";";
				break;
			case 3:
				SQLStatement = "SELECT * FROM account WHERE Account_number = " + id + ";";

		}
	}

	public void executeSQLUpdate()
	{
		try 
		{
			statement.executeUpdate(SQLStatement);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void executeSQLQuery()
	{
		try 
		{
			results = statement.executeQuery(SQLStatement);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void closeSQLConnection()
	{
		try 
		{
			// results to be closed within the statement that creates them
			statement.close();
			connection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

	
}

