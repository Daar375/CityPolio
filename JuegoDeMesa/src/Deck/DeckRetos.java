package Deck;

import java.util.ArrayList;

public class DeckRetos extends Deck {
	private ArrayList<Reto> Cartas;

	public ArrayList<Reto> getCartas() {
		return Cartas;
	}

	public void setCartas(ArrayList<Reto> cartas) {
		Cartas = cartas;
	}

	public void Add(Reto Carta) {
		Cartas.add(Carta);
		Cantidad++;

	}

}
