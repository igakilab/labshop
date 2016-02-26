package jp.ac.oit.igakilab.labshop.sessions;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.SessionDBController;
import jp.ac.oit.igakilab.labshop.member.AuthMemberData;

public class SessionManager {
	public static int DEFAULT_EXPIRE_DATE = 7;

	public SessionManager(){}

	public SessionData createSession(int mid, int expire){
		SessionData session = new SessionData(mid);
		session.setDueDateInNumOfDate(expire);
		return session;
	}

	public SessionData createSession(int mid){
		return createSession(mid, DEFAULT_EXPIRE_DATE);
	}

	public boolean registSession(SessionData ses){
		SessionDBController sdb = new SessionDBController();
		boolean res;
		if( sdb.isIdRegisted(ses.getId()) ){
			res = sdb.updateSessionData(ses);
		}else{
			res = sdb.addSession(ses);
		}
		sdb.close();
		return res;
	}

	public boolean authMember(int member_id, String member_passwd){
		MemberDBController mdb = new MemberDBController();
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
		MemberDBController mdb = new MemberDBController();
		if( !mdb.isIdRegisted(member_id) ){
			return null;
		}
		AuthMemberData amd =
			AuthMemberData.getInstance(mdb.getMemberById(member_id));
		if( !amd.authentication(member_passwd) ){
			return null;
		}

		SessionDBController sdb = new SessionDBController(mdb);
		SessionData session = createSession(member_id, expire);
		sdb.addSession(session);

		sdb.close();
		mdb.close();
		return session;
	}

	public SessionData issueSession(int member_id, String member_passwd){
		return issueSession(member_id, member_passwd, DEFAULT_EXPIRE_DATE);
	}

	public boolean removeSession(String sid){
		SessionDBController sdb = new SessionDBController();
		boolean res = sdb.deleteSession(sid);
		sdb.close();
		return res;
	}

	public SessionData getSession(String sid){
		SessionDBController sdb = new SessionDBController();
		SessionData session = sdb.getSessionById(sid);
		sdb.close();
		return session;
	}

	public static void main(String[] args){
		SessionManager manager = new SessionManager();
		SessionData ns = manager.issueSession(100101, "");
		System.out.println("ID : " + ns.getId());
		System.out.println("MI : " + ns.getMemberId());
		System.out.println("DD : " + ns.getDueDate());
	}


}
