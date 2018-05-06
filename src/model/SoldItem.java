package model;

import entity.SoldItemTO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SoldItem {

	private IntegerProperty id;
	private StringProperty name;
	private StringProperty barcode;
	private IntegerProperty quantity;
	private DoubleProperty profit;
	private DoubleProperty wholeSalePrice;
	private DoubleProperty retailPrice;
	
	public SoldItem() {}
	
	public SoldItem(SoldItemTO entity) {
		this.id = new SimpleIntegerProperty(entity.getId());
		this.name = new SimpleStringProperty(entity.getSoldItem().getName());
		this.barcode = new SimpleStringProperty(entity.getSoldItem().getBarcode());
		this.quantity = new SimpleIntegerProperty(entity.getQuantity());
		this.profit = new SimpleDoubleProperty(0.0);
		this.wholeSalePrice = new SimpleDoubleProperty(entity.getWholesalePrice());
		this.retailPrice = new SimpleDoubleProperty(entity.getRetailPrice());
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
	public DoubleProperty getProfit() {
		return profit;
	}
	public void setProfit(DoubleProperty profit) {
		this.profit = profit;
	}

	public DoubleProperty getWholeSalePrice() {
		return wholeSalePrice;
	}

	public void setWholeSalePrice(DoubleProperty wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}

	public DoubleProperty getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(DoubleProperty retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	
	
	
}
