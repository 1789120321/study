package stock.net126;

/**
 * Created by Luonanqin on 5/10/15.
 */
public class NetData {

	private float percent;
	private float high;
	private float price;
	private float open;
	private float low;
	private float updown;
	private float ask1;
	private float ask2;
	private float ask3;
	private float ask4;
	private float ask5;
	private int askvol1;
	private int askvol2;
	private int askvol3;
	private int askvol4;
	private int askvol5;
	private float bid1;
	private float bid2;
	private float bid3;
	private float bid4;
	private float bid5;
	private int bidvol1;
	private int bidvol2;
	private int bidvol3;
	private int bidvol4;
	private int bidvol5;
	private String time;
	private long volume;
	private long turnover;

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getUpdown() {
		return updown;
	}

	public void setUpdown(float updown) {
		this.updown = updown;
	}

	public float getAsk1() {
		return ask1;
	}

	public void setAsk1(float ask1) {
		this.ask1 = ask1;
	}

	public float getAsk2() {
		return ask2;
	}

	public void setAsk2(float ask2) {
		this.ask2 = ask2;
	}

	public float getAsk3() {
		return ask3;
	}

	public void setAsk3(float ask3) {
		this.ask3 = ask3;
	}

	public float getAsk4() {
		return ask4;
	}

	public void setAsk4(float ask4) {
		this.ask4 = ask4;
	}

	public float getAsk5() {
		return ask5;
	}

	public void setAsk5(float ask5) {
		this.ask5 = ask5;
	}

	public int getAskvol1() {
		return askvol1;
	}

	public void setAskvol1(int askvol1) {
		this.askvol1 = askvol1;
	}

	public int getAskvol2() {
		return askvol2;
	}

	public void setAskvol2(int askvol2) {
		this.askvol2 = askvol2;
	}

	public int getAskvol3() {
		return askvol3;
	}

	public void setAskvol3(int askvol3) {
		this.askvol3 = askvol3;
	}

	public int getAskvol4() {
		return askvol4;
	}

	public void setAskvol4(int askvol4) {
		this.askvol4 = askvol4;
	}

	public int getAskvol5() {
		return askvol5;
	}

	public void setAskvol5(int askvol5) {
		this.askvol5 = askvol5;
	}

	public float getBid1() {
		return bid1;
	}

	public void setBid1(float bid1) {
		this.bid1 = bid1;
	}

	public float getBid2() {
		return bid2;
	}

	public void setBid2(float bid2) {
		this.bid2 = bid2;
	}

	public float getBid3() {
		return bid3;
	}

	public void setBid3(float bid3) {
		this.bid3 = bid3;
	}

	public float getBid4() {
		return bid4;
	}

	public void setBid4(float bid4) {
		this.bid4 = bid4;
	}

	public float getBid5() {
		return bid5;
	}

	public void setBid5(float bid5) {
		this.bid5 = bid5;
	}

	public int getBidvol1() {
		return bidvol1;
	}

	public void setBidvol1(int bidvol1) {
		this.bidvol1 = bidvol1;
	}

	public int getBidvol2() {
		return bidvol2;
	}

	public void setBidvol2(int bidvol2) {
		this.bidvol2 = bidvol2;
	}

	public int getBidvol3() {
		return bidvol3;
	}

	public void setBidvol3(int bidvol3) {
		this.bidvol3 = bidvol3;
	}

	public int getBidvol4() {
		return bidvol4;
	}

	public void setBidvol4(int bidvol4) {
		this.bidvol4 = bidvol4;
	}

	public int getBidvol5() {
		return bidvol5;
	}

	public void setBidvol5(int bidvol5) {
		this.bidvol5 = bidvol5;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getTurnover() {
		return turnover;
	}

	public void setTurnover(long turnover) {
		this.turnover = turnover;
	}

	@Override
	public String toString() {
		return time + ',' + percent + ',' + high + ',' + price + ',' + open + ',' + low + ',' + updown + ',' + ask1 + ',' + ask2 + ',' + ask3 + ',' + ask4
				+ ',' + ask5 + ',' + askvol1 + ',' + askvol2 + ',' + askvol3 + ',' + askvol4 + ',' + askvol5 + ',' + bid1 + ',' + bid2 + ',' + bid3 + ','
				+ bid4 + ',' + bid5 + ',' + bidvol1 + ',' + bidvol2 + ',' + bidvol3 + ',' + bidvol4 + ',' + bidvol5 + ',' + volume + ',' + turnover;
	}
}
