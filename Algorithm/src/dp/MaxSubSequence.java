package dp;

public class MaxSubSequence {

	public static int maxSum(int[] array) {
		int sum = 0;
		int maxSum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
			if (sum < 0)
				sum = 0;
			if (maxSum < sum) {
				maxSum = sum;
			}
		}

		return maxSum;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 1, -2, 3, 10, -4, 7, 2, -5 };
		System.out.println(maxSum(array));
	}

}
