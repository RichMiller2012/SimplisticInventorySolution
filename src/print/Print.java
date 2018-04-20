package print;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

public class Print {

	public static void printTransaction(String transaction) {
		String defaultPrinter = 
                PrintServiceLookup.lookupDefaultPrintService().getName();
              System.out.println("Default printer: " + defaultPrinter);
              PrintService service = PrintServiceLookup.lookupDefaultPrintService();

              String printString = transaction;
            		  
              InputStream is=null;
              try {
                  //printString+="\f";
                  System.out.println(printString);
                  is = new ByteArrayInputStream(printString.getBytes("UTF8"));                    
              } catch (UnsupportedEncodingException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
              }

              PrintRequestAttributeSet  pras = new HashPrintRequestAttributeSet();
              pras.add(new Copies(1));


              DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

              Doc doc = new SimpleDoc(is, flavor, null);

              DocPrintJob job = service.createPrintJob();

              PrintJobWatcher pjw = new PrintJobWatcher(job);
              try {
                  job.print(doc, pras);
              } catch (PrintException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
              pjw.waitForDone();
              try {
                  is.close();
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
	}
	
	public static PrintService getPrinterService(String name) {
	    try {
	        PrintService[] printServices = PrintServiceLookup
	                .lookupPrintServices(null, null);
	        PrintService printService = PrintServiceLookup
	                .lookupDefaultPrintService();
	        String printerName = "sss";// PrintConfig.getOsReceiptPrinterName();

	        for (int i = 0; i < printServices.length; i++) {
	            PrintService service = printServices[i];
	            System.out.println("service.getName() " + service.getName());
	            if ("Microsoft XPS Document Writer".equals(service.getName())) {
	                return service;
	            }
	        }
	        // job.setPrintService(printService);
	        // printToKitchen=false;
	    } catch (Exception e) {
	    }
	    return null;
	}
}
