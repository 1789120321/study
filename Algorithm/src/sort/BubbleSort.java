package sort;

public class BubbleSort {

	// 从小到大排序
	public static void sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] > array[j]) {
					ArrayUtil.swap(array, i, j);
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 3, 5, 1, 7, 2, 4, 10, 8, 9, 6, 0 };
		sort(array);
		ArrayUtil.showArray(array);
	}

}
