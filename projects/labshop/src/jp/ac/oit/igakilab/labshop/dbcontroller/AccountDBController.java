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
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class AccountDBController extends DBConnector {
	/* static values */
	public static String DB_NAME = "labshop";
	public static String COLLECTION_NAME = "account";
	public static String COUNTER_KEY = "account";
	public static int COUNTER_MIN = 10000000;
	public static int COUNTER_MAX = 99999999;

	/* static methods */
	public static Document toDocument(AccountData data){
		Document doc;
		doc = new Document()
			.append("id", data.getId())
			.append("timestamp", data.getTimestamp())
			.append("memberId", data.getMemberId())
			.append("itemId", data.getItemId())
			.append("sellPrice", data.getSellPrice());

		return doc;
	}

	public static AccountData toAccountData(Document doc){
		AccountData data = new AccountData(
			doc.getInteger("id", 0), doc.getDate("timestamp"),
			doc.getInteger("memberId", 0), doc.getInteger("itemId", 0),
			doc.getInteger("sellPrice", 0));

		return data;
	}

	public static List<AccountData> toItemData(FindIterable<Document> list){
		List<AccountData> datas = new ArrayList<AccountData>();
		for(Document doc : list){
			datas.add(toAccountData(doc));
		}
		return datas;
	}

	public static int popCounter(DBConnector client){
		CounterDBController cdb = new CounterDBController(client);
		if( !cdb.isKeyRegisted(COUNTER_KEY) ){

		}
	}

	/* constructors */
	private MongoCollection<Document> collection;

	public AccountDBController(){
		super();
		initCollection();
	}

	public AccountDBController(String host, int port){
		super(host, port);
		initCollection();
	}

	public AccountDBController(DBConnector c0){
		super(c0);
		initCollection();
	}

	/* methods */
	public void initCollection(){
		if( collection == null ){
			collection = getClient().getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
		}
	}

	public void createCounter(){

	}

	public List<ItemData> getAllItemList(){
		FindIterable<Document> result = collection.find();

		return toItemData(result);
	}

	public boolean updateItemData(ItemData data){
		System.out.println(Filters.eq("id", data.getId()));
		UpdateResult result = collection.replaceOne(
			Filters.eq("id", data.getId()), toDocument(data));

		return result.getMatchedCount() == 1;
	}

	public boolean deleteMember(int item_id){
		DeleteResult result = collection.deleteOne(
			Filters.eq("id", item_id));

		return result.getDeletedCount() == 1;
	}

	public ItemData getItemById(int item_id){
		FindIterable<Document> result =
			collection.find(Filters.eq("id", item_id));

		Document doc = result.iterator().tryNext();
		if( doc != null ){
			return toItemData(doc);
		}else{
			return null;
		}
	}

	public void close(){
		super.close();
		collection = null;
	}
}
