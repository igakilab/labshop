package jp.ac.oit.igakilab.labshop.webservice;

import java.util.List;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;
import jp.ac.oit.igakilab.labshop.webservice.forms.AccountDataForm;

public class WebAccountManager {
	public WebAccountManager(){}

	public AccountDataForm addAccount(AccountDataForm form){
		AccountData acc = AccountDataForm.toAccountData(form);
		AccountDBController adb = new AccountDBController();
		AccountData reg = adb.addAccount(acc, true, true);

		adb.close();
		return AccountDataForm.toAccountDataForm(reg);
	}

	public AccountDataForm[] getAllAccountList(){
		AccountDBController adb = new AccountDBController();
		List<AccountData> list = adb.getAllAccountList();

		adb.close();
		return AccountDataForm.toAccountDataForm(
			list.toArray(new AccountData[list.size()]));
	}

	public AccountDataForm getAccount(int acc_id){
		AccountDBController adb = new AccountDBController();
		AccountDataForm form = null;

		if( adb.isIdRegisted(acc_id) ){
			form = AccountDataForm.toAccountDataForm(adb.getAccountById(acc_id));
		}
		adb.close();
		return form;
	}

	public boolean deleteAccount(int acc_id)
	throws ExcuteFailedException{
		AccountDBController adb = new AccountDBController();
		if( !adb.isIdRegisted(acc_id) ){
			adb.close();
			throw new ExcuteFailedException("idが見つかりません");
		}
		boolean rs = adb.deleteAccount(acc_id);

		adb.close();
		return rs;
	}
}
