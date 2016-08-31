package jp.ac.oit.igakilab.labshop.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jp.ac.oit.igakilab.labshop.webservice.forms.KeyValueMapForm;

public class WebShowEnviroments{
	public WebShowEnviroments(){};

	public KeyValueMapForm[] getEnviroments(){
		Map<String,String> params = System.getenv();
		List<KeyValueMapForm> results = new ArrayList<KeyValueMapForm>();
		for(Map.Entry<String,String> entry : params.entrySet()){
			results.add(new KeyValueMapForm(
				entry.getKey(), entry.getValue()));
		}
		return results.toArray(new KeyValueMapForm[results.size()]);
	}

	public KeyValueMapForm[] getProperties(){
		Properties properties = System.getProperties();
		List<KeyValueMapForm> results = new ArrayList<KeyValueMapForm>();
		for(Object key : properties.keySet()){
			results.add(new KeyValueMapForm(
				key.toString(),
				properties.get(key).toString()));
		}
		return results.toArray(new KeyValueMapForm[results.size()]);
	}
}
