package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Inventory;
import service.InventoryService;

public class InventoryController implements Initializable {
	
	private MainController main;
	
	@FXML
	private TableView<Inventory> inventoryTable;
	
	@FXML
	private TableColumn<Inventory, Number> id;
	
	@FXML 
	private TableColumn<Inventory, String> name;
	
	@FXML 
	private TableColumn<Inventory, String> barcode;
	
	@FXML
	private TableColumn<Inventory, Number> quantity;
	
	@FXML 
	private TableColumn<Inventory, Double> wholesalePrice;
	
	@FXML 
	private TableColumn<Inventory, Double> retailPrice;
	
	private InventoryService service = new InventoryService();
	
	private ObservableList<Inventory> items = FXCollections.observableArrayList();
		
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
		inventoryTable.setItems(items);
				
		id.setCellValueFactory(idCol -> idCol.getValue().getId());
		name.setCellValueFactory(valueCol -> valueCol.getValue().getName());
		barcode.setCellValueFactory(barcodeCol -> barcodeCol.getValue().getBarcode());
		quantity.setCellValueFactory(quantityCol -> quantityCol.getValue().getQuantity());
		wholesalePrice.setCellValueFactory(wp -> wp.getValue().getWholesalePrice().asObject());
		retailPrice.setCellValueFactory(rp -> rp.getValue().getRetailPrice().asObject());	
		
		if(inventoryTable.getColumns().size() == 7) {
			inventoryTable.getColumns().remove(6);
		}
		
		inventoryTable.getColumns().add(addWarningColumn());
		
	}
	
	private TableColumn addWarningColumn() {
		TableColumn<Inventory, Inventory> warningCol = new TableColumn<>();
		warningCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		warningCol.setId("1");
		warningCol.setCellFactory(param -> new TableCell<Inventory, Inventory>() {
			
			@Override
			protected void updateItem(Inventory item, boolean empty) {
				super.updateItem(item, empty);
				
				if(item == null) {
					setGraphic(null);
					return;
				}

				if(item.getQuantity().get() <= item.getLowAlertLevel().get()) {
					ImageView image = new ImageView(new Image("warningIcon.png"));
					
					Tooltip tooltip = new Tooltip();
					tooltip.setText("This item is low");
									
					image.setFitHeight(25);
					image.setFitWidth(25);
					setGraphic(image);		
				}
			}
		});
		
		warningCol.setMaxWidth(50);
		warningCol.setResizable(false);		
		return warningCol;	
	}
}
