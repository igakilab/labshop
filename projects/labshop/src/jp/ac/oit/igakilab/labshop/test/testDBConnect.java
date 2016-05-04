package jp.ac.oit.igakilab.labshop.test;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class testDBConnect {
	public static void main(String[] args){
		MongoClient client = new MongoClient("localhost", 27017);
		MongoCollection<Document> collection =
			client.getDatabase("labshop").getCollection("member");
		FindIterable<Document> result = collection.find();

		/*for(Document doc : result){
			System.out.println(doc.toString());
		}*/

		client.close();
		return;
	}
}
