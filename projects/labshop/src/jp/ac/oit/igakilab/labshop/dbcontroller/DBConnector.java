package jp.ac.oit.igakilab.labshop.dbcontroller;

import com.mongodb.MongoClient;

public class DBConnector {
	//標準の接続先設定
	public static String DEFAULT_HOST = "localhost";
	public static int DEFAULT_HOST_PORT = 27017;


	/* インスタンス */
	private MongoClient client;
	private boolean closable;

	public DBConnector(){
		client = new MongoClient(DEFAULT_HOST, DEFAULT_HOST_PORT);
		closable = true;
	}

	public DBConnector(String host, int port){
		client = new MongoClient(host, port);
		closable = true;
	}

	public DBConnector(DBConnector conec){
		client = conec.getClient();
		closable = false;
	}

	public MongoClient getClient(){
		return client;
	}

	public boolean isClosed(){
		return (client == null);
	}

	public void close(){
		if( closable ){
			client.close();
			client = null;
			closable = false;
		}
	}
}
