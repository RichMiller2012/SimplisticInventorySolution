package util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.InventoryDAO;
import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import model.PDFSalesItem;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import print.SalesReportPrint;

public class Seed extends Application {

	private  InventoryDAO dao = new InventoryDAO();
	
	private  SalesReportPrint pdfPrintService = new SalesReportPrint();
	
	public  void seed() {
		
		List<InventoryTO> seedItems = new ArrayList<>();
		
		InventoryTO startItem = new InventoryTO();
		startItem.setName("Start Item");
		startItem.setBarcode("4321");
		startItem.setQuantity(40);
		startItem.setWholesalePrice(1.00);
		startItem.setRetailPrice(2.00);
		
		InventoryTO item1111 = new InventoryTO();
		item1111.setName("item1111");
		item1111.setBarcode("1111");
		item1111.setQuantity(11);
		item1111.setWholesalePrice(11.00);
		item1111.setRetailPrice(112.00);
		
		InventoryTO item2222 = new InventoryTO();
		item2222.setName("item2222");
		item2222.setBarcode("2222");
		item2222.setQuantity(22);
		item2222.setWholesalePrice(2.00);
		item2222.setRetailPrice(22.00);
		
		InventoryTO item3333 = new InventoryTO();
		item3333.setName("item3333");
		item3333.setBarcode("3333");
		item3333.setQuantity(33);
		item3333.setWholesalePrice(3.00);
		item3333.setRetailPrice(33.00);
		
		InventoryTO item4444 = new InventoryTO();
		item4444.setName("item4444");
		item4444.setBarcode("4444");
		item4444.setQuantity(44);
		item4444.setWholesalePrice(4.00);
		item4444.setRetailPrice(44.00);
		
		InventoryTO item5555 = new InventoryTO();
		item5555.setName("item5555");
		item5555.setBarcode("5555");
		item5555.setQuantity(55);
		item5555.setWholesalePrice(5.00);
		item5555.setRetailPrice(55.00);
		
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
		trans1SellItem1.setTransaction(trans1);
		SoldItemTO trans1SellItem2 = new SoldItemTO();
		trans1SellItem2.setSoldItem(item2222);
		trans1SellItem2.setQuantity(2);
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
		trans2SellItem1.setTransaction(trans2);
		SoldItemTO trans2SellItem2 = new SoldItemTO();
		trans2SellItem2.setSoldItem(item4444);
		trans2SellItem2.setQuantity(4);
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
		trans3SellItem1.setTransaction(trans3);
		trans3.getSoldItems().add(trans3SellItem1);
		
		printedSalesItems.add(trans3SellItem1);
		
		//pdfPrintService.printSalesReport(LocalDate.now(), printedSalesItems);
		//printSalesReport(printedSalesItems);

		
		dao.commitTransaction(trans1);
		dao.commitTransaction(trans2);
		dao.commitTransaction(trans3);	
	
	}
	
//	//Saves PDF to a file and renders it in a browser window
//	private void printSalesReport(List<SoldItemTO> soldItems) {
//		
//		//get the path to the compiled jasper template
//		String jasperFilePath = System.getProperty("user.dir") + "\\resources\\jasperreports\\\\salesReport.jasper";
//		System.out.println(jasperFilePath);
//		
//		//get the path to the save location;
//		String saveFilePath = Paths.get(System.getProperty("user.dir")).getParent().toString() + "\\test.pdf";
//		System.out.println(saveFilePath);
//			
//		String sourceFileNameJasper = jasperFilePath;
//		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(flattenSoldItems(soldItems));
//		
//		Map<String, Object> parameters = new HashMap<>();
//		
//		//generate the PDF and save to the save location
//		try {
//			JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileNameJasper, parameters, dataSource);
//			JasperExportManager.exportReportToPdfFile(jasperPrint, saveFilePath);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//				
//		//display the PDF in the default browser or host service
//		HostServices hostServices = getHostServices();
//		hostServices.showDocument(saveFilePath);
//	}
	
	
	
//	private static List<PDFSalesItem> flattenSoldItems(List<SoldItemTO> soldItems){
//		
//		List<PDFSalesItem> pdfItems = new ArrayList<>();
//		
//		for(SoldItemTO soldItem : soldItems) {
//			PDFSalesItem pdfItem = new PDFSalesItem();
//			pdfItem.setQuantity(soldItem.getQuantity());
//			pdfItem.setName(soldItem.getSoldItem().getName());
//			pdfItem.setBarcode(soldItem.getSoldItem().getBarcode());
//			pdfItem.setWholesalePrice(soldItem.getSoldItem().getWholesalePrice());
//			pdfItem.setRetailPrice(soldItem.getSoldItem().getRetailPrice());
//			pdfItem.setSellDate(soldItem.getTransaction().getSellDate().toString());
//			
//			pdfItems.add(pdfItem);	
//		}
//		
//		return pdfItems;
//	}



	@Override
	public void start(Stage arg0) throws Exception {
		seed();	
	}
	
	
	
	
}
