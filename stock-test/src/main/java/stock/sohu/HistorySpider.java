package stock.sohu;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luonanqin on 4/18/15.
 */
public class HistorySpider {

	private static String IN_MARKET_PATH = "/Users/Luonanqin/stock/stockInMarket/";
	private static String field = "日期,开盘价,收盘价,涨跌额,涨跌幅,最低价,最高价,成交量（股）,成交额（万）,换手率";
	private static String uri_prefix = "http://quotes.money.163.com/service/chddata.html?code=0";
	private static String uri_bridge = "&start=";
	private static String uri_suffix = "&end=20150417&fields=TCLOSE;HIGH;LOW;TOPEN;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER";

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
			String str;
			while ((str = raf.readLine()) != null) {
				String[] split = str.split("_");

				stockInMarkets.put(split[0], split[1].replaceAll("\\-", ""));
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

	public static void main(String[] args) {
		for (String stockCodeName : Recorder.stockList) {
			System.out.println(stockCodeName);
			String stockCode = stockCodeName.substring(0, 6);
			List<HistoryStockData> stockData = getStockData(uri_prefix + stockCode + uri_bridge + stockInMarkets.get(stockCode) + uri_suffix);
			if (stockData.isEmpty()) {
				continue;
			}
			recordData(stockData, stockCodeName);
		}
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
				StringBuffer sb = new StringBuffer();
				boolean firstLine = true;
				for (int i = 0; i < result.length(); i++) {
					if (result.charAt(i) == '\r' && result.charAt(i + 1) == '\n') {
						i++;
						if (firstLine) {
							sb.delete(0, sb.length());
							firstLine = false;
							continue;
						}
						String data = sb.toString();
						sb.delete(0, sb.length());
						String[] dataSplit = data.split(",");

						String date = dataSplit[0].trim();
						String close = dataSplit[3].trim();
						String max = dataSplit[4].trim();
						String min = dataSplit[5].trim();
						String open = dataSplit[6].trim();
						String zde = dataSplit[7].trim();
						String zdf = dataSplit[8].trim();
						String hs = dataSplit[9].trim();
						String vols = dataSplit[10].trim();
						String vole = dataSplit[11].trim();

						if ("0".equals(vols) && "0.0".equals(vole)) {
							continue;
						}

						HistoryStockData hData = new HistoryStockData();
						hData.setDate(date);
						hData.setKp(open);
						hData.setSp(close);
						hData.setMax(max);
						hData.setMin(min);
						hData.setZdf(zdf);
						hData.setZde(zde);
						hData.setVols(vols);
						double temp = Double.valueOf(vole);
						hData.setVole("" + (long) temp);
						hData.setHs(hs);

						stockDatas.add(hData);
					} else {
						sb.append(result.charAt(i));
					}
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
}
