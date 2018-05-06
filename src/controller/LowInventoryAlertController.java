package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import entity.InventoryTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import model.Inventory;

public class LowInventoryAlertController implements Initializable{

	@FXML
    private TreeTableView<Inventory> itemTable;

    @FXML
    private TreeTableColumn<Inventory, String> itemCol;

    @FXML
    private TreeTableColumn<Inventory, Integer> quantityCol;

    @FXML
    private Button printButton;
    
	private ObservableList<TreeItem<Inventory>>	items = FXCollections.observableArrayList();
	private TreeItem<Inventory> root = new TreeItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0, 0));
	private Map<String, InventoryTO> lowItems;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		itemCol.setCellValueFactory(item -> item.getValue().getValue().getName());
		quantityCol.setCellValueFactory(quantity -> quantity
				.getValue().getValue().getDoubleLowLevelAlert().asObject());
		
		itemTable.setRoot(root);
		itemTable.setShowRoot(false);
	}
	
	public void initData(Map<String, InventoryTO> lowItems) {
			//this.lowItems = lowItems;
			
			for(String item : lowItems.keySet()) {
				TreeItem<Inventory> treeItem = new TreeItem<>(new Inventory(lowItems.get(item)));
				items.add(treeItem);
			}
			
			root.getChildren().setAll(items);
	}
}
