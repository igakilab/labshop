package jp.ac.oit.igakilab.labshop.shopping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountData {
	public static int MAX_ID_VALUE = 99999999;

	private int id;
	private Date timestamp;
	private int memberId;
	private int itemId;
	private int sellPrice;

	public AccountData(){
		setId(0);
		setTimestamp();
		setMemberId(0);
		setItemId(0);
		setSellPrice(0);
	}

	public AccountData(int id, int m_id, int i_id, int price){
		setTimestamp();
		setId(id);
		setMemberId(m_id);
		setItemId(i_id);
		setSellPrice(price);
	}

	public AccountData(int id, Date ts, int m_id, int i_id, int price){
		setTimestamp(ts);
		setId(id);
		setMemberId(m_id);
		setItemId(i_id);
		setSellPrice(price);
	}

	/* setter/getter */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		if( id >= 0 && id <= MAX_ID_VALUE ){
			this.id = id;
		}
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public void setTimestamp(){
		this.timestamp = Calendar.getInstance().getTime();
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String toString(){
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return String.format("ACCOUNT[ID:%d, ITEM:%d MEMBER:%d, PRICE:%d DATE:%s]\n",
			id, itemId, memberId, sellPrice, df.format(timestamp));
	}


}
