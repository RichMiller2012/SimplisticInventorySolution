package dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import antlr.collections.List;
import entity.InventoryTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import model.Inventory;
import service.InventoryService;

public class InventoryDAO {
	
	private Session session;
	
	public InventoryDAO() {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(InventoryTO.class)
				.buildSessionFactory();
	
		
		this.session = factory.getCurrentSession();
	}
	
	public void saveInventoryItem(InventoryTO inventory) {
		
		try {
			openSession();
			session.beginTransaction();
			session.save(inventory);
			session.getTransaction().commit();
		} catch(Exception e) {
			System.out.println("Something happened saving invenory item " + inventory.toString());
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void updateItem(InventoryTO item) {
		
		try {
			openSession();
			session.beginTransaction();
			
			InventoryTO queriedItem = (InventoryTO) session.createQuery("from InventoryTO i where i.barcode = '" + item.getBarcode() + "'").list().get(0);
			queriedItem.setName(item.getName());
			queriedItem.setQuantity(item.getQuantity());
			queriedItem.setRetailPrice(item.getRetailPrice());
			queriedItem.setWholesalePrice(item.getWholesalePrice());
			
			session.getTransaction().commit();
			
		} catch(Exception e) {
			System.out.println("There was a problem fetching an item by barcode");
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ObservableList<TreeItem<Inventory>> getAllInventoryItems(){
		ObservableList<TreeItem<Inventory>> inventory = FXCollections.observableArrayList();
		ArrayList<InventoryTO> allInventoryItems = new ArrayList<>();
		
		try {
			openSession();
		    session.beginTransaction();	
		    allInventoryItems = (ArrayList<InventoryTO>) session.createCriteria(InventoryTO.class).list();
			session.getTransaction().commit();
		} catch(Exception e) {
			System.out.println("There was an issue fetching all Inventory Items");
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for(InventoryTO item : allInventoryItems) {
			inventory.add(new TreeItem<>(new Inventory(item.getId(), item.getName(), item.getBarcode(), item.getQuantity(), item.getWholesalePrice(), item.getRetailPrice())));
		}
		
		return inventory;
	}
	
	public Inventory getItemById(int id) {
		return null;
	}
	
	public Inventory getItemByBarcode(String barcode) {
		
		InventoryTO queriedItem = null;
		try {
			openSession();
			session.beginTransaction();
			queriedItem = (InventoryTO) session.createQuery("from InventoryTO i where i.barcode = '" + barcode + "'").list().get(0);
			session.getTransaction().commit();
		} catch(Exception e) {
			System.out.println("There was an issue querying by barcode or the item does not exist");
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		Inventory item = null;
		if(queriedItem != null) {
			item = new Inventory(queriedItem);
		}
		
		return item;
	}
	
	private void openSession() {
		if(!session.isOpen()) {
			session = session.getSessionFactory().openSession();
		}
	}
}
