package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
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
		DBConnector dbc = new DBConnector();
		SessionDataForm form = SessionDataForm.getInstance(session, dbc);

		dbc.close();
		return form;
	}


	public boolean isSessionOpened(String sid){
		SessionManager manager = new SessionManager();
		return manager.isSessionRegisted(sid);
	}

	public SessionDataForm getSessionData(String sid){
		SessionManager manager = new SessionManager();
		DBConnector dbc = new DBConnector();
		SessionDataForm form =
			SessionDataForm.getInstance(manager.getSession(sid), dbc);
		dbc.close();
		return form;
	}

	public boolean closeSession(String sid){
		SessionManager manager = new SessionManager();
		return manager.removeSession(sid);
	}
}
