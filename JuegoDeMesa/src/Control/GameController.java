package Control;

import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ThreadLocalRandom;

import Google.HTTPPlaces;
import UI.LoginWindow;
import UI.Ventana;

public class GameController {
	private CityPoly Control;
	private Ventana VentanaJuego;
	private Jugador PlayerActual;

	public GameController(CityPoly ControlB) {
		Control = ControlB;
		VentanaJuego=Control.getGameWindow();
		VentanaJuego.setGame(this);
	}

	public void rankingButton() {

	}

	public void logoffButton() {
		Control.EndGame();
		LoginWindow login = new LoginWindow(Control);

	}

	public void ciudadButton() {
		Control.setCity(Control.getDecC().getRandomCard());
		Control.getCity().getInfo();
		VentanaJuego.PanelMap(Control.getCity().getPictureULR());
		Control.genGrafo();
	}

	public void retoButton() {
		PlayerActual.setReto(Control.getDecR().getRandomCard());
	}

	public void diceButton() {
		int dice = rollDice();
		ArrayList Path = PlayerActual.getCurrentPath();
		int index = 0;
		while (index != dice) {
			Path.remove(0);
		}
	}

	public int rollDice() {
		return ThreadLocalRandom.current().nextInt(1, 7);
	}

}
