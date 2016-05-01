package jp.ac.oit.igakilab.labshop.webservice.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.shopping.MemberSalesChart;
import jp.ac.oit.igakilab.labshop.webservice.ExcuteFailedException;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataMonthlyQueryForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberSalesChartForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedAccountDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedItemSalesForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedMemberPriceForm;

public class WebAdminAccountAggregate {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";

	boolean authAdmin(String sid, DBConnector dbc){
		SessionManager sm = new SessionManager(dbc);
		boolean result = sm.isSessionAdmin(sid);
		sm.close();
		return result;
	}

	public MemberSalesChartForm getMonthlyMemberSalesChart(String sid, int monthVal)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		if( !sm.isSessionAdmin(sid) ){
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		AggregateAccountDB aadb = new AggregateAccountDB(sm.getDBConnector());
		MemberDBController mdb = new MemberDBController(sm.getDBConnector());
		MemberSalesChart chart = new MemberSalesChart();

		List<MemberData> memberList = mdb.getAllMemberList();
		mdb.close();
		for(MemberData md : memberList){
			chart.addMember(md.getId());
		}

		Calendar cal = AccountDataMonthlyQueryForm.toCalendar(monthVal);
		chart.buildChart(aadb, DateFilters.oneMonth("timestamp", cal));
		aadb.close();

		MemberSalesChartForm form = MemberSalesChartForm.getInstance(chart, sm.getDBConnector());
		sm.close();
		return form;
	}

	public NamedMemberPriceForm[] getMonthlyMemberPriceList(String sid, int monthVal, boolean isAllMember){
		AggregateAccountDB aadb = new AggregateAccountDB();

		if( !authAdmin(sid, aadb) ){
			return new NamedMemberPriceForm[0];
		}

		List<HashMap<String, Integer>> result =
			aadb.getMemberChargeList(DateFilters.oneMonth(
				"timestamp", AccountDataMonthlyQueryForm.toCalendar(monthVal)));

		List<NamedMemberPriceForm> forms = new ArrayList<NamedMemberPriceForm>();
		if( isAllMember ){
			MemberDBController mdb = new MemberDBController(aadb);
			List<MemberData> mlist = mdb.getAllMemberList();
			for(MemberData md : mlist){
				forms.add(NamedMemberPriceForm.getInstance(md.getId(), 0, aadb));
			}
			mdb.close();
		}

		for(HashMap<String, Integer> tmp : result){
			int mid = tmp.get("memberId");
			int idx = -1;
			for(int i=0; i<forms.size(); i++){
				if( forms.get(i).getId() == mid ){
					idx = i;
					break;
				}
			}

			if( idx < 0 ){
				forms.add(NamedMemberPriceForm.getInstance(mid, tmp.get("sumPrice"), aadb));
			}else{
				forms.get(idx).setPrice(tmp.get("sumPrice"));
			}
		}

		forms.sort(new Comparator<NamedMemberPriceForm>(){
			@Override
			public int compare(NamedMemberPriceForm o1, NamedMemberPriceForm o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});

		aadb.close();
		return forms.toArray(new NamedMemberPriceForm[forms.size()]);
	}

	public NamedItemSalesForm[] getMonthlyItemSalesList(String sid, int monthVal){
		AggregateAccountDB aadb = new AggregateAccountDB();

		if( !authAdmin(sid, aadb) ){
			return new NamedItemSalesForm[0];
		}

		List<HashMap<String, Integer>> result =
			aadb.getItemSalesList(
				DateFilters.oneMonth(
					"timestamp", AccountDataMonthlyQueryForm.toCalendar(monthVal)));

		NamedItemSalesForm[] forms = new NamedItemSalesForm[result.size()];
		for(int i=0; i<result.size(); i++){
			HashMap<String, Integer> tmp = result.get(i);
			forms[i] = NamedItemSalesForm.getInstance(
				tmp.get("itemId"), tmp.get("qty"), tmp.get("sumPrice"), aadb);
		}

		aadb.close();
		return forms;
	}

	public NamedAccountDataForm[] getMonthlyMemberItemAccounts(String sid, int monthVal, int mid, int iid){
		AggregateAccountDB aadb = new AggregateAccountDB();

		if( !authAdmin(sid, aadb) ){
			return new NamedAccountDataForm[0];
		}

		Bson query= Filters.and(Arrays.asList(
			Filters.eq("memberId", mid),
			Filters.eq("itemId", iid),
			DateFilters.oneMonth("timestamp", AccountDataMonthlyQueryForm.toCalendar(monthVal))
		));

		List<AccountData> result =aadb.getAccountList(query);
		NamedAccountDataForm[] forms =  NamedAccountDataForm.toNamedAccountDataForm(
			result.toArray(new AccountData[result.size()]), aadb);
		aadb.close();
		return forms;
	}

	public NamedAccountDataForm[] getMonthlyMemberAccounts(String sid, int monthVal, int mid){
		AggregateAccountDB aadb = new AggregateAccountDB();

		if( !authAdmin(sid, aadb) ){
			return new NamedAccountDataForm[0];
		}

		List<AccountData> result =
			aadb.getAccountListByMemberId(mid,
				DateFilters.oneMonth(
						"timestamp", AccountDataMonthlyQueryForm.toCalendar(monthVal)));
		NamedAccountDataForm[] forms =  NamedAccountDataForm.toNamedAccountDataForm(
			result.toArray(new AccountData[result.size()]), aadb);
		aadb.close();
		return forms;
	}

	public NamedAccountDataForm[] getMonthlyItemAccounts(String sid, int monthVal, int iid){
		AggregateAccountDB aadb = new AggregateAccountDB();

		if( !authAdmin(sid, aadb) ){
			return new NamedAccountDataForm[0];
		}

		List<AccountData> result =
			aadb.getAccountListByItemId(iid,
				DateFilters.oneMonth(
					"timestamp", AccountDataMonthlyQueryForm.toCalendar(monthVal)));
		NamedAccountDataForm[] forms = NamedAccountDataForm.toNamedAccountDataForm(
			result.toArray(new AccountData[result.size()]), aadb);
		aadb.close();
		return forms;
	}
}
