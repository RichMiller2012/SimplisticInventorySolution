package print;

import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.apache.tools.ant.util.FileUtils;
import org.springframework.core.io.ClassPathResource;

import entity.SoldItemTO;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import model.PDFSalesItem;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import service.ReportsService;
import util.Months;

public class SalesReportPrint  extends Application {

	private static final long serialVersionUID = 1L;
	
	ReportsService reportsService = new ReportsService();
	JasperPrint print;
	
	public void printSalesReport(LocalDate date, List<SoldItemTO> sales) {
		
		List<PDFSalesItem> data = reportsService.flatSellItemTOs(sales);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
		HashMap<String, Object> reportParameters = setUpReportParamaters();	
		String reportTemplate = "C:\\Users\\Rich\\eclipse-workspace\\SimplisticInventorySolution\\resources\\jasperreports\\salesReport.jrxml";
	
		
		 System.out.println("directory of jrxml: " + reportTemplate);
		
		printJasperReport(reportTemplate, reportParameters, dataSource);
	}
	
	public void printSalesReport(Months month, List<SoldItemTO> sales) {
		
		List<PDFSalesItem> data = reportsService.flatSellItemTOs(sales);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sales);
		HashMap<String, Object> reportParameters = setUpReportParamaters();	
		String reportTemplate = "C:\\Users\\Rich\\eclipse-workspace\\SimplisticInventorySolution\\resources\\jasperreports\\salesReport.jrxml";
		
		printJasperReport(reportTemplate, reportParameters, dataSource);
	}
	
	@Override
	public void start(final Stage stage) {
		HostServices hostServices = getHostServices();
		//hostServices.
		
	}
	
	private void printJasperReport(String sourceFileName, HashMap<String, Object> parameters, JRBeanCollectionDataSource dataSource) {
		
		try {
			
			File file = new File(sourceFileName);
			if(file.exists()) {
				System.out.print("File found");
				
			} else {
				System.out.println("File not found");
				return;
			}
			
			final InputStream is = new FileInputStream(file);
						
			try {
				//JasperReport jasperReport = JasperCompileManager.compileReport(sourceFileName);
			    //JasperFillManager.fillReportToFile(jasperReport, "C:\\Users\\Rich\\sample_report.pdf", parameters, dataSource);
				//JasperExportManager.exportReportToPdfFile(printFileName, "C:\\sample_report.pdf");
			} catch (Exception e) {
				System.out.println("An error happened when attempting to create a file for the PDF");
				e.printStackTrace();
			}
			
			
			
			
			
			//final InputStream is = getClass().getResourceAsStream(sourceFileName);
			JasperReport jasperReport2 = JasperCompileManager.compileReport(is);
			this.print = JasperFillManager.fillReport(jasperReport2, parameters, dataSource);
			JRViewer viewer = new JRViewer(print);
			viewer.setOpaque(true);
	        viewer.setVisible(true);
	        	       
	       System.out.println("");
		} catch(JRException jre) {
			System.out.println("There was an error attempting to print the PDF");
			jre.printStackTrace();
		} catch(Exception e) {
			System.out.println("An unknown error happened while trying to print out the PDF");
			e.printStackTrace();
		}
	}
	
	private HashMap<String, Object> setUpReportParamaters() {
		return new HashMap<>();
	}
}
