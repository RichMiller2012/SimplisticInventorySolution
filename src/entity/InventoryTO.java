package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inventory")
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
	@Column(name="id")
	private int id;
	
	@Column
	private String name;
	
	@Column 
	private String barcode;
	
	@Column
	private int quantity;
	
	@Column(name="wholesale_price")
	private Double wholesalePrice;
	
	@Column(name="retail_price")
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
	
	@Override
	public String toString() {
		return "InventoryTO [id=" + id + ", name=" + name + ", barcode=" + barcode + ", quantity=" + quantity
				+ ", wholesalePrice=" + wholesalePrice + ", retailPrice=" + retailPrice + "]";
	}
}
