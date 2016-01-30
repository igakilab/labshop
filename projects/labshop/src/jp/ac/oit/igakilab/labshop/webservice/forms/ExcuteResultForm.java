package jp.ac.oit.igakilab.labshop.webservice.forms;

public class ExcuteResultForm {
	private boolean success;
	private int code;
	private String message;
	private Object result;

	public ExcuteResultForm(){
		success = false;
		message = "resultset初期状態";
		result = null;
	}

	public boolean getSuccess(){
		return success;
	}
	public void setSuccess(boolean s0){
		success = s0;
	}

	public int getCode(){
		return code;
	}
	public void setCode(int c0){
		code = c0;
	}

	public String getMessage(){
		return message;
	}
	public void setMessage(String m0){
		message = m0;
	}

	public Object getResult(){
		return result;
	}
	public void setResult(Object d0){
		result = d0;
	}
}
