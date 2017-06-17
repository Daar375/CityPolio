package Estructuras;
import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.ArrayList;

public class BPlusTree<Key extends Comparable<Key>, Value> {

	public TreeNode<Key,Value> root;
	public static final int Degrees = 2;


	
	public LeafNode getFirst(){
		if(root == null) {
			return null;
		}else if(root.isLeafNode){
			return (LeafNode) root;
			
		}
		else{
		
			TreeNode Searching =root;
			IndexNode<Key,Value> index = (IndexNode<Key, Value>) Searching;
			
			while(!Searching.isLeafNode){
				Searching=index.children.get(0);

			}
			return (LeafNode) Searching;

		}
		
		
		
	}

	public Value search(Key key) {
		// retorna si la llave o el arbol estan vacios
		if(key == null || root == null) {
			return null;
		}
		// busca desde la raiz la llave que tiene
		LeafNode<Key,Value> leaf = (LeafNode<Key,Value>)treeSearch(root, key);
					
		//busca el valor dentro de la hoja
		for(int i=0; i<leaf.keys.size(); i++) {
			if(key.compareTo(leaf.keys.get(i)) == 0) {
				return leaf.values.get(i);
			}
		}
					
		return null;
	}
	
	private TreeNode<Key,Value> treeSearch(TreeNode<Key,Value> node, Key key) {
		//si el nodo es una hoja significa que ya encontro el valor
		if(node.isLeafNode) {
			return node;
		} 
		// si es un nodo de indice
		else {
			IndexNode<Key,Value> index = (IndexNode<Key,Value>)node;
			
			// si el valor de la llave es menor al primer valor que esta en el nodo indice 
			///entonces se llama a si misma con ese valor
			if(key.compareTo(index.keys.get(0)) < 0) {
				return treeSearch((TreeNode<Key,Value>)index.children.get(0), key);
			}
			// si el valor de la llave es mayor o igual al ultimo valor  que esta en el nodo indice 
			///entonces se llama a si misma con ese valor
			else if(key.compareTo(index.keys.get(node.keys.size()-1)) >= 0) {
				return treeSearch((TreeNode<Key,Value>)index.children.get(index.children.size()-1), key);
			}
			// en el caso que ninguna de los 2 se cumpla busca uno por uno hasta encontrarlo
			else {
				// Linear searching
				for(int i=0; i<index.keys.size()-1; i++) {
					if(key.compareTo(index.keys.get(i)) >= 0 && key.compareTo(index.keys.get(i+1)) < 0) {
						return treeSearch((TreeNode<Key,Value>)index.children.get(i+1), key);
					}
				}
 			}
			return null;
		}
	} 
	

	public void insert(Key key, Value value) {
		
		//crea la hoja donde se va a insertar el valor
		LeafNode<Key,Value> newLeaf = new LeafNode<Key,Value>(key, value);
		//crea una entry donde se va a guardar el dato en la hoja
		Entry<Key, TreeNode<Key,Value>> entry = new AbstractMap.SimpleEntry<Key, TreeNode<Key,Value>>(key, newLeaf);
		
		//inserta la entry  en la raiz del arbol
		if(root == null || root.keys.size() == 0) {
			root = entry.getValue();
		}
		
		//manda la raiz , la entry y un null(en este caso) a getchild entry que devuelve
		Entry<Key, TreeNode<Key,Value>> newChildEntry = getChildEntry(root, entry, null);
		
		if(newChildEntry == null) {
			return;
		} else {
			IndexNode<Key,Value> newRoot = new IndexNode<Key,Value>(newChildEntry.getKey(), root, 
					newChildEntry.getValue());
			root = newRoot;
			return;
		}
	}
	
