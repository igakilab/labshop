package jp.ac.oit.igakilab.labshop.webservice.forms;

public class KeyValueMapForm {
	private String key;
	private String value;

	public KeyValueMapForm(){
		key = null;
		value = null;
	}

	public KeyValueMapForm(String k0, String v0){
		set(k0, v0);
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void set(String k0, String v0){
		key = k0;
		value = v0;
	}


}
