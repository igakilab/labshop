package jp.ac.oit.igakilab.labshop.webservice;

import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.item.ItemData;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.webservice.forms.ItemDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberDataForm;

public class webAdminService {
	public static String ERRMSG_ID_REGISTED = "idがすでに登録されています";
	public static String ERRMSG_ID_NOTFOUND = "対象idが見つかりません";
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";
	public static String ERRMSG_COMMAND_INVALID = "操作コマンドが不正です";

	public webAdminService(){}

	boolean authAdmin(DBConnector dbc, String sessionId){
		SessionManager manager = new SessionManager(dbc);

		if( manager.isSessionRegisted(sessionId) ){
			SessionData session = manager.getSession(sessionId);
			MemberDBController mdb = new MemberDBController(dbc);
			MemberData member;
			if( mdb.isIdRegisted(session.getMemberId()) ){
				member = mdb.getMemberById(session.getMemberId());
			}else{
				return false;
			}

			return member.getIsAdmin();
		}

		manager.close();

		return false;
	}

	public boolean memberDBEdit(String sid, String command, MemberDataForm mform)
	throws ExcuteFailedException{
		MemberDBController mdb = new MemberDBController();
		if( authAdmin(mdb, sid) ){
			if( command.equals("add") ){
				if( !mdb.isIdRegisted(mform.getId()) ){
					boolean result = mdb.addMember(MemberDataForm.toMemberData(mform));
					mdb.close();
					return result;
				}else{
					mdb.close();
					throw new ExcuteFailedException(ERRMSG_ID_REGISTED);
				}

			}else if( command.equals("update") ){
				if( mdb.isIdRegisted(mform.getId()) ){
					boolean result = mdb.updateMemberData(MemberDataForm.toMemberData(mform));
					mdb.close();
					return result;
				}else{
					mdb.close();
					throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
				}

			}else if( command.equals("remove") ){
				if( mdb.isIdRegisted(mform.getId()) ){
					boolean result = mdb.deleteMember(mform.getId());
					mdb.close();
					return result;
				}else{
					mdb.close();
					throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
				}

			}else{
				mdb.close();
				throw new ExcuteFailedException(ERRMSG_COMMAND_INVALID);
			}
		}else{
			mdb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public MemberDataForm getMember(String sid, int id)
	throws ExcuteFailedException{
		MemberDBController mdb = new MemberDBController();

		if( authAdmin(mdb, sid) ){
			if( mdb.isIdRegisted(id) ){
				 MemberData mdata = mdb.getMemberById(id);
				 mdb.close();
				 return MemberDataForm.toMemberDataForm(mdata);
			}else{
				mdb.close();
				throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
			}
		}else{
			mdb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public MemberDataForm[] getMemberList(String sid)
	throws ExcuteFailedException{
		MemberDBController mdb = new MemberDBController();

		if( authAdmin(mdb, sid) ){
			List<MemberData> list = mdb.getAllMemberList();
			mdb.close();
			return MemberDataForm.toMemberDataForm(list.toArray(new MemberData[list.size()]));
		}else{
			mdb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public boolean itemDBEdit(String sid, String command, ItemDataForm iform)
	throws ExcuteFailedException{
		ItemDBController idb = new ItemDBController();
		ItemData data = ItemDataForm.toItemData(iform);

		if( authAdmin(idb, sid) ){
			if( command.equals("add") ){
				if( !idb.isIdRegisted(data.getId()) ){
					boolean result = idb.addItem(data);
					idb.close();
					return result;
				}else{
					idb.close();
					throw new ExcuteFailedException(ERRMSG_ID_REGISTED);
				}

			}else if( command.equals("update") ){
				if( idb.isIdRegisted(data.getId()) ){
					boolean result = idb.updateItemData(data);
					idb.close();
					return result;
				}else{
					idb.close();
					throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
				}

			}else if( command.equals("remove") ){
				if( idb.isIdRegisted(data.getId()) ){
					boolean result = idb.deleteItem(data.getId());
					idb.close();
					return result;
				}else{
					idb.close();
					throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
				}

			}else{
				idb.close();
				throw new ExcuteFailedException(ERRMSG_COMMAND_INVALID);
			}
		}else{
			idb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public ItemDataForm getItem(String sid, int id)
	throws ExcuteFailedException{
		ItemDBController idb = new ItemDBController();

		if( authAdmin(idb, sid) ){
			if( idb.isIdRegisted(id) ){
				ItemData data = idb.getItemById(id);
				idb.close();
				return ItemDataForm.toItemDataForm(data);
			}else{
				idb.close();
				throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
			}
		}else{
			idb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public ItemDataForm[] getItemList(String sid)
	throws ExcuteFailedException{
		ItemDBController idb = new ItemDBController();

		if( authAdmin(idb, sid) ){
			List<ItemData> list = idb.getAllItemList();
			idb.close();
			return ItemDataForm.toItemDataForm(list.toArray(new ItemData[list.size()]));
		}else{
			idb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}

}
