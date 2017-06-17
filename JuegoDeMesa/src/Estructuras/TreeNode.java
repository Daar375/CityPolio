package Estructuras;

import java.util.ArrayList;

public class TreeNode<Key extends Comparable<Key>, Value> {
	protected boolean isLeafNode;
	protected ArrayList<Key> keys;

	public boolean isOverflowed() {
		return keys.size() > 2 * BPlusTree.Degrees;
	}

	public boolean isUnderflowed() {
		return keys.size() < BPlusTree.Degrees;
	}

}