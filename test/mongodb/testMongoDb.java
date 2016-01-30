import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.*;

public class testMongoDb {

    public static void main(String[] args) {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            MongoDatabase db = mongo.getDatabase("testchan");   
            MongoCollection<Document> coll = db.getCollection("labmember");
            //MongoCollection<Document> collection = database.getCollection("test");

            Document doc = new Document();
            doc.append("name", "kitaba");
            doc.append("team", "marshmallow-warriors");

            coll.insertOne(doc);

            mongo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}