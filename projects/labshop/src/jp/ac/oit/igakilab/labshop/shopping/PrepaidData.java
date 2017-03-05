package jp.ac.oit.igakilab.labshop.shopping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrepaidData {
	public static int MAX_ID_VALUE = 99999999;

	private int id;
	private Date timestamp;
	private int memberId;
	private int price;

	public PrepaidData(){
		setId(0);
		setTimestamp();
		setMemberId(0);
		setPrice(0);
	}

	public PrepaidData(int id, int m_id, int price){
		setTimestamp();
		setId(id);
		setMemberId(m_id);
		setPrice(price);
	}

	public PrepaidData(int id, Date ts, int m_id, int price){
		setTimestamp(ts);
		setId(id);
		setMemberId(m_id);
		setPrice(price);
	}

	public PrepaidData(int memberId, int price) {
		this(0, memberId, price);
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public String toString(){
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return String.format("PREPAID[ID:%d, MEMBER:%d, PRICE:%d DATE:%s]\n",
			id, memberId, price, df.format(timestamp));
	}


}
