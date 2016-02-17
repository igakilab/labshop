package jp.ac.oit.igakilab.labshop.shopping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.sf.orangesignal.csv.Csv;
import jp.sf.orangesignal.csv.CsvConfig;
import jp.sf.orangesignal.csv.handlers.StringArrayListHandler;

public class CsvAccountRegister {
	static void outputMessage(String str){
		System.err.println(str);
	}

	private boolean dependencyCheck;
	private AccountData[] accountList;
	private AccountDBController adb;
	private ItemDBController idb;
	private MemberDBController mdb;

	public CsvAccountRegister(){
		dependencyCheck = true;
		accountList = null;
	}

	public void openDBControllers(){
		adb = new AccountDBController();
		idb = new ItemDBController(adb);
		mdb = new MemberDBController(adb);
	}

	public void closeDBControllers(){
		mdb.close();
		mdb = null;
		idb.close();
		idb = null;
		adb.close();
		adb = null;
	}

	public AccountData token2AccountData(String[] tokens){
		//列指定 [年], [月], [日], [商品id], [購入者id], [価格]
		if( tokens.length < 6 ){
			outputMessage("列の長さが足りません");
			return null;
		}

		Calendar cal = Calendar.getInstance();
		int mid, iid, price;
		try{
			cal.clear();
			cal.set(Calendar.YEAR, Integer.parseInt(tokens[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(tokens[1]) - 1);
			cal.set(Calendar.DATE, Integer.parseInt(tokens[2]));

			iid = Integer.parseInt(tokens[3]);
			mid = Integer.parseInt(tokens[4]);
			price = Integer.parseInt(tokens[5]);
		}catch(NumberFormatException e0){
			outputMessage("数値変換に失敗しました");
			return null;
		}

		return new AccountData(0, cal.getTime(), mid, iid, price);
	}


	public boolean isDependencyRightness(AccountData data){
		if( !mdb.isIdRegisted(data.getMemberId()) ){
			return false;
		}
		if( !idb.isIdRegisted(data.getItemId()) ){
			return false;
		}
		return true;
	}


	public boolean loadCsv(String filePath)
	throws IOException{
		List<String[]> csvData;
		/*try{*/
			csvData = Csv.load(new File(filePath), new CsvConfig(), new StringArrayListHandler());
		/*}catch(IOException e0){
			outputMessage("ファイルの読み込みに失敗しました");
			return false;
		}*/

		if( dependencyCheck ){
			openDBControllers();
		}

		List<AccountData> dataList = new ArrayList<AccountData>();
		for(int i=0; i<csvData.size(); i++){
			String[] row = csvData.get(i);
			if( row.length > 0 || row[0].charAt(0) == '#' ){
				AccountData acc = token2AccountData(row);
				if( acc == null ){
					outputMessage("行" + i + ": 変換失敗");
				}else if( dependencyCheck && !isDependencyRightness(acc) ){
					outputMessage("行" + i + ": id依存関係不正");
				}else{
					dataList.add(acc);
				}
			}
		}

		if( dependencyCheck ){
			closeDBControllers();
		}

		accountList = dataList.toArray(new AccountData[dataList.size()]);

		return true;
	}

	public void applyToDB(){
		openDBControllers();
		for(AccountData acc : accountList){
			adb.addAccount(acc, true, false);
		}
		closeDBControllers();
	}

	public AccountData[] getAccountList(){
		return accountList;
	}
}