	private Entry<Key, TreeNode<Key,Value>> getChildEntry(TreeNode<Key,Value> node, Entry<Key, TreeNode<Key,Value>> entry, 
			Entry<Key, TreeNode<Key,Value>> newChildEntry) {
	
		if(!node.isLeafNode) {
			// busca en el subarbol el valor que sea mayor que la entry 
			IndexNode<Key,Value> index = (IndexNode<Key,Value>) node;
			int comparandoI = 0;
			while(comparandoI < index.keys.size()) {
				if(entry.getKey().compareTo(index.keys.get(comparandoI)) < 0) {
					break;
				}
				comparandoI++;
			}
			// inserta recursivamente la entry en el subarbol
			newChildEntry = getChildEntry((TreeNode<Key,Value>) index.children.get(comparandoI), entry, newChildEntry);
			
			// en caso que el resultado anterior sea null esto devuelve null
			if(newChildEntry == null) {
				return null;
			} 
			// busca en el subarbol el valor que sea mayor que el newChildEntry
			else {
				int comparandoj = 0;
				while (comparandoj < index.keys.size()) {
					if(newChildEntry.getKey().compareTo(index.keys.get(comparandoj)) < 0) {
						break;
					}
					comparandoj++;
				}
				//inserta en el puesto que va en el index el newChildEntry
				index.insertSorted(newChildEntry, comparandoj);
				
				// si el indice no esta overflowed (tiene mas valores dentro de los que permite ) se sale
				if(!index.isOverflowed()) {
					return null;
				} 
				
				else{
					newChildEntry = splitIndexNode(index);
					
					// se divide el index
					if(index == root) {
						// se crea un nuevo nodo y se hace que la raiz apunte a la nueva raiz
						IndexNode<Key,Value> newRoot = new IndexNode<Key,Value>(newChildEntry.getKey(), root, 
								newChildEntry.getValue());
						root = newRoot;
						return null;
					}
					return newChildEntry;
				}
			}
		}
		// si el nodo de puntero es una hoja
		else {
			LeafNode<Key,Value> leaf = (LeafNode<Key,Value>)node;
			LeafNode<Key,Value> newLeaf = (LeafNode<Key,Value>)entry.getValue();
			
			leaf.insertSorted(entry.getKey(), newLeaf.values.get(0));
			
			// si la hoja tiene espacio se le pone la entry y se pone el newChild en null y se devuelve
			if(!leaf.isOverflowed()) {
				return null;
			}
			// si la hoja esta llena
			else {
				//se divide la hoja
				newChildEntry = splitLeafNode(leaf);
				if(leaf == root) {
					IndexNode<Key,Value> newRoot = new IndexNode<Key,Value>(newChildEntry.getKey(), leaf, 
							newChildEntry.getValue());
					root = newRoot;
					return null;
				}
				return newChildEntry;
			}
		}
	}

	/**
	 * TODO Split a leaf node and return the new right node and the splitting
	 * key as an Entry<slitingKey, RightNode>
	 * 
	 * @param leaf, any other relevant data
	 * @return the key/node pair as an Entry
	 */
	public Entry<Key, TreeNode<Key,Value>> splitLeafNode(LeafNode<Key,Value> leaf) {
		ArrayList<Key> newKeys = new ArrayList<Key>();
		ArrayList<Value> newValues = new ArrayList<Value>();
		
		// The rest D entries move to brand new node
		for(int i=Degrees; i<=2*Degrees; i++) {
			newKeys.add(leaf.keys.get(i));
			newValues.add(leaf.values.get(i));
		}
		
		// First D entries stay
		for(int i=Degrees; i<=2*Degrees; i++) {
			leaf.keys.remove(leaf.keys.size()-1);
			leaf.values.remove(leaf.values.size()-1);
		}
		
		Key splitKey = newKeys.get(0);
		LeafNode<Key,Value> rightNode = new LeafNode<Key,Value>(newKeys, newValues);
		
		// Set sibling pointers
		LeafNode<Key,Value> tmp = leaf.nextLeaf;
		leaf.nextLeaf = rightNode;
		leaf.nextLeaf.previousLeaf = rightNode;
		rightNode.previousLeaf = leaf;
		rightNode.nextLeaf = tmp;
        
		Entry<Key, TreeNode<Key,Value>> newChildEntry = new AbstractMap.SimpleEntry<Key, TreeNode<Key,Value>>(splitKey, rightNode);
		
		return newChildEntry;
	}


