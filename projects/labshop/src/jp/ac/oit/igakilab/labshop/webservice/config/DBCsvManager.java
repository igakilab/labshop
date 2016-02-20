package jp.ac.oit.igakilab.labshop.webservice.config;

import java.io.IOException;

import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.export.ItemDBCsvExporter;
import jp.ac.oit.igakilab.labshop.dbcontroller.export.MemberDBCsvExporter;

public class DBCsvManager {
	public void exportItemDB(String path)
	throws IOException{
		ItemDBController dbc = new ItemDBController();
		ItemDBCsvExporter exporter = new ItemDBCsvExporter(dbc);
		exporter.exportCsv(path);
		dbc.close();
	}

	public void importItemDB(String path)
	throws IOException{
		ItemDBController dbc = new ItemDBController();
		ItemDBCsvExporter exporter = new ItemDBCsvExporter(dbc);
		exporter.importCsv(path);
		dbc.close();
	}

	public void exportMemberDB(String path)
	throws IOException{
		MemberDBController dbc = new MemberDBController();
		MemberDBCsvExporter exporter = new MemberDBCsvExporter(dbc);
		exporter.exportCsv(path);
		dbc.close();
	}

	public void importMemberDB(String path)
	throws IOException{
		MemberDBController dbc = new MemberDBController();
		MemberDBCsvExporter exporter = new MemberDBCsvExporter(dbc);
		exporter.importCsv(path);
		dbc.close();
	}
}
