package sort;

public class ShellSort {

	public static void sort(int array[]) {
		int step = initstep(array);

		for (; step >= 1; step = (step + 1) / 2 - 1) {
			for (int groupIndex = 0; groupIndex < step; groupIndex++)
				insertSort(array, groupIndex, step);
		}
	}

	private static void insertSort(int[] array, int startIndex, int step) {
		int endIndex = startIndex;

		while (endIndex + step < array.length) {
			endIndex += step;
		}

		for (int i = startIndex + step; i <= endIndex; i += step) {
			// 插入的具体实现
		}
	}

	private static int initstep(int[] array) {
		int step = 1;
		while ((step + 1) * 2 - 1 <= array.length) {
			step = (step + 1) * 2 - 1;
		}

		return step;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
