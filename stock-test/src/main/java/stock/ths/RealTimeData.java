package stock.ths;

/**
 * Created by Luonanqin on 4/7/15.
 */
public class RealTimeData {

	// "jj":"13.41"
	private int code;
	private String name;
	private double currentPrice;
	private double riseFallSize; // 涨跌幅
	private double riseFallMoney; // 涨跌金额
	private double tradeMoney; // 成交额数值
	private String tradeMoneyUnit; // 成交额单位
	private double tradeSum; // 成交量数值
	private String tradeSumUnit; // 成交量单位
	private double beginPrice; // 开盘价
	private double lastDayPrice; // 昨收
	private double maxPrice; // 最高价
	private double minPrice; // 最低价
	private double conversionRate; // 换手率
	private double profitRate; // 市盈率（动）
	private long innerZone; // 内盘
	private long outerZone; // 外盘
	private double shake; // 振幅
	private double riseTop; // 涨停
	private double fallBottom; // 跌停
	private double entrustRate; // 委比
	private double entrustDiff; // 委差
	private double buy1Price; // 买一价
	private int buy1Sum; // 买一手
	private double buy2Price; // 买二价
	private int buy2Sum; // 买二手
	private double buy3Price; // 买三价
	private int buy3Sum; // 买三手
	private double buy4Price; // 买四价
	private int buy4Sum; // 买四手
	private double buy5Price; // 买五价
	private int buy5Sum; // 买五手
	private double sell1Price; // 卖一价
	private int sell1Sum; // 卖一手
	private double sell2Price; // 卖二价
	private int sell2Sum; // 卖二手
	private double sell3Price; // 卖三价
	private int sell3Sum; // 卖三手
	private double sell4Price; // 卖四价
	private int sell4Sum; // 卖四手
	private double sell5Price; // 卖五价
	private int sell5Sum; // 卖五手
	private double sector; // 所属行业涨跌幅

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getRiseFallSize() {
		return riseFallSize;
	}

	public void setRiseFallSize(double riseFallSize) {
		this.riseFallSize = riseFallSize;
	}

	public double getRiseFallMoney() {
		return riseFallMoney;
	}

	public void setRiseFallMoney(double riseFallMoney) {
		this.riseFallMoney = riseFallMoney;
	}

	public double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getTradeMoneyUnit() {
		return tradeMoneyUnit;
	}

	public void setTradeMoneyUnit(String tradeMoneyUnit) {
		this.tradeMoneyUnit = tradeMoneyUnit;
	}

	public double getTradeSum() {
		return tradeSum;
	}

	public void setTradeSum(double tradeSum) {
		this.tradeSum = tradeSum;
	}

	public String getTradeSumUnit() {
		return tradeSumUnit;
	}

	public void setTradeSumUnit(String tradeSumUnit) {
		this.tradeSumUnit = tradeSumUnit;
	}

	public double getBeginPrice() {
		return beginPrice;
	}

	public void setBeginPrice(double beginPrice) {
		this.beginPrice = beginPrice;
	}

	public double getLastDayPrice() {
		return lastDayPrice;
	}

	public void setLastDayPrice(double lastDayPrice) {
		this.lastDayPrice = lastDayPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}

	public double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}

	public long getInnerZone() {
		return innerZone;
	}

	public void setInnerZone(long innerZone) {
		this.innerZone = innerZone;
	}

	public long getOuterZone() {
		return outerZone;
	}

	public void setOuterZone(long outerZone) {
		this.outerZone = outerZone;
	}

	public double getShake() {
		return shake;
	}

	public void setShake(double shake) {
		this.shake = shake;
	}

	public double getRiseTop() {
		return riseTop;
	}

	public void setRiseTop(double riseTop) {
		this.riseTop = riseTop;
	}

	public double getFallBottom() {
		return fallBottom;
	}

	public void setFallBottom(double fallBottom) {
		this.fallBottom = fallBottom;
	}

	public double getEntrustRate() {
		return entrustRate;
	}

	public void setEntrustRate(double entrustRate) {
		this.entrustRate = entrustRate;
	}

	public double getEntrustDiff() {
		return entrustDiff;
	}

	public void setEntrustDiff(double entrustDiff) {
		this.entrustDiff = entrustDiff;
	}

	public double getBuy1Price() {
		return buy1Price;
	}

	public void setBuy1Price(double buy1Price) {
		this.buy1Price = buy1Price;
	}

	public int getBuy1Sum() {
		return buy1Sum;
	}

	public void setBuy1Sum(int buy1Sum) {
		this.buy1Sum = buy1Sum;
	}

	public double getBuy2Price() {
		return buy2Price;
	}

	public void setBuy2Price(double buy2Price) {
		this.buy2Price = buy2Price;
	}

	public int getBuy2Sum() {
		return buy2Sum;
	}

	public void setBuy2Sum(int buy2Sum) {
		this.buy2Sum = buy2Sum;
	}

	public double getBuy3Price() {
		return buy3Price;
	}

	public void setBuy3Price(double buy3Price) {
		this.buy3Price = buy3Price;
	}

	public int getBuy3Sum() {
		return buy3Sum;
	}

	public void setBuy3Sum(int buy3Sum) {
		this.buy3Sum = buy3Sum;
	}

	public double getBuy4Price() {
		return buy4Price;
	}

	public void setBuy4Price(double buy4Price) {
		this.buy4Price = buy4Price;
	}

	public int getBuy4Sum() {
		return buy4Sum;
	}

	public void setBuy4Sum(int buy4Sum) {
		this.buy4Sum = buy4Sum;
	}

	public double getBuy5Price() {
		return buy5Price;
	}

	public void setBuy5Price(double buy5Price) {
		this.buy5Price = buy5Price;
	}

	public int getBuy5Sum() {
		return buy5Sum;
	}

	public void setBuy5Sum(int buy5Sum) {
		this.buy5Sum = buy5Sum;
	}

	public double getSell1Price() {
		return sell1Price;
	}

	public void setSell1Price(double sell1Price) {
		this.sell1Price = sell1Price;
	}

	public int getSell1Sum() {
		return sell1Sum;
	}

	public void setSell1Sum(int sell1Sum) {
		this.sell1Sum = sell1Sum;
	}

	public double getSell2Price() {
		return sell2Price;
	}

	public void setSell2Price(double sell2Price) {
		this.sell2Price = sell2Price;
	}

	public int getSell2Sum() {
		return sell2Sum;
	}

	public void setSell2Sum(int sell2Sum) {
		this.sell2Sum = sell2Sum;
	}

	public double getSell3Price() {
		return sell3Price;
	}

	public void setSell3Price(double sell3Price) {
		this.sell3Price = sell3Price;
	}

	public int getSell3Sum() {
		return sell3Sum;
	}

	public void setSell3Sum(int sell3Sum) {
		this.sell3Sum = sell3Sum;
	}

	public double getSell4Price() {
		return sell4Price;
	}

	public void setSell4Price(double sell4Price) {
		this.sell4Price = sell4Price;
	}

	public int getSell4Sum() {
		return sell4Sum;
	}

	public void setSell4Sum(int sell4Sum) {
		this.sell4Sum = sell4Sum;
	}

	public double getSell5Price() {
		return sell5Price;
	}

	public void setSell5Price(double sell5Price) {
		this.sell5Price = sell5Price;
	}

	public int getSell5Sum() {
		return sell5Sum;
	}

	public void setSell5Sum(int sell5Sum) {
		this.sell5Sum = sell5Sum;
	}

	public double getSector() {
		return sector;
	}

	public void setSector(double sector) {
		this.sector = sector;
	}
}
