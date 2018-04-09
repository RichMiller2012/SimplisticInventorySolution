package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import dao.ReportsDAO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.SoldItem;
import service.ReportsService;
import util.Months;

public class ReportingController implements Initializable {

	private MainController main;
	
	private ReportsDAO dao = new ReportsDAO();
	
	private ReportsService reportsService = new ReportsService();
	
    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> monthPicker;

    @FXML
    private TextArea displayArea;
    
    @FXML
    private TableView<SoldItem> reportsTable;
    
    @FXML
    private TableColumn<SoldItem, String> name;
    
    @FXML 
    private TableColumn<SoldItem, String> barcode;
    
    @FXML
    private TableColumn<SoldItem, Number> quantity;
    
    @FXML 
    private TableColumn<SoldItem, Double> profit;
    
	TreeItem<SoldItem> root = new TreeItem<>(new SoldItem());
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datePicker.valueProperty().addListener((ov,oldValue, newValue) -> {
			initSoldItemsByDate(newValue);
		});

		name.setCellValueFactory(nameCol -> nameCol.getValue().getName());
		barcode.setCellValueFactory(barcodeCol -> barcodeCol.getValue().getBarcode());
		quantity.setCellValueFactory(quantCol -> quantCol.getValue().getQuantity());
		profit.setCellValueFactory(profitCol -> profitCol.getValue().getProfit().asObject());
		
		//set the combo box
		ObservableList<String> monthItems = FXCollections.observableArrayList();
		
		for(Months month : Arrays.asList(Months.values())) {
			monthItems.add(month.getMonth());
		}
		
		monthPicker.setItems(monthItems);	
	}
	
	public void initData() {
		reportsService.initData();
	}
	
	public void initSoldItemsByDate(LocalDate newValue) {	
	    ObservableList<SoldItem> allSoldItems = reportsService.getSoldItemsByDate(newValue);
		System.out.println("Found " + allSoldItems.size() + " Sold Items");
		
		reportsTable.getItems().clear();
		reportsTable.getItems().addAll(allSoldItems);
	}
	
	@FXML
	public void initSoldItemsByMonth() {
		String month = monthPicker.getValue();
		ObservableList<SoldItem> soldItemsByMonth = reportsService.getSoldItemsBymonth(month);
		
		System.out.println("Found " + soldItemsByMonth.size() + " Sold Items");
		
		reportsTable.setItems(soldItemsByMonth);
	}
	
	public void init(MainController mainController) {
		main = mainController;
	}
}