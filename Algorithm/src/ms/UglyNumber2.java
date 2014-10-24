package ms;

import sort.ArrayUtil;

public class UglyNumber2 {

	private static void siftDown(int heap[], int from, int size) {
		int c = from * 2 + 1;
		while (c < size) {
			if (c + 1 < size && heap[c + 1] < heap[c])
				c++;
			if (heap[c] < heap[from])
				ArrayUtil.swap(heap, c, from);
			from = c;
			c = from * 2 + 1;
		}
	}

	private static void siftUp(int heap[], int from, int size) {
		while (from > 0) {
			int p = (from - 1) / 2;
			if (heap[p] > heap[from])
				ArrayUtil.swap(heap, p, from);
			from = p;
		}
	}

	public static void no1500() {
		int[] heap = new int[4500];
		heap[0] = 2;
		heap[1] = 3;
		heap[2] = 5;
		int size = 3;
		for (int i = 1; i < 1500; i++) {
			int s = heap[0];
			heap[0] = s * 2;
			siftDown(heap, 0, size);
			heap[size] = s * 3;
			siftUp(heap, size, size + 1);
			heap[size + 1] = s * 5;
			siftUp(heap, size + 1, size + 2);
			size += 2;
		}

		for (int i = 0; i < heap.length; i++) {
			System.out.println(heap[i]);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long begin = System.currentTimeMillis();
		UglyNumber2.no1500();
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

}
