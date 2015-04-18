package stock.crawler4j;

import com.google.gson.Gson;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
import stock.dzh.DzhOtherData;
import stock.dzh.DzhStockData;
import stock.dzh.Recorder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Luonanqin on 4/18/15.
 */
public class Crawler4jTest {

	private static Gson gson = new Gson();
	private static String date;
	private static String uri_prefix = "http://cj.gw.com.cn/img/bd/stockData/SH/";
	private static String stock_uri_suffix = "JYMX.json";
	private static String other_uri_suffix = "5DPK.json";

	public static PoolingHttpClientConnectionManager connectionManager;
	public static CloseableHttpClient httpClient;

	static {
		CrawlConfig config = new CrawlConfig();
		RequestConfig requestConfig = RequestConfig.custom().setExpectContinueEnabled(false).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.setRedirectsEnabled(false).setSocketTimeout(config.getSocketTimeout()).setConnectTimeout(config.getConnectionTimeout()).build();

		RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
		connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);

		Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
		connectionManager = new PoolingHttpClientConnectionManager(connRegistry);
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setDefaultRequestConfig(requestConfig);
		clientBuilder.setConnectionManager(connectionManager);
		// clientBuilder.setUserAgent(config.getUserAgentString());

		httpClient = clientBuilder.build();
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		date = sdf.format(new Date(System.currentTimeMillis()));
		int i = 1;

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println(e);
				break;
			}
			System.out.println(i + ": " + new Date(System.currentTimeMillis()));

			for (String stockCodeName : Recorder.stockList) {
				String tmp = stockCodeName.substring(4, 6);
				int codeSuffix = Integer.valueOf(tmp);
				String stockCode = stockCodeName.substring(0, 6);
				StringBuffer stockData = getStockData(uri_prefix + codeSuffix + "/SH" + stockCode + "/SH" + stockCode + stock_uri_suffix);
				if (stockData == null || stockData.length() == 0) {
					continue;
				}
				StringBuffer otherData = getOtherData(uri_prefix + codeSuffix + "/SH" + stockCode + "/SH" + stockCode + other_uri_suffix);
				if (otherData == null || otherData.length() == 0) {
					continue;
				}
				stockData.append(otherData);
				Recorder.recordData(date, stockData.toString(), stockCodeName);
				System.out.println(stockCodeName);
			}
			i++;
		}
	}

	private static StringBuffer getOtherData(String url) {
		HttpGet get = null;
		StringBuffer sb = new StringBuffer();
		try {
			get = new HttpGet(url);

			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				byte[] response = EntityUtils.toByteArray(entity);

				String result = new String(response, "utf-8");
				StringBuffer json = new StringBuffer(result);

				json.delete(0, 2);
				json.delete(json.length() - 2, json.length());

				DzhOtherData dzhOtherData = gson.fromJson(json.toString(), DzhOtherData.class);
				sb.append(dzhOtherData.getWb() + ",");
				sb.append(dzhOtherData.getWc() + ",");
				sb.append(dzhOtherData.getWp() + ",");
				sb.append(dzhOtherData.getNp() + ",");
				sb.append(dzhOtherData.getB1p() + ",");
				sb.append(dzhOtherData.getB1v() + ",");
				sb.append(dzhOtherData.getB2p() + ",");
				sb.append(dzhOtherData.getB2v() + ",");
				sb.append(dzhOtherData.getB3p() + ",");
				sb.append(dzhOtherData.getB3v() + ",");
				sb.append(dzhOtherData.getB4p() + ",");
				sb.append(dzhOtherData.getB4v() + ",");
				sb.append(dzhOtherData.getB5p() + ",");
				sb.append(dzhOtherData.getB5v() + ",");
				sb.append(dzhOtherData.getS1p() + ",");
				sb.append(dzhOtherData.getS1v() + ",");
				sb.append(dzhOtherData.getS2p() + ",");
				sb.append(dzhOtherData.getS2v() + ",");
				sb.append(dzhOtherData.getS3p() + ",");
				sb.append(dzhOtherData.getS3v() + ",");
				sb.append(dzhOtherData.getS4p() + ",");
				sb.append(dzhOtherData.getS4v() + ",");
				sb.append(dzhOtherData.getS5p() + ",");
				sb.append(dzhOtherData.getS5v() + ",");
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
		return sb;
	}

	private static StringBuffer getStockData(String url) {
		HttpGet get = null;
		StringBuffer sb = new StringBuffer();
		try {
			get = new HttpGet(url);

			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				byte[] response = EntityUtils.toByteArray(entity);

				String result = new String(response, "utf-8");
				StringBuffer json = new StringBuffer(result);

				json.delete(0, 2);
				json.delete(json.length() - 2, json.length());

				String[] split = json.toString().split("},");
				if (split[0].endsWith("}")) {
					return sb;
				}
				DzhStockData dzhStockData = gson.fromJson(split[0] + "}", DzhStockData.class);
				sb.append(dzhStockData.getTime() + ",");
				sb.append(dzhStockData.getLp() + ",");
				sb.append(dzhStockData.getZd() + ",");
				sb.append(dzhStockData.getZf() + ",");
				sb.append(dzhStockData.getVol() + ",");
				sb.append(dzhStockData.getVolp() + ",");
				sb.append(dzhStockData.getHs() + ",");
				sb.append(dzhStockData.getOp() + ",");
				sb.append(dzhStockData.getPriceMax() + ",");
				sb.append(dzhStockData.getPriceMin() + ",");
				sb.append(dzhStockData.getCp() + ",");
				sb.append(dzhStockData.getLb() + ",");
				sb.append(dzhStockData.getSyl() + ",");
				// sb.append(dzhStockData.getLtsz() + ",");
				// sb.append(dzhStockData.getZsz() + ",");
				sb.append(dzhStockData.getDdx60() + ",");
				sb.append(dzhStockData.getDdy60() + ",");
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
		return sb;
	}
}
