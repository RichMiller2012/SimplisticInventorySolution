package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class InventoryTO {

	
	public InventoryTO() {
		
	}	
	
	public InventoryTO(int id, String name, String barcode, int quantity, Double wholesalePrice, Double retailPrice) {
		this.id = id;
		this.name = name;
		this.barcode = barcode;
		this.quantity = quantity;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
	}

	@Id
	@GeneratedValue
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
	
	public void decrementQuantity() {
		if(quantity >= 0) {
			quantity--;
		}
	}
}
