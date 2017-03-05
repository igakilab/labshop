package jp.ac.oit.igakilab.labshop.dbcontroller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import jp.ac.oit.igakilab.labshop.dbcontroller.export.DBCsvExportable;
import jp.ac.oit.igakilab.labshop.member.MemberData;

public class MemberDBController extends DBConnector
implements DBCsvExportable{
	/* static values */
	public static String DB_NAME = "labshop";
	public static String COLLECTION_NAME = "member";

	/* static methods */
	public static MemberData toMemberData(Document doc){
		MemberData data = new MemberData(
			doc.getInteger("id", 0), doc.getString("name"));

		data.setIsAdmin(doc.getBoolean("isAdmin", false));
		data.setIsPrimary(doc.getBoolean("isPrimary", false));
		data.setBalance(doc.getInteger("balance", 0));
		String ph = doc.getString("passwordHash");
		if( ph != null ){
			data.setPasswordHash(ph);
		}

		return data;
	}

	public static List<MemberData> toMemberData(FindIterable<Document> result){
		List<MemberData> datas = new ArrayList<MemberData>();
		for(Document doc : result){
			datas.add(toMemberData(doc));
		}
		return datas;
	}

	public static Document toDocument(MemberData data){
		Document doc = new Document();
		doc.append("id", data.getId())
			.append("name", data.getName())
			.append("isAdmin", data.getIsAdmin())
			.append("isPrimary", data.getIsPrimary())
			.append("balance", data.getBalance());
		if( data.getPasswordHash() != null ){
			doc.append("passwordHash", data.getPasswordHash());
		}

		return doc;
	}

	/* constructors */
	private MongoCollection<Document> collection;

	public MemberDBController(){
		super();
		initCollection();
	}

	public MemberDBController(String host, int port){
		super(host, port);
		initCollection();
	}

	public MemberDBController(DBConnector c0){
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

	public boolean canDataInsert(MemberData data){
		if( isIdRegisted(data.getId()) ){
			return false;
		}
		return true;
	}

	public List<MemberData> getAllMemberList(){
		FindIterable<Document> result = collection.find();

		return toMemberData(result);
	}

	public List<MemberData> getPrimaryMemberList(){
		Bson query = Filters.eq("isPrimary", true);
		FindIterable<Document> result = collection.find(query);

		return toMemberData(result);
	}

	public boolean addMember(MemberData data){
		if( canDataInsert(data) ){
			collection.insertOne(toDocument(data));
			return true;
		}
		return false;
	}

	public boolean recharge(int memberId, int price) {
		if (isIdRegisted(memberId)) {
			collection.updateOne(new Document("id", memberId), new Document("$inc", new Document("balance", price)));
			return true;
		}
		return false;
	}

	public boolean updateMemberData(MemberData data){
		System.out.println(Filters.eq("id", data.getId()));
		UpdateResult result = collection.replaceOne(
			Filters.eq("id", data.getId()), toDocument(data));

		return result.getMatchedCount() == 1;
	}

	public boolean deleteMember(int member_id){
		DeleteResult result = collection.deleteOne(
			Filters.eq("id", member_id));

		return result.getDeletedCount() == 1;
	}

	public MemberData getMemberById(int member_id){
		FindIterable<Document> result =
			collection.find(Filters.eq("id", member_id));

		Document doc = result.iterator().tryNext();
		if( doc != null ){
			return toMemberData(doc);
		}else{
			return null;
		}
	}

	public MongoCollection<Document> getCollection(){
		return collection;
	}

	public boolean isInsertable(Document doc){
		Integer id = doc.getInteger("id");
		if( id != null && !isIdRegisted(id) ){
			return true;
		}
		return false;
	}

	public void close(){
		super.close();
		collection = null;
	}
}
