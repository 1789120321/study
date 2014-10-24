/**
 * 
 */
package ms;

/**
 * @author pangyang
 * 
 */
public class NumberSum {

	public static String sum(int[] array, int sum) {
		int numSum = 0;
		int left = 0, right = array.length - 1;
		while (numSum != sum) {
			numSum = array[left] + array[right];
			if (numSum > sum) {
				right--;
			}
			if (numSum < sum) {
				left++;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append(left).append("+").append(right);
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 1, 2, 3, 4, 6, 7, 9, 10, 11, 12, 15, 17 };
		String result = NumberSum.sum(array, 15);
		System.out.println(result);
	}

}
