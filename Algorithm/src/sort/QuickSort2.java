package sort;

public class QuickSort2 {

	public static void quickSort(int[] array, int low, int high) {
		if (low < high) {
			int k = sort(array, low, high);
			quickSort(array, low, k - 1);
			quickSort(array, k + 1, high);
		}
	}

	private static int mid(int[] array, int low, int high) {
		int center = (low + high) / 2;
		if (array[center] < array[low])
			ArrayUtil.swap(array, center, low);
		if (array[high] < array[low])
			ArrayUtil.swap(array, high, low);
		if (array[high] < array[center])
			ArrayUtil.swap(array, high, center);
		ArrayUtil.swap(array, center, high - 1);
		return high - 1;
	}

	public static int sort(int[] array, int low, int high) {
		int pivot = mid(array, low, high);

		int i = low, j = high - 1;
		for (;;) {
			while (array[pivot] > array[++i]) {
			}
			while (array[pivot] < array[--j]) {
			}

			if (i < j)
				ArrayUtil.swap(array, i, j);
			else
				break;
		}
		ArrayUtil.swap(array, i, pivot);
		return i;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 3, 5, 1, 7, 2, 4, 10, 8, 9, 6, 0 };
		quickSort(array, 0, array.length - 1);
		ArrayUtil.showArray(array);
	}

}
