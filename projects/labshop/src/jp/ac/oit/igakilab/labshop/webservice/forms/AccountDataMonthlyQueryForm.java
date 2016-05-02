package jp.ac.oit.igakilab.labshop.webservice.forms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.DateFilters;

public class AccountDataMonthlyQueryForm {
	private int[] memberIds;
	private int[] itemIds;
	private int startMonthValue;
	private int endMonthValue;

	public AccountDataMonthlyQueryForm(){
		memberIds = null;
		itemIds = null;
		startMonthValue = 0;
		endMonthValue = 0;
	}

	public int[] getMemberIds() {
		return memberIds;
	}
	public void setMemberIds(int[] memberIds) {
		this.memberIds = memberIds;
	}
	public int[] getItemIds() {
		return itemIds;
	}
	public void setItemIds(int[] itemIds) {
		this.itemIds = itemIds;
	}
	public int getStartMonthValue() {
		return startMonthValue;
	}
	public void setStartMonthValue(int startMonthValue) {
		this.startMonthValue = startMonthValue;
	}
	public int getEndMonthValue() {
		return endMonthValue;
	}
	public void setEndMonthValue(int endMonthValue) {
		this.endMonthValue = endMonthValue;
	}


	public static int monthVal(int year, int month){
		return (year * 12) + (month - 1);
	}

	public static Calendar toCalendar(int monthVal){
		return DateFilters.month(monthVal / 12, (monthVal % 12) + 1);
	}

	public Bson getBsonFilter(){
		List<Bson> andNode = new ArrayList<Bson>();
		List<Bson> tmp = new ArrayList<Bson>();

		//member filter
		if( memberIds != null && memberIds.length > 0 ){
			tmp.clear();
			for(int mid : memberIds){
				tmp.add(Filters.eq("memberId", mid));
			}
			andNode.add(Filters.or(tmp));
		}

		//item filter
		if( itemIds != null && itemIds.length > 0 ){
			tmp.clear();
			for(int iid : itemIds){
				tmp.add(Filters.eq("itemId", iid));
			}
			andNode.add(Filters.or(tmp));
		}

		//date filterq
		Calendar sd = null;
		if( startMonthValue > 0 ) sd = DateFilters.month(toCalendar(startMonthValue));
		Calendar ed = null;
		if( endMonthValue > 0 ) ed = DateFilters.month(toCalendar(endMonthValue));
		Bson dfilter = DateFilters.betweenMonth("timestamp", sd, ed);
		if( dfilter != null ) andNode.add(dfilter);

		return Filters.and(andNode);
	}
}
