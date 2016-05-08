package jp.ac.oit.igakilab.labshop.webservice;

import java.util.Comparator;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataMonthlyQueryForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedAccountDataForm;

public class WebAccountListFinder {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";
	public static String ERRMSG_ACCOUNT_NOTFOUND = "アカウントデータが見つかりません";

	void dateSorting(List<AccountData> list){
		list.sort(new Comparator<AccountData>(){

			@Override
			public int compare(AccountData o1, AccountData o2) {
				return o1.getTimestamp().compareTo(o2.getTimestamp());
			}

		});
	}

	public NamedAccountDataForm[] getAccountList(String sid, AccountDataMonthlyQueryForm query)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		if( !sm.isSessionRegisted(sid) ){
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		SessionData session = sm.getSession(sid);
		Bson filter = Filters.eq("memberId", session.getMemberId());

		AggregateAccountDB aadb = new AggregateAccountDB(sm.getDBConnector());
		List<AccountData> list = aadb.getAccountList(
			Filters.and(query.getBsonFilter(), filter));
		dateSorting(list);
		NamedAccountDataForm[] forms =
			NamedAccountDataForm.toNamedAccountDataForm(
				list.toArray(new AccountData[list.size()]), sm.getDBConnector());

		aadb.close();
		sm.close();
		return forms;
	}

	public NamedAccountDataForm[] adminGetAccountList(String sid, AccountDataMonthlyQueryForm query)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		if( !sm.isSessionAdmin(sid) ){
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		AggregateAccountDB aadb = new AggregateAccountDB(sm.getDBConnector());
		List<AccountData> list = aadb.getAccountList(query.getBsonFilter());
		dateSorting(list);
		NamedAccountDataForm[] forms =
			NamedAccountDataForm.toNamedAccountDataForm(
				list.toArray(new AccountData[list.size()]), sm.getDBConnector());

		aadb.close();
		sm.close();
		return forms;
	}

	public NamedAccountDataForm getAccountData(int id)
	throws ExcuteFailedException{
		AccountDBController adb = new AccountDBController();
		AccountData data = adb.getAccountById(id);
		if( data == null ){
			adb.close();
			throw new ExcuteFailedException(ERRMSG_ACCOUNT_NOTFOUND);
		}

		NamedAccountDataForm form =
			NamedAccountDataForm.toNamedAccountDataForm(data, adb);

		adb.close();
		return form;
	}
}
