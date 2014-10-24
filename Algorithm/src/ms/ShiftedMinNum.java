package ms;

public class ShiftedMinNum {

	public static int search(int[] array, int low, int high, int preHigh) {
		int mid = low + (high - low) / 2;
		if (array[low] > array[high]) {
			if (low + 1 == high)
				return array[high];

			return search(array, low, mid, preHigh);
		} else {
			if (array[low] < array[high] && array[low] < array[array.length - 1])
				return array[low];

			return search(array, high, preHigh, preHigh - 1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 1, 2, 3, 4, 5, 6, 7 };
		int result = ShiftedMinNum.search(array, 0, array.length - 1, array.length - 1);
		System.out.println(result);
	}

}
