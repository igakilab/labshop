import com.mongodb.*;

public class testMongoDb {

    public static void main(String[] args) {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("testchan");
            System.out.println("Connected");

            mongo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}