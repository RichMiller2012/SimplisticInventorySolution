package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import entity.InventoryTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Inventory;
import service.InventoryService;
import util.Seed;
import model.AlertButtonContext;

public class MainController implements Initializable {

	@FXML InventoryController inventoryController;
	
	@FXML ReceivingController receivingController;
	
	@FXML ReportingController reportingController;
	
	@FXML PointOfSaleController pointOfSaleController;
	
	@FXML CalculatorController calculatorController;
	
	@FXML
	private TabPane mainTabPane;
	
	@FXML
	private Tab receivingTab;
	
	@FXML
	private Tab inventoryTab;
	
	@FXML
	private Tab posTab;
	
	@FXML
	private Tab reportsTab;
	
	@FXML
	private Button warningButton;
	
	private Map<String, InventoryTO> lowLevelAertItems = new HashMap<>();
	
	private InventoryService inventoryService = new InventoryService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
//		Seed seed = new Seed();
//		seed.seed();
		
		//use inventory service to find all currently low items
		for(InventoryTO lowItem : inventoryService.findLowItems()) {
			lowLevelAertItems.put(lowItem.getBarcode(), lowItem);
		}
		
		inventoryController.init(this);
		receivingController.init(this);	
		reportingController.init(this);
		pointOfSaleController.init(this);
		
		evaluateWarningButton();
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
	
	public void reportsTabSelected() {
		if(reportsTab.isSelected()) {
			System.out.println("Reports Tab Selected");
			reportingController.initData();
		}
	}
	
	public void posTabSelected() {
		if(posTab.isSelected()) {
			System.out.println("POS Tab Selected");
			pointOfSaleController.reselected();
		}
	}
	
	public boolean alertMapContainsItem(String barcode) {
		return lowLevelAertItems.containsKey(barcode);
	}
	
	public void updateLowItem(InventoryTO item) {
		lowLevelAertItems.put(item.getBarcode(), item);
		evaluateWarningButton();
	}
	
	public void removeLowItem(String barcode) {
		lowLevelAertItems.remove(barcode);
		evaluateWarningButton();
	}
	
	private void evaluateWarningButton() {
		if(lowLevelAertItems.isEmpty()) {
			warningButton.setVisible(false);
		} else {
			warningButton.setVisible(true);
		}
	}
	
	public void displayLowItems() {
		
		if(lowLevelAertItems.isEmpty()){
			return;
		}
		//load a new window with the list of low alert items
		try {		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../fxml/alertWindow.fxml"));
			Parent root = loader.load();
			LowInventoryAlertController controller = loader.getController();
			controller.initData(lowLevelAertItems);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add("css/styles.css");
			Stage stage = new Stage();
			stage.setTitle("Low Inventory Items");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	
	
}
