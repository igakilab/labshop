package jp.ac.oit.igakilab.labshop.member;

public class MemberData {
	public static int MAX_ID_VALUE = 999999;


	/*いんすたんす*/
	private int id;
	private String name;
	private boolean isAdmin;
	private String passwordHash;

	public MemberData(){
		id = 0;
		name = "unknown";
		isAdmin = false;
	}

	public MemberData(int i0, String n0){
		this();
		setId(i0);
		setName(n0);
	}

	public int getId(){
		return id;
	}
	public void setId(int i0){
		if( i0 >= 0 && i0 < MAX_ID_VALUE ){
			id = i0;
		}
	}

	public String getName(){
		return name;
	}
	public void setName(String n0){
		name = n0;
	}

	public boolean getIsAdmin(){
		return isAdmin;
	}
	public void setIsAdmin(boolean a0){
		isAdmin = a0;
	}

	public String getPasswordHash(){
		return passwordHash;
	}
	public void setPasswordHash(String h0){
		passwordHash = h0;
	}
}
