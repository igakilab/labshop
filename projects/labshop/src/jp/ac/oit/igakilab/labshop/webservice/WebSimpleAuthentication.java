package jp.ac.oit.igakilab.labshop.webservice;

public class WebSimpleAuthentication {
	public static String[] URLS = {"testTop.html"};
	public static String[] PASSWORDS = {"marsh-1015"};

	public String getUrl(String password)
	throws ExcuteFailedException{
		for(int i=0; i<PASSWORDS.length; i++){
			if( PASSWORDS[i].equals(password) ){
				if( URLS.length > i ){
					return URLS[i];
				}else{
					throw new ExcuteFailedException("urlが定義されていません");
				}
			}
		}
		throw new ExcuteFailedException("パスワードが不正です");
	}
}
