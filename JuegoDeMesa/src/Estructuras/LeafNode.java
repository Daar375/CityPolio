package Estructuras;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LeafNode<Key extends Comparable<Key>, Value> extends TreeNode<Key, Value> {
	protected ArrayList<Value> values;
	protected LeafNode<Key, Value> nextLeaf;//siguiente hoja
	protected LeafNode<Key, Value> previousLeaf;//hoja anterior

	//nodo hoja, tiene una llave comparable y el valor de esa llave
	
	public LeafNode(Key firstKey, Value firstValue) {
		isLeafNode = true;
		keys = new ArrayList<Key>();
		values = new ArrayList<Value>();
		keys.add(firstKey);
		values.add(firstValue);

	}

	public boolean HasNextLeaf() {//se fija si hay una siguiente hoja en el arbol
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
	public void insertSorted(Key key, Value value) {//inserta un nuevo valor en la hoja
		if (key.compareTo(keys.get(0)) < 0) {//lo compara con la primera hoja, si es menor lo inserta de primero
			keys.add(0, key);
			values.add(0, value);
		} else if (key.compareTo(keys.get(keys.size() - 1)) > 0) {//lo compara con la ultima hoja si es mayor lo inserata de ultimo
			keys.add(key);
			values.add(value);
		} else {
			ListIterator<Key> iterator = keys.listIterator();
			while (iterator.hasNext()) {//va iterando hasta encontrar donde tiene que insertarlo
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