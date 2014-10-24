package recursive;

import sort.ArrayUtil;

public class FullArray {

	public static void arrange(String[] str, int start, int num) {
		if (num <= 1)
			return;

		for (int i = start; i < start + num; ++i) {
			ArrayUtil.swap(str, i, start);
			arrange(str, start + 1, num - 1);
			ArrayUtil.swap(str, i, start);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strs = { "a", "b", "c" };
		arrange(strs, 0, 3);
	}

}
