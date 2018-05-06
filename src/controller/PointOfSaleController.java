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
import javafx.application.Platform;
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
import service.TransactionPrintUtility;

public class PointOfSaleController implements Initializable {

	private static final String TOTAL_TEXT = "Total: ";
	private static final String PHP = " PHP";
	
	private InventoryService inventoryService = new InventoryService();
	
	private MainController main;
	
	private TransactionPrintUtility printUtil = new TransactionPrintUtility();
			
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
    
	private TreeItem<Inventory> root = new TreeItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0, 0));
	
	private ObservableList<TreeItem<Inventory>> sellings = FXCollections.observableArrayList(); 
		
	private Map<InventoryTO, SoldItemTO> inventorySellItemMap = new HashMap();
	
	private Map<InventoryTO, Integer> inventoryQuantityMap = new HashMap();
	
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
		Platform.runLater(() -> barcodeInput.requestFocus());
	}
	
	public void init(MainController mainController) {
		main = mainController;
		Platform.runLater(() -> barcodeInput.requestFocus());
	}
	
	public void reselected() {
		Platform.runLater(() -> barcodeInput.requestFocus());
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
			updateInventoryMaps(selectedItem);
		}
		
		barcodeInput.setText("");
	}
	
	public void commitTransaction() {
		System.out.println("Beginning transaction commission");
		
		TransactionsTO transaction = new TransactionsTO();
		double transactionTotal = 0.00;
		for(InventoryTO item : inventorySellItemMap.keySet()) {
			int quantityToReduce = inventoryQuantityMap.get(item);
				
			//update the quantity on the item
			item.reduceQuantityBy(quantityToReduce);
			inventoryService.updateItem(item);
			
			//save the sold item
			SoldItemTO soldItem = inventorySellItemMap.get(item);
			soldItem.setSoldItem(item);
			soldItem.setWholesalePrice(item.getWholesalePrice());
			soldItem.setRetailPrice(item.getRetailPrice());
			transaction.getSoldItems().add(inventorySellItemMap.get(item));
			soldItem.setTransaction(transaction);
			
			transactionTotal += soldItem.getQuantity() * item.getRetailPrice();
			
			//check low inventory levels
			if(item.getQuantity() <= item.getLowLevelAlert()) {
				main.updateLowItem(item);
			} else if(main.alertMapContainsItem(item.getBarcode())) {
				main.removeLowItem(item.getBarcode());
			}
		}
		
		transaction.setTotal(transactionTotal);
		
		//render receipt printing if selected
		printUtil.setTransaction(transaction);
		printUtil.renderPrintReceiptAlert();
		
		inventoryService.commitTransaction(transaction);	
		
		//clear the map for the next transaction
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);
		inventorySellItemMap.clear();
		inventoryQuantityMap.clear();
		sellings.clear();
		initialize(null, null);
		barcodeInput.requestFocus();
	}
		
	private void updateInventoryMaps(InventoryTO item) {
		if(inventoryQuantityMap.containsKey(item) && inventorySellItemMap.containsKey(item)) {
			 
			 //update the quantity to be subtracted from the quantity of the item
			 Integer currentAmount = inventoryQuantityMap.get(item);
			 inventoryQuantityMap.put(item, ++currentAmount);	 
			 
			 //increment the quantity of the sold item
			 inventorySellItemMap.get(item).incrementQuantity();			 
		} else {
			
			//create a new sold item and place in map
			SoldItemTO soldItem = new SoldItemTO();
			soldItem.setQuantity(1);
			soldItem.setSoldItem(item);
			soldItem.setWholesalePrice(item.getWholesalePrice());
			soldItem.setRetailPrice(item.getRetailPrice());
			
			//place items in the maps
			inventorySellItemMap.put(item, soldItem);
			inventoryQuantityMap.put(item, 1);		
		}
	}
	
	private void updateTotal(Double added) {
		total += added;
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);

	}
}