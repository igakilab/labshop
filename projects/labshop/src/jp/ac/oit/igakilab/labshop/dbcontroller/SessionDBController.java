package jp.ac.oit.igakilab.labshop.dbcontroller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import jp.ac.oit.igakilab.labshop.sessions.SessionData;

public class SessionDBController extends DBConnector {
	/* static values */
	public static String DB_NAME = "labshop";
	public static String COLLECTION_NAME = "session";

	/* static methods */
	public static SessionData toSessionData(Document doc){
		SessionData data = new SessionData(
			doc.getString("id"),
			doc.getInteger("memberId", 0),
			doc.getDate("dueDate"));

		return data;
	}

	public static List<SessionData> toSessionData(FindIterable<Document> result){
		List<SessionData> datas = new ArrayList<SessionData>();
		for(Document doc : result){
			datas.add(toSessionData(doc));
		}
		return datas;
	}

	public static Document toDocument(SessionData data){
		Document doc = new Document();
		doc.append("id", data.getId())
			.append("memberId", data.getMemberId())
			.append("dueDate", data.getDueDate());

		return doc;
	}

	/* constructors */
	private MongoCollection<Document> collection;

	public SessionDBController(){
		super();
		initCollection();
	}

	public SessionDBController(String host, int port){
		super(host, port);
		initCollection();
	}

	public SessionDBController(DBConnector c0){
		super(c0);
		initCollection();
	}

	/* methods */
	public void initCollection(){
		if( collection == null ){
			collection = getClient().getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
		}
	}

	public boolean isIdRegisted(String id_val){
		return collection.count(Filters.eq("id", id_val)) > 0;
	}

	public List<SessionData> getAllSessionList(){
		FindIterable<Document> result = collection.find();

		return toSessionData(result);
	}

	public boolean addSession(SessionData data){
		if( !isIdRegisted(data.getId()) ){
			collection.insertOne(toDocument(data));
			return true;
		}
		return false;
	}

	public boolean updateSessionData(SessionData data){
		UpdateResult result = collection.replaceOne(
			Filters.eq("id", data.getId()), toDocument(data));

		return result.getMatchedCount() == 1;
	}

	public boolean deleteSession(String sid){
		DeleteResult result = collection.deleteOne(
			Filters.eq("id", sid));

		return result.getDeletedCount() == 1;
	}

	public SessionData getSessionById(String sid){
		FindIterable<Document> result =
			collection.find(Filters.eq("id", sid));

		Document doc = result.iterator().tryNext();
		if( doc != null ){
			return toSessionData(doc);
		}else{
			return null;
		}
	}

	public MongoCollection<Document> getCollection(){
		return collection;
	}

	public boolean isInsertable(Document doc){
		String id = doc.getString("id");
		if( id != null && !isIdRegisted(id) ){
			return true;
		}
		return false;
	}

	public int cleanExpired(){
		Date now = Calendar.getInstance().getTime();
		DeleteResult result =
			collection.deleteMany(Filters.lt("dueDate", now));

		return (int)result.getDeletedCount();
	}

	public void close(){
		super.close();
		collection = null;
	}
}
