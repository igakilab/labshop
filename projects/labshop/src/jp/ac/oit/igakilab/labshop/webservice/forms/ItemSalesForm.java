package jp.ac.oit.igakilab.labshop.webservice.forms;

public class ItemSalesForm {
	private int id;
	private int qty;
	private int price;

	public ItemSalesForm(){
		id = 0;
		qty = 0;
		price = 0;
	}

	public ItemSalesForm(int i0, int c0, int p0){
		setId(i0);
		setqty(c0);
		setPrice(p0);
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getqty() {
		return qty;
	}
	public void setqty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


}
