package jp.ac.oit.igakilab.labshop.dbcontroller.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class AggregateAccountDB extends AccountDBController {

	public static Bson dateFilter(Calendar start, Calendar end){
		Bson f_dstart = null;
		Bson f_dend = null;
		if( start != null ){
			f_dstart = Filters.gte("timestamp", start);
			if( end == null ){
				return f_dstart;
			}
		}
		if( end != null ){
			f_dend = Filters.lte("timestamp", end);
			if( start == null ){
				return f_dend;
			}
		}
		if( start != null && end != null ){
			return Filters.and(f_dstart, f_dend);
		}else{
			return null;
		}
	}

	public static Bson monthDateFilter(Calendar start, Calendar end){
		Calendar tmp = Calendar.getInstance();
		Bson f_mstart = null;
		Bson f_mend = null;
		if( start != null ){
			tmp.clear();
			tmp.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 1);
			f_mstart = Filters.gte("timestamp", tmp);
			if( end == null ){
				return f_mstart;
			}
		}
		if( end != null ){
			tmp.clear();
			tmp.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH + 1), 1);
			tmp.add(Calendar.MONTH, 1);
			f_mend = Filters.lt("timestamp", tmp);
			if( start == null ){
				return f_mend;
			}
		}
		if( start != null && end != null ){
			return Filters.and(f_mstart, f_mend);
		}else{
			return null;
		}
	}

	public static Bson monthDateFilter(Calendar month){
		return monthDateFilter(month, month);
	}

	public static Calendar monthDate(int year, int month){
		Calendar tmp = Calendar.getInstance();
		tmp.clear();
		tmp.set(year, month, 1);
		return tmp;
	}



	public AggregateAccountDB(){
		super();
	}

	public AggregateAccountDB(String host, int port){
		super(host, port);
	}

	public AggregateAccountDB(DBConnector con){
		super(con);
	}

	public List<AccountData> getAccountList(Bson filter){
		FindIterable<Document> result;
		if( filter != null ){
			result = getCollection().find(filter);
		}else{
			result = getCollection().find();
		}
		return toAccountData(result);
	}

	public List<AccountData> getAccountList(){
		return getAccountList((Bson)null);
	}

	public List<AccountData> getAccountListByMemberId(int id, Bson filter){
		FindIterable<Document> result;
		if( filter != null ){
			result = getCollection().find(
				Filters.and(Filters.eq("memberId", id), filter));
		}else{
			result = getCollection().find(Filters.eq("memberId", id));
		}
		return toAccountData(result);
	}

	public List<AccountData> getAccountListByMemberId(int id){
		return getAccountListByMemberId(id, null);
	}

	public List<AccountData> getAccountListByItemId(int id, Bson filter){
		FindIterable<Document> result;
		if( filter != null ){
			result = getCollection().find(
				Filters.and(Filters.eq("itemId", id), filter));
		}else{
			result = getCollection().find(Filters.eq("itemId", id));
		}
		return toAccountData(result);
	}

	public List<AccountData> getAccountListByItemId(int id){
		return getAccountListByItemId(id, null);
	}

	public List<HashMap<String, Integer>> getMemberChargeList(Bson filter){
		AggregateIterable<Document> result;
		result = getCollection().aggregate(Arrays.asList(
			Aggregates.match(filter),
			Aggregates.group("memberId", Accumulators.sum("sumPrice", "$sellPrice"))
		));

		List<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		for(Document doc : result){
			HashMap<String, Integer> tmp = new HashMap<String, Integer>();
			tmp.put("memberId", doc.getInteger("_id"));
			tmp.put("sumPrice", doc.getInteger("sumPrice"));
			list.add(tmp);
		}

		return list;
	}


}
