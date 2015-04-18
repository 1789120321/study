package stock.dzh;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luonanqin on 4/7/15.
 */
public class DzhRealTimeSpider {

	private static Gson gson = new Gson();

	private static final String BASE_PATH = "/Users/Luonanqin/stock/data2/";

	private static String date;
	private static String charset = "gbk";
	private static String field = "时间,当前价,涨跌额,涨跌幅,成交总手,成交金额,换手率,开盘价,最高价,最低价,昨收,量比,市盈率,60日DDX,60日DDY,委比,委差,外盘,内盘,"
			+ "买一价,买一手,买二价,买二手,买三价,买三手,买四价,买四手,买五价,买五手,卖一价,卖一手,卖二价,卖二手,卖三价,卖三手,卖四价,卖四手,卖五价,卖五手";

	private static List<String> stockList = new ArrayList<String>();
	private static String uri_prefix = "http://cj.gw.com.cn/img/bd/stockData/SH/";
	private static String stock_uri_suffix = "JYMX.json";
	private static String other_uri_suffix = "5DPK.json";

	private static HttpClient httpClient;
	private static Map<String, Integer> proxys = new HashMap<String, Integer>();
	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();

	// private static GetMethod get = new GetMethod();
	static {
		// File stockName = new File(BASE_PATH + "stockname copy.txt");
		File stockName = new File(BASE_PATH + "stockname.txt");
		try {
			RandomAccessFile raf = new RandomAccessFile(stockName, "r");
			String str = null;
			while ((str = raf.readLine()) != null) {
				String[] split = str.split("=");
				byte[] bytes = split[1].getBytes("ISO-8859-1");
				String s = new String(bytes, "gbk");

				stockList.add(split[0] + "_" + s);
			}
			// stockList.clear();
			// stockList.add("600036");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// proxys.put("119.90.127.2", 80);
		// proxys.put("202.108.50.75", 82);
		// proxys.put("124.88.67.13", 843);
		// proxys.put("124.88.67.13", 82);
		// proxys.put("111.186.100.150", 18186);
		// proxys.put("117.135.250.69", 80);

		proxys.put("120.198.243.115", 8088);
		// proxys.put("120.198.243.115", 8085);


	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		date = sdf.format(new Date(System.currentTimeMillis()));
		int i = 1;

		while (i <= 10) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println(e);
				break;
			}
			// for (String proxyIP : proxys.keySet()) {
			System.out.println(i + ": " + new Date(System.currentTimeMillis()));

			httpClient = new HttpClient(manager);
//			httpClient.getHostConfiguration().setProxy("120.198.243.115", 8088);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);

			for (String stockCodeName : stockList) {
				// try {
				// Thread.sleep(100);
				// } catch (InterruptedException e) {
				// System.err.println(e);
				// break;
				// }
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
				String filePath = BASE_PATH + stockCodeName + File.separator + date + File.separator;
				recordData(stockData.toString(), filePath);
				System.out.println(stockCodeName);
			}
			httpClient.getHttpConnectionManager().closeIdleConnections(1);
			i++;
			// }
		}
	}

	private static StringBuffer getStockData(String uri) {
		GetMethod get = new GetMethod(uri);
		// try {
		// get.setURI(new URI(uri, true));
		// } catch (URIException e) {
		// e.printStackTrace();
		// }
		StringBuffer sb = new StringBuffer();
		try {
			int status = httpClient.executeMethod(get);

			if (status == HttpStatus.SC_OK) {
				byte[] response = get.getResponseBody();

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
		} catch (Exception e) {
			System.err.println(uri);
			// System.err.println("getStockData" + e.getMessage());
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return sb;
	}

	private static StringBuffer getOtherData(String uri) {
		GetMethod get = new GetMethod(uri);

		// try {
		// get.setURI(new URI(uri, true));
		// } catch (URIException e) {
		// e.printStackTrace();
		// }
		StringBuffer sb = new StringBuffer();
		try {
			int status = httpClient.executeMethod(get);

			if (status == HttpStatus.SC_OK) {
				byte[] response = get.getResponseBody();

				String result = new String(response, "utf-8");
				StringBuffer json = new StringBuffer(result);

				json.delete(0, 2);
				json.delete(json.length() - 2, json.length());

				DzhOtherData dzhOtherData = gson.fromJson(json.toString(), DzhOtherData.class);
				// System.out.println(json);
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
		} catch (Exception e) {
			System.err.println(uri);
			System.err.println("getOtherData" + e.getMessage());
		} finally {
			get.releaseConnection();
		}
		return sb;
	}

	private static void recordData(String info, String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(path + date + ".csv");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			long length = raf.length();
			if (length == 0) {
				raf.write(field.getBytes(charset));
				raf.writeByte((byte) 0XA);
			} else {
				raf.skipBytes((int) length);
			}
			raf.write(info.toString().getBytes(charset));
			raf.writeByte((byte) 0XA);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
