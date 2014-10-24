package ms;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayMinNum {

	public static String smallest(int[] array) {
		Integer aux[] = new Integer[array.length];
		for (int i = 0; i < array.length; i++)
			aux[i] = array[i];
		Arrays.sort(aux, new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				// TODO Auto-generated method stub
				return ("" + i1 + i2).compareTo("" + i2 + i1);
			}
		});

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < aux.length; i++) {
			sb.append(aux[i]);
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = { 32, 321, 322, 34, 657, 343, 23, 546, 89, 609, 34, 56, 7, 89, 5, 112, 324, 3534 };
		String result = ArrayMinNum.smallest(array);
		System.out.println(result);
	}

}
