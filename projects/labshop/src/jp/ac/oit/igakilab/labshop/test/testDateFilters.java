package jp.ac.oit.igakilab.labshop.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;
import jp.ac.oit.igakilab.labshop.shopping.AccountData;

public class testDateFilters {
	public static void showCalendar(Calendar cal){
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(df.format(cal.getTime()));
	}

	public static void showMonthCalendar(int year, int month){
		Calendar cal = DateFilters.month(year, month);
		showCalendar(cal);
	}

	public static void showResult(FindIterable<Document> r){
		for(Document doc : r){
			System.out.println(doc.toString());
		}
	}

	public static void showResult(AggregateIterable<Document> a){
		for(Document doc : a){
			System.out.println(doc.toString());
		}
	}

	public static void showResult(List<AccountData> list){
		for(AccountData data : list){
			System.out.println(data.toString());
		}
	}

	public static void showMapResult(List<HashMap<String, Integer>> mlist){
		for(HashMap<String, Integer> map : mlist){
			showHash(map);
		}
	}

	public static void showHash(HashMap<String, Integer> map){
		Set<String> keys = map.keySet();
		System.out.print("{ ");
		for(String key : keys){
			System.out.print(key + ":" + map.get(key) + " ");
		}
		System.out.println("}");
	}

	public static void main(String[] args){
		AggregateAccountDB aadb = new AggregateAccountDB();
		showMapResult(aadb.getItemSalesList(
			DateFilters.oneMonth("timestamp", DateFilters.month(2016, 1))));
	}
}
