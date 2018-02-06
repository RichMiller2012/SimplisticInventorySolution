package service;

import dto.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class InventoryService {

	public static ObservableList<TreeItem<Inventory>> createStubInventoryItems(){
		
		ObservableList<TreeItem<Inventory>> inventory = FXCollections.observableArrayList();
		
		inventory.add(new TreeItem<>(new Inventory(1, "Cigaretts", "1234", 80, 2.00, 6.00)));
		inventory.add(new TreeItem<>(new Inventory(2, "Lighter", "55555", 20, 1.00, 2.67)));
		inventory.add(new TreeItem<>(new Inventory(3, "Peanuts", "0909090", 50, .60, .99)));
		inventory.add(new TreeItem<>(new Inventory(1, "Cigaretts", "1234", 80, 2.00, 6.00)));
		inventory.add(new TreeItem<>(new Inventory(2, "Lighter", "55555", 20, 1.00, 2.67)));
		inventory.add(new TreeItem<>(new Inventory(3, "Peanuts", "0909090", 50, .60, .99)));
		inventory.add(new TreeItem<>(new Inventory(1, "Cigaretts", "1234", 80, 2.00, 6.00)));
		inventory.add(new TreeItem<>(new Inventory(2, "Lighter", "55555", 20, 1.00, 2.67)));
		inventory.add(new TreeItem<>(new Inventory(3, "Peanuts", "0909090", 50, .60, .99)));
		inventory.add(new TreeItem<>(new Inventory(1, "Cigaretts", "1234", 80, 2.00, 6.00)));
		inventory.add(new TreeItem<>(new Inventory(2, "Lighter", "55555", 20, 1.00, 2.67)));
		inventory.add(new TreeItem<>(new Inventory(3, "Peanuts", "0909090", 50, .60, .99)));
		inventory.add(new TreeItem<>(new Inventory(1, "Cigaretts", "1234", 80, 2.00, 6.00)));
		inventory.add(new TreeItem<>(new Inventory(2, "Lighter", "55555", 20, 1.00, 2.67)));
		inventory.add(new TreeItem<>(new Inventory(3, "Peanuts", "0909090", 50, .60, .99)));
		
		
		return inventory;
	}
}
