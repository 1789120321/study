package linkedList;

/**
 * Created by Luonanqin on 10/24/14.
 */
public class MergeLinkedList {

	public static void main(String[] args) {
		LinkedListItem list1 = LinkedListUtil.generateSorted(10);
		LinkedListItem list2 = LinkedListUtil.generateSorted(15);

		LinkedListUtil.print("Original List1: ", list1);
		LinkedListUtil.print("Original List2: ", list2);

		LinkedListItem head = LinkedListUtil.getHead();
		LinkedListItem list3 = head;

		LinkedListItem current1 = null;
		LinkedListItem current2 = null;
		LinkedListItem temp1 = list1.next();
		LinkedListItem temp2 = list2.next();
		while (true) {
			if (temp1 == null) {
				break;
			}
			if (temp2 == null) {
				break;
			}

			current1 = temp1;
			current2 = temp2;
			if (temp1.getData() <= temp2.getData()) {
				temp1 = current1.next();
				current1.setNext(null);
				list3.setNext(current1);
			} else {
				temp2 = current2.next();
				current2.setNext(null);
				list3.setNext(current2);
			}

			list3 = list3.next();
		}

		if (temp1 != null) {
			list3.setNext(temp1);
		}
		if (temp2 != null) {
			list3.setNext(temp2);
		}

		LinkedListUtil.print("Merge List1 & List2: ", head);
	}
}
