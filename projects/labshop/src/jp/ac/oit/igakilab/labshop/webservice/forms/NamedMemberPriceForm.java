package jp.ac.oit.igakilab.labshop.webservice.forms;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;

public class NamedMemberPriceForm extends MemberPriceForm{
	private String memberName;

	public NamedMemberPriceForm(){
		super();
		memberName = "unknown";
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}



	public static NamedMemberPriceForm getInstance(int id, int price, DBConnector dbc){
		NamedMemberPriceForm form = new NamedMemberPriceForm();
		MemberDBController mdb = new MemberDBController(dbc);
		form.setId(id);
		form.setPrice(price);
		if( mdb.isIdRegisted(id) ){
			form.setMemberName(mdb.getMemberById(id).getName());
		}
		mdb.close();
		return form;
	}

	public static NamedMemberPriceForm getInstance(MemberPriceForm mpform, DBConnector dbc){
		return getInstance(mpform.getId(), mpform.getPrice(), dbc);
	}
}
