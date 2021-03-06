package model;

public class PDFSalesItem {

	private Integer quantity;
	private String name;
	private String barcode;
	private Double wholesalePrice;
	private Double retailPrice;
	private Double totalRetail;//accumulates retail of all (retail * quantity)
	private String sellDate;
	private Double itemProfit;
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	public String getSellDate() {
		return sellDate;
	}
	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}
	public void setItemProfit(Double profit) {
		this.itemProfit = profit;
	}
	public Double getItemProfit() {
		return itemProfit;
	}
	public Double getTotalRetail() {
		return totalRetail;
	}
	public void setTotalRetail(Double totalRetail) {
		this.totalRetail = totalRetail;
	}
	
	

}
