package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.webservice.forms.SessionDataForm;

public class WebSessionManager {
	public WebSessionManager(){}

	public SessionDataForm openSession(int member_id, String passwd)
	throws ExcuteFailedException {
		SessionManager manager = new SessionManager();
		if( !manager.authMember(member_id, passwd) ){
			throw new ExcuteFailedException("ユーザidまたはパスワードが違います。");
		}

		SessionData session = manager.issueSession(member_id, passwd);
		SessionDataForm form = SessionDataForm.getInstance(session,
			manager.getDBConnector());

		manager.close();
		return form;
	}


	public boolean isSessionOpened(String sid){
		SessionManager manager = new SessionManager();
		boolean isRegisted = manager.isSessionRegisted(sid);
		manager.close();
		return isRegisted;
	}

	public SessionDataForm getSessionData(String sid){
		SessionManager manager = new SessionManager();
		SessionDataForm form =
			SessionDataForm.getInstance(
				manager.getSession(sid), manager.getDBConnector());
		manager.close();
		return form;
	}

	public boolean closeSession(String sid){
		SessionManager manager = new SessionManager();
		boolean iss = manager.removeSession(sid);
		manager.close();
		return iss;
	}
}
