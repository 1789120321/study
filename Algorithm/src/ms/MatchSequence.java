package ms;

public class MatchSequence {

	public static String match(String str, String sequence) {
		String result = null;
		int beginIndex = 0, endIndex = 0;
		int seqIndex = 0;
		int minLength = str.length();

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == sequence.charAt(0)) {
				beginIndex = i;
				seqIndex = 0;
				seqIndex++;
				continue;
			}
			if (str.charAt(i) == sequence.charAt(seqIndex)) {
				if (seqIndex == sequence.length() - 1) {
					if (i - beginIndex < minLength) {
						endIndex = i;
						minLength = endIndex - beginIndex + 1;
					}
					continue;
				}
				seqIndex++;
			}
		}

		result = str.substring(endIndex - minLength + 1, endIndex + 1);

		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "abdcefabcbcagfbdcx";
		String result = MatchSequence.match(str, "abc");
		System.out.println(result);
	}

}
