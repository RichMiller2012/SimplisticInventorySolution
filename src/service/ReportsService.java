package service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.ReportsDAO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PDFSalesItem;
import model.SoldItem;
import util.Months;

public class ReportsService {

	private ReportsDAO dao;
	
	private List<SoldItemTO> allSoldItems;
	
	public ReportsService() {
		this.dao = new ReportsDAO();
		allSoldItems = dao.getAllSoldItems();
	}
	
	public ObservableList<SoldItem> getAllSoldItems(){
		List<SoldItemTO> allSoldItems = dao.getAllSoldItems();
		
		ObservableList<SoldItem> displayList = FXCollections.observableArrayList();
				
		for(SoldItemTO soldItem : allSoldItems) {
			//may need to do some aggregation of similar sold items
			displayList.add(new SoldItem(soldItem));
		}
		
		return displayList;
	}
	
	public void initData() {
		allSoldItems = dao.getAllSoldItems();
	}
	
	public ObservableList<SoldItem> getSoldItemsByDate(LocalDate date){
				
		ObservableList<SoldItem> displayList = FXCollections.observableArrayList();
		
		for(SoldItemTO soldItem : allSoldItems) {
			TransactionsTO soldItemTransaction = soldItem.getTransaction();
			
			Date thedate = soldItemTransaction.getSellDate();
			System.out.println(thedate.toString());
			
			if(dateMatches(thedate, date)) {
				displayList.add(new SoldItem(soldItem));
			}
			
		}
		
		return displayList;
	}
	
	public ObservableList<SoldItem> getSoldItemsBymonth(String month){		
		ObservableList<SoldItem> displayList = FXCollections.observableArrayList();
		
		for(SoldItemTO soldItem : allSoldItems) {
			TransactionsTO soldItemTransaction = soldItem.getTransaction();
			
			if(monthMatches(soldItemTransaction.getSellDate(), month)) {
				displayList.add(new SoldItem(soldItem));
			}
		}
		
		return displayList;
	}
	
	public List<PDFSalesItem> flatSellItemTOs(List<SoldItemTO> sellItems){
		
		List<PDFSalesItem> salesItems = new ArrayList<>();
		
		for(SoldItemTO sellItem : sellItems) {
			PDFSalesItem saleItem = new PDFSalesItem();
			saleItem.setQuantity(sellItem.getQuantity());
			saleItem.setName(sellItem.getSoldItem().getName());
			saleItem.setBarcode(sellItem.getSoldItem().getBarcode());
			saleItem.setWholesalePrice(sellItem.getSoldItem().getWholesalePrice());
			saleItem.setRetailPrice(sellItem.getSoldItem().getRetailPrice());
			saleItem.setSellDate(sellItem.getTransaction().getSellDate().toString());
			
			salesItems.add(saleItem);
		}
		
		return salesItems;
	}
	
	@SuppressWarnings("deprecation")
	private boolean dateMatches(Date date, LocalDate localDate) {
		String transactionDate = date.toString();		
		String selectedDate = localDate.toString();		
		boolean matches = transactionDate.toString().equals(selectedDate.toString());		
		if(!matches) {
		    System.out.println(date.toString() + " does NOT matche the selected date " + localDate.toString());
		} else {
			System.out.println(date.toString() + " does match the selected date" + localDate.toString());
		}
		
		return matches;
	}
	
	private boolean monthMatches(Date date, String month) {		
		SimpleDateFormat sdf = new SimpleDateFormat("MM");	
		Integer transactionMonth = Integer.parseInt(sdf.format(date));	
		Integer monthInt = Months.getValueOfMonth(month);	
		boolean matches = transactionMonth == monthInt;
		
		return matches;
	}
	
	
}
