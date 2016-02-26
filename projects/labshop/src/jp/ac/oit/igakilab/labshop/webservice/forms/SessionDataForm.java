package jp.ac.oit.igakilab.labshop.webservice.forms;

import java.util.Date;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;

public class SessionDataForm {
	private String id;
	private int memberId;
	private String memberName;
	private boolean isAdmin;
	private Date dueDate;

	public SessionDataForm(){
		setId("");
		setMemberId(0);
		setMemberName("unknown");
		setIsAdmin(false);
		setDueDate(null);
	}

	public String getId() {
		return id;
	}

	public void setId(String sessionId) {
		this.id = sessionId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}



	public static SessionDataForm getInstance(SessionData data, DBConnector dbc){
		if( data == null ) return null;

		SessionDataForm form = new SessionDataForm();
		form.setId(data.getId());
		form.setMemberId(data.getMemberId());
		form.setDueDate(data.getDueDate());
		if( dbc != null ){
			MemberDBController mdb = new MemberDBController(dbc);
			if( mdb.isIdRegisted(data.getMemberId()) ){
				MemberData md = mdb.getMemberById(data.getMemberId());
				form.setMemberName(md.getName());
				form.setIsAdmin(md.getIsAdmin());
			}
		}

		return form;
	}

}
