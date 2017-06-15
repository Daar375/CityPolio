package Estructuras;

import Control.Jugador;

public class test {
	public static void main(String[] args) throws Exception {
		BPlusTree tree = new BPlusTree();
		tree.insert(0, 0);
		tree.insert(1, 1);

		tree.insert(2, 2);

		tree.insert(3, 3);

		tree.insert(4, 4);

		tree.insert(5, 5);

		tree.insert(6, 6);

		tree.insert(7, 7);

	
		tree.insert(8, 8);

		
		tree.insert(9, 9);
		System.out.println(		tree.getFirst().nextLeaf.keys.get(0));

		
	}
	
}
