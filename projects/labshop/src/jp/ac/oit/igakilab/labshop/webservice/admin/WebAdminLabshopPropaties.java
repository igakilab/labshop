package jp.ac.oit.igakilab.labshop.webservice.admin;

import jp.ac.oit.igakilab.labshop.LabshopProperties;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.webservice.ExcuteFailedException;
import jp.ac.oit.igakilab.labshop.webservice.forms.KeyValueMapForm;

public class WebAdminLabshopPropaties {
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";

	void assertAdmin(String sid)
	throws ExcuteFailedException{
		SessionManager sm = new SessionManager();
		boolean result = sm.isSessionAdmin(sid);
		sm.close();
		if( !result ) throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
	}

	public KeyValueMapForm[] getProperyList(String sid)
	throws ExcuteFailedException{
		assertAdmin(sid);

		LabshopProperties property =  new LabshopProperties();
		String[] keys = property.getXmlConfig().getKeyList();
		KeyValueMapForm[] forms = new KeyValueMapForm[keys.length];
		for(int i=0; i<keys.length; i++){
			forms[i] = new KeyValueMapForm(
				keys[i], property.getProperty(keys[i]));
		}

		return forms;
	}

	public String getProperty(String sid, String key)
	throws ExcuteFailedException{
		assertAdmin(sid);

		return LabshopProperties.sGetProperty(key);
	}

	public void setProperty(String sid, String key, String val)
	throws ExcuteFailedException{
		assertAdmin(sid);

		LabshopProperties.sSetProperty(key, val);
	}
}
