package stock.sohu;

import com.google.gson.Gson;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import stock.dzh.Recorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Luonanqin on 4/18/15.
 */
public class HistorySpider {

	private static String IN_MARKET_PATH = "/Users/Luonanqin/stock/stockInMarket/";
	private static String field = "日期,开盘价,收盘价,涨跌额,涨跌幅,最低价,最高价,成交量（手）,成交额（万）,换手率";
	private static Gson gson = new Gson();
	private static String uri_prefix = "http://q.stock.sohu.com/hisHq?code=cn_";
	private static String uri_bridge = "&start=";
	private static String uri_suffix = "&end=20150417&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp&r=0.";
	// private static String uri_suffix = "&start=19901219&end=20150417&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";

	public static PoolingHttpClientConnectionManager connectionManager;
	public static CloseableHttpClient httpClient;
	public static Map<String, String> stockInMarkets = new HashMap<String, String>();
	public static String HISTORY_FILE = "history_base.csv";

	static {
		CrawlConfig config = new CrawlConfig();
		RequestConfig requestConfig = RequestConfig.custom().setExpectContinueEnabled(false).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.setRedirectsEnabled(false).setSocketTimeout(50000).setConnectTimeout(50000).build();

		RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
		connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);

		Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
		connectionManager = new PoolingHttpClientConnectionManager(connRegistry);
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setDefaultRequestConfig(requestConfig);
		clientBuilder.setConnectionManager(connectionManager);

		httpClient = clientBuilder.build();

		File stockName = new File(IN_MARKET_PATH + "inMarket.txt");
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(stockName, "r");
			String str = null;
			while ((str = raf.readLine()) != null) {
				String[] split = str.split("_");

				stockInMarkets.put(split[0], split[1].replaceAll("\\-", ""));
			}
			// stockList.clear();
			// stockList.add("600000_浦发银行");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(new Date());
		for (String stockCodeName : Recorder.stockList) {
			System.out.println(stockCodeName);
			String stockCode = stockCodeName.substring(0, 6);
			List<HistoryStockData> stockData = getStockData(uri_prefix + stockCode + uri_bridge + stockInMarkets.get(stockCode) + uri_suffix);
			if (stockData.isEmpty()) {
				continue;
			}
			recordData(stockData, stockCodeName);
		}
		System.out.println(new Date());
	}

	private static List<HistoryStockData> getStockData(String url) {
		HttpGet get = null;
		List<HistoryStockData> stockDatas = new ArrayList<HistoryStockData>();
		try {
			get = new HttpGet(url);

			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				byte[] response = EntityUtils.toByteArray(entity);

				String result = new String(response, "utf-8");
				StringBuffer json = new StringBuffer(result);

				json.delete(0, 22);
				json.delete(json.length() - 3, json.length());

				HistoryData hsd = gson.fromJson(json.toString(), HistoryData.class);

				for (String[] days : hsd.getHq()) {
					HistoryStockData historyStockData = new HistoryStockData();
					historyStockData.setDate(days[0]);
					historyStockData.setKp(days[1]);
					historyStockData.setSp(days[2]);
					historyStockData.setZde(days[3]);
					historyStockData.setZdf(days[4]);
					historyStockData.setMin(days[5]);
					historyStockData.setMax(days[6]);
					historyStockData.setVols(days[7]);
					historyStockData.setVole(days[8]);
					historyStockData.setHs(days[9]);

					stockDatas.add(historyStockData);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
		return stockDatas;
	}

	public static void recordData(List<HistoryStockData> stockDatas, String stockCodeName) {
		String path = Recorder.BASE_PATH + stockCodeName + File.separator;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(path + HISTORY_FILE);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			long length = raf.length();
			if (length == 0) {
				raf.write(field.getBytes(Recorder.charset));
				raf.writeByte((byte) 0XA);
			} else {
				raf.skipBytes((int) length);
			}
			for (HistoryStockData data : stockDatas) {
				raf.write(data.toString().getBytes());
				raf.writeByte((byte) 0XA);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getRandomNum() {
		Random rm = new Random();

		int strLength = 17;
		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);

		// 返回固定的长度的随机数
		return fixLenthString.substring(1, strLength + 1);
	}
}
