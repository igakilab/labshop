package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.member.MemberData;

public class MemberDataForm {
	private int id = 0;
	private String name = "unknown";
	private boolean isAdmin;
	private boolean isPrimary;
	private int balance;

	public MemberDataForm(){}

	public int getId(){
		return id;
	}
	public void setId(int i0){
		id = i0;
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
	public void setIsAdmin(boolean b0){
		isAdmin = b0;
	}

	public boolean getIsPrimary(){
		return isPrimary;
	}
	public void setIsPrimary(boolean b0){
		isPrimary = b0;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public static MemberData toMemberData(MemberDataForm form){
		MemberData data = new MemberData(form.getId(), form.getName());
		data.setIsAdmin(form.getIsAdmin());
		data.setIsPrimary(form.getIsPrimary());
		data.setBalance(form.getBalance());
		return data;
	}

	public static MemberDataForm toMemberDataForm(MemberData data){
		MemberDataForm form = new MemberDataForm();
		form.setId(data.getId());
		form.setName(data.getName());
		form.setIsAdmin(data.getIsAdmin());
		form.setIsPrimary(data.getIsPrimary());
		form.setBalance(data.getBalance());
		return form;
	}

	public static MemberDataForm[] toMemberDataForm(MemberData[] array){
		MemberDataForm[] forms = new MemberDataForm[array.length];

		for(int i=0; i<array.length; i++){
			forms[i] = toMemberDataForm(array[i]);
		}

		return forms;
	}

}