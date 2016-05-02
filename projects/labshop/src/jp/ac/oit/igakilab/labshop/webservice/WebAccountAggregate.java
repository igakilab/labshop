package jp.ac.oit.igakilab.labshop.webservice;

import java.util.HashMap;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedAccountDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedItemSalesForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedMemberPriceForm;

public class WebAccountAggregate {
	@Deprecated
	public NamedMemberPriceForm[] getMonthlyMemberPriceList(int y, int m){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<HashMap<String, Integer>> result =
			aadb.getMemberChargeList(DateFilters.oneMonth("timestamp", DateFilters.month(y, m)));

		NamedMemberPriceForm[] forms = new NamedMemberPriceForm[result.size()];
		for(int i=0; i<result.size(); i++){
			HashMap<String, Integer> tmp = result.get(i);
			forms[i] = NamedMemberPriceForm.getInstance(
				tmp.get("memberId"), tmp.get("sumPrice"), aadb);
		}

		aadb.close();
		return forms;
	}

	@Deprecated
	public NamedItemSalesForm[] getMonthlyItemSalesList(int y, int m){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<HashMap<String, Integer>> result =
			aadb.getItemSalesList(
				DateFilters.oneMonth("timestamp", DateFilters.month(y, m)));

		NamedItemSalesForm[] forms = new NamedItemSalesForm[result.size()];
		for(int i=0; i<result.size(); i++){
			HashMap<String, Integer> tmp = result.get(i);
			forms[i] = NamedItemSalesForm.getInstance(
				tmp.get("itemId"), tmp.get("qty"), tmp.get("sumPrice"), aadb);
		}

		aadb.close();
		return forms;
	}

	@Deprecated
	public NamedAccountDataForm[] getMonthlyMemberAccounts(int y, int m, int id){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<AccountData> result =
			aadb.getAccountListByMemberId(id,
				DateFilters.oneMonth("timestamp", DateFilters.month(y, m)));
		NamedAccountDataForm[] forms =  NamedAccountDataForm.toNamedAccountDataForm(
			result.toArray(new AccountData[result.size()]), aadb);
		aadb.close();
		return forms;
	}

	@Deprecated
	public NamedAccountDataForm[] getMonthlyItemAccounts(int y, int m, int id){
		AggregateAccountDB aadb = new AggregateAccountDB();
		List<AccountData> result =
			aadb.getAccountListByItemId(id,
				DateFilters.oneMonth("timestamp", DateFilters.month(y, m)));
		NamedAccountDataForm[] forms = NamedAccountDataForm.toNamedAccountDataForm(
			result.toArray(new AccountData[result.size()]), aadb);
		aadb.close();
		return forms;
	}

}
