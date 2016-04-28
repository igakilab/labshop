package jp.ac.oit.igakilab.labshop.webservice.admin;

import java.io.IOException;

import jp.ac.oit.igakilab.labshop.CacheFileManager;
import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.shopping.CsvAccountRegister;
import jp.ac.oit.igakilab.labshop.webservice.ExcuteFailedException;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedAccountDataForm;

public class WebAdminCsvAccountRegister {
	static String CACHEFILE_DIR = "labshop/";
	static String CACHEFILE_EXT = "csv";

	public static String ERRMSG_SAVETEXT_FAILED = "テキストの保存に失敗しました";
	public static String ERRMSG_LOADCSV_FAILED = "csvデータのロードに失敗しました";
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";

	public WebAdminCsvAccountRegister(){}

	String saveCache(String str){
		CacheFileManager cmanager = new CacheFileManager(CACHEFILE_DIR, CACHEFILE_EXT);
		try{
			cmanager.saveString(str);
		}catch(IOException e0){
			return null;
		}
		return cmanager.getPath();
	}

	AccountData[] loadCsv(String filePath){
		CsvAccountRegister register = new CsvAccountRegister();
		register.setDependencyCheck(false);
		try{
			if( !register.loadCsv(filePath) ){
				return null;
			}
		}catch(IOException e0){
			return null;
		}
		return register.getAccountList();
	}

	public void outputTestMessage()
	throws IOException{
		CacheFileManager cmanager = new CacheFileManager(CACHEFILE_DIR, "txt");
		cmanager.saveString("THIS IS TEST MESSAGE");
	}

	public NamedAccountDataForm[] attemptAccountCsvText(String str)
	throws ExcuteFailedException{
		String cachePath = saveCache(str);
		if( cachePath == null ){
			throw new ExcuteFailedException(ERRMSG_SAVETEXT_FAILED);
		}

		AccountData[] list = loadCsv(cachePath);
		if( list == null ){
			throw new ExcuteFailedException(ERRMSG_LOADCSV_FAILED);
		}

		DBConnector dbc = new DBConnector();
		NamedAccountDataForm[] forms =
			NamedAccountDataForm.toNamedAccountDataForm(list, dbc);
		dbc.close();

		return forms;
	}

	public boolean registAccountCsvText(String sid, String str)
	throws ExcuteFailedException{
		SessionManager manager = new SessionManager();
		if( !manager.isSessionAdmin(sid) ){
			manager.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		String cachePath = saveCache(str);
		if( cachePath == null ){
			manager.close();
			throw new ExcuteFailedException(ERRMSG_SAVETEXT_FAILED);
		}

		AccountData[] list = loadCsv(cachePath);
		if( list == null ){
			manager.close();
			throw new ExcuteFailedException(ERRMSG_LOADCSV_FAILED);
		}

		boolean isSuccess = true;
		AccountDBController adb = new AccountDBController(manager.getDBConnector());
		for(int i=0; i<list.length; i++){
			if( adb.addAccount(list[i], true, false) == null ){
				isSuccess = false;
			}
		}

		manager.close();
		return isSuccess;
	}
}
