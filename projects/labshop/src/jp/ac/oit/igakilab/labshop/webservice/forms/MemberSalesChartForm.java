package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.shopping.MemberSalesChart;

public class MemberSalesChartForm {

	public static MemberSalesChartForm getInstance(MemberSalesChart chart, DBConnector dbc){
		MemberSalesChartForm form = new MemberSalesChartForm();

		Integer[] mlist= chart.getMemberList();
		form.memberList = new int[mlist.length];
		for(int i=0; i<mlist.length; i++){
			form.memberList[i] = mlist[i];
		}

		if( dbc != null ){
			MemberDBController mdb = new MemberDBController(dbc);
			form.memberNameList = new String[mlist.length];
			for(int i=0; i<mlist.length; i++){
				form.memberNameList[i] = mdb.getMemberById(mlist[i]).getName();
			}
			mdb.close();
		}

		Integer[] ilist = chart.getItemList();
		form.itemList = new int[ilist.length];
		for(int i=0; i<ilist.length; i++){
			form.itemList[i] = ilist[i];
		}

		if( dbc != null ){
			ItemDBController idb = new ItemDBController(dbc);
			form.itemNameList= new String[ilist.length];
			for(int i=0; i<ilist.length; i++){
				form.itemNameList[i] = idb.getItemById(ilist[i]).getName();
			}
			idb.close();
		}

		form.members = new MemberSalesChartMemberForm[chart.getMemberCount()];
		for(int i=0; i<chart.getMemberCount(); i++){
			form.members[i] = new MemberSalesChartMemberForm();
			form.members[i].setItemCounts(chart.getMemberItemCount(i));
			form.members[i].setItemPrices(chart.getMemberItemPrice(i));
			form.members[i].setSumPrice(chart.getMemberSumPrice(i));
		}

		form.items = new MemberSalesChartItemForm[chart.getItemCount()];
		for(int i=0; i<chart.getItemCount(); i++){
			form.items[i] = new MemberSalesChartItemForm();
			form.items[i].setCount(chart.getItemSumCount(i));
			form.items[i].setSumPrice(chart.getItemSumPrice(i));
		}

		form.sumPrice = chart.getAllSumPrice();

		return form;
	}



	private int[] memberList;
	private String[] memberNameList;
	private int[] itemList;
	private String[] itemNameList;
	private MemberSalesChartMemberForm[] members;
	private MemberSalesChartItemForm[] items;
	private int sumPrice;


	public int[] getMemberList() {
		return memberList;
	}
	public void setMemberList(int[] memberList) {
		this.memberList = memberList;
	}
	public String[] getMemberNameList() {
		return memberNameList;
	}
	public void setMemberNameList(String[] memberNameList) {
		this.memberNameList = memberNameList;
	}
	public int[] getItemList() {
		return itemList;
	}
	public void setItemList(int[] itemList) {
		this.itemList = itemList;
	}
	public String[] getItemNameList() {
		return itemNameList;
	}
	public void setItemNameList(String[] itemNameList) {
		this.itemNameList = itemNameList;
	}
	public MemberSalesChartMemberForm[] getMembers() {
		return members;
	}
	public void setMembers(MemberSalesChartMemberForm[] members) {
		this.members = members;
	}
	public MemberSalesChartItemForm[] getItems() {
		return items;
	}
	public void setItems(MemberSalesChartItemForm[] items) {
		this.items = items;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
}
