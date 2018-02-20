package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnector {

	public void connect() {
		
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/sis?useSSL=false";
		String user = "sisuser";
		String pass = "sisuser";
		
		try {
			System.out.println("Connecting to database " + jdbcUrl);
			Connection myconn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			System.out.println("Connectoin success");
			myconn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
