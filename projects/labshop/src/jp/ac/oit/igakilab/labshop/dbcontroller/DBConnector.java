package jp.ac.oit.igakilab.labshop.dbcontroller;

import com.mongodb.MongoClient;

public class DBConnector {
	//標準の接続先設定
	public static String DEFAULT_HOST = "localhost";
	public static int DEFAULT_HOST_PORT = 27017;

	private MongoClient createDefaultClient(){
		String host = DEFAULT_HOST;
		int port = DEFAULT_HOST_PORT;

		String ehost = System.getenv("LABSHOP_DB_HOST");
		if( ehost != null ){
			host = ehost;
		}
		String eport = System.getenv("LABSHOP_DB_PORT");
		if( eport != null ){
			try{
				port = Integer.parseInt(eport);
			}catch(NumberFormatException e0){
				port = DEFAULT_HOST_PORT;
			}
		}

		return new MongoClient(host, port);
	}


	/* インスタンス */
	private MongoClient client;
	private boolean closable;

	public DBConnector(){
		client = createDefaultClient();
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
