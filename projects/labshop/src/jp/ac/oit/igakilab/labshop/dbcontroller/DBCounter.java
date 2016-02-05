package jp.ac.oit.igakilab.labshop.dbcontroller;

public class DBCounter {
	private String key;
	private int counter;
	private int minValue;
	private int maxValue;

	public DBCounter(){
		setKey("unknown");
		setCounter(0);
		setMinValue(0);
		setMaxValue(Integer.MAX_VALUE);
	}

	public DBCounter(String key, int ic){
		this();
		setKey(key);
		setCounter(ic);
	}

	public DBCounter(String key, int ic, int min, int max){
		this();
		setKey(key);
		setCounter(Math.max(min, Math.min(ic, max)));
		setMinValue(min);
		setMaxValue(max);
	}

	/* setter / getter */
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		if( minValue < this.maxValue ){
			this.minValue = minValue;
		}
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		if( this.minValue < maxValue ){
			this.maxValue = maxValue;
		}
	}

	public int pop(){
		int tmp = counter;
		if( counter >= maxValue ){
			counter = minValue;
		}else{
			counter++;
		}
		return tmp;
	}

	public void reset(){
		counter = minValue;
	}
}