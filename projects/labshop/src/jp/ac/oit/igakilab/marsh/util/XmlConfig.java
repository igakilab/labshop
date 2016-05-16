package jp.ac.oit.igakilab.marsh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlConfig {
	public static boolean DEFAULT_AUTOSAVE_ENABLED = false;
	public static String GETPARAM_DEFAULT_VALUE = null;

	private HashMap<String, String> params;
	private File targetFile;
	private boolean isAutoSaveEnabled;

	public XmlConfig(File f0, boolean a0){
		isAutoSaveEnabled = a0;
		targetFile = f0;
		params = new HashMap<String, String>();
		load();
	}

	public XmlConfig(File f0){
		this(f0, DEFAULT_AUTOSAVE_ENABLED);
	}

	boolean exceptionLog(Exception e0){
		DebugLog.logm("XmlConfig", DebugLog.LS_EXCEPTION, e0.toString());
		return false;
	}

	public boolean load(){
		params.clear();

		Document doc;
		try{
			DocumentBuilderFactory dbf =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			FileInputStream stream = new FileInputStream(targetFile);
			doc = db.parse(stream);
		}catch(FileNotFoundException e0){
			return exceptionLog(e0);
		}catch(ParserConfigurationException e1){
			return exceptionLog(e1);
		}catch(IOException e2){
			return exceptionLog(e2);
		}catch(SAXException e3){
			return exceptionLog(e3);
		}

		Element root = doc.getDocumentElement();
		if( root.getTagName().equals("configs") ){
			NodeList list = root.getElementsByTagName("param");
			for(int i=0; i<list.getLength(); i++){
				Element el = (Element)list.item(i);
				String key = el.getAttribute("key");
				String value = el.getAttribute("value");
				if( !key.equals("") && !value.equals("") ){
					params.put(key, value);
				}
			}
		}

		return true;
	}


	public boolean save(){
		Document doc;
		try{
			DocumentBuilderFactory dbf =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
		}catch(ParserConfigurationException e0){
			return exceptionLog(e0);
		}

		Element root = doc.createElement("configs");
		doc.appendChild(root);

		Set<String> keySet = params.keySet();
		for(String k : keySet){
			Element ep = doc.createElement("param");
			ep.setAttribute("key", k);
			ep.setAttribute("value", params.get(k));
			root.appendChild(ep);
		}

		Transformer tf;
		try{
			TransformerFactory tff = TransformerFactory.newInstance();
			tf = tff.newTransformer();
		}catch(TransformerConfigurationException e0){
			return exceptionLog(e0);
		}

		tf.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		tf.setOutputProperty("indent", "yes");
		tf.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");

		StreamResult target = new StreamResult(targetFile);
		try{
			tf.transform(new DOMSource(doc), target);
		}catch(TransformerException e0){
			return exceptionLog(e0);
		}

		return true;
	}


	public void setParameter(String key, String value){
		params.put(key, value);
		if( isAutoSaveEnabled ) save();
	}

	public String getParameter(String key, String defaultValue){
		String value = params.get(key);
		if( value != null ){
			return value;
		}else{
			return defaultValue;
		}
	}

	public String getParameter(String key){
		return getParameter(key, GETPARAM_DEFAULT_VALUE);
	}

	public int getParameterCount(){
		return params.size();
	}

	public boolean containsKey(String key){
		return params.containsKey(key);
	}

	public void setAutoSaveEnabled(boolean b){
		isAutoSaveEnabled = b;
	}

	public void setFile(File f0){
		targetFile = f0;
	}

	public String[] getKeyList(){
		List<String> keys = new ArrayList<String>();
		for(String k : params.keySet()) keys.add(k);
		return keys.toArray(new String[keys.size()]);
	}
}
