package jp.ac.oit.igakilab.labshop.item;

public class ItemGroupData {
	public static int MAX_ID_VALUE = 9999;


	private int id;
	private String name;

	public ItemGroupData(){
		setId(0);
		setName("noname");
	}

	public ItemGroupData(int i0, String n0){
		this();
		setId(i0);
		setName(n0);
	}

	public int getId(){
		return id;
	}
	public void setId(int i0){
		if( id >= 0 && id <= MAX_ID_VALUE ){
			id = i0;
		}
	}

	public String getName(){
		return name;
	}
	public void setName(String n0){
		name = n0;
	}
}
