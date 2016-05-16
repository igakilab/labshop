package jp.ac.oit.igakilab.labshop.webservice;

public class ExcuteFailedException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExcuteFailedException(String msg){
		super(msg);
	}
}
