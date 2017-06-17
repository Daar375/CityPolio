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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

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
	private Dijkstra Dijkstra ;
	private ArrayList<Jugador> Ranking;
	public BufferedImage getMapWithMarkers() {
		String Player1Markerlat=Double.toString(City.getPlaces().get(player1.getCurrentPos()).getLatitud());
		String Player1Markerlong=Double.toString(City.getPlaces().get(player1.getCurrentPos()).getLongitud());
		String Player2Markerlat=Double.toString(City.getPlaces().get(player2.getCurrentPos()).getLatitud());
		String Player2Markerlong=Double.toString(City.getPlaces().get(player2.getCurrentPos()).getLongitud());
		return HTTPPlaces.getmap(LISTA_CIUDADES[City.getCityNumber()][1], LISTA_CIUDADES[City.getCityNumber()][2],Player1Markerlat,Player1Markerlong,Player2Markerlat,Player2Markerlong);

	}
        /**
         * Constructor
         * @throws IOException 
         * @throws ClassNotFoundException 
         */
	public CityPoly(){
		GameWindow = new Ventana();
		Game = new GameController(this);
		GameWindow.setVisible(false);
		DeckBuilder build = new DeckBuilder();
		DecC = build.bluidCityDeck();
		ArchivoSecuencial rankingread = new ArchivoSecuencial();
		try {
			Ranking = rankingread.LeerSecuenciaRanking();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Jugador> getRanking() {
			return Ranking;
		}
		public void setRanking(ArrayList<Jugador> ranking) {
			Ranking = ranking;
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
		ArrayList<Jugador> PlayersInNode = Inicio.getValues();
		SAVEFILE.delete();
		SAVEFILE.createNewFile();
		ArchivoSecuencial save = new ArchivoSecuencial();

		for(Jugador player:PlayersInNode){
			save.EscribirSecuancialUsuarios(player);
		}
		while(Inicio.HasNextLeaf()){
			
			PlayersInNode = Inicio.getValues();
			for(Jugador player:PlayersInNode){
				save.EscribirSecuancialUsuarios(player);
			}
			Inicio=Inicio.getNextLeaf();

		}
	}
	public void SaveRanking() throws IOException{
		RANKINGFILE.delete();
		RANKINGFILE.createNewFile();
		ArchivoSecuencial save = new ArchivoSecuencial();
		MergeSort sort = new MergeSort(Ranking);
		sort.sortGivenArray();
		Ranking=sort.getSortedArray();
		int index = 0;
		while(index!=Ranking.size()){
			
			save.EscribirSecuancialRanking(Ranking.get(index).getName(),  Integer.toString(Ranking.get(index).getPointsLife()));
			index++;
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
						Grafo.addEdge(indexP, indexP2, (int) distanciaEntrePlace, false);
						ExistenAristas = true;
					}
				}

			}
			// Si busco y no encontro places cerca para agregarse como vecinos
			// al grafo, hace la busqueda otra vez pero con 50mts mas
			if (!ExistenAristas) {
				distanciaPermitida += 50;
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
        int DistanciActual= 999999;
        for(Place lugar : City.getPlaces()){
            
            if(lugar.getTipo().equals(TipoBuscado)){
                DistanciActual = (int) DistanceCalc.distance(
                        City.getPlaces().get(CurrentPos).getLatitud(),
                        City.getPlaces().get(CurrentPos).getLongitud(),
                        City.getPlaces().get(index).getLatitud(),
                        City.getPlaces().get(index).getLongitud());
                
                if(DistanciActual < DistanciaMejor && CurrentPos != index&& DistanciaMejor>1){
                    if(!City.getPlaces().get(index).isVisitado()){
                        DistanciaMejor = DistanciActual;
                        EncontradoMejor = index;
                    }
                    
                }
            }
            index++;
            
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
            this.Dijkstra.dijkstra(JugadorActual.getCurrentPos());
            int lugaruno = this.searchNear(JugadorActual.getCurrentPos(), JugadorActual.getReto().getTipo());
            int SegundoLugar=0;
            CaminoAAgregar = this.Dijkstra.shortestPath(lugaruno);
            if(dosRetos){ // Si son dos lugares en el reto
                this.Dijkstra.dijkstra(lugaruno);
                // Buscamos el mass cercano desde el ultimo lugar hasta el segundo lugar
                SegundoLugar = this.searchNear(lugaruno, JugadorActual.getReto().getTipo());
                // Eliminar el ultimo elemento ya que se repite al agregarle el segundo camino
                CaminoAAgregar.remove(CaminoAAgregar.size()-1); 
                // Le agregamos al camino que ya tenemos, el nuevo que va desde
                // el ultimo encontrado hasta el siguiente luar del reto
                CaminoAAgregar.addAll(this.Dijkstra.shortestPath(SegundoLugar));
            	JugadorActual.getObjetivo().add(City.getPlaces().get(lugaruno));
            	JugadorActual.getObjetivo().add(City.getPlaces().get(CaminoAAgregar.get(CaminoAAgregar.size()-1)));

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
					JOptionPane.showMessageDialog(null, "Player 1 Logged, Log Player 2");

					player1 = playerfound;
				} else {
					JOptionPane.showMessageDialog(null, "Player 2 Logged");

					player2 = playerfound;

				}
				return true;

			}
		}
		return false;

	}
        
        
	public Dijkstra getDijkstra() {
		return Dijkstra;
	}

	public void setDijkstra(Dijkstra Dijkstra) {
		this.Dijkstra = Dijkstra;
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
