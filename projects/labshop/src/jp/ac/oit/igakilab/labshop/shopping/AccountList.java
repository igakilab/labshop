package jp.ac.oit.igakilab.labshop.shopping;

import java.util.ArrayList;
import java.util.List;

public class AccountList {
	protected List<AccountData> accounts;

	public AccountList(){
		accounts = new ArrayList<AccountData>();
	}

	boolean isBounds(int idx){
		return idx >= 0 && idx < accounts.size();
	}

	int getAccountIdxById(int id){
		for(int i=0; i<accounts.size(); i++){
			if( accounts.get(i).getId() == id ){
				return i;
			}
		}
		return -1;
	}

	public boolean isIdRegisted(int id){
		return getAccountIdxById(id) >= 0;
	}

	public void clearAccount(){
		accounts.clear();
	}

	public void addAccount(AccountData data){
		accounts.add(data);
	}

	public void addAccount(AccountData[] list){
		for(AccountData data : list){
			addAccount(data);
		}
	}

	public AccountData getAccount(int idx){
		if( isBounds(idx) ) return accounts.get(idx);
		return null;
	}

	public AccountData getAccountById(int id){
		int idx = getAccountIdxById(id);
		if( idx >= 0 ) return accounts.get(idx);
		return null;
	}



}
