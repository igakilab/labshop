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

	public boolean isIdRegisted(int id_val){
		return collection.count(Filters.eq("id", id_val)) > 0;
	}

	public List<AccountData> getAllAccountList(){
		FindIterable<Document> result = collection.find();

		return toAccountData(result);
	}

	public AccountData addAccount(AccountData acc, boolean auto_num){
		if( auto_num ){
			acc.setId(popCounter());
		}
		if( !isIdRegisted(acc.getId()) ){
			collection.insertOne(toDocument(acc));
			return acc;
		}else{
			return null;
		}
	}

	public boolean updateAccount(AccountData data){
		UpdateResult result = collection.replaceOne(
			Filters.eq("id", data.getId()), toDocument(data));

		return result.getMatchedCount() == 1;
	}

	public boolean deleteAccount(int acc_id){
		DeleteResult result = collection.deleteOne(
			Filters.eq("id", acc_id));

		return result.getDeletedCount() == 1;
	}

	public AccountData getItemById(int acc_id){
		return toAccountData(
			collection.find(Filters.eq("id", acc_id)).first()
		);
	}

	public void refreshCounter(){
		CounterDBController cdb = new CounterDBController(this);
		Document id_max_row = collection.aggregate(
			Collections.singletonList(
				Aggregates.group(null, Accumulators.max("max_id", "$id"))
			)
		).first();
		int max_id = 0;
		if( id_max_row != null ) max_id = id_max_row.getInteger("max_id", 0);
		if( max_id >= COUNTER_MAX ){
			System.err.println("FATAL: ID GENERATE ERROR (in accountdbcontroller)");
		}

		DBCounter counter = new DBCounter(
			COUNTER_KEY, max_id, COUNTER_MIN, COUNTER_MAX);
		cdb.upsertDBCounter(counter);
	}

	public int popCounter(){
		CounterDBController cdb = new CounterDBController(this);
		if( cdb.isKeyRegisted(COUNTER_KEY) ){
			refreshCounter();
		}
		int cnt = cdb.popCounter(COUNTER_KEY);
		if( isIdRegisted(cnt) ){
			refreshCounter();
			cnt = cdb.popCounter(COUNTER_KEY);
		}
		return cnt;
	}

	public MongoCollection<Document> getCollection(){
		return collection;
	}

	public void close(){
		super.close();
		collection = null;
	}
}
