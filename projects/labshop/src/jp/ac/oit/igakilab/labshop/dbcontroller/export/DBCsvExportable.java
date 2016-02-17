package jp.ac.oit.igakilab.labshop.dbcontroller.export;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public interface DBCsvExportable {
	public boolean isInsertable(Document doc);
	public MongoCollection<Document> getCollection();
}
