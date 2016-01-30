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
					showHelp("addコマンドの引数が不足しています");
				}
			}else if( cmd.equals("change_team") ){
				if( args.length > 2 ){
					changeTeam(args[1], args[2]);
				}else{
					showHelp("change_teamコマンドの引数が不足しています");
				}
			}else if( cmd.equals("change_message") ){
				if( args.length > 3 ){
					changeMessage(args[1], args[2]);
				}else{
					showHelp("change_messageコマンドの引数が不足しています");
				}
			}else if( cmd.equals("delete") ){
				if( args.length > 1 ){
					delete(args[1]);
				}else{
					showHelp("deleteコマンドの引数が不足しています");
				}
			}else if( cmd.equals("show") ){
				show();
			}else{
				showHelp("該当するコマンドがありません");
			}
		}else{
			showHelp("引数を正しく指定して下さい");
		}
	}

	static void showHelp(String msg){
		List<String> msgs = new ArrayList<String>();

		msgs.add("エラー: " + msg);
		msgs.add("-----引数の指定法-----");
		msgs.add("java Labmember <コマンド> <引数1> <引数2> ...");
		msgs.add("コマンド一覧: add <名前> <チーム名> <メッセージ>");
		msgs.add("              change_team <名前> <チーム名>");
		msgs.add("              change_message <名前> <メッセージ>");
		msgs.add("              delete <名前>");
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