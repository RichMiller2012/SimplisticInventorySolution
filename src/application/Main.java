package application;
	

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.JdbcConnector;
import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		JdbcConnector conn = new JdbcConnector();
		conn.connect();
		
		/*SessionFactory se = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(InventoryTO.class)
				.addAnnotatedClass(SoldItemTO.class)
				.addAnnotatedClass(TransactionsTO.class)
				.buildSessionFactory();
	
		
		InventoryTO startItem = new InventoryTO();
		startItem.setName("Start Item");
		startItem.setBarcode("4321");
		startItem.setQuantity(40);
		startItem.setWholesalePrice(1.00);
		startItem.setRetailPrice(2.00);
		
		SoldItemTO sellItem1 = new SoldItemTO();
		sellItem1.setSoldItem(startItem);
		sellItem1.setQuantity(1);
		
		SoldItemTO sellItem2 = new SoldItemTO();
		sellItem2.setSoldItem(startItem);
		sellItem2.setQuantity(1);
		
		TransactionsTO transaction = new TransactionsTO();
		transaction.getSoldItems().add(sellItem1);
		transaction.getSoldItems().add(sellItem2);
		sellItem1.setTransaction(transaction);
		sellItem2.setTransaction(transaction);
		transaction.setTotal(100.00);

		Session session = se.openSession();
		try {
		
		
		session.beginTransaction();
		//session.save(startItem);
		//session.save(sellItem1);
		//session.save(sellItem2);
		session.save(transaction);
		
		session.getTransaction().commit();
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
*/

		
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("../fxml/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("css/application.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
