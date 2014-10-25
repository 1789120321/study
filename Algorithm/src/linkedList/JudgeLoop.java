package linkedList;

/**
 * Created by Luonanqin on 10/25/14.
 */
public class JudgeLoop {

	private static void generateLoop(LinkedListItem head, int intersection) {
		LinkedListItem current = head;
		while (current.next() != null) {
			current = current.next();
		}
		LinkedListItem last = current;
		for (int i = 1; i < intersection; i++) {
			head = head.next();
		}
		current.setNext(head);
	}

	public static void main(String[] args) {
		LinkedListItem list = LinkedListUtil.generate(9);
		LinkedListUtil.print("LinkedList: ", list);

		generateLoop(list, 5);

		LinkedListItem pPoint = list.next();
		LinkedListItem qPoint = list.next().next().next();

		while (pPoint != qPoint) {
			if (pPoint == null) {
				break;
			}
			if (qPoint == null) {
				break;
			}
			pPoint = pPoint.next();
			qPoint = qPoint.next();
			if (qPoint == null) {
				break;
			}
			qPoint = qPoint.next();
		}

		if (pPoint == null || qPoint == null) {
			System.out.println("There is not loop in this LinkedList!");
		} else {
			System.out.println("There is loop in this LinkedList!");
		}

		LinkedListItem cPoint = list.next();
		int count = 1;
		while (true) {
			if (cPoint == pPoint) {
				System.out.println("The point of intersection is " + cPoint.getData() + ", index is " + count);
				break;
			}
			cPoint = cPoint.next();
			count++;
		}
	}
}
