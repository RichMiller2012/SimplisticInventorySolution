package application;
	
import dao.JdbcConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import print.Print;
import util.Seed;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {

		JdbcConnector conn = new JdbcConnector();
		conn.connect();	
		
		//Print.printTransaction("Transaction details here");
	
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("../fxml/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("css/application.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Seed.seed();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
