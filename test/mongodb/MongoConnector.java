import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class MongoConnector{
	public static String DEFAULT_ADDRESS = "localhost";
	public static int DEFAULT_PORT = 27017;
	public static String DEFAULT_DB = "testchan";
	public static String DEFAULT_COLLECTION = "labmember";

	private MongoClient client;

	public static void printCollection(MongoCollection<Document> collec){
		FindIterable<Document> result = collec.find();

		System.out.println("---start------");
		for(Document doc : result ){
			System.out.println(doc.toJson());
		}
		System.out.println("---end--------");
	}

	public MongoConnector(){
		this(DEFAULT_ADDRESS, DEFAULT_PORT);
	}

	public MongoConnector(String addr, int port){
		client = new MongoClient(addr, port);
	}

	public MongoCollection<Document> getCollection(){
		return getCollection(DEFAULT_DB, DEFAULT_COLLECTION);
	}

	public MongoCollection<Document> getCollection(String db, String coll){
		if( client != null ){
			return client.getDatabase(db).getCollection(coll);
		}else{
			return null;
		}
	}

	public void close(){
		if( client != null ){
			client.close();
			client = null;
		}
	}
}