package jp.ac.oit.igakilab.labshop.shopping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bson.conversions.Bson;

import jp.ac.oit.igakilab.labshop.dbcontroller.extension.AggregateAccountDB;

public class MemberItemSalesChart {
	static Comparator<Integer> INTEGER_COMPARATOR = new Comparator<Integer>(){
		@Override
		public int compare(Integer o1, Integer o2) {
			return Integer.compare(o1, o2);
		}
	};


	private List<Integer> memberList;
	private List<Integer> itemList;
	private List<int[]> memberItemCount;
	private List<int[]> memberItemPrice;
	private List<Integer> memberSumPrice;


	public MemberItemSalesChart(){
		memberList = new ArrayList<Integer>();
		itemList = new ArrayList<Integer>();
		memberItemCount = new ArrayList<int[]>();
		memberItemPrice = new ArrayList<int[]>();
		memberSumPrice = new ArrayList<Integer>();
	}

	void initMemberProperty(int len){
		memberItemCount.clear();
		memberItemPrice.clear();
		memberSumPrice.clear();
		for(int i=0; i<len; i++){
			memberItemPrice.add(null);
			memberItemCount.add(null);
			memberSumPrice.add(0);
		}
	}

	int[] createIntArray(int len, int initVal){
		int[] array = new int[len];
		for(int i=0; i<len; i++) array[i] = initVal;
		return array;
	}


	public void buildChart(AggregateAccountDB aadb, Bson filter){
		//メンバー基本集計データの取得
		List<HashMap<String, Integer>> aggrMember =
			aadb.getMemberChargeList(filter);
		//アイテム基本集計データの取得
		List<HashMap<String, Integer>> aggrItem =
			aadb.getItemSalesList(filter);

		//メンバーリスト整形
		for(HashMap<String, Integer> mrow : aggrMember){
			int idx = memberList.indexOf(mrow.get("memberId"));
			if( idx < 0 ){
				memberList.add(mrow.get("memberId"));
			}
		}
		memberList.sort(INTEGER_COMPARATOR);
		initMemberProperty(memberList.size());

		//アイテムリスト整形
		for(HashMap<String, Integer> irow : aggrItem){
			int idx = itemList.indexOf(irow.get("itemId"));
			if( idx < 0 ){
				itemList.add(irow.get("itemId"));
			}
		}
		itemList.sort(INTEGER_COMPARATOR);

		//メンバープロファイルの設定
		for(int i=0; i<memberList.size(); i++){
			//データ生成
			int[] itemCount = createIntArray(itemList.size(), 0);
			int[] itemPrice = createIntArray(itemList.size(), 0);

			//sumPrice設定
			int mprice = 0;
			for(HashMap<String, Integer> tmp : aggrMember){
				if( tmp.get("memberId") == memberList.get(i) ){
					mprice = tmp.get("sumPrice");
					break;
				}
			}

			//アイテム系リストの設定
			if( mprice > 0 ){
				List<HashMap<String, Integer>> midata =
					aadb.getMemberItemList(memberList.get(i), filter);

				for(HashMap<String, Integer> map : midata){
					int itemIdx = itemList.indexOf(map.get("itemId"));
					itemCount[itemIdx] = map.get("qty");
					itemPrice[itemIdx] = map.get("sumPrice");
				}
			}

			//データの格納
			memberSumPrice.set(i, mprice);
			memberItemCount.set(i, itemCount);
			memberItemPrice.set(i, itemPrice);
		}
	}

	public Integer[] getMemberList(){
		return memberList.toArray(new Integer[memberList.size()]);
	}

	public int getMemberCount(){
		return memberList.size();
	}

	public Integer[] getItemList(){
		return itemList.toArray(new Integer[itemList.size()]);
	}

	public int getItemCount(){
		return itemList.size();
	}

	public int[] getMemberItemCount(int idx){
		return memberItemCount.get(idx);
	}

	public int[] getMemberItemCountByMemberId(int id){
		int idx = memberList.indexOf(id);
		if( idx >= 0 ){
			return getMemberItemCount(idx);
		}else{
			return createIntArray(itemList.size(), 0);
		}
	}

	public int[] getMemberItemPrice(int idx){
		return memberItemPrice.get(idx);
	}

	public int[] getMemberItemPriceByMemberId(int id){
		int idx = memberList.indexOf(id);
		if( idx >= 0 ){
			return getMemberItemPrice(idx);
		}else{
			return createIntArray(itemList.size(), 0);
		}
	}

	public int getMemberSumPrice(int idx){
		return memberSumPrice.get(idx);
	}

	public int getMemberSumPriceByMemberId(int id){
		int idx = memberList.indexOf(id);
		if( idx >= 0 ){
			return getMemberSumPrice(idx);
		}else{
			return 0;
		}
	}

	public int getItemSumCount(int idx){
		int cnt = 0;
		for(int[] ary : memberItemCount){
			cnt += ary[idx];
		}
		return cnt;
	}

	public int getItemSumCountByItemId(int id){
		int idx = itemList.indexOf(id);
		if( idx >= 0 ){
			return getItemSumCount(idx);
		}else{
			return 0;
		}
	}

	public int getItemSumPrice(int idx){
		int price = 0;
		for(int[] ary : memberItemPrice){
			price += ary[idx];
		}
		return price;
	}

	public int getItemSumPriceByItemId(int id){
		int idx = itemList.indexOf(id);
		if( idx >= 0 ){
			return getItemSumPrice(idx);
		}else{
			return 0;
		}
	}

	public int getAllSumPrice(){
		int price = 0;
		for(Integer p : memberSumPrice){
			price += p;
		}
		return price;
	}
}