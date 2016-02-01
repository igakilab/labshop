package jp.ac.oit.igakilab.labshop.dbcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

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
		if( doc != null ){
			AccountData data = new AccountData(
				doc.getInteger("id", 0), doc.getDate("timestamp"),
				doc.getInteger("memberId", 0), doc.getInteger("itemId", 0),
				doc.getInteger("sellPrice", 0));
			return data;
		}

		return null;
	}

	public static List<AccountData> toAccountData(FindIterable<Document> list){
		List<AccountData> datas = new ArrayList<AccountData>();
		for(Document doc : list){
			datas.add(toAccountData(doc));
		}
		return datas;
	}

	public static int popCounter(DBConnector con){
		CounterDBController dbc = new CounterDBController(con);
		if( !dbc.isKeyRegisted(COUNTER_KEY) ){
			int max_id = new AccountDBController(con).getIdMax();
			dbc.createCounter(
				COUNTER_KEY, Math.max(COUNTER_MIN, max_id), COUNTER_MIN, COUNTER_MAX);
		}
		return dbc.popCounter(COUNTER_KEY);
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

	public int getIdMax(){
		Document id_max = collection.aggregate(
			Collections.singletonList(
				Aggregates.group(null, Accumulators.max("max_id", "$id"))
			)
		).first();

		if( id_max != null ){
			return id_max.getInteger("max_id", 0);
		}else{
			return 0;
		}
	}

	public List<AccountData> getAllAccountList(){
		FindIterable<Document> result = collection.find();

		return toAccountData(result);
	}

	public boolean updateItemData(AccountData data){
		System.out.println(Filters.eq("id", data.getId()));
		UpdateResult result = collection.replaceOne(
			Filters.eq("id", data.getId()), toDocument(data));

		return result.getMatchedCount() == 1;
	}

	public boolean deleteMember(int acc_id){
		DeleteResult result = collection.deleteOne(
			Filters.eq("id", acc_id));

		return result.getDeletedCount() == 1;
	}

	public AccountData getItemById(int acc_id){
		return toAccountData(
			collection.find(Filters.eq("id", acc_id)).first()
		);
	}

	public MongoCollection<Document> getCollection(){
		return collection;
	}

	public void close(){
		super.close();
		collection = null;
	}
}
