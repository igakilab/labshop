package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;

public class NamedItemSalesForm extends ItemSalesForm{
	private String itemName;

	public NamedItemSalesForm(){
		super();
		itemName = "unknown";
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public static NamedItemSalesForm getInstance(int id, int qty, int price, DBConnector dbc){
		NamedItemSalesForm form = new NamedItemSalesForm();
		form.setId(id);
		form.setQty(qty);
		form.setPrice(price);
		ItemDBController idb = new ItemDBController(dbc);
		if( idb.isIdRegisted(id) ){
			form.setItemName(idb.getItemById(id).getName());
		}
		return form;
	}

	public static NamedItemSalesForm getInstance(ItemSalesForm is_form, DBConnector dbc){
		return getInstance(
			is_form.getId(), is_form.getQty(),
			is_form.getPrice(), dbc);
	}
}
