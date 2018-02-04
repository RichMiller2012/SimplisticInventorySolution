package controller;

import java.net.URL;
import java.util.ResourceBundle;
import dto.Inventory;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import service.InventoryService;

public class InventoryController implements Initializable {
	

	InventoryService inventoryService = new InventoryService();
	
	@FXML
	private JFXTreeTableView<Inventory> inventoryTreeView;

	@Override
	public void initialize(URL aurlrg0, ResourceBundle rb) {
		JFXTreeTableColumn<Inventory, Long> id = new JFXTreeTableColumn<>("Item ID");
		JFXTreeTableColumn<Inventory, String> itemName = new JFXTreeTableColumn<>("Item Name");
		JFXTreeTableColumn<Inventory, String> barcode = new JFXTreeTableColumn<>("Barcode");
		JFXTreeTableColumn<Inventory, Integer> quantity = new JFXTreeTableColumn<>("Quantity");
		JFXTreeTableColumn<Inventory, Double> wholesalePrice = new JFXTreeTableColumn<>("Wholesale Price");
		JFXTreeTableColumn<Inventory, Double> retailPrice = new JFXTreeTableColumn<>("Retail Price");
		
		id.setPrefWidth(200);
		id.setCellValueFactory(idData -> idData.getValue().getValue().getId().asObject());
		
		itemName.setPrefWidth(200);
		itemName.setCellValueFactory(nameData -> nameData.getValue().getValue().getName());
		
		barcode.setPrefWidth(200);
		barcode.setCellValueFactory(barcodeValue -> barcodeValue.getValue().getValue().getBarcode());
		
		quantity.setPrefWidth(200);
		quantity.setCellValueFactory(quant -> quant.getValue().getValue().getQuantity().asObject());
		
		wholesalePrice.setPrefWidth(200);
		wholesalePrice.setCellValueFactory(wp -> wp.getValue().getValue().getWholesalePrice().asObject());
		
		retailPrice.setPrefWidth(200);
		retailPrice.setCellValueFactory(rp -> rp.getValue().getValue().getRetailPrice().asObject());
			
		
		ObservableList<Inventory> inventory = InventoryService.createStubInventoryItems();
		
		final TreeItem<Inventory> root = new RecursiveTreeItem<Inventory>(inventory, RecursiveTreeObject::getChildren);
		inventoryTreeView.getColumns().setAll(id, itemName, barcode, quantity, wholesalePrice, retailPrice);
		inventoryTreeView.setRoot(root);
		inventoryTreeView.setShowRoot(false);

	}

}
