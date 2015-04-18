package stock.jspider;

import com.google.gson.Gson;
import stock.dzh.DzhOtherData;
import stock.dzh.DzhStockData;
import stock.dzh.Recorder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Luonanqin on 4/17/15.
 */
public class JspiderTest {
	private static Gson gson = new Gson();

	private static final String BASE_PATH = "/Users/Luonanqin/stock/data2/";

	private static String date;

	private static List<String> stockList = new ArrayList<String>();
	private static String uri_prefix = "http://cj.gw.com.cn/img/bd/stockData/SH/";
	private static String stock_uri_suffix = "JYMX.json";
	private static String other_uri_suffix = "5DPK.json";

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
			// stockList.add("600000");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
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
			System.out.println(i + ": " + new Date(System.currentTimeMillis()));

			for (String stockCodeName : stockList) {
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
				Recorder.recordData(date, stockData.toString(), filePath);
				System.out.println(stockCodeName);
			}
			i++;
		}
	}

	private static StringBuffer getStockData(String link) throws IOException {
		URL url = new URL(link);
		URLConnection connection = null;

		InputStream inputStream = null;
		StringBuffer sb = new StringBuffer();
		try {
			connection = url.openConnection();

			// RFC states that redirects should be followed.
			// see: http://www.robotstxt.org/wc/norobots-rfc.txt
			((HttpURLConnection) connection).setInstanceFollowRedirects(true);
			connection.setRequestProperty("User-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:37.0) Gecko/20100101 Firefox/37.0");

			connection.connect();

			// if (connection instanceof HttpURLConnection) {
			// httpStatus = ((HttpURLConnection) connection).getResponseCode();
			// switch (httpStatus) {
			// case HttpURLConnection.HTTP_MOVED_PERM:
			// case HttpURLConnection.HTTP_MOVED_TEMP:
			// return;
			// default:
			// break;
			// }
			// }
			inputStream = new BufferedInputStream(connection.getInputStream());

			ByteArrayOutputStream os = null;
			InputStream is = null;
			try {
				os = new ByteArrayOutputStream();
				is = new BufferedInputStream(inputStream);
				int i = is.read();
				while (i != -1) {
					os.write(i);
					i = is.read();
				}
				byte[] response = os.toByteArray();
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
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (connection != null) {
				((HttpURLConnection) connection).disconnect();
			}
		}
		return sb;
	}

	private static StringBuffer getOtherData(String link) throws IOException {
		URL url = new URL(link);
		URLConnection connection = null;

		InputStream inputStream = null;
		StringBuffer sb = new StringBuffer();
		try {
			connection = url.openConnection();

			// RFC states that redirects should be followed.
			// see: http://www.robotstxt.org/wc/norobots-rfc.txt
			((HttpURLConnection) connection).setInstanceFollowRedirects(true);
			connection.setRequestProperty("User-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:37.0) Gecko/20100101 Firefox/37.0");

			connection.connect();

			// if (connection instanceof HttpURLConnection) {
			// httpStatus = ((HttpURLConnection) connection).getResponseCode();
			// switch (httpStatus) {
			// case HttpURLConnection.HTTP_MOVED_PERM:
			// case HttpURLConnection.HTTP_MOVED_TEMP:
			// return;
			// default:
			// break;
			// }
			// }
			inputStream = new BufferedInputStream(connection.getInputStream());

			ByteArrayOutputStream os = null;
			InputStream is = null;
			try {
				os = new ByteArrayOutputStream();
				is = new BufferedInputStream(inputStream);
				int i = is.read();
				while (i != -1) {
					os.write(i);
					i = is.read();
				}
				byte[] response = os.toByteArray();
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
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (connection != null) {
				((HttpURLConnection) connection).disconnect();
			}
		}
		return sb;
	}
}
