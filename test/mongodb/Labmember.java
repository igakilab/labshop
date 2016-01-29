import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.bson.Document;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Labmember{
	public static void main(String[] args){
		if( args.length > 0 ){
			String cmd = args[0];

			if( cmd.equals("add") ){
				if(args.length > 3){
					add(args[1], args[2], args[3]);
				}else{
					showHelp("add�R�}���h�̈������s�����Ă��܂�");
				}
			}else if( cmd.equals("change_team") ){
				if( args.length > 2 ){
					changeTeam(args[1], args[2]);
				}else{
					showHelp("change_team�R�}���h�̈������s�����Ă��܂�");
				}
			}else if( cmd.equals("change_message") ){
				if( args.length > 3 ){
					changeMessage(args[1], args[2]);
				}else{
					showHelp("change_message�R�}���h�̈������s�����Ă��܂�");
				}
			}else if( cmd.equals("delete") ){
				if( args.length > 1 ){
					delete(args[1]);
				}else{
					showHelp("delete�R�}���h�̈������s�����Ă��܂�");
				}
			}else if( cmd.equals("show") ){
				show();
			}else{
				showHelp("�Y������R�}���h������܂���");
			}
		}else{
			showHelp("�����𐳂����w�肵�ĉ�����");
		}
	}

	static void showHelp(String msg){
		List<String> msgs = new ArrayList<String>();

		msgs.add("�G���[: " + msg);
		msgs.add("-----�����̎w��@-----");
		msgs.add("java Labmember <�R�}���h> <����1> <����2> ...");
		msgs.add("�R�}���h�ꗗ: add <���O> <�`�[����> <���b�Z�[�W>");
		msgs.add("              change_team <���O> <�`�[����>");
		msgs.add("              change_message <���O> <���b�Z�[�W>");
		msgs.add("              delete <���O>");
		msgs.add("              show");

		for(String s : msgs){
			System.out.println(s);
		}
	}

	static void delete(String name){
		MongoConnector connection = new MongoConnector();
		MongoCollection<Document> col = connection.getCollection();
		
		col.deleteOne(Filters.eq("name", name));
		
		MongoConnector.printCollection(col);
		connection.close();
	}
	
	static void changeTeam(String name, String team){
		MongoConnector connection = new MongoConnector();
		MongoCollection<Document> col = connection.getCollection();
		Bson b_update = Updates.combine(
			Updates.set("team", team), Updates.set("modified", Calendar.getInstance().getTime()));
		
		col.updateOne(Filters.eq("name", name), b_update);
		
		MongoConnector.printCollection(col);
		connection.close();
	}
	
	static void changeMessage(String name, String msg){
		MongoConnector connection = new MongoConnector();
		MongoCollection<Document> col = connection.getCollection();
		
		col.updateOne(Filters.eq("name", name), Updates.set("msg", msg));
		
		MongoConnector.printCollection(col);
		connection.close();
	}
	
	static void add(String name, String team, String msg){
		MongoConnector connection = new MongoConnector();
		MongoCollection<Document> col = connection.getCollection();
		Document doc = new Document();
		doc.append("name", name);
		doc.append("team", team);
		doc.append("msg", msg);
		
		col.insertOne(doc);
		
		MongoConnector.printCollection(col);
		connection.close();
	}
	
	static void show(){
		MongoConnector connection = new MongoConnector();
		MongoCollection<Document> col = connection.getCollection();
		MongoConnector.printCollection(col);
		connection.close();
	}
}