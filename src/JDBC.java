import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC 
{
	public static void main(String[] args)
	{
		try
		{
			// Needed to create a class for the mysql/jdcb driver to communicate between the two
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Sets up the connection for the server, security checks have been added on the end
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/movielens?autoReconnect=true&useSSL=false", "root", "Rugby123");
			//Creates a statement class to retrieve date, update class is used to edit data
			Statement statement = connection.createStatement();
			// creates a string containing the SQL query
			String sqlStatement = "SELECT id, title, release_date FROM movies";
			// Sends the SQL Query
			ResultSet results = statement.executeQuery(sqlStatement);
			
			// While loop gets the next int, string etc whilst there is a next line(next())
			while (results.next())
			{
				System.out.println("Movie id: " + results.getInt("id"));
				System.out.println("Movie title: " + results.getString("title"));
				System.out.println("Release date: " + results.getDate("release_date"));
			}
			
			sqlStatement = "INSERT INTO movies (id, title, release_date) VALUES (1683, 'The second cumming of the meat rubber', '2018-10-24')";
			
			statement.executeUpdate(sqlStatement);
			
			// Closes all the connections
			results.close();
			statement.close();
			connection.close();
		}
		
		// Catches are needed for this function otherwise it will not compile
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Type it in right you fucking nonce");
		} 
		
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Wrong class name you nonce ");
		}
	}
}
