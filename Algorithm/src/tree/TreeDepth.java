package tree;

public class TreeDepth {

	public static int depth(Node root) {
		int leftDepth = 0, rightDepth = 0;

		if (root == null)
			return 0;
		else {
			leftDepth = depth(root.getLeftNode());
			rightDepth = depth(root.getRightNode());

			if (leftDepth >= rightDepth)
				return leftDepth + 1;
			else
				return rightDepth + 1;
		}

	}

	public static int height(Node root) {
		if (root == null)
			return 0;
		else
			return 1 + Math.max(height(root.getLeftNode()), height(root.getRightNode()));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(TreeDepth.depth(InitTree.init()));
		System.out.println(TreeDepth.height(InitTree.init()));
	}

}
