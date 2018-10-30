package userWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class EventListenerMainWindow implements ActionListener 
{
	// The refrence of the current window is passed into this actionListner
	// this actionlistener onyl controls the flow between different windows
	// When a new window opens the previos one is closed
	JFrame windowToBeClosed;
	EventListenerMainWindow(JFrame window)
	{
		windowToBeClosed = window;
	}
	
	EventListenerMainWindow()
	{
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();
		String btnValue = btn.getText();
		
		if (btnValue.equals("Create Account"))
		{
			CreateAccountWindow createAcObj = new CreateAccountWindow();
			windowToBeClosed.dispose();
		}
		if (btnValue.equals("Deposit Money"))
		{
			DepositWindow depositObj = new DepositWindow();
			windowToBeClosed.dispose();
		}
		if (btnValue.equals("Withdraw Money"))
		{
			WithdrawWindow withdrawObj = new WithdrawWindow();
			windowToBeClosed.dispose();
		}
		if (btnValue.equals("Account Statement"))
		{
			StatementWindow statementObj = new StatementWindow();
			windowToBeClosed.dispose();
		}
		if (btnValue.equals("Return"))
		{
			MainUserWindow mainObj = new MainUserWindow();
			windowToBeClosed.dispose();
		}
	}
	
}
