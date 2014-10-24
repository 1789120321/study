package ms;

import java.util.Deque;
import java.util.LinkedList;

public class CheckStackSequence {

	public static boolean checkSequence(String pushStr, String popStr) {
		boolean result = false;

		Deque<String> list1 = new LinkedList<String>();
		Deque<String> list2 = new LinkedList<String>();
		String word = null;

		// 入栈序列
		for (int n = 0; n < pushStr.length(); n++) {
			list1.addLast(String.valueOf(pushStr.charAt(n)));
		}

		// 出栈序列
		for (int index = 0; index < popStr.length(); index++) {
			char seq = popStr.charAt(index);
			if (!String.valueOf(seq).equals(list2.peekLast())) {
				while (!result && list1.size() != 0) {
					word = list1.pollFirst();
					list2.addLast(word);
					if (word.equals(String.valueOf(seq)))
						result = true;
				}

				if (!result)
					return result;

				list2.pollLast();
				result = false;
			} else {
				list2.pollLast();
			}
		}
		result = true;
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pushStr = "12345";
		String popStr = "34125";
		boolean result = CheckStackSequence.checkSequence(pushStr, popStr);
		System.out.println(result);
	}

}
