package ms;

public class BinaryOne {

	public static int oneSum(int num) {
		int sum = 0;
		while (num > 0) {
			if ((num & 1) == 1) {
				sum++;
			}
			num = num >> 1;
		}

		return sum;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int num = 15;
		int result = BinaryOne.oneSum(num);
		System.out.println(result);
	}

}
