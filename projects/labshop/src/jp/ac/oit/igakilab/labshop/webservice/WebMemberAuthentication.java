package jp.ac.oit.igakilab.labshop.webservice;

import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.member.AuthMemberData;

public class WebMemberAuthentication {
	public boolean auth(int id, String passwd){
		MemberDBController mdb = new MemberDBController();
		if( mdb.isIdRegisted(id) ){
			AuthMemberData amd = AuthMemberData.getInstance(mdb.getMemberById(id));
			mdb.close();
			return amd.authentication(passwd);
		}
		mdb.close();
		return false;
	}

	public boolean setMemberPassword(int id, String old_pw, String new_pw){
		MemberDBController mdb = new MemberDBController();
		if( mdb.isIdRegisted(id) ){
			AuthMemberData amd = AuthMemberData.getInstance(mdb.getMemberById(id));
			if( !amd.authentication(old_pw) ){
				mdb.close();
				return false;
			}
			amd.setPassword(new_pw);
			mdb.updateMemberData(amd);
			return true;
		}
		mdb.close();
		return false;
	}

	public String getPasswordHash(int id){
		MemberDBController mdb = new MemberDBController();
		String hash = "unknown";
		if( mdb.isIdRegisted(id) ){
			hash = mdb.getMemberById(id).getPasswordHash();
			if( hash == null ) hash = "null";
		}
		mdb.close();
		return hash;
	}
}
