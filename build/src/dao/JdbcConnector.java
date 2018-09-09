package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnector {

	public void connect() {
		
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/sis?useSSL=false";
		String user = "root";
		String pass = "password";
		
		try {
			System.out.println("Connecting to database " + jdbcUrl);
			Connection myconn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			System.out.println("Connection success");
			myconn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
