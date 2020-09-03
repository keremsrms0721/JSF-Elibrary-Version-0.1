package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:mysql://localhost:3306/elibrary?serverTimezone=UTC";
		String admin = "root";
		String password = "";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connect = DriverManager.getConnection(url,admin,password);
		return connect;
	}
	
}
