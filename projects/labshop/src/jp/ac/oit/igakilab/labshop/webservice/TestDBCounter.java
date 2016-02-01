package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.dbcontroller.CounterDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.DBCounter;

public class TestDBCounter {
	public void createDBCounter(String key, int ini, int min, int max){
		CounterDBController cdb = new CounterDBController();

		cdb.createCounter(key, ini, min, max);
		cdb.close();
	}

	public DBCounter[] getDBCounterList(){
		CounterDBController cdb = new CounterDBController();
		DBCounter[] list = cdb.getAllCounterList().toArray(new DBCounter[0]);

		cdb.close();
		return list;
	}

	public int popCounter(String key){
		CounterDBController cdb = new CounterDBController();
		int cnt = cdb.popCounter(key);

		cdb.close();
		return cnt;
	}
}
