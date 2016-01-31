package jp.ac.oit.igakilab.labshop.dbcontroller;

import com.mongodb.MongoClient;

public class DBConnector {
	//標準の接続先設定
	public static String DEFAULT_HOST = "localhost";
	public static int DEFAULT_HOST_PORT = 27017;


	/* インスタンス */
	private MongoClient client;

	public DBConnector(){
		client = new MongoClient(DEFAULT_HOST, DEFAULT_HOST_PORT);
	}

	public DBConnector(String host, int port){
		client = new MongoClient(host, port);
	}

	public DBConnector(DBConnector conec){
		client = conec.getClient();
	}

	public MongoClient getClient(){
		return client;
	}

	public boolean isClosed(){
		return (client == null);
	}

	public void close(){
		client.close();
		client = null;
	}
}
