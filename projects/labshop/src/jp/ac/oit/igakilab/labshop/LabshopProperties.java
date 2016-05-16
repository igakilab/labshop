package jp.ac.oit.igakilab.labshop;

import java.io.File;

import jp.ac.oit.igakilab.marsh.util.XmlConfig;

public class LabshopProperties{
	static public String ROOT_DIR_PATH = "/var/lib/tomcat7/labshop/";
	static public String CONFIG_FILE = ROOT_DIR_PATH + "config.xml";


	public static void sSetProperty(String key, String value){
		XmlConfig xc = new XmlConfig(new File(CONFIG_FILE), true);
		xc.setParameter(key, value);
	}

	public static String sGetProperty(String key){
		XmlConfig xc = new XmlConfig(new File(CONFIG_FILE), true);
		return xc.getParameter(key);
	}

	public static String sGetProperty(String key, String defval){
		XmlConfig xc = new XmlConfig(new File(CONFIG_FILE), true);
		return xc.getParameter(key, defval);
	}



	private XmlConfig config;

	public LabshopProperties(){
		config = new XmlConfig(new File(CONFIG_FILE), true);
	}

	public void setProperty(String key, String value){
		config.setParameter(key, value);
	}

	public String getProperty(String key){
		return config.getParameter(key);
	}

	public String getProperty(String key, String defval){
		return config.getParameter(key, defval);
	}

	public boolean isPropertyRegisted(String key){
		return config.containsKey(key);
	}

	public XmlConfig getXmlConfig(){
		return config;
	}
}
