package dto;

import javafx.beans.property.StringProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;



public class Inventory {

	private LongProperty id;
	private StringProperty name;
	private StringProperty barcode;
	private IntegerProperty quantity;
	private DoubleProperty wholesalePrice;
	private DoubleProperty retailPrice;

	public Inventory(
			long id,
			String name,
			String barcode,
			int quantity,
			double wholesalePrice,
			double retailPrice
			) {
		
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
		this.barcode = new SimpleStringProperty(barcode);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.wholesalePrice = new SimpleDoubleProperty(wholesalePrice);
		this.retailPrice = new SimpleDoubleProperty(retailPrice);
	}
	
	public LongProperty getId() {
		return id;
	}

	public void setId(LongProperty id) {
		this.id = id;
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}

	public StringProperty getBarcode() {
		return barcode;
	}

	public void setBarcode(StringProperty barcode) {
		this.barcode = barcode;
	}

	public IntegerProperty getQuantity() {
		return quantity;
	}

	public void setQuantity(IntegerProperty quantity) {
		this.quantity = quantity;
	}

	public DoubleProperty getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(DoubleProperty wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public DoubleProperty getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(DoubleProperty retailPrice) {
		this.retailPrice = retailPrice;
	}
	
}
