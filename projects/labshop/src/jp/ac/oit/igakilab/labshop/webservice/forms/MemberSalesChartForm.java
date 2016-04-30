package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.shopping.MemberSalesChart;

public class MemberSalesChartForm {

	public static MemberSalesChartForm getInstance(MemberSalesChart chart){
		MemberSalesChartForm form = new MemberSalesChartForm();

		Integer[] tmp = chart.getMemberList();
		form.memberList = new int[tmp.length];
		for(int i=0; i<tmp.length; i++){
			form.memberList[i] = tmp[i];
		}

		tmp = chart.getItemList();
		form.itemList = new int[tmp.length];
		for(int i=0; i<tmp.length; i++){
			form.itemList[i] = tmp[i];
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
	private int[] itemList;
	private MemberSalesChartMemberForm[] members;
	private MemberSalesChartItemForm[] items;
	private int sumPrice;


	public int[] getMemberList() {
		return memberList;
	}
	public void setMemberList(int[] memberList) {
		this.memberList = memberList;
	}
	public int[] getItemList() {
		return itemList;
	}
	public void setItemList(int[] itemList) {
		this.itemList = itemList;
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
