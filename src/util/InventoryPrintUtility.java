package util;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.InventoryTO;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import model.Inventory;
import model.PDFInventoryReportItem;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class InventoryPrintUtility extends Application {

	public void printInventoryReport(List<Inventory> items) {
		
		String fileName = "\\Inventory Report - ";
		
		String jasperFilePath = System.getProperty("user.dir") + "\\resources\\jasperreports\\\\inventoryReport.jasper";
		
		System.out.println(jasperFilePath);
		
		//get the path to the save location;
		String saveFilePath = Paths.get(System.getProperty("user.dir")).getParent().toString() + fileName;
		System.out.println(saveFilePath);
		
		List<PDFInventoryReportItem> pdfItems = flattenItems(items);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pdfItems);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ItemDataSource", dataSource);
		parameters.put("date", LocalDate.now().toString());
		
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, parameters, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, saveFilePath);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		HostServices hostServices = getHostServices();
		hostServices.showDocument(saveFilePath);
	}
	
	private List<PDFInventoryReportItem> flattenItems(List<Inventory> items){
		
		List<PDFInventoryReportItem> pdfItems = new ArrayList<>(items.size());
		
		for(Inventory item : items) {
			PDFInventoryReportItem pdfItem = new PDFInventoryReportItem();
			InventoryTO TO = new InventoryTO(item);
			pdfItem.setBarcode(TO.getBarcode());
			pdfItem.setName(TO.getName());
			pdfItem.setQuantity(TO.getQuantity());
			pdfItem.setRetailPrice(TO.getRetailPrice());
			pdfItem.setWholesalePrice(TO.getWholesalePrice());
			pdfItems.add(pdfItem);
		}
		
		return pdfItems;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub		
	}

}
