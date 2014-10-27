package tree;

import java.util.Random;

/**
 * Created by Luonanqin on 10/27/14.
 */
public class TreeUtil {

	private static Random random = new Random(System.currentTimeMillis());

	public static NodeMeta generateNode() {
		int data = random.nextInt(100);
		NodeMeta node = new NodeMeta(data);

		return node;
	}

	public static int generateData() {
		return random.nextInt(100);
	}
}
