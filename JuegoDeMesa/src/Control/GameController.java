package Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ThreadLocalRandom;

import Estructuras.Dijkstra;
import Google.HTTPPlaces;
import UI.LoginWindow;
import UI.Ventana;

public class GameController {
	private CityPoly Control;


	private Ventana VentanaJuego;
	private Jugador PlayerActual ;

	public GameController(CityPoly ControlB) {
		Control = ControlB;
		VentanaJuego=Control.getGameWindow();
		VentanaJuego.setGame(this);
		PlayerActual=Control.getPlayer1();
	}

	
	public void nextTurn(){
		if(PlayerActual==Control.getPlayer1()){
			PlayerActual=Control.getPlayer2();
		}else{
			PlayerActual=Control.getPlayer1();
		}
	}
	public void rankingButton() {

	}

	public void logoffButton() throws ClassNotFoundException, IOException {
		Control.EndGame();
		LoginWindow login = new LoginWindow(Control);

	}

	public void ciudadButton() {
		Control.setCity(Control.getDecC().getRandomCard());
		Control.getCity().getInfo();
		VentanaJuego.PanelMap(Control.getCity().getPictureULR());
		Control.genGrafo();
		Control.setDijkstraplayer1(new Dijkstra(Control.getGrafo()));
		Control.setDijkstraplayer2(new Dijkstra(Control.getGrafo()));

		Control.getPlayer1().getCurrentPath().add(ThreadLocalRandom.current().nextInt(1, Control.getCity().getPlaces().size()));
		Control.getPlayer2().getCurrentPath().add(ThreadLocalRandom.current().nextInt(1, Control.getCity().getPlaces().size()));

		Control.getDijkstraplayer1().dijkstra(Control.getPlayer1().getCurrentPos());
		Control.getDijkstraplayer2().dijkstra(Control.getPlayer2().getCurrentPos());

	}

	public void retoButton() {
		PlayerActual.setReto(Control.getDecR().getRandomCard());
		PlayerActual.setCurrentPath(Control.getDijkstraplayer1().shortestPath(0));
		if(PlayerActual==Control.getPlayer1()){
		}else{
			PlayerActual.setCurrentPath(Control.getDijkstraplayer2().shortestPath(0));

		}
		nextTurn();
	}

	public void diceButton() {
		int dice = rollDice();
		ArrayList Path = PlayerActual.getCurrentPath();
		int index = 0;
		while (index != dice) {
			Path.remove(0);
		}
		nextTurn();
	}

	public int rollDice() {
		return ThreadLocalRandom.current().nextInt(1, 7);
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
