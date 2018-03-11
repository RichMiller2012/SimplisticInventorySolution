package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Inventory;
import service.InventoryService;

public class PointOfSaleController implements Initializable {

	private static final String TOTAL_TEXT = "Total: ";
	private static final String PHP = " PHP";
	
	private InventoryService inventoryService = new InventoryService();
	
    @FXML
    private TextField barcodeInput;
	
	@FXML
    private TreeTableView<Inventory> posTable;

    @FXML
    private Label posTotalLabel;
    
    @FXML
    private Button transactionButton;
    
    @FXML 
	private TreeTableColumn<Inventory, String> name;
	
	@FXML 
	private TreeTableColumn<Inventory, String> barcode;
	
	@FXML 
	private TreeTableColumn<Inventory, Double> retailPrice;
	
	private InventoryService service = new InventoryService();
    
	private TreeItem<Inventory> root = new TreeItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0));
	
	private ObservableList<TreeItem<Inventory>> sellings = FXCollections.observableArrayList(); 
		
	private Map<InventoryTO, SoldItemTO> inventorySellItemMap = new HashMap();

	private Double total = 0.00;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		root.getChildren().setAll(sellings);
		
		name.setCellValueFactory(nameCol -> nameCol.getValue().getValue().getName());
		barcode.setCellValueFactory(barcodeCol -> barcodeCol.getValue().getValue().getBarcode());
		retailPrice.setCellValueFactory(retCol -> retCol.getValue().getValue().getRetailPrice().asObject());
		
		posTable.setRoot(root);
		posTable.setShowRoot(false);
		
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);
	}
	
	public void addItemToSellList(KeyEvent event) {
		String itemBarcode = "";
		if(event.getCode() == KeyCode.ENTER) {
			itemBarcode = barcodeInput.getText();
		}
		
		if(itemBarcode.isEmpty()) {
			return;
		}
		
		System.out.println("recieved barcode: " + itemBarcode );
		
		InventoryTO selectedItem = service.findItemByBarcode(itemBarcode);
		
		if(selectedItem == null) {
			//code for item not found and 
		} else {
			root.getChildren().add(new TreeItem<>(new Inventory(selectedItem)));
			updateTotal(selectedItem.getRetailPrice());
			updateInventoryMap(selectedItem);
		}
		
		barcodeInput.setText("");
	}
	
	public void commitTransaction() {
		System.out.println("Beginning transaction commission");
		
		TransactionsTO transaction = new TransactionsTO();
		double transactionTotal = 0.00;
		for(InventoryTO item : inventorySellItemMap.keySet()) {
			SoldItemTO soldItem = inventorySellItemMap.get(item);
			soldItem.setSoldItem(item);
			transaction.getSoldItems().add(inventorySellItemMap.get(item));
			soldItem.setTransaction(transaction);
			
			transactionTotal += soldItem.getQuantity() * item.getRetailPrice();
		}
		
		transaction.setTotal(transactionTotal);
		
		inventoryService.commitTransaction(transaction);
		
		//clear the map for the next transaction
		inventorySellItemMap.clear();
		sellings.clear();
		initialize(null, null);
		barcodeInput.requestFocus();
	}
	
	
	private void updateInventoryMap(InventoryTO item) {
		
		item.decrementQuantity();
		
		if(inventorySellItemMap.containsKey(item)) {
			inventorySellItemMap.get(item).incrementQuantity();
			inventorySellItemMap.put(item, inventorySellItemMap.get(item));
		} else {
			SoldItemTO soldItem = new SoldItemTO();
			soldItem.setQuantity(1);
			soldItem.setSoldItem(item);
			inventorySellItemMap.put(item, soldItem);
		}		
	}
	
	private void updateTotal(Double added) {
		total += added;
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);

	}
}