package jp.ac.oit.igakilab.labshop.dbcontroller.extension;

import java.util.Calendar;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public class DateFilters {
	public static Calendar month(Calendar cal){
		return month(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}

	public static Calendar month(int year, int month){
		return date(year, month, 1);
	}

	public static Calendar date(Calendar cal){
		return date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
	}

	public static Calendar date(int year, int month, int date){
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

	public static Bson betweenDate(String field, Calendar start, Calendar end){
		Bson f_dstart = null;
		Bson f_dend = null;

		if( start != null ){
			f_dstart = Filters.gte(field, date(start));
			if( end == null ){
				return f_dstart;
			}
		}
		if( end != null ){
			Calendar tmp = date(end);
			tmp.add(Calendar.DATE, 1);
			f_dend = Filters.lt(field, tmp);
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
			f_mstart = Filters.gte(field, month(start));
			if( end == null ){
				return f_mstart;
			}
		}
		if( end != null ){
			Calendar tmp = month(end);
			tmp.add(Calendar.MONTH, 1);
			f_mend = Filters.lt(field, tmp);
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
