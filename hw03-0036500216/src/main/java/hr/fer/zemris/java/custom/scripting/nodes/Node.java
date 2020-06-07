package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Base class for all Graph nodes
 * 
 * @author ilovrencic
 *
 */
public class Node {

	/**
	 * Collection that holds all direct child nodes for this node.
	 */
	ArrayIndexedCollection nodes;

	/**
	 * Method that adds this child into direct nodes for this node. If passed node
	 * is null, we throw {@link NullPointerException}.
	 * 
	 * @param child - Node we want to add to our nodes.
	 */
	public void addChildNode(Node child) {
		if (child == null) {
			throw new NullPointerException("Node can't be null!");
		}

		if (nodes == null)
			nodes = new ArrayIndexedCollection();
		nodes.add(child);
	}

	/**
	 * Returns number of direct children this node has.
	 * 
	 * @return number of nodes in nodes {@link ArrayIndexedCollection}.
	 */
	public int numberOfChildren() {
		if (nodes == null)
			return 0;
		return nodes.size();
	}

	/**
	 * Returns a child at given index.
	 * 
	 * @param index - position in collection
	 * @return - Node at that position, or {@link IndexOutOfBoundsException} if
	 *         index is invalid.
	 */
	public Node getChild(int index) {
		if (nodes == null) {
			throw new NullPointerException("Nodes are uninitalized!");
		}

		return (Node) nodes.get(index);
	}
	
	@Override
	public String toString() {
		return "";
	}

}
