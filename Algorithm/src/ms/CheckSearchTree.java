package ms;

public class CheckSearchTree {

	public static boolean check(int[] array, int start, int end, int index) {
		if ((start + 1) >= end)
			return true;
		// 左子树从start开始
		int i = start;
		for (; i < end - 1; i++) {
			if (array[i] > array[index]) {
				break;
			}
		}
		// 右子树从大于根的值开始
		int j = i;
		for (; j < end; j++) {
			if (array[j] < array[index])
				return false;
		}
		// 递归左子树
		boolean left = true;
		if (i > 0)
			left = check(array, start, i, i - 1);
		// 递归右子树
		boolean right = true;
		if (i < end - 1)
			right = check(array, i, end - 1, index);

		return (left && right);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 5, 7, 6, 9, 11, 10, 8 };
		System.out.println(check(array, 0, array.length, array.length - 1));
	}

}
