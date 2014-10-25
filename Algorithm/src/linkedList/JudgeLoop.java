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

		// 生成有环链表，交点在第五个节点处
		generateLoop(list, 5);

		// pPoint指向第一个节点， qPoint指向第三个节点
		LinkedListItem pPoint = list.next();
		LinkedListItem qPoint = list.next().next().next();

		// pPoint每次向前走一步，qPoint每次向前走两步，直到他们所指的对象是同一个即可跳出循环
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
		// cPoint从头结点向后，直到与pPoint相等，即可求出交点是第几个节点
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
