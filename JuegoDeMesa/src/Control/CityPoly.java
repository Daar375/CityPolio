package Control;

import Estructuras.BPlusTree;

public class CityPoly {
	private BPlusTree<String, Jugador> Players = new BPlusTree();
	private Jugador player1;
	private Jugador player2;

	public void newPlayer(String Name, String Pass) {
		if (Players.search(Name) == null) {
			Jugador player = new Jugador();
			player.setContraseña(Pass);
			player.setName(Name);
			player.setPoints(0);
			Players.insert(Name, player);
		}
	}

	public void logIn(String Name, String Pass) {
		Jugador playerfound = Players.search(Name);
		if (playerfound != null) {
			if (playerfound.getContraseña() == Pass) {
				if (player1 == null) {
					player1 = playerfound;
				} else {
					player2 = playerfound;

				}
			}
		}
	}
}
