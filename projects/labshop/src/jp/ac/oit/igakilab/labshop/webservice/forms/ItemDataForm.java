package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.item.ItemData;

public class ItemDataForm {
	private int id;
	private String name;
	private boolean isOnSale;
	private int price;
	private int count;
	private boolean isFood;
	private boolean isDrink;

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

	public boolean getIsOnSale(){
		return isOnSale;
	}

	public void setIsOnSale(boolean b0){
		isOnSale = b0;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getIsFood() {
		return isFood;
	}

	public void setIsFood(boolean isFood) {
		this.isFood = isFood;
	}

	public boolean getIsDrink() {
		return isDrink;
	}

	public void setIsDrink(boolean isDrink) {
		this.isDrink = isDrink;
	}


	public static ItemDataForm toItemDataForm(ItemData data){
		ItemDataForm form = new ItemDataForm();
		form.setId(data.getId());
		form.setName(data.getName());
		form.setPrice(data.getPrice());
		form.setIsOnSale(data.getIsOnSale());
		form.setCount(data.getCount());
		form.setIsFood(data.getIsFood());
		form.setIsDrink(data.getIsDrink());
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
		data.setIsOnSale(form.getIsOnSale());
		data.setCount(form.getCount());
		data.setIsFood(form.getIsFood());
		data.setIsDrink(form.getIsDrink());
		return data;
	}
}
