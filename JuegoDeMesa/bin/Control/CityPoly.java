package Control;

import Deck.DeckCiudades;
import Deck.DeckRetos;
import Deck.Reto;
import Deck.*;
import Estructuras.*;
import Estructuras.BPlusTree;
import Google.HTTPPlaces;
import Mapa.Place;
import Mapa.Type;
import Tools.DistanceCalc;
import UI.LoginWindow;
import UI.Ventana;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CityPoly implements IConstants {

	private BPlusTree<String, Jugador> Players = new BPlusTree();
	private Jugador player1;
	private Jugador player2;
	private DeckCiudades DecC = new DeckCiudades();
	private DeckRetos DecR = new DeckRetos();
	private Ciudad City;
	private Graph Grafo;
	private GameController Game;
	private Ventana GameWindow ;

	public CityPoly() {
		GameWindow = new Ventana();
		Game = new GameController(this);
		GameWindow.setVisible(false);

		DeckBuilder build = new DeckBuilder();
		DecC = build.bluidCityDeck();
		DecR = build.bluidRetoDeck();
		
	}
	public Ventana getGameWindow() {
		return GameWindow;
	}
	public void setGameWindow(Ventana gameWindow) {
		GameWindow = gameWindow;
	}
	public GameController getGame() {
		return Game;
	}

	public void setGame(GameController game) {
		Game = game;
	}
	public void iniciarUi() {
		LoginWindow UI = new LoginWindow(this);
		UI.setVisible(true);
	}

	public void EndGame() {
		player1 = null;
		player2 = null;
		DecR = null;
		City = null;
		Grafo = null;

	}

	public boolean newPlayer(String Name, String Pass) {
		if (Players.search(Name) == null) {
			Jugador player = new Jugador();
			player.setContrasenha(Pass);
			player.setName(Name);
			player.setPoints(0);
			Players.insert(Name, player);
			return true;
		}
		return false;
	}

	public void genGrafo() {
		System.out.println(City.getPlaces());
		Grafo = new Graph();
		int indexP = -1; // Primer indice para recorrer los places
		int indexP2 = -1; // Segundo indice para recorrer los places
		double distanciaEntrePlace = 0;
		boolean ExistenAristas = false;
		Place P;
		Place P2;
		int distanciaPermitida = 50;

		while (indexP < City.getPlaces().size() - 1) {
			indexP++;
			indexP2 = -1;
			P = City.getPlaces().get(indexP);
			ExistenAristas = false;
			while (indexP2 < City.getPlaces().size() - 1) {
				indexP2++;
				P2 = City.getPlaces().get(indexP2);
				distanciaEntrePlace = DistanceCalc.distance(P.getLatitud(), P.getLongitud(), P2.getLatitud(),
						P2.getLongitud());
				System.out
						.println(distanciaEntrePlace + " es la distancia entre el vertice " + indexP + " y " + indexP2);
				if (P == P2) {
					;
				} else if (distanciaEntrePlace < distanciaPermitida) {

					Grafo.addEdge(indexP, indexP2, (int) distanciaEntrePlace, false);
					ExistenAristas = true;
				}

			}
			// Si busco y no encontro places cerca para agregarse como vecinos
			// al grafo, hace la busqueda otra vez pero con 50mts mas
			if (!ExistenAristas) {
				distanciaPermitida += 50;
				System.out.print("+50 ");
				indexP--;
			} else {
				distanciaPermitida = 50;
			}
		}
	}

	public int genRandom(int rangoI, int rangoF) {
		System.out.println("Random generado!");
		return ThreadLocalRandom.current().nextInt(rangoI, rangoF);
	}

	public boolean logIn(String Name, String Pass) {
		Jugador playerfound = Players.search(Name);
		if (playerfound != null) {
			if (playerfound.getContrasenha().equals(Pass)) {
				if (player1 == null) {
					player1 = playerfound;
				} else {
					player2 = playerfound;

				}
				return true;

			}
		}
		return false;

	}

	public Graph getGrafo() {
		return Grafo;
	}

	public void setGrafo(Graph Grafo) {
		this.Grafo = Grafo;
	}

	public BPlusTree<String, Jugador> getPlayers() {
		return Players;
	}

	public void setPlayers(BPlusTree<String, Jugador> players) {
		Players = players;
	}

	public Jugador getPlayer1() {
		return player1;
	}

	public void setPlayer1(Jugador player1) {
		this.player1 = player1;
	}

	public Jugador getPlayer2() {
		return player2;
	}

	public void setPlayer2(Jugador player2) {
		this.player2 = player2;
	}

	public DeckCiudades getDecC() {
		return DecC;
	}

	public void setDecC(DeckCiudades decC) {
		DecC = decC;
	}

	public DeckRetos getDecR() {
		return DecR;
	}

	public void setDecR(DeckRetos decR) {
		DecR = decR;
	}

	public Ciudad getCity() {
		return City;
	}

	public void setCity(Ciudad city) {
		City = city;
	}
}
