package sort;

public class HeapSort {

	public static void sort(int[] array) {
		// 初始化最大堆
		initheap(array);

		// 将最后一个叶节点和根节点交换，并继续整理堆。
		// 因为是最大堆，所以整理好以后，最大的元素会成为根节点。排序即从这里开始。
		for (int i = array.length; i >= 2; i--) {
			ArrayUtil.swap(array, 0, i - 1);

			adjustNode(array, 1, i - 1);
		}
	}

	private static void initheap(int[] array) {
		int lastBranchIndex = array.length / 2;

		for (int i = lastBranchIndex; i > 0; i--) {
			adjustNode(array, i, array.length);
		}
	}

	private static void adjustNode(int[] array, int parentNodeIndex, int length) {
		// 交换节点，从最末尾一个非叶节点起，匹配其子节点.
		// 将子节点中最大的和父节点交换，并再一次将父节点的子节点与该父节点做比较。循环往复
		int maxIndex = parentNodeIndex;

		// 先匹配左子节点，如果有再匹配右子节点
		if (parentNodeIndex * 2 <= length) {
			if (array[parentNodeIndex - 1] < array[parentNodeIndex * 2 - 1]) {
				maxIndex = parentNodeIndex * 2;
			}

			if (parentNodeIndex * 2 + 1 <= length) {
				if (array[maxIndex - 1] < array[parentNodeIndex * 2 + 1 - 1]) {
					maxIndex = parentNodeIndex * 2 + 1;
				}
			}
		}

		// 将找出来的比父节点大的子节点与父节点交换。
		if (maxIndex != parentNodeIndex) {
			ArrayUtil.swap(array, parentNodeIndex - 1, maxIndex - 1);

			if (maxIndex * 2 <= length) {
				adjustNode(array, maxIndex, length);
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = { 3, 5, 1, 7, 2, 4, 10, 8, 9, 6, 0 };
		sort(array);
		ArrayUtil.showArray(array);
	}

}
