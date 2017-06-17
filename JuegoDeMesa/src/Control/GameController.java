package Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ThreadLocalRandom;

import Estructuras.ArchivoSecuencial;
import Estructuras.Dijkstra;
import Estructuras.MergeSort;
import Google.HTTPPlaces;
import UI.LoginWindow;
import UI.RankingUI;
import UI.Ventana;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Deck.DeckBuilder;

public class GameController {
	private CityPoly Control;

	private Ventana VentanaJuego;
	private Jugador PlayerActual;

	public GameController(CityPoly ControlB) {
		Control = ControlB;
		VentanaJuego = Control.getGameWindow();
		VentanaJuego.setGame(this);
		PlayerActual = Control.getPlayer1();
	}

	public void nextTurn() {

		VentanaJuego.swichPlayer();

		if (PlayerActual.getObjetivo().isVisitado()) {
			// Correr el dijsktra otra vez desde la posicion actual

		}
		if (PlayerActual == Control.getPlayer1()) {
			PlayerActual = Control.getPlayer2();

		} else {
			PlayerActual = Control.getPlayer1();
		}
		if (PlayerActual.getReto() != null) {
			VentanaJuego.getTipoLabel().setText("Tipo: " + PlayerActual.getReto().getTipo().toString());
			VentanaJuego.getCantidadLabel().setText("Tipo: " + PlayerActual.getReto().getCantidad());
		}
		if (PlayerActual.getReto() == null) {
			this.VentanaJuego.getRetoB().setEnabled(true);
			VentanaJuego.getRollDice().setEnabled(false);

		} else {
			this.VentanaJuego.getRetoB().setEnabled(false);
			VentanaJuego.getRollDice().setEnabled(true);

		}
		JOptionPane.showMessageDialog(null, "Turno:" + PlayerActual.getName());
		System.out.println("\n" + PlayerActual.getName() + "\n");
		VentanaJuego.PanelMap(Control.getMapWithMarkers());

	}

	public void rankingButton() throws ClassNotFoundException, IOException {
		ArchivoSecuencial rankingread = new ArchivoSecuencial();
		ArrayList<Jugador> rankingplayerlist = rankingread.LeerSecuenciaRanking();
		Object[][] ranking = new Object[rankingplayerlist.size()][];
		int index = 0;
		while (index != ranking.length) {
			Object[] playerdata = new Object[3];
			playerdata[0] = index + 1;
			playerdata[1] = rankingplayerlist.get(index).getName();

			playerdata[2] = rankingplayerlist.get(index).getPoints();
			ranking[index] = playerdata;

		}
		RankingUI UI = new RankingUI(ranking);
	}

	public void logoffButton() throws ClassNotFoundException, IOException {
		Control.EndGame();
		LoginWindow login = new LoginWindow(Control);
		login.setVisible(true);

	}

	public void ciudadButton() {
		Control.setCity(Control.getDecC().getRandomCard());
		Control.getCity().getInfo();
		VentanaJuego.PanelMap(Control.getCity().getPictureULR());
		Control.genGrafo();
		Control.setDijkstra(new Dijkstra(Control.getGrafo()));

		Control.getPlayer1().getCurrentPath()
				.add(ThreadLocalRandom.current().nextInt(1, Control.getCity().getPlaces().size()));
		Control.getPlayer2().getCurrentPath()
				.add(ThreadLocalRandom.current().nextInt(1, Control.getCity().getPlaces().size()));

		VentanaJuego.PanelMap(Control.getMapWithMarkers());
		PlayerActual = Control.getPlayer1();
		DeckBuilder build = new DeckBuilder();
		Control.setDecR(build.bluidRetoDeck(Control.getCity()));
	}

