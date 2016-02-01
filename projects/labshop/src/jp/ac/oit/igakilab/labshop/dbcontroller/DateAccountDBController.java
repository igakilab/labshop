package jp.ac.oit.igakilab.labshop.dbcontroller;

import java.util.Date;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public class DateAccountDBController extends AccountDBController {
	public static Bson createFilter(Date begin, Date end){
		Bson f_begin = null, f_end = null;

		if( begin != null ){
			f_begin = Filters.gte("timestamp", begin);
			if( end == null ) return f_begin;
		}
		if( end != null ){
			f_end = Filters.lte("timestamp", end);
			if( begin == null ) return f_end;
		}

		return Filters.and(f_begin, f_end);
	}

	/*コンストラクタ*/
	public DateAccountDBController(){
		super();
	}

	public DateAccountDBController(String host, int port){
		super(host, port);
	}

	public DateAccountDBController(DBConnector con){
		super(con);
	}


}
