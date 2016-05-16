package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.item.ItemData;

public class QrItemDataForm extends ItemDataForm{
	public static QrItemDataForm getInstance(ItemDataForm idf){
		QrItemDataForm form = new QrItemDataForm();
		form.setId(idf.getId());
		form.setName(idf.getName());
		form.setPrice(idf.getPrice());
		form.setIsOnSale(idf.getIsOnSale());
		return form;
	}

	public static QrItemDataForm getInstance(ItemData id){
		ItemDataForm idf = ItemDataForm.toItemDataForm(id);
		return getInstance(idf);
	}

	public static QrItemDataForm getInstance(ItemData id, String url){
		QrItemDataForm form = getInstance(id);
		form.setQrUrl(url);
		return form;
	}

	private String url;
	private String qrUrl;

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
