package ms;

import java.util.Deque;
import java.util.LinkedList;

public class WordFlip {

	public static String flip(String str) {
		StringBuffer sb = new StringBuffer();
		Deque<Character> firstList = new LinkedList<Character>();
		Deque<Character> secondList = new LinkedList<Character>();
		for (int i = 0; i < str.length(); i++) {
			char word = str.charAt(i);
			if (word != ' ') {
				firstList.addFirst(word);
			}
			if (word == ' ' || i == str.length() - 1) {
				for (int j = firstList.size() - 1; j >= 0; j--) {
					secondList.addFirst(firstList.pollFirst());
				}
				secondList.addFirst(" ".charAt(0));
			}
		}
		secondList.pollFirst();
		while (secondList.size() != 0) {
			sb.append(secondList.pollFirst());
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "I am a student!";
		System.out.println(WordFlip.flip(str));
	}

}
