package service;

import java.util.List;
import java.util.Optional;

import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import print.Print;

public class TransactionPrintUtility{

	TransactionsTO currentTransaction;
	
	String qtySpace = "   ";
	String descSpace = "       ";
	String amntSpace = "    ";
	
	int qtyColSize = ("QTY" + qtySpace).length();
	int descColSize = ("DESCRIPTION" + descSpace).length();
	int amntColSize = ("AMOUNT" + amntSpace).length();
	
	public void setTransaction(TransactionsTO transaction) {
		this.currentTransaction = transaction;
	}
	
	public void renderPrintReceiptAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Print Receipt");
		alert.setHeaderText("Choose to print a receipt");
		alert.setContentText("Print a receipt for this transaction?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			print();
		} else {
			//do nothing and close
		}		
	}
	
	public void print() {	
		StringBuilder sb = new StringBuilder();		
		sb.append("-------------------------------\n");
		sb.append("          HI-JO STORE          \n");
		sb.append("Brgy:4, Siapalay City\n");
		sb.append("Neg, Occ.\n");
		sb.append("JUDITH G. NICHOLAS - Prop\n");
		sb.append("Non-Vat Reg:TIN 934-537-779-0000\n\n");
		sb.append("CASH INVOICE\n\n");
		sb.append("QTY").append(qtySpace);
		sb.append("DESCRIPTION").append(descSpace);
		sb.append("AMOUNT").append(amntSpace).append("\n");
		sb.append("____________________________\n");
		
		for(SoldItemTO item : currentTransaction.getSoldItems()) {
			sb.append(item.getQuantity())
			.append(calculateQtySpace(item.getQuantity()))
			.append(calculateDescriptionSpace(item.getSoldItem().getName()))
			.append(item.getRetailPrice())
			.append(caclulateAmountSpace(item.getRetailPrice()))
			.append("\n");
			
		}
		sb.append("--------------------------\n");
		sb.append("Total Amount Due:    ").append(currentTransaction.getTotal());
		sb.append("\n\n");
		sb.append("Salamat sa pagbisita po!\n");
		sb.append("\n\n");
		
		
		System.out.println(sb.toString());
		
		Print.printTransaction(sb.toString());
	}
	
	public void printLowItems(List<InventoryTO> lowItems) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Low Items for Purchase\n");
		sb.append("--------------------------\n");

		for(InventoryTO li : lowItems) {
			sb.append(li.getName() + "  " + li.getQuantity() + "\n");
		}
		
		System.out.println(sb.toString());
		Print.printTransaction(sb.toString());
	}
	
	private String caclulateAmountSpace(Double amount) {
		String amntString = String.valueOf(amount);
		int remainingSize = qtyColSize - amntString.length();
		if(remainingSize > 0) {
			return new String(new char[remainingSize]).replace('\0', ' ');
		}
		return "";
	}
	
	private String calculateQtySpace(int value) {
		String amntString = String.valueOf(value);	
		int remainingSize = qtyColSize - amntString.length();
		if(remainingSize > 0) {
			return new String(new char[remainingSize]).replace('\0', ' ');
		}
		return "";
		
	}
	
	private String calculateDescriptionSpace(String desc) {
		if(desc.length() > descColSize) {
			return desc.substring(0, descColSize - 1);
		}
		
		int remainingSpace = descColSize - desc.length();
		
		return desc + new String(new char[remainingSpace]).replace('\0', ' ');	
	}
	
	
	
}
