package jp.ac.oit.igakilab.labshop.webservice;

import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.NamedAccountDataForm;

public class WebAccountHistory {
	public NamedAccountDataForm[] getMonthlyHistoryByMemberId(int year, int month, int mid)
	throws ExcuteFailedException{
		AggregateAccountDB aadb = new AggregateAccountDB();
		MemberDBController mdb = new MemberDBController(aadb);
		if( !mdb.isIdRegisted(mid) ){
			throw new ExcuteFailedException("idが見つかりません");
		}
		mdb.close();
		List<AccountData> list = aadb.getAccountListByMemberId(mid,
			DateFilters.oneMonth("timestamp", DateFilters.month(year, month)));
		NamedAccountDataForm[] nlist =
			NamedAccountDataForm.toNamedAccountDataForm(list.toArray(new AccountData[list.size()]), aadb);
		aadb.close();
		return nlist;
	}

	public MemberDataForm[] getAllMemberList(){
		MemberDBController mdb = new MemberDBController();
		MemberDataForm[] list =
			MemberDataForm.toMemberDataForm(
				mdb.getAllMemberList().toArray(new MemberData[0])
			);
		mdb.close();
		return list;
	}

	public String getMemberName(int mid)
	throws ExcuteFailedException{
		MemberDBController mdb = new MemberDBController();
		if( !mdb.isIdRegisted(mid) ){
			throw new ExcuteFailedException("idが見つかりません");
		}
		String name = mdb.getMemberById(mid).getName();
		mdb.close();
		return name;
	}
}
