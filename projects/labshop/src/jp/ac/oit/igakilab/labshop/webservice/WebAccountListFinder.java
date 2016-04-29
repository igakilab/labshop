package jp.ac.oit.igakilab.labshop.webservice;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataMonthlyQueryForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedAccountDataForm;

public class WebAccountListFinder {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";

	public NamedAccountDataForm[] getAccountList(String sid, AccountDataMonthlyQueryForm query)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		if( !sm.isSessionRegisted(sid) ){
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		SessionData session = sm.getSession(sid);
		Bson filter = Filters.eq("memberId", session.getMemberId());

		AggregateAccountDB aadb = new AggregateAccountDB(sm.getDBConnector());
		AccountData[] list = aadb.getAccountList(
			Filters.and(query.getBsonFilter(), filter)).toArray(new AccountData[0]);
		NamedAccountDataForm[] forms =
			NamedAccountDataForm.toNamedAccountDataForm(list, sm.getDBConnector());

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
		AccountData[] list = aadb.getAccountList(query.getBsonFilter()).toArray(new AccountData[0]);
		NamedAccountDataForm[] forms =
			NamedAccountDataForm.toNamedAccountDataForm(list, sm.getDBConnector());

		aadb.close();
		sm.close();
		return forms;
	}
}
