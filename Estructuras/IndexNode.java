package Estructuras;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class IndexNode<K extends Comparable<K>, T> extends TreeNode<K,T> {

	// m nodes
	protected ArrayList<TreeNode<K,T>> children; // m+1 children

	public IndexNode(K key, TreeNode<K,T> child0, TreeNode<K,T> child1) {
		isLeafNode = false;
		keys = new ArrayList<K>();
		keys.add(key);
		children = new ArrayList<TreeNode<K,T>>();
		children.add(child0);
		children.add(child1);
	}

	public IndexNode(List<K> newKeys, List<TreeNode<K,T>> newChildren) {
		isLeafNode = false;

		keys = new ArrayList<K>(newKeys);
		children = new ArrayList<TreeNode<K,T>>(newChildren);

	}

	/**
	 * insert the entry into this node at the specified index so that it still
	 * remains sorted
	 * 
	 * @param e
	 * @param index
	 */
	public void insertSorted(Entry<K, TreeNode<K,T>> e, int index) {
		K key = e.getKey();
		TreeNode<K,T> child = e.getValue();
		if (index >= keys.size()) {
			keys.add(key);
			children.add(child);
		} else {
			keys.add(index, key);
			children.add(index+1, child);
		}
	}

}