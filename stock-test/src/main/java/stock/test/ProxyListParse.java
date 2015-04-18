package stock.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Luonanqin on 4/9/15.
 */
public class ProxyListParse {

	private static String proxyListFilePath = "/Users/Luonanqin/spider/proxylist.txt";

	public static void cleanProxyNoise() throws IOException {
		File file = new File(proxyListFilePath);

		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		String s1 = raf.readLine();
		String s2 = s1.replaceAll("\\\\", "");
		String s3 = s2.replaceAll("<div style=\"display\\:none\">[0-9]*</div>", "");
		String s4 = s3.replaceAll("<span style=\"display\\:none\">[0-9]*</span>", "");
//		Pattern p = Pattern.compile("^<div style=\"display\\:none\">.*</div>");
//		Matcher matcher = p.matcher(s1);
		System.out.println();

	}

	public static void main(String[] args) throws IOException {
		cleanProxyNoise();
	}
}
