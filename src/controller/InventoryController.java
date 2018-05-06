package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import model.AlertButtonContext;
import model.Inventory;
import service.InventoryService;

public class InventoryController implements Initializable {
	
	private MainController main;
	
	@FXML
	private TreeTableView<Inventory> inventoryTreeTable;
	
	@FXML
	private TreeTableColumn<Inventory, Number> id;
	
	@FXML 
	private TreeTableColumn<Inventory, String> name;
	
	@FXML 
	private TreeTableColumn<Inventory, String> barcode;
	
	@FXML
	private TreeTableColumn<Inventory, Number> quantity;
	
	@FXML 
	private TreeTableColumn<Inventory, Double> wholesalePrice;
	
	@FXML 
	private TreeTableColumn<Inventory, Double> retailPrice;
	
	private InventoryService service = new InventoryService();

	private TreeItem<Inventory> root = new TreeItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0, 0));
	
	private ObservableList<TreeItem<Inventory>>	items = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL aurlrg0, ResourceBundle rb) {		
		initColumns();
	}
	
	public void init(MainController mainController) {
		main = mainController;
	}
	
	public void initColumns() {
		//get this from a database
		items = service.fetchInventory();
		
		root.getChildren().setAll(items);
		
		id.setCellValueFactory(idCol -> idCol.getValue().getValue().getId());
		name.setCellValueFactory(valueCol -> valueCol.getValue().getValue().getName());
		barcode.setCellValueFactory(barcodeCol -> barcodeCol.getValue().getValue().getBarcode());
		quantity.setCellValueFactory(quantityCol -> quantityCol.getValue().getValue().getQuantity());
		wholesalePrice.setCellValueFactory(wp -> wp.getValue().getValue().getWholesalePrice().asObject());
		retailPrice.setCellValueFactory(rp -> rp.getValue().getValue().getRetailPrice().asObject());
		
		inventoryTreeTable.setRoot(root);
		inventoryTreeTable.setShowRoot(false);	
	}
}
