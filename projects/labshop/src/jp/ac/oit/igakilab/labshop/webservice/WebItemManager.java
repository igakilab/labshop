package jp.ac.oit.igakilab.labshop.webservice;

import java.util.Comparator;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.item.ItemData;
import jp.ac.oit.igakilab.labshop.webservice.forms.ItemDataForm;

public class WebItemManager {
	public WebItemManager(){}

	public static void sortItem(List<ItemData> list){
		list.sort(new Comparator<ItemData>(){
			@Override
			public int compare(ItemData o1, ItemData o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});
	}

	@Deprecated
	public boolean addItem(ItemDataForm form)
	throws ExcuteFailedException{
		ItemDBController ctrl = new ItemDBController();

		if( ctrl.isIdRegisted(form.getId()) ){
			ctrl.close();
			throw new ExcuteFailedException("idが登録されています");
		}

		boolean ret = ctrl.addItem(ItemDataForm.toItemData(form));
		ctrl.close();
		return ret;
	}

	@Deprecated
	public boolean updateItem(ItemDataForm form)
	throws ExcuteFailedException{
		ItemDBController ctrl = new ItemDBController();

		if( !ctrl.isIdRegisted(form.getId()) ){
			ctrl.close();
			throw new ExcuteFailedException("登録idに該当するものがありません");
		}

		boolean ret = ctrl.updateItemData(ItemDataForm.toItemData(form));
		ctrl.close();
		return ret;
	}

	@Deprecated
	public boolean deleteItem(int item_id)
	throws ExcuteFailedException {
		ItemDBController ctrl = new ItemDBController();

		if( !ctrl.isIdRegisted(item_id) ){
			ctrl.close();
			throw new ExcuteFailedException("登録idに該当するものがありません");
		}

		boolean ret =  ctrl.deleteItem(item_id);
		ctrl.close();
		return ret;
	}

	public ItemDataForm getItem(int item_id){
		ItemDBController ctrl = new ItemDBController();
		ItemDataForm result = null;

		if( ctrl.isIdRegisted(item_id) ){
			result = ItemDataForm.toItemDataForm(ctrl.getItemById(item_id));
		}

		ctrl.close();
		return result;
	}

	public ItemDataForm[] getItemList(){
		ItemDBController ctrl = new ItemDBController();
		List<ItemData> list = ctrl.getAllItemList();
		sortItem(list);
		ItemDataForm[] forms =
			ItemDataForm.toItemDataForm(list.toArray(new ItemData[list.size()]));

		ctrl.close();
		return forms;
	}

	public ItemDataForm[] getOnSaleItemList(){
		ItemDBController idb = new ItemDBController();
		List<ItemData> list = idb.getOnSaleItemList();
		sortItem(list);
		idb.close();
		return ItemDataForm.toItemDataForm(list.toArray(new ItemData[list.size()]));
	}
}