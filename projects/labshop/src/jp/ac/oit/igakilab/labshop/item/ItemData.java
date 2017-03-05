package jp.ac.oit.igakilab.labshop.item;

public class ItemData {
	public static int MAX_ID_VALUE = 999999;


	/*いんすたんす*/
	private int id;
	private String name;
	private boolean isOnSale;
	private int price;
	private int count;
	private boolean isFood;
	private boolean isDrink;

	public ItemData(){
		id = 0;
		name = "noname";
		price = 0;
		count = 0;
		isOnSale = false;
		isFood = false;
		isDrink = false;
	}

	public ItemData(int i0, String n0){
		this();
		setId(i0);
		setName(n0);
	}

	public int getId(){
		return id;
	}
	public void setId(int i0){
		if( i0 >= 0 && i0 < MAX_ID_VALUE ){
			id = i0;
		}
	}

	public String getName(){
		return name;
	}
	public void setName(String n0){
		name = n0;
	}

	public boolean getIsOnSale(){
		return isOnSale;
	}
	public void setIsOnSale(boolean a0){
		isOnSale = a0;
	}

	public int getPrice(){
		return price;
	}
	public void setPrice(int p0){
		price = p0;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getIsFood() {
		return isFood;
	}

	public void setIsFood(boolean isFood) {
		this.isFood = isFood;
	}

	public boolean getIsDrink() {
		return isDrink;
	}

	public void setIsDrink(boolean isDrink) {
		this.isDrink = isDrink;
	}
}
