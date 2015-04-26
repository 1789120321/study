package stock.base.moveAvg;

import stock.dzh.Recorder;

import java.util.Deque;

/**
 * Created by Luonanqin on 4/26/15.
 */
// 20日均线
public class MA_20 {

	public static void main(String[] args) {
		MA ma = new MA(20);
		for (String stockName : Recorder.stockList) {
			System.out.println(stockName);
			Deque<HistoryPrice> prices = ma.getPriceInfo(stockName);
			Deque<MaData> maDatas = ma.computeMA(prices);
			ma.recorder(maDatas, stockName);
		}
	}
}
