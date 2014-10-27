package tree;

/**
 * Created by Luonanqin on 10/27/14.
 */
/**
 * 1 / \ 2 3 / \ \ 4 5 6 / \ \ 7 8 9 / / 10 11 \ 12
 *
 * 求树的最长路径，即12 11 9 5 2 1 3 6，长度为8
 */
class Node1 extends NodeMeta {

	private Node1 leftNode;
	private Node1 rightNode;
	private int leftMaxLength = 0;
	private int rightMaxLength = 0;

	public Node1(int data) {
		super(data);
	}

	public int getLeftMaxLength() {
		return leftMaxLength;
	}

	public void setLeftMaxLength(int leftMaxLength) {
		this.leftMaxLength = leftMaxLength;
	}

	public int getRightMaxLength() {
		return rightMaxLength;
	}

	public void setRightMaxLength(int rightMaxLength) {
		this.rightMaxLength = rightMaxLength;
	}

	public Node1 getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node1 leftNode) {
		this.leftNode = leftNode;
	}

	public Node1 getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node1 rightNode) {
		this.rightNode = rightNode;
	}
}

public class MaxTreePath {

	private static int maxPath = 0;

	public static Node1 generateTree() {
		Node1 root = new Node1(TreeUtil.generateData());
		Node1 node2 = new Node1(TreeUtil.generateData());
		Node1 node3 = new Node1(TreeUtil.generateData());
		Node1 node4 = new Node1(TreeUtil.generateData());
		Node1 node5 = new Node1(TreeUtil.generateData());
		Node1 node6 = new Node1(TreeUtil.generateData());
		Node1 node7 = new Node1(TreeUtil.generateData());
		Node1 node8 = new Node1(TreeUtil.generateData());
		Node1 node9 = new Node1(TreeUtil.generateData());
		Node1 node10 = new Node1(TreeUtil.generateData());
		Node1 node11 = new Node1(TreeUtil.generateData());
		Node1 node12 = new Node1(TreeUtil.generateData());

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

	public static void findMaxPath(Node1 node) {
		// 左节点为空，左最长距离为0
		if (node.getLeftNode() == null) {
			node.setLeftMaxLength(0);
		}

		// 右节点为空，右最长距离为0
		if (node.getRightNode() == null) {
			node.setRightMaxLength(0);
		}

		// 左节点不空，继续遍历
		if (node.getLeftNode() != null) {
			findMaxPath(node.getLeftNode());
		}

		// 右节点不空，继续遍历
		if (node.getRightNode() != null) {
			findMaxPath(node.getRightNode());
		}

		// 左节点不空，设置左最长距离为左节点的最长距离+1
		if (node.getLeftNode() != null) {
			if (node.getLeftNode().getLeftMaxLength() > node.getLeftNode().getRightMaxLength()) {
				node.setLeftMaxLength(node.getLeftNode().getLeftMaxLength() + 1);
			} else {
				node.setLeftMaxLength(node.getLeftNode().getRightMaxLength() + 1);
			}
		}

		// 右节点不空，设置右最长距离为右节点的最长距离+1
		if (node.getRightNode() != null) {
			if (node.getRightNode().getLeftMaxLength() > node.getRightNode().getRightMaxLength()) {
				node.setRightMaxLength(node.getRightNode().getLeftMaxLength() + 1);
			} else {
				node.setRightMaxLength(node.getRightNode().getRightMaxLength() + 1);
			}
		}

		// 判断左右最长距离相加再加1是否大于最长路径
		if (node.getLeftMaxLength() + node.getRightMaxLength() + 1 > maxPath) {
			maxPath = node.getLeftMaxLength() + node.getRightMaxLength() + 1;
		}
	}

	public static void main(String[] args) {
		Node1 root = generateTree();
		findMaxPath(root);
		System.out.println("Max Path: " + maxPath);
	}
}
