package tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Luonanqin on 10/27/14.
 */
// 求两个叶子节点间的路径
class Node2 extends NodeMeta {

	private Node2 leftNode;
	private Node2 rightNode;
	private Node2 parentNode;

	public Node2(int data) {
		super(data);
	}

	public Node2 getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node2 leftNode) {
		this.leftNode = leftNode;
	}

	public Node2 getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node2 rightNode) {
		this.rightNode = rightNode;
	}

	public Node2 getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node2 parentNode) {
		this.parentNode = parentNode;
	}
}

public class TwoLeafPath {

	// 临时存放要用的两个叶子节点
	public static Node2 leaf = null;
	// 表示已经找到叶子节点
	public static boolean find = false;

	public static Node2 generateTree() {
		Node2 root = new Node2(1);
		Node2 node2 = new Node2(2);
		Node2 node3 = new Node2(3);
		Node2 node4 = new Node2(4);
		Node2 node5 = new Node2(5);
		Node2 node6 = new Node2(6);
		Node2 node7 = new Node2(7);
		Node2 node8 = new Node2(8);
		Node2 node9 = new Node2(9);
		Node2 node10 = new Node2(10);
		Node2 node11 = new Node2(11);
		Node2 node12 = new Node2(12);

		root.setParentNode(null);
		root.setLeftNode(node2);
		root.setRightNode(node3);

		node2.setLeftNode(node4);
		node2.setRightNode(node5);

		node3.setLeftNode(node6);

		node4.setLeftNode(node7);
		node4.setRightNode(node8);

		node5.setRightNode(node9);

		node8.setLeftNode(node10);

		node9.setLeftNode(node11);

		node11.setRightNode(node12);

		return root;
	}

	// 以后序遍历的方式最快找到叶子节点
	public static void findNode(Node2 root, int data) {
		// 如果找到叶子节点则停止遍历
		if (!find) {
			if (root.getLeftNode() != null) {
				root.getLeftNode().setParentNode(root);
				findNode(root.getLeftNode(), data);
			}
		}

		// 如果找到叶子节点则停止遍历
		if (!find) {
			if (root.getRightNode() != null) {
				root.getRightNode().setParentNode(root);
				findNode(root.getRightNode(), data);
			}
		}

		if (root.getData() == data) {
			leaf = root;
			find = true;
			return;
		} else {
			return;
		}
	}

	public static void printLeavesPath(Node2 leaf1, Node2 leaf2) {
		int leaf1Data = leaf1.getData();
		int leaf2Data = leaf2.getData();
		Deque<Integer> leaf1Path = new LinkedList<Integer>();
		Deque<Integer> leaf2Path = new LinkedList<Integer>();

		while (true) {
			leaf1Path.offerFirst(leaf1.getData());
			leaf1 = leaf1.getParentNode();
			if (leaf1 == null) {
				break;
			}
		}

		System.out.println("Leaf "+leaf1Data+" Path: " + leaf1Path);

		while (true) {
			leaf2Path.offerFirst(leaf2.getData());
			leaf2 = leaf2.getParentNode();
			if (leaf2 == null) {
				break;
			}
		}

		System.out.println("Leaf "+leaf2Data+" Path: " + leaf2Path);

		int temp = 0;
		// 比较两个叶子节点的路径。从根节点开始往下查找，若发现节点不同，则说明两条路径从此节点分叉。即可输出两叶子节点间的路径
		while (leaf1Path.peekFirst() == leaf2Path.peekFirst()) {
			temp = leaf1Path.pollFirst();
			leaf2Path.pollFirst();
		}

		// 顺序输出
		Iterator<Integer> leaf1Iter = leaf1Path.iterator();
		// 逆序输出
		Iterator<Integer> leaf2Iter = leaf2Path.descendingIterator();

		StringBuffer result = new StringBuffer();
		while (leaf2Iter.hasNext()) {
			result.append(leaf2Iter.next() + ", ");
		}

		result.append(temp + ", ");

		while (leaf1Iter.hasNext()) {
			result.append(leaf1Iter.next() + ", ");
		}

		System.out.println("Leaf " + leaf1Data + " & Leaf " + leaf2Data + " Path: " + result.substring(0, result.length() - 2));
	}

	public static void main(String[] args) {
		Node2 root = generateTree();

		int data1 = 6;
		int data2 = 12;

		findNode(root, data1);
		find = false;
		Node2 leaf1 = leaf;

		findNode(root, data2);
		find = false;
		Node2 leaf2 = leaf;

		if (leaf1 == null) {
			System.out.println("Can't find leaf with " + data1);
		}
		if (leaf2 == null) {
			System.out.println("Can't find leaf with " + data2);
		}

		printLeavesPath(leaf1, leaf2);
	}
}
