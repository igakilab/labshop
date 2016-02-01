package jp.ac.oit.igakilab.labshop.dbcontroller;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class CounterDBController extends DBConnector{
	/* static values */
	public static String DB_NAME = "labshop";
	public static String COLLECTION_NAME = "counter";

	/* static methods */
	public static Document toDocument(DBCounter cnt){
		Document doc;
		doc = new Document()
			.append("key", cnt.getKey())
			.append("counter", cnt.getCounter())
			.append("minValue", cnt.getMinValue())
			.append("maxValue", cnt.getMaxValue());

		return doc;
	}

	public static DBCounter toDBCounter(Document doc){
		if( doc != null ){
			DBCounter cnt = new DBCounter(
				doc.getString("key"),
				doc.getInteger("counter", 0),
				doc.getInteger("minValue", 0),
				doc.getInteger("maxValue", Integer.MAX_VALUE));
			return cnt;
		}

		return null;
	}

	/* constructors */
	private MongoCollection<Document> collection;

	public CounterDBController(){
		super();
		initCollection();
	}

	public CounterDBController(String host, int port){
		super(host, port);
		initCollection();
	}

	public CounterDBController(DBConnector c0){
		super(c0);
		initCollection();
	}

	/* methods */
	public void initCollection(){
		if( collection == null ){
			collection = getClient().getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
		}
	}

	public boolean isKeyRegisted(String key_val){
		return collection.count(Filters.eq("key", key_val)) > 0;
	}

	public List<DBCounter> getAllCounterList(){
		FindIterable<Document> result = collection.find();
		List<DBCounter> list = new ArrayList<DBCounter>();

		for(Document doc : result){
			list.add(toDBCounter(doc));
		}

		return list;
	}

	public void createCounter(String key, int init_c, int min, int max){
		if( !isKeyRegisted(key) ){
			DBCounter counter = new DBCounter(key, init_c, min, max);
			collection.insertOne(toDocument(counter));
		}
	}

	public void destroyConter(String key){
		if( isKeyRegisted(key) ){
			collection.deleteOne(Filters.eq("key", key));
		}
	}

	public void upsertDBCounter(DBCounter counter){
		if( isKeyRegisted(counter.getKey()) ){
			collection.replaceOne(Filters.eq("key", counter.getKey()), toDocument(counter));
		}else{
			collection.insertOne(toDocument(counter));
		}
	}

	public DBCounter getDBCounter(String key_val){
		return toDBCounter(
			collection.find(Filters.eq("key", key_val)).first()
		);
	}

	public int retrieveCounter(String key){
		if( isKeyRegisted(key) ){
			DBCounter counter = toDBCounter(
				collection.find(Filters.eq("key", key)).first()
			);

			if( counter != null ){
				return counter.getCounter();
			}
		}
		return -1;
	}

	public int popCounter(String key){
		if( isKeyRegisted(key) ){
			DBCounter counter = toDBCounter(
				collection.findOneAndUpdate(Filters.eq("key", key), Updates.inc("counter", 1)));
			int tmp = counter.getCounter();

			if( tmp >= counter.getMaxValue() ){
				int min = counter.getMinValue();
				collection.findOneAndUpdate(Filters.eq("key", key), Updates.set("counter", min));
			}
			return tmp;
		}
		return -1;
	}

	public void resetCounter(String key){
		if( isKeyRegisted(key) ){
			DBCounter counter = getDBCounter(key);
			counter.reset();
			upsertDBCounter(counter);
		}
	}

	public void close(){
		super.close();
		collection = null;
	}
}
