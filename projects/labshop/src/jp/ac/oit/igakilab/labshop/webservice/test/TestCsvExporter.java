package jp.ac.oit.igakilab.labshop.webservice.test;

import java.io.IOException;

import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.export.ItemDBCsvExporter;

public class TestCsvExporter {
	public void exportItemDB()
	throws IOException{
		ItemDBController dbc = new ItemDBController();
		ItemDBCsvExporter exporter = new ItemDBCsvExporter(dbc);
		exporter.exportCsv("/var/log/tomcat7/labshop/itemdb.csv");
		dbc.close();
	}

	public void importItemDB()
	throws IOException{
		ItemDBController dbc = new ItemDBController();
		ItemDBCsvExporter exporter = new ItemDBCsvExporter(dbc);
		exporter.importCsv("/var/log/tomcat7/labshop/itemdb.csv");
		dbc.close();
	}
}
