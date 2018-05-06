package util;

import java.util.ArrayList;
import java.util.List;

import dao.InventoryDAO;
import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.application.Application;
import javafx.stage.Stage;
import print.SalesReportPrint;

public class Seed extends Application {

	private  InventoryDAO dao = new InventoryDAO();
	
	private  SalesReportPrint pdfPrintService = new SalesReportPrint();
	
	public  void seed() {
		
		List<InventoryTO> seedItems = new ArrayList<>();
		
		InventoryTO startItem = new InventoryTO();
		startItem.setName("Canned Pineapple");
		startItem.setBarcode("3890000102");
		startItem.setQuantity(44);
		startItem.setWholesalePrice(1.00);
		startItem.setRetailPrice(2.00);
		startItem.setLowLevelAlert(15);
		
		InventoryTO item1111 = new InventoryTO();
		item1111.setName("Vitamin Water");
		item1111.setBarcode("8616211000");
		item1111.setQuantity(124);
		item1111.setWholesalePrice(11.00);
		item1111.setRetailPrice(112.00);
		item1111.setLowLevelAlert(15);
		
		InventoryTO item2222 = new InventoryTO();
		item2222.setName("Orange Juice Can");
		item2222.setBarcode("4850030569");
		item2222.setQuantity(24);
		item2222.setWholesalePrice(2.00);
		item2222.setRetailPrice(22.00);
		item2222.setLowLevelAlert(15);
		
		InventoryTO item3333 = new InventoryTO();
		item3333.setName("Zinc Supplement");
		item3333.setBarcode("1579402520");
		item3333.setQuantity(34);
		item3333.setWholesalePrice(3.00);
		item3333.setRetailPrice(33.00);
		item3333.setLowLevelAlert(15);
		
		InventoryTO item4444 = new InventoryTO();
		item4444.setName("Vasoline 3.75oz");
		item4444.setBarcode("0521232600");
		item4444.setQuantity(44);
		item4444.setWholesalePrice(4.00);
		item4444.setRetailPrice(44.00);
		item4444.setLowLevelAlert(15);
		
		InventoryTO item5555 = new InventoryTO();
		item5555.setName("San Migual 40oz");
		item5555.setBarcode("5153800209");
		item5555.setQuantity(50);
		item5555.setWholesalePrice(5.00);
		item5555.setRetailPrice(55.00);
		item5555.setLowLevelAlert(15);
		
		seedItems.add(startItem);
		seedItems.add(item1111);
		seedItems.add(item2222);
		seedItems.add(item3333);
		seedItems.add(item4444);
		seedItems.add(item5555);
		
		for(InventoryTO item : seedItems) {
			dao.saveInventoryItem(item);
		}
		
		List<SoldItemTO> printedSalesItems = new ArrayList<>();
		
		TransactionsTO trans1 = new TransactionsTO();
		SoldItemTO trans1SellItem1 = new SoldItemTO();
		trans1.setTotal(105.00);
		trans1SellItem1.setSoldItem(item1111);
		trans1SellItem1.setQuantity(1);
		trans1SellItem1.setWholesalePrice(item1111.getWholesalePrice());
		trans1SellItem1.setRetailPrice(item1111.getRetailPrice());
		trans1SellItem1.setTransaction(trans1);
		SoldItemTO trans1SellItem2 = new SoldItemTO();
		trans1SellItem2.setSoldItem(item2222);
		trans1SellItem2.setQuantity(2);
		trans1SellItem2.setWholesalePrice(item2222.getWholesalePrice());
		trans1SellItem2.setRetailPrice(item2222.getRetailPrice());
		trans1SellItem2.setTransaction(trans1);
		trans1.getSoldItems().add(trans1SellItem1);
		trans1.getSoldItems().add(trans1SellItem2);
		
		printedSalesItems.add(trans1SellItem1);
		printedSalesItems.add(trans1SellItem2);

		
		TransactionsTO trans2 = new TransactionsTO();
		trans2.setTotal(205.00);
		SoldItemTO trans2SellItem1 = new SoldItemTO();
		trans2SellItem1.setSoldItem(item3333);
		trans2SellItem1.setQuantity(3);
		trans2SellItem1.setWholesalePrice(item3333.getWholesalePrice());
		trans2SellItem1.setRetailPrice(item3333.getRetailPrice());
		trans2SellItem1.setTransaction(trans2);
		SoldItemTO trans2SellItem2 = new SoldItemTO();
		trans2SellItem2.setSoldItem(item4444);
		trans2SellItem2.setQuantity(4);
		trans2SellItem2.setWholesalePrice(item4444.getWholesalePrice());
		trans2SellItem2.setRetailPrice(item4444.getRetailPrice());
		trans2SellItem2.setTransaction(trans2);
		trans2.getSoldItems().add(trans2SellItem1);
		trans2.getSoldItems().add(trans2SellItem2);
		
		printedSalesItems.add(trans2SellItem1);
		printedSalesItems.add(trans2SellItem2);


		TransactionsTO trans3 = new TransactionsTO();
		trans3.setTotal(55.00);
		SoldItemTO trans3SellItem1 = new SoldItemTO();
		trans3SellItem1.setSoldItem(item5555);
		trans3SellItem1.setQuantity(10);
		trans3SellItem1.setWholesalePrice(item5555.getWholesalePrice());
		trans3SellItem1.setRetailPrice(item5555.getRetailPrice());
		trans3SellItem1.setTransaction(trans3);
		trans3.getSoldItems().add(trans3SellItem1);
		
		printedSalesItems.add(trans3SellItem1);
		
		//pdfPrintService.printSalesReport(LocalDate.now(), printedSalesItems);
		//printSalesReport(printedSalesItems);
	
		dao.commitTransaction(trans1);
		dao.commitTransaction(trans2);
		dao.commitTransaction(trans3);		
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		seed();	
	}
	
	
	
	
}
