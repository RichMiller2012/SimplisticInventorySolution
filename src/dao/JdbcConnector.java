package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnector {

	public void connect() {
		
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/sisy";
		String user = "sisuser";
		String pass = "sisuser";
		
		try {
			System.out.print("Connecting to database " + jdbcUrl);
			Connection myconn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			System.out.println("Connectoin success");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