	public Entry<Key, TreeNode<Key,Value>> splitIndexNode(IndexNode<Key,Value> index) {
		ArrayList<Key> newKeys = new ArrayList<Key>();
		ArrayList<TreeNode<Key,Value>> newChildren = new ArrayList<TreeNode<Key,Value>>();
		//consigue el valor que esta en degress y lo saca del nodo indice
		Key splitKey = index.keys.get(Degrees);
		index.keys.remove(Degrees);
		
		// agrega al array el valor que esta despues del degrees luego lo remueve
		newChildren.add(index.children.get(Degrees+1));
		index.children.remove(Degrees+1);
		//mientras la cantidad de llaves sea menor que los degrees, agrega a nuevas llaves el que esta en degress y lo remueve del indice
		while(index.keys.size() > Degrees) {
			newKeys.add(index.keys.get(Degrees));
			index.keys.remove(Degrees);
			newChildren.add(index.children.get(Degrees+1));
			index.children.remove(Degrees+1);
		}

		IndexNode<Key,Value> rightNode = new IndexNode<Key,Value>(newKeys, newChildren);//crea un nuevo nodo indice
		Entry<Key, TreeNode<Key,Value>> newChildEntry = new AbstractMap.SimpleEntry<Key, TreeNode<Key,Value>>(splitKey, rightNode);
		//crea el nuevo entry con el nuevo nodo y la llave
		return newChildEntry;
	}

	public void delete(Key key) {
		if(key == null || root == null) {
			return;
		}

		// busca si esta la llave en la hoja
		LeafNode<Key,Value> leaf = (LeafNode<Key,Value>)treeSearch(root, key);
		if(leaf == null) {
			return;//en caso de no estar se sale
		}
		
		////borra la entry del sub arbol con puntero de la raiz
		Entry<Key, TreeNode<Key,Value>> entry = new AbstractMap.SimpleEntry<Key, TreeNode<Key,Value>>(key, leaf);
		
		// una entry que se queda en null a menos  que un hijo sea borrado 
		Entry<Key, TreeNode<Key,Value>> oldChildEntry = deleteChildEntry(root, root, entry, null);
		
		// reacomoda la raiz en caso de que no hijo sea borrado 
		if(oldChildEntry == null) {
			if(root.keys.size() == 0) {
				if(!root.isLeafNode) {
					root = ((IndexNode<Key,Value>) root).children.get(0);
				}
			}
			return;
		}
		// si el hijo es borrado
		else {
			// se busca un nodo vacio
			int i = 0;
			Key oldKey = oldChildEntry.getKey();
			while(i < root.keys.size()) {
				if(oldKey.compareTo(root.keys.get(i)) == 0) {
					break;
				}
				i++;
			}
			// se regresa si no encontro ninguno(el nodo ya fue descartado)
			if(i == root.keys.size()) {
				return;
			}
			// descarta el nodo vacio
			root.keys.remove(i);
			((IndexNode<Key,Value>)root).children.remove(i+1);
			return;
		}
	}
	
