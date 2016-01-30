package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberDataForm;

public class WebMemberManager {
	WebMemberManager(){}

	public boolean addMember(MemberDataForm form)
	throws ExcuteFailedException{
		MemberData data = MemberDataForm.toMemberData(form);
		MemberDBController dbc = new MemberDBController();

		if( dbc.isIdRegisted(data.getId()) ){
			throw new ExcuteFailedException("idがすでに登録されています");
		}
		boolean rs = dbc.addMember(data);
		dbc.close();

		return rs;
	}

	public MemberDataForm[] getMemberList(){
		MemberDBController dbc = new MemberDBController();
		MemberDataForm[] list = MemberDataForm.toMemberDataForm(
			dbc.getAllMemberList().toArray(new MemberData[0]));
		dbc.close();

		return list;
	}

	public boolean updateMember(MemberDataForm form)
	throws ExcuteFailedException{
		MemberDBController dbc = new MemberDBController();

		if( !dbc.isIdRegisted(form.getId()) ){
			throw new ExcuteFailedException("idが登録されていません");
		}
		boolean rs = dbc.updateMemberData(MemberDataForm.toMemberData(form));
		dbc.close();

		return rs;
	}

	public boolean deleteMember(int id)
	throws ExcuteFailedException{
		MemberDBController dbc = new MemberDBController();

		if( !dbc.isIdRegisted(id) ){
			throw new ExcuteFailedException("idが登録されていません");
		}
		boolean rs = dbc.deleteMember(id);
		dbc.close();

		return rs;
	}

	public MemberDataForm getMemberData(int id)
	throws ExcuteFailedException{
		MemberDBController dbc = new MemberDBController();

		if( !dbc.isIdRegisted(id) ){
			throw new ExcuteFailedException("idが登録されていません");
		}

		MemberDataForm form = MemberDataForm
			.toMemberDataForm(dbc.getMemberById(id));

		dbc.close();
		return form;
	}
}
