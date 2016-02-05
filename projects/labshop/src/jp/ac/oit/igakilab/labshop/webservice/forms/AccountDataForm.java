package jp.ac.oit.igakilab.labshop.webservice.forms;

import java.util.Date;

import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class AccountDataForm {
	private int id;
	private Date timestamp;
	private int memberId;
	private int itemId;
	private int sellPrice;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}


	public static AccountData toAccountData(AccountDataForm form){
		if( form == null ) return null;
		AccountData acc = new AccountData(
			form.getId(), form.getTimestamp(), form.getMemberId(),
			form.getItemId(), form.getSellPrice());

		return acc;
	}

	public static AccountDataForm toAccountDataForm(AccountData acc){
		if( acc == null ) return null;
		AccountDataForm form = new AccountDataForm();
		form.setId(acc.getId());
		form.setTimestamp(acc.getTimestamp());
		form.setMemberId(acc.getMemberId());
		form.setItemId(acc.getItemId());
		form.setSellPrice(acc.getSellPrice());
		return form;
	}

	public static AccountDataForm[] toAccountDataForm(AccountData[] a_acc){
		AccountDataForm[] forms = new AccountDataForm[a_acc.length];
		for(int i=0; i<a_acc.length; i++){
			forms[i] = toAccountDataForm(a_acc[i]);
		}
		return forms;
	}
}
