package stock.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created by Luonanqin on 4/5/15.
 */
public class SinaHistory {

	public static void main(String[] args) {
		//		getDataGBK("http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/600000.phtml");
		//		getDataUTF8("http://stockpage.10jqka.com.cn/600004/");

		getDataUTF8("http://stockpage.10jqka.com.cn/spService/600004/Header/realHeader");
	}

	private static void getDataGET_UTF8(String uri) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);

		GetMethod get = new GetMethod(uri);

		try {
			int status = httpClient.executeMethod(get);

			if (status == HttpStatus.SC_OK) {
				byte[] response = get.getResponseBody();
				String result = new String(response, "utf-8");

				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void getDataUTF8(String uri) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);

		PostMethod post = new PostMethod(uri);

		try {
			int status = httpClient.executeMethod(post);

			if (status == HttpStatus.SC_OK) {
				byte[] response = post.getResponseBody();
				String result = new String(response, "utf-8");

				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getDataGBK(String uri) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);

		PostMethod post = new PostMethod(uri);

		try {
			int status = httpClient.executeMethod(post);

			if (status == HttpStatus.SC_OK) {
				byte[] response = post.getResponseBody();
				String result = new String(response, "gbk");

				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
