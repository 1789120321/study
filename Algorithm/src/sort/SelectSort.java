package sort;

public class SelectSort {

	public static void sort(int[] array) {
		int maxIndex = 0;
		int temp;

		for (int i = array.length - 1; i >= 0; i--) {
			// 将要比较的元素索引
			maxIndex = i;

			// 如果找到比被比较的元素大的元素，记录其索引
			for (int j = 0; j < i; j++) {
				if (array[j] > array[maxIndex])
					maxIndex = j;
			}

			// 交换查找出来的最大的元素
			temp = array[maxIndex];
			array[maxIndex] = array[i];
			array[i] = temp;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 2, 3, 6, 4, 7, 1, 8, 10, 9, 0 };
		sort(array);
		ArrayUtil.showArray(array);
	}

}
