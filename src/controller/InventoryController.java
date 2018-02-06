package controller;

import java.net.URL;
import java.util.ResourceBundle;
import dto.Inventory;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import service.InventoryService;

public class InventoryController implements Initializable {
	
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

	
	TreeItem<Inventory> root = new TreeItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0));
	
	@Override
	public void initialize(URL aurlrg0, ResourceBundle rb) {	
		ObservableList<TreeItem<Inventory>>	items = InventoryService.createStubInventoryItems();
		
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
