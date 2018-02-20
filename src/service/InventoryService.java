package service;

import dao.InventoryDAO;
import entity.InventoryTO;
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
	
	public void addItem(Inventory item) {
		
		dao.saveInventoryItem(
				new InventoryTO(
				item.getId().getValue(),
				item.getName().getValue(),
				item.getBarcode().getValue(),
				item.getQuantity().getValue(),
				item.getWholesalePrice().getValue(),
				item.getRetailPrice().getValue()
				));
	}
	
	public void updateItem(Inventory item, int addedQuantity) {
		
		dao.updateItem(
				new InventoryTO(
				item.getId().getValue(),
				item.getName().getValue(),
				item.getBarcode().getValue(),
				item.getQuantity().getValue() + addedQuantity,
				item.getWholesalePrice().getValue(),
				item.getRetailPrice().getValue()
				));
	}
	
	public Inventory findItemByBarcode(String barcode) {
		return dao.getItemByBarcode(barcode);
	}
	
}
