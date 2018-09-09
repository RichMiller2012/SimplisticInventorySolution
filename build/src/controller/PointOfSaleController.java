package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import model.Inventory;
import service.InventoryService;
import service.TransactionPrintUtility;

public class PointOfSaleController implements Initializable {

	private static final String TOTAL_TEXT = "Total: ";
	private static final String PHP = " PHP";
	private static final String NO_QUANTITY = "You are out of this item";
	private static final String NO_ITEM = "Unrecognized barcode, add this item";
	
	private InventoryService inventoryService = new InventoryService();
	
	private MainController main;
	
	private TransactionPrintUtility printUtil = new TransactionPrintUtility();
	
	@FXML
	private Label posWarningLabel;
			
    @FXML
    private TextField barcodeInput;
	
	@FXML
    private TableView<Inventory> posTable;

    @FXML
    private Label posTotalLabel;
    
    @FXML
    private Button transactionButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML 
	private TableColumn<Inventory, String> name;
	
	@FXML 
	private TableColumn<Inventory, String> barcode;
	
	@FXML 
	private TableColumn<Inventory, Double> retailPrice;
		
	private InventoryService service = new InventoryService();
    
	//private TableItem<Inventory> root = new TableItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0, 0));
	
	private ObservableList<Inventory> sellings = FXCollections.observableArrayList(); 
		
	private Map<InventoryTO, SoldItemTO> inventorySellItemMap = new HashMap();
	
	private Map<InventoryTO, Integer> inventoryQuantityMap = new HashMap();
	
	private Double total = 0.00;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		posTable.setItems(sellings);
		
		name.setCellValueFactory(nameCol -> nameCol.getValue().getName());
		barcode.setCellValueFactory(barcodeCol -> barcodeCol.getValue().getBarcode());
		retailPrice.setCellValueFactory(retCol -> retCol.getValue().getRetailPrice().asObject());
		System.out.println("initializing pos");
		posTable.getColumns().add(setUpButtons());
		
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
	
	private void addItemToSellListByButton(String barcode) {

		posWarningLabel.setText("");
		
		System.out.println("recieved barcode: " + barcode );
		
		InventoryTO selectedItem = service.findItemByBarcode(barcode);
		
		if(selectedItem == null) {
			posWarningLabel.setText(NO_ITEM);
		} else if (selectedItem.getQuantity() == 0) {
			posWarningLabel.setText(NO_QUANTITY);
		} else {
			posTable.getItems().add(new Inventory(selectedItem));
			incrementTotal(selectedItem.getRetailPrice());
			updateInventoryMaps(selectedItem);
		}
		
		barcodeInput.setText("");
	}
	
	public void addItemToSellList(KeyEvent event) {
		
		posWarningLabel.setText("");

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
			posWarningLabel.setText(NO_ITEM);
		} else if (selectedItem.getQuantity() == 0) {
			posWarningLabel.setText(NO_QUANTITY);
		} else {
			posTable.getItems().add(new Inventory(selectedItem));
			incrementTotal(selectedItem.getRetailPrice());
			updateInventoryMaps(selectedItem);
		}
		
		barcodeInput.setText("");
	}
	
	public void commitTransaction() {
		posWarningLabel.setText("");
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
		clear();
	}
	
	public void clearTransaction() { clear(); }
	
	private void clear() {
		inventorySellItemMap.clear();
		inventoryQuantityMap.clear();
		sellings.clear();
		//initialize(null, null);
		barcodeInput.requestFocus();
		total = 0.0;
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);
	}
	
	private void removeFromInventoryMaps(InventoryTO item) {
		if(inventoryQuantityMap.containsKey(item) && inventorySellItemMap.containsKey(item)) {
			Integer currentAmount = inventoryQuantityMap.get(item);
			if(currentAmount >= 0) {
				inventoryQuantityMap.put(item, --currentAmount);
				inventorySellItemMap.get(item).decrementQuantity();
			} else {
				inventoryQuantityMap.remove(item);
				inventorySellItemMap.remove(item);
			}
			
			decrementTotal(item.getRetailPrice());
		}
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
	
	private void incrementTotal(Double added) {
		total += added;
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);
	}
	
	private void decrementTotal(Double sub) {
		if(total - sub >= 0) {
			total -= sub;
		} else {
			total = 0.0;
		}
		
		posTotalLabel.setText(TOTAL_TEXT + total.toString() + PHP);
	}
	
	private TableColumn setUpButtons() {
		TableColumn<Inventory, Inventory> addRemoveCol = new TableColumn<>();	
		addRemoveCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));	
		addRemoveCol.setCellFactory(param -> new TableCell<Inventory, Inventory>() {		
			
			@Override
			protected void updateItem(Inventory inventory, boolean empty) {
				super.updateItem(inventory, empty);
				if(inventory == null) {
					setGraphic(null);
					return;
				} 
				
				Button deleteButton = new Button("-");	
				Button addButton = new Button("+");
				addButton.getStyleClass().add("plus-button");
				deleteButton.getStyleClass().add("minus-button");
				HBox buttonBox = new HBox();
				
				addButton.setMinWidth(50);
				deleteButton.setMinWidth(50);
				buttonBox.setMargin(addButton, new Insets(5,5,5,5));
				buttonBox.setMargin(deleteButton, new Insets(5,5,5,5));
				buttonBox.getChildren().addAll(addButton, deleteButton);
				setGraphic(buttonBox);
				
				deleteButton.setOnAction(event -> {
					InventoryTO deleteItem = inventoryService.findItemByBarcode(inventory.getBarcode().getValue());
					removeFromInventoryMaps(deleteItem);
					getTableView().getItems().remove(inventory);
				});
		
				addButton.setOnAction(event -> {
					InventoryTO addItem = inventoryService.findItemByBarcode(inventory.getBarcode().getValue());
					if(inventoryQuantityMap.get(addItem) < addItem.getQuantity()) {
						addItemToSellListByButton(inventory.getBarcode().getValue());
					} else {
						posWarningLabel.setText(NO_QUANTITY);
					}
				});
				
			}
		});
		
		addRemoveCol.setMinWidth(150);
		addRemoveCol.setResizable(false);
		return addRemoveCol;
	}
	
}

	

	
	
	

	
	
	
