package jp.ac.oit.igakilab.labshop.dbcontroller.export;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;

public class MemberDBCsvExporter extends DBCsvExporter{
	public MemberDBCsvExporter(MemberDBController dbc){
		super(dbc);
	}

	public Document toDocument(List<String> cells)
	throws CellConvertException{
		//[id], [name], [isAdmin]
		if( cells.size() < 3 ) {
			throw new CellConvertException("セルの長さが不足しています");
		}

		Document doc = new Document();
		try{
			doc.append("id", Integer.parseInt(cells.get(0)));
			doc.append("name", cells.get(1));
			doc.append("isAdmin", cells.get(2).equals("true"));
		}catch(NumberFormatException e0){
			throw new CellConvertException("数値変換に失敗しました");
		}

		System.out.println(doc.toString());
		return doc;
	}

	public List<String> toCells(Document doc)
	throws CellConvertException{
		//[id], [name], [isAdmin]
		Integer id = doc.getInteger("id");
		if( id == null ){
			throw new CellConvertException("idが見つかりません");
		}
		String name = doc.getString("name");
		if( name == null ){
			throw new CellConvertException("nameが見つかりません");
		}
		Boolean isAdmin = doc.getBoolean("isAdmin");
		if( isAdmin == null ){
			throw new CellConvertException("priceが見つかりません");
		}

		List<String> cells = new ArrayList<String>();
		cells.add(String.valueOf(id));
		cells.add(name);
		cells.add(String.valueOf(isAdmin));

		return cells;
	}
}
