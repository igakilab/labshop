package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.item.ItemData;
import jp.ac.oit.igakilab.labshop.member.AuthMemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class WebAccountRegister {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";
	public static String ERRMSG_SESSION_NOTFOUND = "セッションが見つかりません";
	public static String ERRMSG_ITEM_NOTFOUND = "該当アイテムが見つかりません";

	public WebAccountRegister(){}

	void assertException(boolean r, String msg)
	throws ExcuteFailedException{
		if( r ){
			throw new ExcuteFailedException(msg);
		}
	}

	boolean authSession(String sid, DBConnector dbc){
		SessionManager sm = new SessionManager(dbc);
		boolean res = sm.isSessionRegisted(sid);
		sm.close();
		return res;
	}

	boolean authPassword(int mid, String passwd, DBConnector dbc){
		MemberDBController mdb = new MemberDBController(dbc);
		AuthMemberData amd = (AuthMemberData)mdb.getMemberById(mid);
		if( amd != null ){
			return amd.authentication(passwd);
		}else{
			return false;
		}
	}

	AccountData createAccount(int mid, int iid, DBConnector dbc)
	throws ExcuteFailedException{
		ItemDBController idb = new ItemDBController(dbc);
		ItemData item = idb.getItemById(iid);
		idb.close();
		if( item != null ){
			AccountData acc = new AccountData();
			acc.setMemberId(mid);
			acc.setItemId(iid);
			acc.setSellPrice(item.getPrice());
			return acc;
		}else{
			throw new ExcuteFailedException(ERRMSG_ITEM_NOTFOUND);
		}
	}

	public boolean registAccountBySession(String sid, int itemId)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		SessionData session = sm.getSession(sid);
		if( session == null ){
			sm.close();
			throw new ExcuteFailedException(ERRMSG_SESSION_NOTFOUND);
		}

		AccountData acc = createAccount(
			session.getMemberId(), itemId, sm.getDBConnector());

		AccountDBController adb = new AccountDBController(sm.getDBConnector());
		boolean r = adb.addAccount(acc, true, true) != null;
		adb.close();

		sm.close();
		return r;
	}

	public boolean registAccountByPassword(int mid, String passwd, int itemId)
	throws ExcuteFailedException{
		DBConnector dbc = new DBConnector();
		if( !authPassword(mid, passwd, dbc) ){
			dbc.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		AccountData acc = createAccount(mid, itemId, dbc);

		AccountDBController adb = new AccountDBController(dbc);
		boolean r = adb.addAccount(acc, true, true) != null;
		adb.close();

		dbc.close();
		return r;
	}
}
