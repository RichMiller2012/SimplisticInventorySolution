import java.net.URL;
import java.util.ResourceBundle;

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
import model.Inventory;

public class PointOfSaleController implements Initializable {

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
    
	private TreeItem<Inventory> root = new TreeItem<>(new Inventory(0, "name", "barcode", 0, 0.0, 0.0));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ObservableList<TreeItem<Inventory>> sellings = FXCollections.observableArrayList(); 
		
		name.setCellValueFactory(nameCol -> nameCol.getValue().getValue().getName());
		barcode.setCellValueFactory(barcodeCol -> barcodeCol.getValue().getValue().getBarcode());
		retailPrice.setCellValueFactory(retCol -> retCol.getValue().getValue().getRetailPrice().asObject());
		
		posTable.setRoot(root);
		posTable.setShowRoot(false);
	}
	
	public void addItemToSellList() {
		
	}

}