package model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import entity.InventoryTO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;



public class Inventory {

	private IntegerProperty id;
	private StringProperty name;
	private StringProperty barcode;
	private IntegerProperty quantity;
	private DoubleProperty wholesalePrice;
	private DoubleProperty retailPrice;
	private IntegerProperty lowAlertLevel;

	public Inventory(
			int id,
			String name,
			String barcode,
			int quantity,
			double wholesalePrice,
			double retailPrice,
			int lowAlertLevel
			) {
		
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.barcode = new SimpleStringProperty(barcode);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.wholesalePrice = new SimpleDoubleProperty(wholesalePrice);
		this.retailPrice = new SimpleDoubleProperty(retailPrice);
		this.lowAlertLevel = new SimpleIntegerProperty(lowAlertLevel);
	}
	
	public Inventory(InventoryTO data) {
		this.id = new SimpleIntegerProperty(data.getId());
		this.name = new SimpleStringProperty(data.getName());
		this.barcode = new SimpleStringProperty(data.getBarcode());
		this.quantity = new SimpleIntegerProperty(data.getQuantity());
		this.wholesalePrice = new SimpleDoubleProperty(data.getWholesalePrice());
		this.retailPrice = new SimpleDoubleProperty(data.getRetailPrice());
		this.lowAlertLevel = new SimpleIntegerProperty(data.getLowLevelAlert());
	}
	
	public IntegerProperty getId() {
		return id;
	}

	public void setId(IntegerProperty id) {
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

	public IntegerProperty getLowAlertLevel() {
		return lowAlertLevel;
	}

	public void setLowAlertLevel(IntegerProperty lowAlertLevel) {
		this.lowAlertLevel = lowAlertLevel;
	}
	
	//Take double the low amount and subtract current quantity
	public IntegerProperty getDoubleLowLevelAlert() {
		Integer low = this.lowAlertLevel.getValue();
		return new SimpleIntegerProperty(low * 2 - this.quantity.getValue());
	}
	
	
}
