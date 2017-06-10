package Deck;

import java.util.ArrayList;

public class DeckCiudades extends Deck {
	private ArrayList<Ciudades> Cartas= new ArrayList();

	public ArrayList<Ciudades> getCartas() {
		return Cartas;
	}

	public void setCartas(ArrayList<Ciudades> cartas) {
		Cartas = cartas;
	}

	public void Add(Ciudades Carta) {

		Cartas.add(Carta);
	}
	
	public void AddAll( ArrayList<Ciudades> adding){
		Cartas.addAll(adding);
	}
	public void Print(){
		System.out.println(Cartas.toString());
	}
}
