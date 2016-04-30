package jp.ac.oit.igakilab.labshop.shopping;

import java.util.List;

public class MemberSalesAggregator {
	List<AccountData> accountList;
	
	public void clearAccount(){
		accountList.clear();
	}
	
	public void addAccount(AccountData data){
		accountList.add(data);
	}
	
	public void addAccount(AccountData[] list){
		for(AccountData data : list){
			addAccount(data);
		}
	}
	
	
}
