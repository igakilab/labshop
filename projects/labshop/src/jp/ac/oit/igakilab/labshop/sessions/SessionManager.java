package jp.ac.oit.igakilab.labshop.sessions;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.SessionDBController;
import jp.ac.oit.igakilab.labshop.member.AuthMemberData;

public class SessionManager {
	public static int DEFAULT_EXPIRE_DATE = 7;

	private SessionDBController sdb;

	public SessionManager(){
		sdb = new SessionDBController();
	}

	public SessionManager(DBConnector dbc){
		sdb = new SessionDBController(dbc);
	}

	public SessionData createSession(int mid, int expire){
		SessionData session = new SessionData(mid);
		session.setDueDateInNumOfDate(expire);
		return session;
	}

	public SessionData createSession(int mid){
		return createSession(mid, DEFAULT_EXPIRE_DATE);
	}

	public boolean authMember(int member_id, String member_passwd){
		MemberDBController mdb = new MemberDBController(sdb);
		if( !mdb.isIdRegisted(member_id) ){
			return false;
		}
		AuthMemberData amd =
			AuthMemberData.getInstance(mdb.getMemberById(member_id));
		if( !amd.authentication(member_passwd) ){
			return false;
		}
		return true;
	}

	public SessionData issueSession(int member_id, String member_passwd, int expire){
		MemberDBController mdb = new MemberDBController(sdb);
		if( !mdb.isIdRegisted(member_id) ){
			return null;
		}
		AuthMemberData amd =
			AuthMemberData.getInstance(mdb.getMemberById(member_id));
		if( !amd.authentication(member_passwd) ){
			return null;
		}

		SessionData session = createSession(member_id, expire);
		sdb.addSession(session);

		mdb.close();
		return session;
	}

	public SessionData issueSession(int member_id, String member_passwd){
		return issueSession(member_id, member_passwd, DEFAULT_EXPIRE_DATE);
	}

	public boolean registSession(SessionData ses){
		boolean res;
		if( sdb.isIdRegisted(ses.getId()) ){
			res = sdb.updateSessionData(ses);
		}else{
			res = sdb.addSession(ses);
		}
		return res;
	}

	public boolean removeSession(String sid){
		boolean res = sdb.deleteSession(sid);
		return res;
	}

	public SessionData getSession(String sid){
		return sdb.getSessionById(sid);
	}

	public boolean isSessionRegisted(String sid){
		return sdb.isIdRegisted(sid);
	}

	public DBConnector getDBConnector(){
		return sdb;
	}

	public void close(){
		sdb.close();
	}

}