	public void retoButton() {


		PlayerActual.setReto(Control.getDecR().getRandomCard());

		System.out.println(PlayerActual.getReto().toString());
		System.out.println("esta: " + PlayerActual.getCurrentPos());
		PlayerActual.setCurrentPath(this.Control.caminoMasCorto(PlayerActual.getReto().isDosRetos(), PlayerActual));
		System.out.println(PlayerActual.getCurrentPath());

		System.out.println("ahora esta: " + PlayerActual.getCurrentPos());

		PlayerActual.setObjetivo(Control.getCity().getPlaces()
				.get(PlayerActual.getCurrentPath().get(PlayerActual.getCurrentPath().size() - 1)));
		thisTurn(PlayerActual);
		PlayerActual.setRetosCompletos(PlayerActual.getRetosCompletos()+1);
		nextTurn();

	}

	public void diceButton() {

		int dice = rollDice();
		JOptionPane.showMessageDialog(null, dice);

		ArrayList Path = PlayerActual.getCurrentPath();
		int index = 0;
		while (index != dice && Path.size() > 1) {
			Path.remove(0);
			index++;
		}

		if (PlayerActual.getCurrentPath().size() > 1) {
			thisTurn(PlayerActual);
		}
		// Lo que pasa cuando el jugador llega al lugar
		else {

			PlayerActual.setReto(null);
			PlayerActual.addPoints(this.Control.getCity().getPlaces().get(PlayerActual.getCurrentPos()).getValor());
			if(PlayerActual.getRetosCompletos()==3){
				if(PlayerActual.equals(Control.getPlayer1())){
					GameOver(PlayerActual,Control.getPlayer2());
				}else{
					GameOver(PlayerActual,Control.getPlayer1());

				}
			}
		}

		nextTurn();
	}

	private void thisTurn(Jugador Actual) {
		this.actualizarRecorridoUi(Actual);

	}

	private void GameOver(Jugador ganador,Jugador perdedor)  {
		ganador.addPointsLife(Control.getPlayer1().getPoints());
		perdedor.addPointsLife(Control.getPlayer2().getPoints() / 2);
		boolean player1bool = false;
		boolean player2bool = false;
		ArrayList<Jugador> ranking = Control.getRanking();
		int index = 0;
		while (ranking.size() != index) {
			if (ranking.get(index).getName() == ganador.getName()) {
				ranking.get(index).addPointsLife(ganador.getPoints());
				player1bool=true;
			} else if (ranking.get(index).getName() == perdedor.getName()) {
				ranking.get(index).addPointsLife(perdedor.getPoints()/2);
				player2bool = false;
			}
			if(!player1bool){
				ranking.add(ganador);
			}else if(!player2bool){
				ranking.add(perdedor);
			}
			index++;
		}
		MergeSort sort = new MergeSort(ranking);
	
	}

	private void actualizarRecorridoUi(Jugador Actual) {
		int index = 0;
		int indexPlace = 0;
		List<JLabel> recorrido;
		if (this.Control.getPlayer1().equals(Actual)) {
			recorrido = this.VentanaJuego.getP1Move();
		} else {
			recorrido = this.VentanaJuego.getP2Move();
		}
		for (JLabel JL : recorrido) {
			JL.setText("");
		}
		for (JLabel JL : recorrido) {
			if (Actual.getCurrentPath().size() - 1 < index) {
				break;
			}
			indexPlace = Actual.getCurrentPath().get(index);
			JL.setText(this.Control.getCity().getPlaces().get(indexPlace).getName());

			index++;

		}

	}

	public int rollDice() {
		return ThreadLocalRandom.current().nextInt(1, 6);
	}

	public CityPoly getControl() {
		return Control;
	}

	public void setControl(CityPoly control) {
		Control = control;
	}

	public Ventana getVentanaJuego() {
		return VentanaJuego;
	}

	public void setVentanaJuego(Ventana ventanaJuego) {
		VentanaJuego = ventanaJuego;
	}

	public Jugador getPlayerActual() {
		return PlayerActual;
	}

	public void setPlayerActual(Jugador playerActual) {
		PlayerActual = playerActual;
	}
}
