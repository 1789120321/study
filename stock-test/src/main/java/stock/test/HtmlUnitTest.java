package stock.test;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * Created by Luonanqin on 4/7/15.
 */
public class HtmlUnitTest {

	public static void main(String[] args) throws IOException {
		WebClient client = new WebClient();
//		client.getOptions().setCssEnabled(false);
//		client.getOptions().setJavaScriptEnabled(false);
		HtmlPage page = client.getPage("http://stockpage.10jqka.com.cn/600004/");
//		String stri = page.asText();
//		System.out.println(stri);

		ScriptResult scriptResult = page.executeJavaScript("http://s.thsi.cn/combo?js/excanvas.min.js&js/jquery-1.7.2.min.js&js/swfobject.js&js/seajs/&sea.1.2.js");
		Page newPage = scriptResult.getNewPage();


		client.closeAllWindows();
	}
}
