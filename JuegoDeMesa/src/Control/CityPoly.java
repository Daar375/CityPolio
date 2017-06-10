package Control;

import java.util.ArrayList;

import Deck.DeckCiudades;
import Deck.DeckRetos;
import Deck.Reto;
import Estructuras.BPlusTree;
import Google.GeoMap;
import Google.HTTPPlaces;
import Mapa.Type;

public class CityPoly {
	private BPlusTree<String, Jugador> Players = new BPlusTree();
	private Jugador player1;
	private Jugador player2;
	private DeckCiudades DecC= new DeckCiudades();
	private DeckRetos DecR= new DeckRetos();
	
	
	public CityPoly(){
		bluidDecks();
	}
	public void newPlayer(String Name, String Pass) {
		if (Players.search(Name) == null) {
			Jugador player = new Jugador();
			player.setContraseña(Pass);
			player.setName(Name);
			player.setPoints(0);
			Players.insert(Name, player);
		}
	}

	public void bluidDecks(){
		GeoMap tool = new GeoMap();
		HTTPPlaces get = new HTTPPlaces();
		ArrayList City = new ArrayList();
		City.addAll(tool.loadplaces(get.getplaces("9.9354028", "-84.0753903", "restaurant"), Type.restaurant));

		City.addAll(tool.loadplaces(get.getplaces("9.9354028", "-84.0753903", "hospital"), Type.hospital));
		City.addAll(tool.loadplaces(get.getplaces("9.9354028", "-84.0753903", "church"), Type.church));
		DecC.AddAll(City);

		int index = 0;
		while(index!=30){
			Reto reto= new Reto();
			reto.setTipo(DecR.randomEnum(Type.class));
			reto.setCantidad(new Dado().rollDice());
			DecR.Add(reto);
			index++;
		}
		System.out.println("hoa");

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
	
	
	public static void main(String[] args) throws Exception {
		CityPoly test = new CityPoly();
	}
}
