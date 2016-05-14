package jp.ac.oit.igakilab.labshop.webservice;

import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.item.ItemData;
import jp.ac.oit.igakilab.labshop.shopping.GoogleApiQrcodeUrl;
import jp.ac.oit.igakilab.labshop.webservice.forms.QrItemDataForm;

public class WebItemqrGenerator {
	public static String ERRMSG_ITEM_NOTFOUND = "アイテムが存在しません";

	String createQrUrl(int itemId, String hostUrl){
		String url = "http://" + hostUrl + "/labshop/regist/";
		url += "?itemid=" + itemId;

		GoogleApiQrcodeUrl qr = new GoogleApiQrcodeUrl(url);
		return qr.toString();
	}

	QrItemDataForm getQrItemDataForm(ItemData data, String hostUrl){
		QrItemDataForm form = QrItemDataForm.getInstance(data);
		form.setQrUrl(createQrUrl(data.getId(), hostUrl));
		return form;
	}

	public QrItemDataForm getQrItemData(int itemid, String hostUrl)
	throws ExcuteFailedException{
		ItemDBController idb = new ItemDBController();
		ItemData data = idb.getItemById(itemid);
		idb.close();

		if( data == null ){
			throw new ExcuteFailedException(ERRMSG_ITEM_NOTFOUND);
		}

		return getQrItemDataForm(data, hostUrl);
	}

	public QrItemDataForm[] getQrItemDataList(boolean isAll, String hostUrl){
		ItemDBController idb = new ItemDBController();
		List<ItemData> list;
		if( isAll ){
			list = idb.getAllItemList();
		}else{
			list = idb.getOnSaleItemList();
		}

		QrItemDataForm[] forms = new QrItemDataForm[list.size()];
		for(int i=0; i<list.size(); i++){
			forms[i] = getQrItemDataForm(list.get(i), hostUrl);
		}

		return forms;
	}
}
