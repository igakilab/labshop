package jp.ac.oit.igakilab.labshop.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bson.Document;

import com.mongodb.client.FindIterable;

import jp.ac.oit.igakilab.labshop.dbcontroller.AccountDBController;
import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;

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

	public static void main(String[] args){
		AccountDBController dbc = new AccountDBController();
		FindIterable<Document> result = dbc.getCollection().find();
		showResult(result);
	}
}
