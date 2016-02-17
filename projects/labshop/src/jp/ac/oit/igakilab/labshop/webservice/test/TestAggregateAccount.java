package jp.ac.oit.igakilab.labshop.webservice.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.shopping.CsvAccountRegister;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.ItemSalesForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberPriceForm;
import jp.sf.orangesignal.csv.Csv;
import jp.sf.orangesignal.csv.CsvConfig;
import jp.sf.orangesignal.csv.handlers.StringArrayListHandler;

public class TestAggregateAccount {
	public static MemberPriceForm[] toMemberPriceForm(List<HashMap<String, Integer>> list){
		List<MemberPriceForm> form_list = new ArrayList<MemberPriceForm>();
		for(HashMap<String, Integer> set : list){
			form_list.add(
				new MemberPriceForm(set.get("memberId"), set.get("sumPrice"))
			);
		}

		return form_list.toArray(new MemberPriceForm[form_list.size()]);
	}

	public static ItemSalesForm[] toItemSalesForm(List<HashMap<String, Integer>> list){
		List<ItemSalesForm> form_list = new ArrayList<ItemSalesForm>();
		for(HashMap<String, Integer> set : list){
			form_list.add(
				new ItemSalesForm(set.get("itemId"), set.get("qty"), set.get("sumPrice"))
			);
		}

		return form_list.toArray(new ItemSalesForm[form_list.size()]);
	}


	public MemberPriceForm[] getMemberPriceList(int year, int month){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<HashMap<String, Integer>> result;

		result = aadb.getMemberChargeList(
			DateFilters.oneMonth("timestamp", DateFilters.month(year, month)));
		aadb.close();

		return toMemberPriceForm(result);
	}

	public AccountDataForm[] getMemberAccountList(int mid, int year, int month){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<AccountData> result = aadb.getAccountListByMemberId(
			mid, DateFilters.oneMonth("timestamp", DateFilters.month(year, month))
		);
		aadb.close();

		return AccountDataForm.toAccountDataForm(result.toArray(new AccountData[result.size()]));
	}

	public ItemSalesForm[] getItemSalesList(int year, int month){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<HashMap<String, Integer>> result = aadb.getItemSalesList(
			DateFilters.oneMonth("timestamp", DateFilters.month(year, month))
		);
		aadb.close();

		return toItemSalesForm(result);
	}

	public AccountDataForm[] getItemAccountList(int iid, int year, int month){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<AccountData> result = aadb.getAccountListByItemId(
			iid, DateFilters.oneMonth("timestamp", DateFilters.month(year, month))
		);
		aadb.close();

		return AccountDataForm.toAccountDataForm(result.toArray(new AccountData[result.size()]));
	}

	public AccountDataForm[] getFebruaryAccountList(){
		return null;
	}

	public AccountDataForm[] loadCsv(String fileName)
	throws Exception{
		CsvAccountRegister register = new CsvAccountRegister();
		if( !register.loadCsv(fileName) ){
			throw new Exception("ロードエラー");
		}
		AccountData[] list = register.getAccountList();
		return AccountDataForm.toAccountDataForm(list);
	}

	public boolean applyCsvAccount(String fileName)
	throws IOException{
		CsvAccountRegister register = new CsvAccountRegister();
		if( register.loadCsv(fileName) ){
			register.applyToDB();
		}
		return true;
	}

	public void outputTextFile()
	throws IOException {
		String[] row1 = {"working directry", "is here"};
		List<String[]> data = new ArrayList<String[]>();
		data.add(row1);
		Csv.save(data, new File("output.csv"), new CsvConfig(), new StringArrayListHandler());
	}
}
