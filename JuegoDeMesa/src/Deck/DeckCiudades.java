package Deck;

import java.util.ArrayList;

public class DeckCiudades extends Deck {
	private ArrayList<Ciudad> Cartas= new ArrayList<Ciudad>();

	public ArrayList<Ciudad> getCartas() {
		return Cartas;
	}

	public void setCartas(ArrayList<Ciudad> cartas) {
		Cartas = cartas;
	}

	public void Add(Ciudad Carta) {

		Cartas.add(Carta);
	}
	
	public void AddAll( ArrayList<Ciudad> adding){
		Cartas.addAll(adding);
	}
	public void Print(){
		System.out.println(Cartas.toString());
	}
}
