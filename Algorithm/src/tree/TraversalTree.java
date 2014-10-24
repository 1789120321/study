package tree;

import java.util.Deque;
import java.util.LinkedList;

public class TraversalTree {

	// 先序遍历
	public static void preorder(Node root) {
		if (root != null) {
			System.out.print(root.getData() + " ");
			preorder(root.getLeftNode());
			preorder(root.getRightNode());
		} else {
			return;
		}
	}

	// 中序遍历
	public static void midorder(Node root) {
		if (root != null) {
			midorder(root.getLeftNode());
			System.out.print(root.getData() + " ");
			midorder(root.getRightNode());
		} else {
			return;
		}
	}

	// 后序遍历
	public static void postorder(Node root) {
		if (root != null) {
			postorder(root.getLeftNode());
			postorder(root.getRightNode());
			System.out.print(root.getData() + " ");
		} else {
			return;
		}
	}

	// 按层遍历
	public static Deque<Node> nodeList = new LinkedList<Node>();

	// 初始化根节点
	public static void initHead(Node root) {
		nodeList.add(root);
	}

	// 递归遍历
	public static void layer(Node root) {
		if (nodeList.size() != 0) {
			if (root.getLeftNode() != null)
				nodeList.add(root.getLeftNode());
			if (root.getRightNode() != null)
				nodeList.add(root.getRightNode());
			System.out.print(nodeList.poll().getData() + " ");
			layer(nodeList.peek());
		} else {
			return;
		}
	}

	public static void main(String[] args) {
		// 先序遍历
		TraversalTree.preorder(InitTree.init());
		System.out.println();

		// 中序遍历
		TraversalTree.midorder(InitTree.init());
		System.out.println();

		// 后序遍历
		TraversalTree.postorder(InitTree.init());
		System.out.println();

		// 按层遍历
		TraversalTree.initHead(InitTree.init());
		TraversalTree.layer(InitTree.init());
	}
}
