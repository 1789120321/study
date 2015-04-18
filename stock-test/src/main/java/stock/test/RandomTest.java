package stock.test;

import java.util.Random;

/**
 * Created by Luonanqin on 4/18/15.
 */
public class RandomTest {

	public static void main(String[] args) {
		Random rm = new Random();

		int strLength = 17;
		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);

		// 返回固定的长度的随机数
		System.out.println(fixLenthString.substring(1, strLength + 1));
	}
}
