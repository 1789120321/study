package linkedList;

import java.util.Random;

/**
 * Created by Luonanqin on 10/24/14.
 */
public class LinkedListUtil {

	public static LinkedListItem generate(int size) {
		LinkedListItem[] items = generateArray(size);

		return generateList(size, items);
	}

	public static LinkedListItem generateSorted(int size) {
		LinkedListItem[] items = generateArray(size);

		for (int i = 0; i < size; i++) {
			int tmp = i;
			for (int j = i + 1; j < size; j++) {
				if (items[j].getData() < items[tmp].getData()) {
					tmp = j;
				}
			}
			LinkedListItem temp = items[i];
			items[i] = items[tmp];
			items[tmp] = temp;
		}

		return generateList(size, items);
	}

	private static LinkedListItem generateList(int size, LinkedListItem[] items) {
		LinkedListItem head = getHead();
		LinkedListItem current = head;
		for (int i = 0; i < size; i++) {
			current.setNext(items[i]);
			current = current.next();
		}
		current.setNext(null);

		return head;
	}

	public static LinkedListItem getHead() {
		return new LinkedListItem(-1);
	}

	private static LinkedListItem[] generateArray(int size) {
		long currentTime = System.currentTimeMillis();
		int feed = (int) (currentTime % 1000);

		Random random = new Random(feed);
		LinkedListItem[] items = new LinkedListItem[size];
		for (int i = 0; i < size; i++) {
			LinkedListItem item = new LinkedListItem(random.nextInt(feed));
			items[i] = item;
		}
		return items;
	}

	public static void print(String description, LinkedListItem first) {
		System.out.println(description);
		while (true) {
			if (first.getData() == -1) {
				first = first.next();
				continue;
			}
			System.out.print(first.getData());
			if (first.next() == null) {
				break;
			}
			System.out.print(" -> ");
			first = first.next();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		LinkedListItem item = generate(10);
		print("generate: ", item);
		LinkedListItem item1 = generateSorted(10);
		print("generateSorted: ", item1);
	}
}
