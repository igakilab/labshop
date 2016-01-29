import com.mongodb.*;
import com.mongodb.client.*;

public class testMongoDb {

    public static void main(String[] args) {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            MongoDatabase db = mongo.getDatabase("testchan");   
            System.out.println("Connected");

            MongoIterable<String> colls = db.listCollectionNames();
            MongoCursor<String> itr = colls.iterator();
            System.out.print("COLLECTIONS: ");
            for(String s : colls ){
                System.out.println(" > > >" + s);
            }

            mongo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}