	private Entry<Key, TreeNode<Key,Value>> deleteChildEntry(TreeNode<Key,Value> parentNode, TreeNode<Key,Value> node, 
			Entry<Key, TreeNode<Key,Value>> entry, Entry<Key, TreeNode<Key,Value>> oldChildEntry) {
		if(!node.isLeafNode) {
			// busca un sub arbol donde este la entry
			IndexNode<Key,Value> index = (IndexNode<Key,Value>)node;
			int i = 0;
			Key entryKey = entry.getKey();
			while(i < index.keys.size()) {
				if(entryKey.compareTo(index.keys.get(i)) < 0) {
					break;
				}
				i++;
			}
			// borra recursivamente
			oldChildEntry = deleteChildEntry(index, index.children.get(i), entry, oldChildEntry);
			
			// en caso de que el hijo no sea borrado aun regresa null
			if(oldChildEntry == null) {
				return null;
			}
			// el hijo que hay que borrar esta en este nodo
			else {
				int j = 0;
				Key oldKey = oldChildEntry.getKey();
				while(j < index.keys.size()) {
					if(oldKey.compareTo(index.keys.get(j)) == 0) {
						break;
					}
					j++;
				}
				//lo remueve del nodo
				index.keys.remove(j);
				index.children.remove(j+1);
				
				//ve si esta en underflow (menos valores de los que deberia tener) regresa null si vacio
				if(!index.isUnderflowed() || index.keys.size() == 0) {
					// Node has entries to spare, delete doesn't go further
					return null; 
				}
				else {
					// se regresa si es la raiz
					if(index == root) {
						return oldChildEntry;
					}
					//busca a los hermanos
					int SiblingI = 0;
					Key firstKey = index.keys.get(0);
					while(SiblingI < parentNode.keys.size()) {
						if(firstKey.compareTo(parentNode.keys.get(SiblingI)) < 0) {
							break;
						}
						SiblingI++;
					}
					//  maneja le underflow del indice
					int splitKeyPos;
					IndexNode<Key,Value> parent = (IndexNode<Key,Value>)parentNode;
					//revisa de que lado tiene hermano
					if(SiblingI > 0 && parent.children.get(SiblingI-1) != null) {
						splitKeyPos = handleIndexNodeUnderflow(
								(IndexNode<Key,Value>)parent.children.get(SiblingI-1), index, parent);
					} else {
						splitKeyPos = handleIndexNodeUnderflow(
								index, (IndexNode<Key,Value>)parent.children.get(SiblingI+1), parent);
					}
					// si el hermano tiene entry de mas retorna
					if(splitKeyPos == -1) {
						return null;
					}
					// mezcla el nodo indice y el nodo hermano
					else {
						Key parentKey = parentNode.keys.get(splitKeyPos);
						oldChildEntry = new AbstractMap.SimpleEntry<Key, TreeNode<Key,Value>>(parentKey, parentNode);
						return oldChildEntry;
					}
				}
			}
		}
		// is el nodo es una hoja
		else {
			LeafNode<Key,Value> leaf = (LeafNode<Key,Value>)node;
			// busca el valor que va a borrar
			for(int i=0; i<leaf.keys.size(); i++) {
				if(leaf.keys.get(i) == entry.getKey()) {
					leaf.keys.remove(i);
					leaf.values.remove(i);
					break;
				}
			}
			// si no hay underflow
			if(!leaf.isUnderflowed()) {
				return null;
			}
			//si tiene underflow
			else {
				// regresa si el la raiz
				if(leaf == root || leaf.keys.size() == 0) {
					return oldChildEntry;
				}
				// maneja el under flow
				int splitKeyPos;
				Key firstKey = leaf.keys.get(0);
				Key parentKey = parentNode.keys.get(0);
				
				if(leaf.previousLeaf != null && firstKey.compareTo(parentKey) >= 0) {
					splitKeyPos = handleLeafNodeUnderflow(leaf.previousLeaf, leaf, (IndexNode<Key,Value>)parentNode);
				} else {
					splitKeyPos = handleLeafNodeUnderflow(leaf, leaf.nextLeaf, (IndexNode<Key,Value>)parentNode);
				}
				//si el hermano tiene entries extra devuelve null
				if(splitKeyPos == -1) {
					return null;
				} 
				//convina la hoja con el hermano
				else {
					parentKey = parentNode.keys.get(splitKeyPos);
					oldChildEntry = new AbstractMap.SimpleEntry<Key, TreeNode<Key,Value>>(parentKey, parentNode);
					return oldChildEntry;
				}	
			}
		}
	}

