package jp.ac.oit.igakilab.labshop.webservice.forms;

import java.util.ArrayList;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class NamedAccountDataForm extends AccountDataForm{
	private String memberName;
	private String itemName;

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public static NamedAccountDataForm toNamedAccountDataForm(AccountDataForm acc, DBConnector dbc){
		ItemDBController idb = new ItemDBController(dbc);
		MemberDBController mdb = new MemberDBController(dbc);
		NamedAccountDataForm nacc = new NamedAccountDataForm();
		nacc.setId(acc.getId());
		nacc.setTimestamp(acc.getTimestamp());
		nacc.setItemId(acc.getItemId());
		nacc.setItemName(idb.getItemById(acc.getItemId()).getName());
		nacc.setMemberId(acc.getMemberId());
		nacc.setMemberName(mdb.getMemberById(acc.getMemberId()).getName());
		nacc.setSellPrice(acc.getSellPrice());
		return nacc;
	}


	public static NamedAccountDataForm toNamedAccountDataForm(AccountData acc, DBConnector dbc){
		ItemDBController idb = new ItemDBController(dbc);
		MemberDBController mdb = new MemberDBController(dbc);
		NamedAccountDataForm nacc = new NamedAccountDataForm();
		nacc.setId(acc.getId());
		nacc.setTimestamp(acc.getTimestamp());
		nacc.setItemId(acc.getItemId());
		nacc.setItemName(idb.getItemById(acc.getItemId()).getName());
		nacc.setMemberId(acc.getMemberId());
		nacc.setMemberName(mdb.getMemberById(acc.getMemberId()).getName());
		nacc.setSellPrice(acc.getSellPrice());
		return nacc;
	}


	public static NamedAccountDataForm[] toNamedAccountDataForm(AccountDataForm[] acc, DBConnector dbc){
		List<NamedAccountDataForm> naccs = new ArrayList<NamedAccountDataForm>();
		for(int i=0; i<acc.length; i++){
			naccs.add(toNamedAccountDataForm(acc[i], dbc));
		}
		return naccs.toArray(new NamedAccountDataForm[naccs.size()]);
	}

	public static NamedAccountDataForm[] toNamedAccountDataForm(AccountData[] acc, DBConnector dbc){
		List<NamedAccountDataForm> naccs = new ArrayList<NamedAccountDataForm>();
		for(int i=0; i<acc.length; i++){
			naccs.add(toNamedAccountDataForm(acc[i], dbc));
		}
		return naccs.toArray(new NamedAccountDataForm[naccs.size()]);
	}
}
