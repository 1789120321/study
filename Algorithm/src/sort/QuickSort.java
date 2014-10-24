package sort;

public class QuickSort {

	public static void quickSort(int[] array, int low, int high) {
		if (low < high) {
			int pivot = partition1(array, low, high);

			quickSort(array, low, pivot - 1);
			quickSort(array, pivot + 1, high);
		}
	}

	private static int partition1(int[] array, int low, int high) {
		int pivotElem = array[low];
		int border = low;
		for (int i = low + 1; i <= high; i++) {
			if (array[i] < pivotElem) {
				ArrayUtil.swap(array, ++border, i);
			}
		}
		ArrayUtil.swap(array, border, low);
		return border;
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
