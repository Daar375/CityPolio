package Estructuras;

import java.util.ArrayList;

public class TreeNode<Key extends Comparable<Key>, Value> {
	protected boolean isLeafNode;
	protected ArrayList<Key> keys;//llaves del nodo

	public boolean isOverflowed() {//se fija si tiene mas llavse de las permitidas
		return keys.size() > 2 * BPlusTree.Degrees;
	}

	public boolean isUnderflowed() {//se fija si tiene menos llaves de la permitidas 
		return keys.size() < BPlusTree.Degrees;
	}

}