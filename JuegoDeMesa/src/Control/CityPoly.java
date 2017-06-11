package Control;

import Deck.DeckCiudades;
import Deck.DeckRetos;
import Deck.Reto;
import Deck.*;
import Estructuras.*;
import Estructuras.BPlusTree;
import Google.GeoMap;
import Google.HTTPPlaces;
import Mapa.Place;
import Mapa.Type;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CityPoly implements IConstants {
	private BPlusTree<String, Jugador> Players = new BPlusTree();
	private Jugador player1;
	private Jugador player2;
	private DeckCiudades DecC = new DeckCiudades();
	private DeckRetos DecR = new DeckRetos();
	private Graph Grafo;

	public CityPoly() {
		bluidDecks();

	}

	public void newPlayer(String Name, String Pass) {
		if (Players.search(Name) == null) {
			Jugador player = new Jugador();
			player.setContrasenha(Pass);
			player.setName(Name);
			player.setPoints(0);
			Players.insert(Name, player);
		}
	}

	public void bluidDecks() {
		GeoMap tool = new GeoMap();
		HTTPPlaces get = new HTTPPlaces();
		ArrayList City = new ArrayList();
		int index = 0;



		while (index != LISTA_CIUDADES.length) {
			Ciudad ciudad = new Ciudad();
			System.out.println(LISTA_CIUDADES[index][2]);
			ciudad.setLatitud(Double.parseDouble(LISTA_CIUDADES[index][1]));
			ciudad.setLongitud(Double.parseDouble(LISTA_CIUDADES[index][2]));
			ciudad.setName(LISTA_CIUDADES[index][0]);
			for (Type t : Type.values()) {
				
				
	
				City.addAll(tool.loadplaces(HTTPPlaces.getplaces(LISTA_CIUDADES[index][1], LISTA_CIUDADES[index][2], t.toString()), t));
			}
			ciudad.setPlaces(City);

			DecC.Add(ciudad);
			index++;
		}
		index = 0;
		while (index != 30) {
			Reto reto = new Reto();
			reto.setTipo(DecR.randomEnum(Type.class));
			reto.setCantidad(new Dado().rollDice());
			DecR.Add(reto);
			index++;
		}

	}

	public void iniciarJuego() {
		int ran = genRandom(0, DecC.getCartas().size());
		System.out.println(DecC.getCartas().size());
		System.out.println(ran);
		Ciudad CiudadJuego = DecC.getCartas().get(ran);

		/*
		 * Ciudad CiudadPrueba = new Ciudad();
		 * /*CiudadPrueba.setLatitud(51.509865);
		 * CiudadPrueba.setLongitud(-0.118092); CiudadPrueba.setName("London");
		 */

		int i = 0;
		ArrayList<Place> po = new ArrayList();
		for (i = 0; i < 1000; i += 60) {
			Place p = new Place();
			p.setLatitud(CiudadJuego.getLatitud() + i / genRandom(10, 50));
			p.setLongitud(CiudadJuego.getLongitud() + i / genRandom(10, 50));
			p.setName("Nombre");
			p.setTipo(Type.park);
			p.setValor(5);
			po.add(p);
			// System.out.println("i vale: " + i);

		}
		CiudadJuego.setPlaces(po);

		Grafo = new Graph(CiudadJuego.getPlaces().size());
		genGrafo(CiudadJuego);

	}

	public void genGrafo(Ciudad CiudadJuego) {
		int indexP = -1; // Primer indice para recorrer los places
		int indexP2 = -1; // Segundo indice para recorrer los places
		double distanciaEntrePlace = 0;
		boolean ExistenAristas = false;
		Place P;
		Place P2;
		int distanciaPermitida = 50;

		while (indexP < CiudadJuego.getPlaces().size() - 1) {
			indexP++;
			indexP2 = -1;
			P = CiudadJuego.getPlaces().get(indexP);
			ExistenAristas = false;
			while (indexP2 < CiudadJuego.getPlaces().size() - 1) {
				indexP2++;
				P2 = CiudadJuego.getPlaces().get(indexP2);
				distanciaEntrePlace = GeoMap.distance(P.getLatitud(), P.getLongitud(), P2.getLatitud(),
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

	private int genRandom(int rangoI, int rangoF) {
		System.out.println("Random generado!");
		return ThreadLocalRandom.current().nextInt(rangoI, rangoF);
	}

	public void logIn(String Name, String Pass) {
		Jugador playerfound = Players.search(Name);
		if (playerfound != null) {
			if (playerfound.getContrasenha() == Pass) {
				if (player1 == null) {
					player1 = playerfound;
				} else {
					player2 = playerfound;

				}
			}
		}

	}

	public Graph getGrafo() {
		return Grafo;
	}

	public void setGrafo(Graph Grafo) {
		this.Grafo = Grafo;
	}

	public static void main(String[] args) throws Exception {
		CityPoly test = new CityPoly();

		test.DecC.Print();
		for (Reto c : test.DecR.getCartas()) {
			System.out.println(c.toString());
		}

		test.iniciarJuego();
		Dijkstra Dij = new Dijkstra(test.getGrafo());
		Dij.dijkstra(0);
		System.out.println(Dij.shortestPath(0).size());

	}
}
