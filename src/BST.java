import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BST<E extends Comparable<E>> {
	private BNode<E> root;

	public BST() {
		this.root = null;
	}

	public void add(E e) {
		root = addNode(root, e);
	}

	private BNode<E> addNode(BNode<E> node, E e) {
		if (node == null) {
			return new BNode<>(e);
		}

		int compareResult = e.compareTo(node.getData());
		if (compareResult < 0) {
			node.setLeft(addNode(node.getLeft(), e));
		} else if (compareResult > 0) {
			node.setRight(addNode(node.getRight(), e));
		}

		return node;
	}

	public void add(Collection<E> col) {
		for (E element : col) {
			add(element);
		}
	}

	public int depth(E node) {
		return getNodeDepth(root, node, 0);
	}

	private int getNodeDepth(BNode<E> currentNode, E node, int depth) {
		if (currentNode == null) {
			return -1;
		}

		int compareResult = node.compareTo(currentNode.getData());
		if (compareResult == 0) {
			return depth;
		} else if (compareResult < 0) {
			return getNodeDepth(currentNode.getLeft(), node, depth + 1);
		} else {
			return getNodeDepth(currentNode.getRight(), node, depth + 1);
		}
	}

	public int height() {
		return calculateHeight(root);
	}

	private int calculateHeight(BNode<E> node) {
		if (node == null) {
			return 0;
		}

		int leftHeight = calculateHeight(node.getLeft());
		int rightHeight = calculateHeight(node.getRight());

		return Math.max(leftHeight, rightHeight) + 1;
	}

	public int size() {
		return calculateSize(root);
	}

	private int calculateSize(BNode<E> node) {
		if (node == null) {
			return 0;
		}

		int leftSize = calculateSize(node.getLeft());
		int rightSize = calculateSize(node.getRight());

		return leftSize + rightSize + 1;
	}

	public boolean contains(E e) {
		return searchElement(root, e);
	}

	private boolean searchElement(BNode<E> node, E e) {
		if (node == null) {
			return false;
		}

		int compareResult = e.compareTo(node.getData());
		if (compareResult == 0) {
			return true;
		} else if (compareResult < 0) {
			return searchElement(node.getLeft(), e);
		} else {
			return searchElement(node.getRight(), e);
		}
	}

	public E findMin() {
		BNode<E> minNode = findMinNode(root);
		if (minNode != null) {
			return minNode.getData();
		}
		return null;
	}

	private BNode<E> findMinNode(BNode<E> node) {
		if (node == null) {
			return null;
		}

		if (node.getLeft() == null) {
			return node;
		}

		return findMinNode(node.getLeft());
	}

	public E findMax() {
		BNode<E> maxNode = findMaxNode(root);
		if (maxNode != null) {
			return maxNode.getData();
		}
		return null;
	}

	private BNode<E> findMaxNode(BNode<E> node) {
		if (node == null) {
			return null;
		}

		if (node.getRight() == null) {
			return node;
		}

		return findMaxNode(node.getRight());
	}

	public boolean remove(E e) {
		root = removeNode(root, e);
		return root != null;
	}

	private BNode<E> removeNode(BNode<E> node, E e) {
		if (node == null) {
			return null;
		}

		int compareResult = e.compareTo(node.getData());
		if (compareResult < 0) {
			node.setLeft(removeNode(node.getLeft(), e));
		} else if (compareResult > 0) {
			node.setRight(removeNode(node.getRight(), e));
		} else {
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else {
				BNode<E> minRightNode = findMinNode(node.getRight());
				node.setData(minRightNode.getData());
				node.setRight(removeNode(node.getRight(), minRightNode.getData()));
			}
		}

		return node;
	}

	public List<E> descendants(E data) {
		List<E> descendantsList = new ArrayList<>();

		// Find the target node
		BNode<E> targetNode = findNode(root, data);

		if (targetNode != null) {
			// Traverse the subtree rooted at the target node and collect descendants
			collectDescendants(targetNode, descendantsList);
		}

		return descendantsList;
	}

	private void collectDescendants(BNode<E> node, List<E> descendantsList) {
		if (node == null) {
			return;
		}

		collectDescendants(node.getLeft(), descendantsList);
		collectDescendants(node.getRight(), descendantsList);
		descendantsList.add(node.getData());
	}

	public List<E> ancestors(E data) {
		List<E> ancestorsList = new ArrayList<>();
		findAncestors(root, data, ancestorsList);
		return ancestorsList;
	}

	private boolean findAncestors(BNode<E> node, E data, List<E> ancestorsList) {
		if (node == null) {
			return false;
		}

		if (node.getData().equals(data)) {
			return true;
		}

		if (findAncestors(node.getLeft(), data, ancestorsList) || findAncestors(node.getRight(), data, ancestorsList)) {
			ancestorsList.add(node.getData());
			return true;
		}

		return false;
	}

	public List<E> preorderTraversal() {
		List<E> traversalList = new ArrayList<>();
		preorderTraversal(root, traversalList);
		return traversalList;
	}

	private void preorderTraversal(BNode<E> node, List<E> traversalList) {
		if (node == null) {
			return;
		}

		traversalList.add(node.getData());
		preorderTraversal(node.getLeft(), traversalList);
		preorderTraversal(node.getRight(), traversalList);
	}

	public List<E> inorderTraversal() {
		List<E> traversalList = new ArrayList<>();
		inorderTraversal(root, traversalList);
		return traversalList;
	}

	private void inorderTraversal(BNode<E> node, List<E> traversalList) {
		if (node == null) {
			return;
		}

		inorderTraversal(node.getLeft(), traversalList);
		traversalList.add(node.getData());
		inorderTraversal(node.getRight(), traversalList);
	}

	public List<E> postorderTraversal() {
		List<E> traversalList = new ArrayList<>();
		postorderTraversal(root, traversalList);
		return traversalList;
	}

	private void postorderTraversal(BNode<E> node, List<E> traversalList) {
		if (node == null) {
			return;
		}

		postorderTraversal(node.getLeft(), traversalList);
		postorderTraversal(node.getRight(), traversalList);
		traversalList.add(node.getData());
	}

	public BNode<E> findNode(E data) {
		return findNode(root, data);
	}

	private BNode<E> findNode(BNode<E> node, E data) {
		if (node == null || node.getData().equals(data)) {
			return node;
		}

		int compareResult = data.compareTo(node.getData());
		if (compareResult < 0) {
			return findNode(node.getLeft(), data);
		} else {
			return findNode(node.getRight(), data);
		}
	}
}
