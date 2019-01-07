package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import model.Inventory;

@Entity
public class InventoryTO {

	
	public InventoryTO() {
		this.wholesalePrice= 0.0;
		this.retailPrice = 0.0;
		this.lowLevelAlert=0;
	}
	
	public InventoryTO(InventoryTO item) {
		this.id = item.id;
		this.name = item.name;
		this.barcode = item.barcode;
		this.quantity  = item.quantity;
		this.wholesalePrice = item.wholesalePrice;
		this.retailPrice = item.retailPrice;
		this.lowLevelAlert = item.lowLevelAlert;
	}
	
	public InventoryTO(int id, String name, String barcode, int quantity, Double wholesalePrice, Double retailPrice, Integer lowLevelAlert) {
		this.id = id;
		this.name = name;
		this.barcode = barcode;
		this.quantity = quantity;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.lowLevelAlert = lowLevelAlert;
	}
	
	public InventoryTO(Inventory item) {
		this.id = item.getId().getValue();
		this.name = item.getName().getValue();
		this.barcode = item.getBarcode().getValue();
		this.quantity = item.getQuantity().getValue();
		this.wholesalePrice = item.getWholesalePrice().getValue();
		this.retailPrice = item.getRetailPrice().getValue();
		this.lowLevelAlert = item.getLowAlertLevel().getValue();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column 
	private String barcode;
	
	@Column
	private int quantity;
	
	@Column
	private Double wholesalePrice;
	
	@Column
	private Double retailPrice;
	
	@Column
	private Integer lowLevelAlert;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(Double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public Integer getLowLevelAlert() {
		return lowLevelAlert;
	}

	public void setLowLevelAlert(Integer lowLevelAlert) {
		this.lowLevelAlert = lowLevelAlert;
	}

	public void decrementQuantity() {
		if(quantity >= 0) {
			quantity--;
		}
	}
	
	public void reduceQuantityBy(int amount) {
		if(quantity > amount) {
			quantity -= amount;
		} else {
			quantity = 0;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		
		if(!(other instanceof InventoryTO)) {
			return false;
		}
		
		InventoryTO item = (InventoryTO)other;
		
		boolean equals = item.id == this.id 
				&& item.barcode.equals(this.barcode)
				&& item.name.equals(this.name);
		
		return equals;
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + this.id;
		hash = 53 * hash + (this.barcode != null ? this.barcode.hashCode() : 0);
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		
		return hash;
	}
}
