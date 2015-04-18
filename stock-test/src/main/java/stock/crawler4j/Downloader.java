package stock.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.Parser;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.httpclient.HttpStatus;

/**
 * Created by Luonanqin on 4/17/15.
 */
public class Downloader {
	// private static final Logger logger = LoggerFactory.getLogger(Downloader.class);

	private final Parser parser;
	private final PageFetcher pageFetcher;

	public Downloader() {
		CrawlConfig config = new CrawlConfig();
		parser = new Parser(config);
		pageFetcher = new PageFetcher(config);
	}

	public static void main(String[] args) {
		Downloader downloader = new Downloader();
		// downloader.processUrl("http://en.wikipedia.org/wiki/Main_Page/");
		// downloader.processUrl("http://www.yahoo.com/");
		downloader.processUrl("http://cj.gw.com.cn/img/bd/stockData/SH/28/SH601328/SH6013285DPK.json");
	}

	public void processUrl(String url) {
		// logger.debug("Processing: {}", url);
		Page page = download(url);
		if (page != null) {
			ParseData parseData = page.getParseData();
			if (parseData != null) {
				if (parseData instanceof HtmlParseData) {
					HtmlParseData htmlParseData = (HtmlParseData) parseData;
					// logger.debug("Title: {}", htmlParseData.getTitle());
					// logger.debug("Text length: {}", htmlParseData.getText().length());
					// logger.debug("Html length: {}", htmlParseData.getHtml().length());
					System.out.println(htmlParseData.getText());
					// System.out.println("Title: {}"+ htmlParseData.getTitle());
					// System.out.println("Text length: {}"+ htmlParseData.getText().length());
					// System.out.println("Html length: {}"+ htmlParseData.getHtml().length());
				}
			} else {
				System.err.println("Couldn't parse the content of the page.");
			}
		} else {
			System.err.println("Couldn't fetch the content of the page.");
		}
		System.err.println("==============");
	}

	private Page download(String url) {
		WebURL curURL = new WebURL();
		curURL.setURL(url);
		PageFetchResult fetchResult = null;
		try {
			fetchResult = pageFetcher.fetchPage(curURL);
			if (fetchResult.getStatusCode() == HttpStatus.SC_OK) {
				Page page = new Page(curURL);
				fetchResult.fetchContent(page);
				parser.parse(page, curURL.getURL());
				return page;
			}
		} catch (Exception e) {
			System.err.println("Error occurred while fetching url: " + curURL.getURL());
			e.printStackTrace();
		} finally {
			if (fetchResult != null) {
				fetchResult.discardContentIfNotConsumed();
			}
		}
		return null;
	}
}
