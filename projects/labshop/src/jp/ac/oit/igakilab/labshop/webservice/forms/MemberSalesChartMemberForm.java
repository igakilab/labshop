package jp.ac.oit.igakilab.labshop.webservice.forms;

public class MemberSalesChartMemberForm {
	private int[] itemCounts;
	private int[] itemPrices;
	private int sumPrice;


	public int[] getItemCounts() {
		return itemCounts;
	}
	public void setItemCounts(int[] itemCounts) {
		this.itemCounts = itemCounts;
	}
	public int[] getItemPrices() {
		return itemPrices;
	}
	public void setItemPrices(int[] itemPrices) {
		this.itemPrices = itemPrices;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
}
