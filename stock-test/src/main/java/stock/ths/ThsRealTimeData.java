package stock.ths;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Luonanqin on 4/7/15.
 */
public class ThsRealTimeData {
	private static Gson gson = new Gson();

	private static final String BASE_PATH = "/Users/Luonanqin/stock/data/";

	private static String date;
	private static String charset = "gbk";
	private static String field = "现价,涨跌幅,涨跌额,成交量,成交额,开盘价,昨收,最高,最低,换手率,市盈率(动),内盘,外盘,振幅,涨停,跌停,行业涨幅,委比,委差,"
			+ "买一价,买一手,买二价,买二手,买三价,买三手,买四价,买四手,买五价,买五手,卖一价,卖一手,卖二价,卖二手,卖三价,卖三手,卖四价,卖四手,卖五价,卖五手";

	private static List<String> stockList = new ArrayList<String>();
	private static String uri_prefix = "http://stockpage.10jqka.com.cn/spService/";
	private static String uri_suffix = "/Header/realHeader";

	private static HttpClient httpClient = new HttpClient();

	static {
		// File stockName = new File(BASE_PATH + "stockname copy.txt");
		File stockName = new File(BASE_PATH + "stockname.txt");
		try {
			RandomAccessFile raf = new RandomAccessFile(stockName, "r");
			String str = null;
			while ((str = raf.readLine()) != null) {
				String[] split = str.split("=");
				stockList.add(split[0]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		date = sdf.format(new Date(System.currentTimeMillis()));
		// getData(uri_prefix + "600058" + uri_suffix);
		for (String stockCode : stockList) {
			getData(uri_prefix + stockCode + uri_suffix);
			System.out.println(stockCode);
		}
	}

	private static void getData(String uri) {
		PostMethod postMethod = new PostMethod(uri);

		try {
			int status = httpClient.executeMethod(postMethod);

			if (status == HttpStatus.SC_OK) {
				byte[] response = postMethod.getResponseBody();
				String result = new String(response, "utf-8");

				RealTimeDataPojo realTimeDataPojo = gson.fromJson(result, RealTimeDataPojo.class);
				if (realTimeDataPojo == null) {
					System.out.println("Error: " + uri);
					return;
				}
//				System.out.println(result);
				String filePath = buildFilePath(realTimeDataPojo);
				recordData(realTimeDataPojo, filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String buildFilePath(RealTimeDataPojo dataPojo) {
		String stockCode = dataPojo.getStockcode();
		String stockName = dataPojo.getStockname();

		return BASE_PATH + stockCode + "_" + stockName + File.separator + date + File.separator;
	}

	private static void recordData(RealTimeDataPojo dataPojo, String path) {
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

		BufferedOutputStream bw = null;
		StringBuffer sb = new StringBuffer();
		try {
			bw = new BufferedOutputStream(new FileOutputStream(file));
			bw.write(field.getBytes(charset));
			bw.write((byte) 0XA);
			sb.append(dataPojo.getXj() + ",");
			sb.append(dataPojo.getZdf() + ",");
			sb.append(dataPojo.getZde() + ",");
			sb.append(dataPojo.getCjl() + ",");
			sb.append(dataPojo.getCje() + ",");
			sb.append(dataPojo.getKp() + ",");
			sb.append(dataPojo.getZs() + ",");
			sb.append(dataPojo.getZg() + ",");
			sb.append(dataPojo.getZd() + ",");
			sb.append(dataPojo.getHs() + ",");
			sb.append(dataPojo.getSyl() + ",");
			sb.append(dataPojo.getNp() + ",");
			sb.append(dataPojo.getWp() + ",");
			sb.append(dataPojo.getZf() + ",");
			sb.append(dataPojo.getZt() + ",");
			sb.append(dataPojo.getDt() + ",");
			sb.append(dataPojo.getField() + ",");
			sb.append(dataPojo.getWb() + ",");
			sb.append(dataPojo.getWc() + ",");
			sb.append(dataPojo.getBuy1() + ",");
			sb.append(dataPojo.getBuy1data() + ",");
			sb.append(dataPojo.getBuy2() + ",");
			sb.append(dataPojo.getBuy2data() + ",");
			sb.append(dataPojo.getBuy3() + ",");
			sb.append(dataPojo.getBuy3data() + ",");
			sb.append(dataPojo.getBuy4() + ",");
			sb.append(dataPojo.getBuy4data() + ",");
			sb.append(dataPojo.getBuy5() + ",");
			sb.append(dataPojo.getBuy5data() + ",");
			sb.append(dataPojo.getSell1() + ",");
			sb.append(dataPojo.getSell1data() + ",");
			sb.append(dataPojo.getSell2() + ",");
			sb.append(dataPojo.getSell2data() + ",");
			sb.append(dataPojo.getSell3() + ",");
			sb.append(dataPojo.getSell3data() + ",");
			sb.append(dataPojo.getSell4() + ",");
			sb.append(dataPojo.getSell4data() + ",");
			sb.append(dataPojo.getSell5() + ",");
			sb.append(dataPojo.getSell5data() + ",");
			bw.write(sb.toString().getBytes("gbk"));
			bw.write((byte) 0XA);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
