package Deck;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DeckCiudades extends Deck {
	private ArrayList<Ciudad> Cartas= new ArrayList<Ciudad>();

	public ArrayList<Ciudad> getCartas() {
		return Cartas;
	}
	public Ciudad getRandomCard(){
		return Cartas.get(ThreadLocalRandom.current().nextInt(0, Cartas.size()));
	}

	public void setCartas(ArrayList<Ciudad> cartas) {
		Cartas = cartas;
	}

	public void Add(Ciudad city) {

		Cartas.add(city);
	}
	
	public void AddAll( ArrayList<Ciudad> adding){
		Cartas.addAll(adding);
	}
	public void Print(){
		System.out.println(Cartas.toString());
	}
}
