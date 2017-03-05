package jp.ac.oit.igakilab.labshop.webservice.admin;

import java.util.Comparator;
import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.dbcontroller.ItemDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.MemberDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.PrepaidDBController;
import jp.ac.oit.igakilab.labshop.item.ItemData;
import jp.ac.oit.igakilab.labshop.member.MemberData;
import jp.ac.oit.igakilab.labshop.sessions.SessionData;
import jp.ac.oit.igakilab.labshop.sessions.SessionManager;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.shopping.PrepaidData;
import jp.ac.oit.igakilab.labshop.webservice.ExcuteFailedException;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.ItemDataForm;
import jp.ac.oit.igakilab.labshop.webservice.forms.MemberDataForm;

public class WebAdminDBEditor {
	public static String ERRMSG_ID_REGISTED = "idがすでに登録されています";
	public static String ERRMSG_ID_NOTFOUND = "対象idが見つかりません";
	public static String ERRMSG_AUTH_FAILED = "認証に失敗しました";
	public static String ERRMSG_COMMAND_INVALID = "操作コマンドが不正です";

	public WebAdminDBEditor(){}

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

			}else if( command.equals("passwordReset") ){
				if( mdb.isIdRegisted(mform.getId()) ){
					MemberData data = mdb.getMemberById(mform.getId());
					data.setPasswordHash(null);
					boolean result = mdb.updateMemberData(data);
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

			list.sort(new Comparator<MemberData>(){
				@Override
				public int compare(MemberData o1, MemberData o2) {
					return Integer.compare(o1.getId(), o2.getId());
				}
			});

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

			list.sort(new Comparator<ItemData>(){
				public int compare(ItemData o1, ItemData o2){
					return Integer.compare(o1.getId(), o2.getId());
				}
			});

			idb.close();
			return ItemDataForm.toItemDataForm(list.toArray(new ItemData[list.size()]));
		}else{
			idb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public boolean accountDBEdit(String sid, String command, AccountDataForm aform)
	throws ExcuteFailedException{
		AccountDBController adb = new AccountDBController();
		AccountData data = AccountDataForm.toAccountData(aform);

		if( authAdmin(adb, sid) ){
			if( command.equals("insert") ){
				if( !adb.isIdRegisted(data.getId()) ){
					AccountData result = adb.addAccount(data, false, false);
					adb.close();
					return result != null;
				}else{
					adb.close();
					throw new ExcuteFailedException(ERRMSG_ID_REGISTED);
				}

			}else if( command.equals("update") ){
				if( adb.isIdRegisted(data.getId()) ){
					boolean result = adb.updateAccount(data);
					adb.close();
					return result;
				}else{
					adb.close();
					throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
				}

			}else if( command.equals("remove") ){
				if( adb.isIdRegisted(data.getId()) ){
					boolean result = adb.deleteAccount(data.getId());
					adb.close();
					return result;
				}else{
					adb.close();
					throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
				}

			}else{
				adb.close();
				throw new ExcuteFailedException(ERRMSG_COMMAND_INVALID);
			}
		}else{
			adb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public AccountDataForm registNewAccount(String sid, AccountDataForm aform)
	throws ExcuteFailedException{
		AccountData data = AccountDataForm.toAccountData(aform);
		AccountDBController adb = new AccountDBController();

		if( authAdmin(adb, sid) ){
			AccountData genAccount = adb.addAccount(data, true, true);
			adb.close();
			return AccountDataForm.toAccountDataForm(genAccount);
		}else{
			adb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public AccountDataForm getAccount(String sid, int id)
	throws ExcuteFailedException{
		AccountDBController adb = new AccountDBController();

		if( authAdmin(adb, sid) ){
			if( adb.isIdRegisted(id) ){
				AccountData data = adb.getAccountById(id);
				adb.close();
				return AccountDataForm.toAccountDataForm(data);
			}else{
				adb.close();
				throw new ExcuteFailedException(ERRMSG_ID_NOTFOUND);
			}
		}else{
			adb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}


	public AccountDataForm[] getAccountList(String sid)
	throws ExcuteFailedException{
		AccountDBController adb = new AccountDBController();

		if( authAdmin(adb, sid) ){
			List<AccountData> list = adb.getAllAccountList();
			adb.close();
			return AccountDataForm.toAccountDataForm(list.toArray(new AccountData[list.size()]));
		}else{
			adb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}

	public boolean recharge(String sid, int memberId, int price)
	throws ExcuteFailedException{
		PrepaidDBController pdb = new PrepaidDBController();
		MemberDBController mdb = new MemberDBController();
		if( authAdmin(pdb, sid) ){
			mdb.recharge(memberId, price);
			PrepaidData result = pdb.addPrepaid(new PrepaidData(memberId, price), true, true);
			pdb.close();
			return result != null;
		}else{
			pdb.close();
			throw new ExcuteFailedException(ERRMSG_AUTH_FAILED);
		}
	}

}
