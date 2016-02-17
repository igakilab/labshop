package jp.ac.oit.igakilab.labshop.dbcontroller.export;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;

import jp.sf.orangesignal.csv.CsvReader;
import jp.sf.orangesignal.csv.CsvWriter;

public abstract class DBCsvExporter {
	abstract Document toDocument(List<String> cells)
	throws CellConvertException;
	abstract List<String> toCells(Document doc)
	throws CellConvertException;

	public CsvReader createReader(String fileName)
	throws FileNotFoundException{
		return new CsvReader(new FileReader(fileName));
	}

	public CsvWriter createWriter(String fileName)
	throws IOException{
		return new CsvWriter(new FileWriter(fileName));
	}

	public void outputMessage(int row, String msg){
		System.out.println("[行:" + row + "] " + msg);
	}

	private DBCsvExportable dbc;

	public DBCsvExporter(DBCsvExportable d0){
		dbc = d0;
	}

	public void importCsv(String fileName)
	throws IOException{
		CsvReader reader= createReader(fileName);

		List<String> tmp;
		int cntRow = 0;
		while( (tmp = reader.readValues()) != null ){
			cntRow++;

			if( tmp.size() <= 0 ) continue;
			if( tmp.get(0).length() > 0 && tmp.get(0).charAt(0) == '#' ) continue;

			Document doc;
			try{
				doc = toDocument(tmp);
			}catch(CellConvertException e0){
				outputMessage(cntRow, "CellConvertException: " + e0.getMessage());
				continue;
			}

			if( !dbc.isInsertable(doc) ){
				outputMessage(cntRow, "挿入できません");
				continue;
			}

			dbc.getCollection().insertOne(doc);
		}

		reader.close();
	}

	public void exportCsv(String fileName)
	throws IOException{
		CsvWriter writer = createWriter(fileName);

		FindIterable<Document> list = dbc.getCollection().find();

		int cntRow = 0;
		for(Document doc : list){
			cntRow++;

			List<String> tmp;
			try{
				tmp = toCells(doc);
			}catch(CellConvertException e0){
				outputMessage(cntRow, "CellConvertException: " + e0.getMessage());
				continue;
			}

			writer.writeValues(tmp);
		}

		writer.close();
	}
}
