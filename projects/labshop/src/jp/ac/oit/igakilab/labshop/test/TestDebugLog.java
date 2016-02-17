package jp.ac.oit.igakilab.labshop.test;

import jp.ac.oit.igakilab.marsh.util.DebugLog;

public class TestDebugLog {
	public void WriteLogTest(String msg){
		DebugLog.logm("test", msg);
	}

	public void WriteLogDefault(String msg){
		DebugLog.logs(msg);
	}
}
