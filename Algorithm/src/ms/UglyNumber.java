package ms;

import java.util.ArrayList;
import java.util.List;

public class UglyNumber {

	public final static int NUMBER = 1500;

	public static void num() {
		List<Integer> list = new ArrayList<Integer>(4500);
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		int sum = 5;
		int i = 5;
		while (true) {
			i++;
			if (sum == 100)
				break;

			if (i % 2 == 0) {
				if (!BinarySearch.search(list, i / 2))
					continue;
				else {
					sum++;
					list.add(i);
					continue;
				}
			}
			if (i % 3 == 0) {
				if (!BinarySearch.search(list, i / 3))
					continue;
				else {
					sum++;
					list.add(i);
					continue;
				}
			}
			if (i % 5 == 0) {
				if (!BinarySearch.search(list, i / 5))
					continue;
				else {
					sum++;
					list.add(i);
					continue;
				}
			}
			i++;
		}

		// int n = 1;
		// for (Integer num : list)
		// {
		// System.out.print(num + " ");
		// if (n % 20 == 0)
		// System.out.println();
		// n++;
		// }
		// System.out.println(i);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long begin = System.currentTimeMillis();
		UglyNumber.num();
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

}
