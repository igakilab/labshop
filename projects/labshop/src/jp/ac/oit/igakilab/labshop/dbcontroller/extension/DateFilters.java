package jp.ac.oit.igakilab.labshop.dbcontroller.extension;

import java.util.Calendar;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public class DateFilters {
	public static Calendar genMonthDate(Calendar cal){
		return genMonthDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}

	public static Calendar genMonthDate(int year, int month){
		return genDate(year, month, 1);
	}

	public static Calendar genDate(int year, int month, int date){
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month, date);
		return cal;
	}

	public static Bson between(String field, Calendar start, Calendar end){
		Bson f_dstart = null;
		Bson f_dend = null;
		if( start != null ){
			f_dstart = Filters.gte(field, start);
			if( end == null ){
				return f_dstart;
			}
		}
		if( end != null ){
			f_dend = Filters.lte(field, end);
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

	public static Bson betweenMonth(String field, Calendar start, Calendar end){
		Bson f_mstart = null;
		Bson f_mend = null;
		if( start != null ){
			f_mstart = Filters.gte("timestamp", genMonthDate(start));
			if( end == null ){
				return f_mstart;
			}
		}
		if( end != null ){
			f_mend = Filters.lt("timestamp", genMonthDate(end));
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

	public static Bson oneMonth(String field, Calendar month){
		return betweenMonth(field, month, month);
	}
}
