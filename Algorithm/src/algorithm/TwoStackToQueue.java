package algorithm;

import java.util.Stack;

/**
 * Created by Luonanqin on 11/1/14.
 */
public class TwoStackToQueue {

	private static Stack<Integer> stack = new Stack<Integer>();
	private static Stack<Integer> queue = new Stack<Integer>();

	public static void main(String[] args) {
		offer(1);
		offer(4);
		offer(2);
		offer(9);
		offer(0);
		offer(7);
		offer(8);
		offer(10);
		offer(3);

		print();
	}

	private static void offer(int i) {
		while (true) {
			if (queue.empty()) {
				break;
			}
			Integer pop = queue.pop();
			stack.push(pop);
		}

		queue.push(i);

		while (true) {
			if (stack.empty()) {
				break;
			}
			Integer pop = stack.pop();
			queue.push(pop);
		}
	}

	private static void print() {
		while (!queue.empty()) {
			System.out.print(queue.pop() + " ");
		}
		System.out.println();
	}
}