	/**
	 * TODO Handle LeafNode Underflow (merge or redistribution)
	 * 
	 * @param left
	 *            : the smaller node
	 * @param right
	 *            : the bigger node
	 * @param parent
	 *            : their parent index node
	 * @return the splitkey position in parent if merged so that parent can
	 *         delete the splitkey later on. -1 otherwise
	 */
	public int handleLeafNodeUnderflow(LeafNode<Key,Value> left, LeafNode<Key,Value> right,
			IndexNode<Key,Value> parent) {
		// Find entry in parent for node on right
		int i = 0;
		Key rKey = right.keys.get(0);
		while(i < parent.keys.size()) {
			if(rKey.compareTo(parent.keys.get(i)) < 0) {
				break;
			}
			i++;
		}	
		// Redistribute evenly between right and left nodes
		// If S has extra entries
		if(left.keys.size() + right.keys.size() >= 2*Degrees) {
			// Left node has more entries
			if(left.keys.size() > right.keys.size()) {
				while(left.keys.size() > Degrees) {
					right.keys.add(0, left.keys.get(left.keys.size()-1));
					right.values.add(0, left.values.get(left.keys.size()-1));
					left.keys.remove(left.keys.size()-1);
					left.values.remove(left.values.size()-1);
				}
			}
			// Right node has more entries
			else {
				while(left.keys.size() < Degrees) {
					left.keys.add(right.keys.get(0));
					left.values.add(right.values.get(0));
					right.keys.remove(0);
					right.values.remove(0);
				}
			}
			// Replace key value in parent entry by low-key in right node
			parent.keys.set(i-1, right.keys.get(0));
			
			return -1;
		}
		// No extra entries, return splitKeyPos
		else {
			// Move all entries from right to left node
			while(right.keys.size() > 0) {
				left.keys.add(right.keys.get(0));
				left.values.add(right.values.get(0));
				right.keys.remove(0);
				right.values.remove(0);
			}
			// Adjust sibling pointers
			if(right.nextLeaf != null) {
				right.nextLeaf.previousLeaf = left;
			}
			left.nextLeaf = right.nextLeaf;
			
			return i-1;
		}
	}
	

	public int handleIndexNodeUnderflow(IndexNode<Key,Value> leftIndex,IndexNode<Key,Value> rightIndex, IndexNode<Key,Value> parent){
		// Find entry in parent for node on right
		int i = 0;
		Key rKey = rightIndex.keys.get(0);
		while(i < parent.keys.size()) {
			if(rKey.compareTo(parent.keys.get(i)) < 0) {
				break;
			}
			i++;
		}
		// Redistribute evenly between node and S through parent
		// If S has extra entries
		if(leftIndex.keys.size() + rightIndex.keys.size() >= 2*Degrees) {
			// Left node has more entries
			if(leftIndex.keys.size() > rightIndex.keys.size()) {
				while(leftIndex.keys.size() > Degrees) {
					rightIndex.keys.add(0, parent.keys.get(i-1));
					rightIndex.children.add(leftIndex.children.get(leftIndex.children.size()-1));
					parent.keys.set(i-1, leftIndex.keys.get(leftIndex.keys.size()-1));
					leftIndex.keys.remove(leftIndex.keys.size()-1);
					leftIndex.children.remove(leftIndex.children.size()-1);
				}
			}
			// Right node has more entries
			else {
				while(leftIndex.keys.size() < Degrees) {
					leftIndex.keys.add(parent.keys.get(i-1));
					leftIndex.children.add(rightIndex.children.get(0));
					parent.keys.set(i-1, rightIndex.keys.get(0));
					rightIndex.keys.remove(0);
					rightIndex.children.remove(0);
				}
			}
			return -1;
		}
		// No extra entries, return spiltKeyPos
		else {
			leftIndex.keys.add(parent.keys.get(i-1));
			// Move all entries from right to left node
			while(rightIndex.keys.size() > 0) {
				leftIndex.keys.add(rightIndex.keys.get(0));
				leftIndex.children.add(rightIndex.children.get(0));
				rightIndex.keys.remove(0);
				rightIndex.children.remove(0);
			}
			leftIndex.children.add(rightIndex.children.get(0));
			rightIndex.children.remove(0);
			
			return i-1;
		}
	}
}
