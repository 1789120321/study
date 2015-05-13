package stock.net126;

import com.google.gson.Gson;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
 * Created by Luonanqin on 5/10/15.
 */
public class Net126Spider {
	public static PoolingHttpClientConnectionManager connectionManager;
	public static CloseableHttpClient httpClient;
	private static String uri_prefix = "http://api.money.126.net/data/feed/0";
	private static Gson gson = new Gson();

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
	}

	public static Map<String, List<NetData>> stockDatas = new HashMap<String, List<NetData>>();
	private static Map<String, String> stockCodeMap = new HashMap<String, String>();

	public static void main(String[] args) throws IOException {
		// Thread record = new Thread(new RecordStockData(), "recordData");
		// record.start();
		// Recorder.stockList.clear();
		// Recorder.stockList.add("600000_浦发银行");
		int i = 1;
		while (true) {
			i = 1;
			for (String stockCodeName : Recorder.stockList) {
				// if (!stockDatas.containsKey(stockCodeName)) {
				// stockDatas.put(stockCodeName, new ArrayList<NetData>());
				// }
				// System.out.println(stockCodeName);
				if (!stockCodeMap.containsKey(stockCodeName)) {
					String stockCode = stockCodeName.substring(0, 6);
					stockCodeMap.put(stockCodeName, stockCode);
				}
				NetData stockData = getRealData(uri_prefix + stockCodeMap.get(stockCodeName));
				if (stockData == null) {
					continue;
				}
				System.out.println(System.currentTimeMillis() + "  " + i++);
				// synchronized (stockCodeName) {
				// stockDatas.get(stockCodeName).add(stockData);
				// }
				// recordData(stockData, stockCodeName);
			}
		}
	}

	private static NetData getRealData(String url) {
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse resp = httpClient.execute(get);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				byte[] response = EntityUtils.toByteArray(entity);

				String result = new String(response, "utf-8");
				StringBuffer json = new StringBuffer(result);

				json.delete(0, 32);
				json.delete(json.length() - 4, json.length());

				NetData data = gson.fromJson(json.toString(), NetData.class);
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return null;
	}

	public static void recordData(NetData stockData, String stockCodeName) {
		String path = Recorder.BASE_PATH + stockCodeName + File.separator;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(path + "20150511.csv");
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
			raf.skipBytes((int) length);
			raf.write(stockData.toString().getBytes());
			raf.writeByte((byte) 0XA);
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

class RecordStockData implements Runnable {

	public void run() {
		while (true) {
			for (String stockCodeName : Recorder.stockList) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List<NetData> netDatas = Net126Spider.stockDatas.get(stockCodeName);
				if (netDatas == null) {
					continue;
				}
				List<NetData> stockData = null;
				synchronized (stockCodeName) {
					stockData = new ArrayList<NetData>(Net126Spider.stockDatas.get(stockCodeName));
					Net126Spider.stockDatas.get(stockCodeName).clear();
				}
				recordData(stockData, stockCodeName);
			}
		}
	}

	public void recordData(List<NetData> stockData, String stockCodeName) {
		String path = Recorder.BASE_PATH + stockCodeName + File.separator;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(path + "20150511-1.csv");
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
			raf.skipBytes((int) length);
			for (NetData data : stockData) {
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
