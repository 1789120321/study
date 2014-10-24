package ms;

public class RotateString {

	public static String merge(String str, int rotateIndex) {
		String result = null;

		String str1 = str.substring(0, rotateIndex);
		String str2 = str.substring(rotateIndex, str.length());

		StringBuffer sb1 = rotate(str1);
		StringBuffer sb2 = rotate(str2);
		StringBuffer sb3 = sb1.append(sb2);

		result = rotate(sb3.toString()).toString();

		return result;
	}

	private static StringBuffer rotate(String str) {
		StringBuffer s = new StringBuffer(str);
		for (int i = 0; i < str.length() / 2; i++) {
			char temp = str.charAt(str.length() - i - 1);
			s.setCharAt(str.length() - i - 1, s.charAt(i));
			s.setCharAt(i, temp);
		}

		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "abcdef";
		String result = RotateString.merge(str, 2);
		System.out.println(result);
	}

}
