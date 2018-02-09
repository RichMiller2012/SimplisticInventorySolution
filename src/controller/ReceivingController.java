package controller;

import java.net.URL;
import java.util.ResourceBundle;

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
	private Label itemNotFoundText;
	
	@FXML
	private Button itemAddButton;
	
	@FXML
	private Button itemCancelButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		itemNotFoundText.setVisible(false);
		barcodeInput.setDisable(false);
		quantityInput.getValueFactory().setValue(1);
		Platform.runLater(() -> barcodeInput.requestFocus());
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
		}
		
		if(barcode.isEmpty()) {
			return;
		} 
		
		Inventory selectedItem = InventoryService.findItemByBarcode(barcode);
		
		if(selectedItem == null) {
			itemNotFoundText.setVisible(true);
		} else {
			barcodeInfo.setText(barcode);
			itemNameInfo.setText(selectedItem.getName().getValue());
			currentQuantityInfo.setText(selectedItem.getQuantity().getValue().toString());
			retailPriceInfo.setText(selectedItem.getRetailPrice().getValue().toString());
			wholesalePriceInfo.setText(selectedItem.getWholesalePrice().getValue().toString());
			
			if(itemNotFoundText.isVisible()) {
				itemNotFoundText.setVisible(false);
			}
		}
	}
	
	public void cancelBarcodeEntry() {
		if(itemNotFoundText.isVisible()) {
			itemNotFoundText.setVisible(false);
		}
		
		barcodeInput.clear();
		barcodeInfo.clear();
		itemNameInfo.clear();
		currentQuantityInfo.clear();
		retailPriceInfo.clear();
		wholesalePriceInfo.clear();
		
		quantityInput = new Spinner<Number>(1,9999,1);
		
		reselected();
	}	
}