package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.item.ItemData;

public class ItemDataForm {
	private int id;
	private String name;
	private int price;

	public ItemDataForm(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}



	public static ItemDataForm toItemDataForm(ItemData data){
		ItemDataForm form = new ItemDataForm();
		form.setId(data.getId());
		form.setName(data.getName());
		form.setPrice(data.getPrice());
		return form;
	}

	public static ItemDataForm[] toItemDataForm(ItemData[] ary){
		ItemDataForm[] forms = new ItemDataForm[ary.length];
		for(int i=0; i<ary.length; i++){
			forms[i] = toItemDataForm(ary[i]);
		}
		return forms;
	}

	public static ItemData toItemData(ItemDataForm form){
		ItemData data = new ItemData(form.getId(), form.getName());
		data.setPrice(form.getPrice());
		return data;
	}
}
