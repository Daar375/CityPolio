package Control;

import Deck.DeckCiudades;
import Deck.DeckRetos;
import Deck.Reto;
import Deck.*;
import Estructuras.*;
import Google.HTTPPlaces;
import Mapa.Place;
import Mapa.Type;
import Tools.DistanceCalc;
import UI.LoginWindow;
import UI.Ventana;

import java.io.IOException;
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
	private Ventana GameWindow;
	private Dijkstra dijkstraplayer1 ;
	private Dijkstra dijkstraplayer2 ;


        /**
         * Constructor
         */
	public CityPoly() {
		GameWindow = new Ventana();
		Game = new GameController(this);
		GameWindow.setVisible(false);
		DeckBuilder build = new DeckBuilder();
		DecC = build.bluidCityDeck();
		DecR = build.bluidRetoDeck();

	}

	/**
         * Inicia la ventana de Login
	 * @throws IOException 
	 * @throws ClassNotFoundException 
         */
	public void iniciarUi() throws ClassNotFoundException, IOException {
		LoginWindow UI = new LoginWindow(this);
		UI.setVisible(true);
	}

        /**
         * Termina el juego y pone en NULL todo lo utilizado en el juego pasado.
         */
	public void EndGame() {
		player1 = null;
		player2 = null;
		DecR = null;
		City = null;
		Grafo = null;

	}

	public void SaveTree() throws IOException{
		LeafNode Inicio = Players.getFirst();
		
		while(Inicio.HasNextLeaf()){
			
			ArrayList<Jugador> PlayersInNode = Inicio.getValues();
			for(Jugador player:PlayersInNode){
				ArchivoSecuencial save = new ArchivoSecuencial();
				save.EscribirSecuancialUsuarios(player);
			}
			Inicio=Inicio.getNextLeaf();

		}
	}
	
	
        /**
         * Crea un nuevo jugador
         * @param Name
         * @param Pass
         * @return 
         */
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

        /**
         * Basado en la cantidad de vertices del grafo, revisa la lista de lugares
         * en la ciudad y va armando el grafo segun sus distancias, contando
         * como distancia minima los que estan a 50m.
         */
	public void genGrafo() {
		System.out.println(City.getPlaces().size() + " lugares en esta ciudad.");
		Grafo = new Graph(City.getPlaces().size());
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
				if (!(indexP == indexP2)) {
					P2 = City.getPlaces().get(indexP2);
					distanciaEntrePlace = DistanceCalc.distance(P.getLatitud(), P.getLongitud(), P2.getLatitud(),
							P2.getLongitud());

					if (distanciaEntrePlace < distanciaPermitida) {
						System.out.println((int) distanciaEntrePlace + " es la distancia entre el vertice " + indexP + " y "+ indexP2);
						Grafo.addEdge(indexP, indexP2, (int) distanciaEntrePlace, false);
						ExistenAristas = true;
					}
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

        /**
         * Busca el indice del Place del tipo buscado mas cercano a la posicion 
         * actual
         * @param CurrentPos
         * @param TipoBuscado
         * @return 
         */
        private int searchNear(int CurrentPos, Type TipoBuscado){
            int index = 0;
            int EncontradoMejor = CurrentPos;
            int DistanciaMejor = 999999; 
            int DistanciActual= 0;
            for(Place lugar : City.getPlaces()){
                index++;
                if(lugar.getTipo().equals(TipoBuscado)){
                    DistanciActual = (int) DistanceCalc.distance(
                            City.getPlaces().get(CurrentPos).getLatitud(),
                            City.getPlaces().get(CurrentPos).getLongitud(),
                            City.getPlaces().get(index).getLatitud(),
                            City.getPlaces().get(index).getLongitud());
                    
                    if(DistanciActual < DistanciaMejor){
                        if(!City.getPlaces().get(index).isVisitado()){
                            DistanciaMejor = DistanciActual;
                            EncontradoMejor = index;
                        }
                        
                    }
                }
                
            }
            return EncontradoMejor;
        }
        
        /**
         * Recibe al jugador en juego, y si son dos retos o uno y devuelve el camino
         * mas corto que hay que recorrer para cubrir un reto o ambos
         * @param dosRetos
         * @param JugadorActual
         * @return 
         */
        public ArrayList<Integer> caminoMasCorto(boolean dosRetos, Jugador JugadorActual){
            ArrayList<Integer> CaminoAAgregar;
            this.dijkstraplayer1.dijkstra(JugadorActual.getCurrentPos());
            int Cercano = this.searchNear(JugadorActual.getCurrentPos(), JugadorActual.getReto().getTipo());
            CaminoAAgregar = this.dijkstraplayer1.shortestPath(Cercano);
            if(dosRetos){ // Si son dos lugares en el reto
                this.dijkstraplayer1.dijkstra(Cercano);
                // Buscamos el mass cercano desde el ultimo lugar hasta el segundo lugar
                Cercano = this.searchNear(Cercano, JugadorActual.getReto().getTipo());
                // Eliminar el ultimo elemento ya que se repite al agregarle el segundo camino
                CaminoAAgregar.remove(CaminoAAgregar.size()-1); 
                // Le agregamos al camino que ya tenemos, el nuevo que va desde
                // el ultimo encontrado hasta el siguiente luar del reto
                CaminoAAgregar.addAll(this.dijkstraplayer1.shortestPath(Cercano));
                
            }
            
            
            return CaminoAAgregar;

        }


        /**
         * Genera un int random en el rango indicado
         * @param rangoI
         * @param rangoF
         * @return 
         */
	public int genRandom(int rangoI, int rangoF) {
		System.out.println("Random generado!");
		return ThreadLocalRandom.current().nextInt(rangoI, rangoF);
	}

        /**
         * Recibe username y password del usuario para comprobar si existen
         * @param Name
         * @param Pass
         * @return True if exist, else False.
         */
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
        
        
	public Dijkstra getDijkstraplayer1() {
		return dijkstraplayer1;
	}

	public void setDijkstraplayer1(Dijkstra dijkstraplayer1) {
		this.dijkstraplayer1 = dijkstraplayer1;
	}

	public Dijkstra getDijkstraplayer2() {
		return dijkstraplayer2;
	}

	public void setDijkstraplayer2(Dijkstra dijkstraplayer2) {
		this.dijkstraplayer2 = dijkstraplayer2;
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

}
