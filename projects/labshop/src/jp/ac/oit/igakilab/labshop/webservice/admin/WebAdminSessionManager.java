package jp.ac.oit.igakilab.labshop.webservice.admin;

import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.webservice.ExcuteFailedException;
import jp.ac.oit.igakilab.labshop.webservice.forms.SessionDataForm;

public class WebAdminSessionManager {
	public static String ERRMSG_SESSION_NOTFOUND = "セッションが見つかりません";
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";


	public WebAdminSessionManager(){}


	public SessionDataForm getSessionData(String adminSid, String targetSid)
	throws ExcuteFailedException{
		SessionManager manager = new SessionManager();

		if( manager.isSessionAdmin(adminSid) ){
			if( manager.isSessionRegisted(targetSid) ){
				SessionData data = manager.getSession(targetSid);
				manager.close();
				return SessionDataForm.getInstance(data, manager.getDBConnector());
			}else{
				manager.close();
				throw new ExcuteFailedException(ERRMSG_SESSION_NOTFOUND);
			}
		}else{
			manager.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}

	public SessionDataForm[] getSessionList(String adminSid)
	throws ExcuteFailedException{
		SessionManager manager = new SessionManager();

		if( manager.isSessionAdmin(adminSid) ){
			SessionData[] list = manager.getSessionList();
			SessionDataForm[] forms =
				SessionDataForm.getInstance(list, manager.getDBConnector());
			manager.close();
			return forms;
		}else{
			manager.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}

	public boolean removeSession(String adminSid, String targetSid)
	throws ExcuteFailedException{
		SessionManager manager = new SessionManager();

		if( manager.isSessionAdmin(adminSid) ){
			if( manager.isSessionRegisted(targetSid) ){
				boolean result = manager.removeSession(targetSid);
				manager.close();
				return result;
			}else{
				manager.close();
				throw new ExcuteFailedException(ERRMSG_SESSION_NOTFOUND);
			}
		}else{
			manager.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}

}
