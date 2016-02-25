package jp.ac.oit.igakilab.labshop.sessions;

import java.util.Date;

public class SessionData {
	public static int DEFAULT_SESSIONID_LENGTH= 16;

	public static String generateSessionId(){
		StringBuilder stb = new StringBuilder();
		for(int i=0; i<DEFAULT_SESSIONID_LENGTH; i++){
			int val = (int)(Math.random() * 16);
			if( val < 10 ){
				stb.append((char)('0' + val));
			}else{
				stb.append((char)('a' + (val-10)));
			}
		}
		return stb.toString();
	}

	private String id;
	private int memberId;
	private Date dueDate;

	public SessionData(String sid, int mid, Date due){
		setId(sid);
		setMemberId(mid);
		setDueDate(due);
	}

	public SessionData(int mid, Date due){
		this(generateSessionId(), mid, due);
	}

	public SessionData(int mid){
		this(mid, null);
	}

	public SessionData(){
		this(0);
	}

	public String getId() {
		return id;
	}

	public void setId(String sessionId) {
		this.id = sessionId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
