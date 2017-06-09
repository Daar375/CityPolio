package Estructuras;

import Control.Jugador;

public class test {
	public static void main(String[] args) throws Exception {
		BPlusTree arbol = new BPlusTree();
		Jugador Daar = new Jugador();
		Daar.setContraseña("123");
		Daar.setName("Daar");
		Daar.setPoints(1000);
		arbol.insert(Daar.getName(), Daar);
		if(arbol.search("Daar2")!=null){
			System.out.println(		arbol.search("Daar"));

		}
		arbol.delete("Daar");
	}

	
}
