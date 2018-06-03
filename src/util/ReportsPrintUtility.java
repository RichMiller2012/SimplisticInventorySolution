package util;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.SoldItemTO;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.PDFSalesItem;
import model.SoldItem;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportsPrintUtility extends Application {
	
	//Saves PDF to a file and renders it in a browser window
	public  void printSalesReport(LocalDate date, List<SoldItemTO> soldItems, boolean daily) {
		
		//append the file name with the date
		String fileName = "\\Sales Report - ";
		
		if(daily) {
			fileName = fileName + date.toString() + ".pdf";
		} else {
			fileName = fileName + date.getMonth().toString() + "-" + date.now().getYear() + ".pdf";
		}

		//get the path to the compiled jasper template
		String jasperFilePath = System.getProperty("user.dir") + "\\resources\\jasperreports\\\\salesReport.jasper";
		System.out.println(jasperFilePath);
		
		//get the path to the save location;
		String saveFilePath = Paths.get(System.getProperty("user.dir")).getParent().toString() + fileName;
		System.out.println(saveFilePath);
			
		List<PDFSalesItem> pdfItems = flattenSoldItems(soldItems);
		
		String sourceFileNameJasper = jasperFilePath;
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pdfItems);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ItemDataSource", dataSource);
		parameters.putAll(addReportParameters(date, daily, pdfItems));
		
		//generate the PDF and save to the save location
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileNameJasper, parameters, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, saveFilePath);
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		//display the PDF in the default browser or host service
			HostServices hostServices = getHostServices();
			hostServices.showDocument(saveFilePath);
	}
	
	private Map<String, Object> addReportParameters(LocalDate date, boolean daily, List<PDFSalesItem> pdfItems) {
		Map<String, Object> reportParameters = new HashMap<>();
		
		//calculate the date or month to display
		if(daily) {
			reportParameters.put("date", date.toString());
			reportParameters.put("isDaily", Boolean.TRUE);
		} else {			
			reportParameters.put("month", date.getMonth().toString());
			reportParameters.put("isDaily", Boolean.FALSE);
		}
		
		//calculate the total profit
		Double totalProfit = 0.0;
		for(PDFSalesItem item : pdfItems) {
			totalProfit += item.getItemProfit();
		}
		reportParameters.put("totalProfit", totalProfit);
		
		return reportParameters;
	}

	private List<PDFSalesItem> flattenSoldItems(List<SoldItemTO> soldItems){
		
		List<PDFSalesItem> pdfItems = new ArrayList<>();
		
		for(SoldItemTO soldItem : soldItems) {
			PDFSalesItem pdfItem = new PDFSalesItem();
			pdfItem.setQuantity(soldItem.getQuantity());
			pdfItem.setName(soldItem.getSoldItem().getName());
			pdfItem.setBarcode(soldItem.getSoldItem().getBarcode());
			pdfItem.setWholesalePrice(soldItem.getWholesalePrice());
			pdfItem.setRetailPrice(soldItem.getRetailPrice());
			pdfItem.setSellDate(soldItem.getTransaction().getSellDate().toString());
			
			Double profit = (soldItem.getRetailPrice() - 
					soldItem.getWholesalePrice()) * soldItem.getQuantity();
			
			pdfItem.setItemProfit(profit);
			
			pdfItems.add(pdfItem);	
		}
		
		return pdfItems;
	}
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
