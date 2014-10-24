package sort;

public class InsertionSort {

	public static void sort(int array[]) {
		int aLength = array.length, temp = 0, j = 0;

		for (int i = 1; i < aLength; i++) {
			// 要排序的数值
			temp = array[i];

			// 从要排序的数值开始向左比较，如果大于该值就向后移动
			j = i - 1;
			while (j >= 0 && array[j] > temp) {
				array[j + 1] = array[j];
				j--;
			}
			// 插入
			array[++j] = temp;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int a[] = { 2, 5, 4, 3, 1, 7, 6, 8, 19, 10 };
		sort(a);
		ArrayUtil.showArray(a);
	}
}
