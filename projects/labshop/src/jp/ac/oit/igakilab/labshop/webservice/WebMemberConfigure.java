package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.member.AuthMemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;

public class WebMemberConfigure {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";
	public static String ERRMSG_OLDPASSWD_INVALID = "古いパスワードが一致しません";
	public static String ERRMSG_SETPASSWD_FAILED = "パスワードの設定に失敗しました";

	public WebMemberConfigure(){}

	public boolean setPassword(String sid, String oldPasswd, String newPasswd)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		int memberId;

		if( sm.isSessionRegisted(sid) ){
			SessionData session = sm.getSession(sid);
			memberId = session.getMemberId();
		}else{
			sm.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}

		MemberDBController mdb = new MemberDBController(sm.getDBConnector());
		AuthMemberData amdata = AuthMemberData.getInstance(mdb.getMemberById(memberId));

		if( !amdata.authentication(oldPasswd) ){
			mdb.close();
			sm.close();
			throw new ExcuteFailedException(ERRMSG_OLDPASSWD_INVALID);
		}

		if( !amdata.setPassword(newPasswd) ){
			mdb.close();
			sm.close();
			return false;
		}

		if( !mdb.updateMemberData(amdata) ){
			mdb.close();
			sm.close();
			return false;
		}

		mdb.close();
		sm.close();
		return true;
	}
}
