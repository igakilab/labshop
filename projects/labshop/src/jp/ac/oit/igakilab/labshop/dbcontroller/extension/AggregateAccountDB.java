package jp.ac.oit.igakilab.labshop.dbcontroller.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.DBConnector;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class AggregateAccountDB extends AccountDBController {
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
				Filters.and(Filters.eq("memberId", id), filter)).sort(Sorts.ascending("timestamp"));
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
				Filters.and(Filters.eq("itemId", id), filter)).sort(Sorts.ascending("timestamp"));
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
		List<? extends Bson> query = Arrays.asList(
			Aggregates.match(filter),
			Aggregates.group("$memberId", Accumulators.sum("sumPrice", "$sellPrice")),
			Aggregates.sort(Sorts.ascending("_id"))
		);

		result = getCollection().aggregate(query);

		List<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		for(Document doc : result){
			HashMap<String, Integer> tmp = new HashMap<String, Integer>();
			tmp.put("memberId", doc.getInteger("_id"));
			tmp.put("sumPrice", doc.getInteger("sumPrice"));
			list.add(tmp);
		}

		return list;
	}

	public List<HashMap<String, Integer>> getMemberItemList(int memberId, Bson filter){
		AggregateIterable<Document> result;
		List<? extends Bson> query = Arrays.asList(
			Aggregates.match(Filters.and(Filters.eq("memberId", memberId), filter)),
			Aggregates.group("$itemId", Arrays.asList(
				Accumulators.sum("qty", 1),
				Accumulators.sum("sumPrice", "$sellPrice")
			)),
			Aggregates.sort(Sorts.ascending("_id"))
		);

		result = getCollection().aggregate(query);

		List<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		for(Document doc : result){
			HashMap<String, Integer> tmp = new HashMap<String, Integer>();
			tmp.put("itemId", doc.getInteger("_id"));
			tmp.put("qty", doc.getInteger("qty"));
			tmp.put("sumPrice", doc.getInteger("sumPrice"));
			list.add(tmp);
		}

		return list;
	}

	public List<HashMap<String, Integer>> getItemSalesList(Bson filter){
		List<? extends Bson> query = Arrays.asList(
			Aggregates.match(filter),
			Aggregates.group("$itemId", Arrays.asList(
				Accumulators.sum("qty", 1),
				Accumulators.sum("sumPrice", "$sellPrice")
			)),
			Aggregates.sort(Sorts.ascending("_id"))
		);

		AggregateIterable<Document> result = getCollection().aggregate(query);

		List<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		for(Document doc : result){
			HashMap<String, Integer> tmp = new HashMap<String, Integer>();
			tmp.put("itemId", doc.getInteger("_id"));
			tmp.put("qty", doc.getInteger("qty"));
			tmp.put("sumPrice", doc.getInteger("sumPrice"));
			list.add(tmp);
		}

		return list;
	}



}
