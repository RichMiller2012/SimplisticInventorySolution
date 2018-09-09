package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;

public class AlertButtonContext {

	private ObservableList<TreeItem<Inventory>> alertItems = FXCollections.observableArrayList();
	private Button warningButton;
	
	public ObservableList<TreeItem<Inventory>> getAlertItems() {
		return alertItems;
	}
	public void setAlertItems(ObservableList<TreeItem<Inventory>> alertItems) {
		this.alertItems = alertItems;
	}
	public Button getWarningButton() {
		return warningButton;
	}
	public void setWarningButton(Button warningButton) {
		this.warningButton = warningButton;
	}	
}
