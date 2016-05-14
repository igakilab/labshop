package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.LabshopProperties;

public class WebLabshopProperties {
	public WebLabshopProperties(){}

	public String[] getProperties(String[] keys){
		String[] values = new String[keys.length];
		LabshopProperties property = new LabshopProperties();
		for(int i=0; i<keys.length; i++){
			values[i] = property.getProperty(keys[i], "");
		}
		return values;
	}
}
