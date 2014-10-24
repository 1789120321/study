package ms;

public class ContinuesNSeries {

	public static void series(int n) {
		int[] array = new int[n + 1];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		int small = 1, big = small + 1;
		int sum;
		while (big <= ((n + 1) / 2)) {
			sum = 0;
			for (int m = small; m <= big; m++) {
				sum += array[m];
			}
			if (sum < n) {
				big++;
			} else if (sum > n) {
				small++;
			} else if (sum == n) {
				for (int m = small; m <= big; m++) {
					System.out.print(m + " ");
				}
				System.out.println();
				big++;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContinuesNSeries.series(15);
	}

}
