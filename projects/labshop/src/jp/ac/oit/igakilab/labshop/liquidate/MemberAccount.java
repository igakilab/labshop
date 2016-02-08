package jp.ac.oit.igakilab.labshop.liquidate;

import java.util.List;

import org.bson.conversions.Bson;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class MemberAccount {
	private int mid;
	private AccountData[] alist;

	private static MemberAccount getInstance(int id, List<AccountData> lst){
		MemberAccount acc = new MemberAccount();
		acc.mid = id;
		acc.alist = lst.toArray(new AccountData[lst.size()]);
		return acc;
	}

	public static MemberAccount getInstance(AggregateAccountDB dbc, int id){
		return getInstance(id, dbc.getAccountListByItemId(id));
	}

	public static MemberAccount getInstance(AggregateAccountDB dbc, int id, Bson filter){
		return getInstance(id, dbc.getAccountListByMemberId(id, filter));
	}

	public static MemberAccount getInstance(AggregateAccountDB dbc, int id, int year, int month){
		return getInstance(dbc, id,
			AggregateAccountDB.monthDateFilter(
				AggregateAccountDB.monthDate(year, month)
			)
		);
	}







}
