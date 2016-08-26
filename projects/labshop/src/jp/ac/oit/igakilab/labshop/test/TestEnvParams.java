package jp.ac.oit.igakilab.labshop.test;


public class TestEnvParams {
	public static void main(String[] args){
		String host = "localhost";
		int port = 27107;

		String ehost = System.getenv("LABSHOP_DB_HOST");
		if( ehost != null ){
			host = ehost;
		}
		String eport = System.getenv("LABSHOP_DB_PORT");
		if( eport != null ){
			try{
				port = Integer.parseInt(eport);
			}catch(NumberFormatException e0){
				port = 27017;
			}
		}

		System.out.println("HOST=" + host + ", PORT=" + port);
	}
}
