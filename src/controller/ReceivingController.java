package controller;

import java.net.URL;
import java.util.ResourceBundle;

import entity.InventoryTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Inventory;
import service.InventoryService;

public class ReceivingController implements Initializable {

	private MainController main;
	
	@FXML
	private TextField barcodeInput;
	
	@FXML
	private Spinner<Number> quantityInput;
	
	@FXML
	private TextField barcodeInfo;
	
	@FXML
	private TextField itemNameInfo;
	
	@FXML
	private TextField currentQuantityInfo;
	
	@FXML
	private TextField wholesalePriceInfo;
	
	@FXML
	private TextField retailPriceInfo;
	
	@FXML
    private TextField lowLimitInfo;
	
	@FXML
	private Label itemNotFoundText;
	
	@FXML
	private Button itemAddButton;
	
	@FXML
	private Button itemCancelButton;
	
	private boolean editMode;
	private InventoryTO currentItem;
	
	private InventoryService service = new InventoryService();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//set listeners to all to item properties for edit mode to shut off 
		barcodeInfo.textProperty().addListener((observable, oldValue, newValue) -> {	
			validateItemNotChanged(newValue, currentItem.getBarcode());
		});
		
		itemNameInfo.textProperty().addListener((observable, oldValue, newValue) -> {	
			validateItemNotChanged(newValue, currentItem.getName());
		});
		
		currentQuantityInfo.textProperty().addListener((observable, oldValue, newValue) -> {	
			validateItemNotChanged(newValue, new Integer(currentItem.getQuantity()).toString());
		});
		
		retailPriceInfo.textProperty().addListener((observable, oldValue, newValue) -> {	
			validateItemNotChanged(newValue, currentItem.getRetailPrice().toString());
		});
		
		wholesalePriceInfo.textProperty().addListener((observable, oldValue, newValue) -> {	
			validateItemNotChanged(newValue, currentItem.getWholesalePrice().toString());
		});
		
		lowLimitInfo.textProperty().addListener((observable, oldValue, newValue) -> {	
			validateItemNotChanged(newValue, currentItem.getLowLevelAlert().toString());
		});
		
		quantityInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
				  quantityInput.increment(0); // won't change value, but will commit editor
			  }
			});
		
		itemNotFoundText.setVisible(false);
		barcodeInput.setDisable(false);
		quantityInput.getValueFactory().setValue(1);
		Platform.runLater(() -> barcodeInput.requestFocus());
	} 
	
	private void validateItemNotChanged(String newValue, String checkValue) {

		if(currentItem == null) return;
		
		if(!newValue.equals(checkValue)) {
			//if the new value is different from the oldvalue set editMode
			editMode = true;
		} else { 
			//else check all the other properties
			editMode = 
				barcodeInfo.getText().equals(currentItem.getBarcode()) &&
				itemNameInfo.getText().equals(currentItem.getName()) &&
				currentQuantityInfo.getText().equals(currentItem.getQuantity()) &&
				retailPriceInfo.getText().equals(currentItem.getRetailPrice()) &&
				wholesalePriceInfo.getText().equals(currentItem.getWholesalePrice()) &&
				lowLimitInfo.getText().equals(currentItem.getLowLevelAlert());
		}
		
		if(editMode) {
			itemAddButton.setText("Edit");
		} else {
			itemAddButton.setText("Add");
		}
		
		quantityInput.setDisable(editMode);
	}
	
	public void init(MainController mainController) {
		main = mainController;
	}
	
	public void reselected() {
		quantityInput.getValueFactory().setValue(1);
		Platform.runLater(() -> barcodeInput.requestFocus());
	}
	
	@FXML
	public void barcodeItemEntered(KeyEvent event) {
			
		String barcode = "";
		if(event.getCode() == KeyCode.ENTER) {
		    barcode = barcodeInput.getText();
		} else {
			return;
		}
		
		if(barcode.isEmpty()) {
			return;
		} 
			
		InventoryTO selectedItem = service.findItemByBarcode(barcode);
		
		if(selectedItem == null) {
			itemNotFoundText.setVisible(true);
			barcodeInfo.setText(barcode);
			currentItem = null;
		} else {
			
			currentItem = selectedItem;
			
			barcodeInfo.setText(barcode);
			itemNameInfo.setText(selectedItem.getName());
			currentQuantityInfo.setText(new Integer(selectedItem.getQuantity()).toString());
			retailPriceInfo.setText(selectedItem.getRetailPrice().toString());
			wholesalePriceInfo.setText(selectedItem.getWholesalePrice().toString());
			lowLimitInfo.setText(selectedItem.getLowLevelAlert().toString());
			
			if(itemNotFoundText.isVisible()) {
				itemNotFoundText.setVisible(false);
			}
		}
	}
	
	public void addInventoryItem() {
		
		InventoryTO selectedItem = service.findItemByBarcode(barcodeInfo.getText());
		quantityInput.increment(0); // won't change value, but will commit editor
		
		int q = quantityInput.getValue().intValue();
		int itemId = 0;
		int itemQuantity = 0;
		boolean newItem = false;
		
		if(selectedItem != null) {
			itemId = selectedItem.getId();
			
			//check to see if use adjusted the quantity box or by edit mode
			if(editMode) {
				itemQuantity = Integer.parseInt(currentQuantityInfo.getText());
			} else {
				itemQuantity = selectedItem.getQuantity() + q;	
			}
		} else {
			newItem = true;
			itemQuantity = q;
		}
			
		selectedItem = new InventoryTO(
				itemId,
				itemNameInfo.getText(),
				barcodeInput.getText(),
				itemQuantity,
				Double.parseDouble(wholesalePriceInfo.getText()),
				Double.parseDouble(retailPriceInfo.getText()),
				Integer.parseInt(lowLimitInfo.getText())
				);
		
		if(newItem) {
			service.addItem(selectedItem);	
		} else {
			service.updateItem(selectedItem);
		}
		
		//update low level alert
		if(selectedItem.getQuantity() <= selectedItem.getLowLevelAlert()) {
			main.updateLowItem(selectedItem);
		} else if(main.alertMapContainsItem(selectedItem.getBarcode())) {
			main.removeLowItem(selectedItem.getBarcode());
		}
		
		cancelBarcodeEntry();
	}
	
	
	public void cancelBarcodeEntry() {
		if(itemNotFoundText.isVisible()) {
			itemNotFoundText.setVisible(false);
		}
		
		barcodeInput.clear();
		barcodeInfo.clear();
		itemNameInfo.clear();
		currentQuantityInfo.clear();
		quantityInput.getValueFactory().setValue(1);
		retailPriceInfo.clear();
		wholesalePriceInfo.clear();
		lowLimitInfo.clear();
		
		currentItem = null;
		editMode = false;
		
		reselected();
	}	
}