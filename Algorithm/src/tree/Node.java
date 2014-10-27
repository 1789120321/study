package tree;

/**
 * Created by Luonanqin on 10/27/14.
 */
public class Node extends NodeMeta {

	private Node leftNode;
	private Node rightNode;

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
}
