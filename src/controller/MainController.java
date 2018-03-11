package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController implements Initializable {

	@FXML InventoryController inventoryController;
	
	@FXML ReceivingController receivingController;
	
	@FXML
	private TabPane mainTabPane;
	
	@FXML
	private Tab receivingTab;
	
	@FXML
	private Tab inventoryTab;
	
	@FXML
	private Tab posTab;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inventoryController.init(this);
		receivingController.init(this);	
	}
	
	public void receivingTabSelected() {
		if(receivingTab.isSelected()) {
			System.out.println("Receiving Tab Selected");
			receivingController.reselected();
		}
	}
	
	public void inventoryTabSelected() {
		if(inventoryTab.isSelected()) {
			System.out.println("Inventory Tab Selected");
			inventoryController.initColumns();
		}
	}
	
}
