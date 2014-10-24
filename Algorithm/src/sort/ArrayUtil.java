package sort;

public final class ArrayUtil {

	public static void showArray(int[] array) {
		for (int a : array) {
			System.out.print(a + " ");
		}
	}

	public static void showArray(String[] array) {
		for (String a : array) {
			System.out.print(a + " ");
		}
	}

	public static void swap(int[] array, int aIndex, int bIndex) {
		int temp = array[aIndex];
		array[aIndex] = array[bIndex];
		array[bIndex] = temp;
	}

	public static void swap(String[] array, int aIndex, int bIndex) {
		String temp = array[aIndex];
		array[aIndex] = array[bIndex];
		array[bIndex] = temp;
	}
}
