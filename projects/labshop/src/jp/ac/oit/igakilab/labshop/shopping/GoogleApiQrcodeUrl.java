package jp.ac.oit.igakilab.labshop.shopping;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

public class GoogleApiQrcodeUrl {
	public static final String ROOT_URL = "https://chart.googleapis.com/chart?";
	public static String DEFAULT_SIZE = "157x157";
	public static String DEFAULT_TEXT = "hello world";

	public static final String CORRECTION_L = "L";
	public static final String CORRECTION_M = "M";
	public static final String CORRECTION_Q = "Q";
	public static final String CORRECTION_H = "H";
	public static int DEFAULT_MARGIN = 4;

	private HashMap<String, String> params;

	public GoogleApiQrcodeUrl(){
		params = new HashMap<String, String>();
		params.put("cht", "qr");
		params.put("chs", DEFAULT_SIZE);
		setText(DEFAULT_TEXT);
	}

	public GoogleApiQrcodeUrl(String t0){
		this();
		setText(t0);
	}

	public void setSize(int width, int height){
		params.put("chs", width + "x" + height);
	}

	public void setColor(String ccode){
		params.put("chco", ccode);
	}

	public void setText(String text){
		String replaced = Pattern.compile(" ").matcher(text).replaceAll("+");
		params.put("chl", replaced);
	}

	public String getText(){
		return params.get("chl");
	}

	public void setEncodint(String encoding){
		params.put("choe", encoding);
	}

	public void setDetails(String collection_level, int margin){
		params.put("chld", collection_level + "|" + margin);
	}

	public String toString(){
		StringBuffer buf = new StringBuffer(ROOT_URL);

		Set<String> keys = params.keySet();
		for(String k : keys){
			if( !k.equals("chl") ){
				buf.append(k).append("=")
					.append(params.get(k)).append("&");
			}
		}

		buf.append("chl=").append(params.get("chl"));

		return buf.toString();
	}


	public static void main(String[] args){
		GoogleApiQrcodeUrl url = new GoogleApiQrcodeUrl();
		url.setText("http://150.89.234.239:8080/labshop/regist?itemid=100401");
		System.out.println(url.toString());
	}
}
