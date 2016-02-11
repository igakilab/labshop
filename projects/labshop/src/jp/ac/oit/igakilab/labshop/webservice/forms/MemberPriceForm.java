package jp.ac.oit.igakilab.labshop.webservice.forms;

public class MemberPriceForm {
	private int id;
	private int price;

	public MemberPriceForm(){
		id = 0;
		price = 0;
	}

	public MemberPriceForm(int i0, int p0){
		setId(i0);
		setPrice(p0);
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
