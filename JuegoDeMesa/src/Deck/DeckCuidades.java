package Deck;

import java.util.ArrayList;

public class DeckCuidades extends Deck {
	private ArrayList<Cuidad> Cartas= new ArrayList();

	public ArrayList<Cuidad> getCartas() {
		return Cartas;
	}

	public void setCartas(ArrayList<Cuidad> cartas) {
		Cartas = cartas;
	}

	public void Add(Cuidad Carta) {

		Cartas.add(Carta);
	}
	
	public void AddAll( ArrayList<Cuidad> adding){
		Cartas.addAll(adding);
	}
	public void Print(){
		System.out.println(Cartas.toString());
	}
}
