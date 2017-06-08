package Deck;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	protected int Cantidad;

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	public void Shuffle(ArrayList Cartas) {
		Collections.shuffle(Cartas);
	}

}
