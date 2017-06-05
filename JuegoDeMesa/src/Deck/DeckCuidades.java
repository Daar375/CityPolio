package Deck;

import java.util.ArrayList;

public class DeckCuidades extends Deck {
	private ArrayList<Cuidad> Cartas;

	public ArrayList<Cuidad> getCartas() {
		return Cartas;
	}

	public void setCartas(ArrayList<Cuidad> cartas) {
		Cartas = cartas;
	}

	public void Add(Cuidad Carta) {

		Cartas.add(Carta);
		Cantidad++;
	}
}
