package ms;

import java.util.Arrays;
import java.util.List;

public class BinarySearch {

	public static boolean search(List<Integer> array, int num) {
		int low = 0, high = array.size() - 1;
		int mid = 0;

		while (low <= high) {
			mid = (low + high) / 2;
			if (num == array.get(mid)) {
				return true;
			}
			if (num < array.get(mid)) {
				high--;
			}
			if (num > array.get(mid)) {
				low++;
			}
		}

		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(BinarySearch.search(Arrays.asList(0, 1, 2, 3, 5, 6, 8, 9), 4));
	}

}
