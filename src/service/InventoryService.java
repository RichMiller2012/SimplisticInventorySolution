package service;

import java.util.ArrayList;
import java.util.List;

import dao.InventoryDAO;
import entity.InventoryTO;
import entity.TransactionsTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import model.Inventory;

public class InventoryService {
	
	private InventoryDAO dao;
	
	public InventoryService() {
		this.dao = new InventoryDAO();
	}
	
	public ObservableList<TreeItem<Inventory>> fetchInventory(){
		return dao.getAllInventoryItems();
	}
	
	public void addItem(InventoryTO item) {		
		dao.saveInventoryItem(item);
	}
	
	public void updateItem(InventoryTO item) {
			dao.updateItem(item);
	}
	
	public void updateItem(InventoryTO item, int quantity) {
		item.setQuantity(item.getQuantity() + quantity);
		dao.updateItem(item);
}
	
	public InventoryTO findItemByBarcode(String barcode) {
		return dao.getItemByBarcode(barcode);
	}
	
	public void commitTransaction(TransactionsTO transaction) {
		dao.commitTransaction(transaction);
	}
	
	public List<InventoryTO> findLowItems(){
		List<InventoryTO> lowItems = new ArrayList<>();
		
		for(InventoryTO item : dao.getAllInventoryTOs()) {
			if(item.getQuantity() <= item.getLowLevelAlert()) {
				lowItems.add(item);
			}
		}
		
		return lowItems;
		
	}

}
