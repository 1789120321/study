package ms;

public class FirstNotRepeatWord {

	public static char find(String str) {
		char word = '\u0000';
		int[] array = new int[27];
		for (int i = 0; i < str.length(); i++) {
			array[hash(str.charAt(i))]++;
		}
		for (int j = 0; j < str.length(); j++) {
			if (array[hash(str.charAt(j))] == 1) {
				word = str.charAt(j);
				break;
			}
		}
		return word;
	}

	private static int hash(char c) {
		return c - 96;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "abaccdeff";
		char result = find(str);
		System.out.println(result);
	}

}
