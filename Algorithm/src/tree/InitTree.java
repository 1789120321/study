package tree;

public final class InitTree {

	public static Node init() {
		Node[] nodes = new Node[11];
		for (int i = 1; i < 11; i++) {
			nodes[i] = new Node();
			nodes[i].setData(i);
		}
		nodes[1].setLeftNode(nodes[2]);
		nodes[1].setRightNode(nodes[3]);

		nodes[2].setLeftNode(nodes[4]);
		nodes[2].setRightNode(nodes[5]);

		nodes[3].setLeftNode(nodes[6]);
		nodes[3].setRightNode(nodes[7]);

		nodes[4].setLeftNode(nodes[8]);
		nodes[4].setRightNode(nodes[9]);

		nodes[5].setLeftNode(null);
		nodes[5].setRightNode(null);

		nodes[6].setLeftNode(nodes[10]);
		nodes[6].setRightNode(null);

		nodes[7].setLeftNode(null);
		nodes[7].setRightNode(null);

		nodes[8].setLeftNode(null);
		nodes[8].setRightNode(null);

		nodes[9].setLeftNode(null);
		nodes[9].setRightNode(null);

		nodes[10].setLeftNode(null);
		nodes[10].setRightNode(null);

		return nodes[1];
	}
}
