package Estructuras;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class IndexNode<Key extends Comparable<Key>, Value> extends TreeNode<Key,Value> {

	// m nodes
	protected ArrayList<TreeNode<Key,Value>> children; // m+1 children

	public IndexNode(Key key, TreeNode<Key,Value> child0, TreeNode<Key,Value> child1) {
		isLeafNode = false;
		keys = new ArrayList<Key>();
		keys.add(key);
		children = new ArrayList<TreeNode<Key,Value>>();
		children.add(child0);
		children.add(child1);
	}

	public IndexNode(List<Key> newKeys, List<TreeNode<Key,Value>> newChildren) {
		isLeafNode = false;

		keys = new ArrayList<Key>(newKeys);
		children = new ArrayList<TreeNode<Key,Value>>(newChildren);

	}

	/**
	 * insert the entry into this node at the specified index so that it still
	 * remains sorted
	 * 
	 * @param e
	 * @param index
	 */
	public void insertSorted(Entry<Key, TreeNode<Key,Value>> e, int index) {
		Key key = e.getKey();
		TreeNode<Key,Value> child = e.getValue();
		if (index >= keys.size()) {
			keys.add(key);
			children.add(child);
		} else {
			keys.add(index, key);
			children.add(index+1, child);
		}
	}

}