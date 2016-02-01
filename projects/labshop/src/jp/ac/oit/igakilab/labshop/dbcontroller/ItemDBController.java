package jp.ac.oit.igakilab.labshop.dbcontroller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import jp.ac.oit.igakilab.labshop.item.ItemData;

public class ItemDBController extends DBConnector {
	/* static values */
	public static String DB_NAME = "labshop";
	public static String COLLECTION_NAME = "item";

	/* static methods */
	public static Document toDocument(ItemData data){
		Document doc;
		doc = new Document()
			.append("id", data.getId())
			.append("name", data.getName())
			.append("price", data.getPrice());

		return doc;
	}

	public static ItemData toItemData(Document doc){
		if( doc != null ){
			ItemData data = new ItemData(
				doc.getInteger("id", 0), doc.getString("name"));
			data.setPrice(doc.getInteger("price", 0));
			return data;
		}

		return null;
	}

	public static List<ItemData> toItemData(FindIterable<Document> list){
		List<ItemData> datas = new ArrayList<ItemData>();
		for(Document doc : list){
			datas.add(toItemData(doc));
		}
		return datas;
	}

	/* constructors */
	private MongoCollection<Document> collection;

	public ItemDBController(){
		super();
		initCollection();
	}

	public ItemDBController(String host, int port){
		super(host, port);
		initCollection();
	}

	public ItemDBController(DBConnector c0){
		super(c0);
		initCollection();
	}

	/* methods */
	public void initCollection(){
		if( collection == null ){
			collection = getClient().getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
		}
	}

	public boolean isIdRegisted(int id_val){
		return collection.count(Filters.eq("id", id_val)) > 0;
	}

	public List<ItemData> getAllItemList(){
		FindIterable<Document> result = collection.find();

		return toItemData(result);
	}

	public boolean addItem(ItemData data){
		if( !isIdRegisted(data.getId()) ){
			collection.insertOne(toDocument(data));
			return true;
		}
		return false;
	}

	public boolean updateItemData(ItemData data){
		System.out.println(Filters.eq("id", data.getId()));
		UpdateResult result = collection.replaceOne(
			Filters.eq("id", data.getId()), toDocument(data));

		return result.getMatchedCount() == 1;
	}

	public boolean deleteItem(int item_id){
		DeleteResult result = collection.deleteOne(
			Filters.eq("id", item_id));

		return result.getDeletedCount() == 1;
	}

	public ItemData getItemById(int item_id){
		return toItemData(
			collection.find(Filters.eq("id", item_id)).first()
		);
	}

	public void close(){
		super.close();
		collection = null;
	}
}