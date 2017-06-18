package Estructuras;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LeafNode<Key extends Comparable<Key>, Value> extends TreeNode<Key, Value> {
	protected ArrayList<Value> values;
	protected LeafNode<Key, Value> nextLeaf;
	protected LeafNode<Key, Value> previousLeaf;

	public LeafNode(Key firstKey, Value firstValue) {
		isLeafNode = true;
		keys = new ArrayList<Key>();
		values = new ArrayList<Value>();
		keys.add(firstKey);
		values.add(firstValue);

	}

	public boolean HasNextLeaf() {
		if (nextLeaf == null) {
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<Value> getValues() {
		return values;
	}

	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}

	public LeafNode<Key, Value> getNextLeaf() {
		return nextLeaf;
	}

	public void setNextLeaf(LeafNode<Key, Value> nextLeaf) {
		this.nextLeaf = nextLeaf;
	}

	public LeafNode<Key, Value> getPreviousLeaf() {
		return previousLeaf;
	}

	public void setPreviousLeaf(LeafNode<Key, Value> previousLeaf) {
		this.previousLeaf = previousLeaf;
	}

	public LeafNode(List<Key> newKeys, List<Value> newValues) {
		isLeafNode = true;
		keys = new ArrayList<Key>(newKeys);
		values = new ArrayList<Value>(newValues);

	}

	/**
	 * insert key/value into this node so that it still remains sorted
	 * 
	 * @param key
	 * @param value
	 */
	public void insertSorted(Key key, Value value) {
		if (key.compareTo(keys.get(0)) < 0) {
			keys.add(0, key);
			values.add(0, value);
		} else if (key.compareTo(keys.get(keys.size() - 1)) > 0) {
			keys.add(key);
			values.add(value);
		} else {
			ListIterator<Key> iterator = keys.listIterator();
			while (iterator.hasNext()) {
				if (iterator.next().compareTo(key) > 0) {
					int position = iterator.previousIndex();
					keys.add(position, key);
					values.add(position, value);
					break;
				}
			}

		}
	}

}