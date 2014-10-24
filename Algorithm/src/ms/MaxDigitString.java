package ms;

public class MaxDigitString {

	public static String maxString(String str) {
		String result = null;
		int maxIndex = 0;
		int maxLength = 0;
		int digitLength = 0;

		for (int index = 0; index < str.length(); index++) {
			if (str.charAt(index) < 48 || str.charAt(index) > 57) {
				if (digitLength > maxLength) {
					maxLength = digitLength;
					maxIndex = index - digitLength;
				}
				digitLength = 0;
				continue;
			}
			digitLength++;
		}

		if (digitLength > maxLength) {
			maxLength = digitLength;
			maxIndex = str.length() - digitLength;
		}

		result = str.substring(maxIndex, maxLength + maxIndex);

		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "abcd12345ed125ss123456789";
		String result = MaxDigitString.maxString(str);
		System.out.println(result);
	}
}
