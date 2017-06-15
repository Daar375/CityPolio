package Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ThreadLocalRandom;

import Estructuras.ArchivoSecuencial;
import Estructuras.Dijkstra;
import Google.HTTPPlaces;
import UI.LoginWindow;
import UI.RankingUI;
import UI.Ventana;
import java.util.List;
import javax.swing.JLabel;

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
                if(PlayerActual.getReto()==null){
                    this.VentanaJuego.getRetoB().setEnabled(true);}
                    else{this.VentanaJuego.getRetoB().setEnabled(false);}
                if(PlayerActual.getObjetivo().isVisitado()){
                    // Correr el dijsktra otra vez desde la posicion actual
                    
                }
                
                
	}
	public void rankingButton() throws ClassNotFoundException, IOException {
		ArchivoSecuencial rankingread = new ArchivoSecuencial();
		ArrayList<Jugador> rankingplayerlist = rankingread.LeerSecuenciaRanking();
		Object[][] ranking = new Object[rankingplayerlist.size()][];
		int index=0;
		while(index!=ranking.length){
			Object[] playerdata = new Object[3];
			playerdata[0]=index+1;
			playerdata[1]=rankingplayerlist.get(index).getName();

			playerdata[2]=rankingplayerlist.get(index).getPoints();
			ranking[index]=playerdata;

		}
		RankingUI UI= new RankingUI(ranking);
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

		Control.getPlayer1().getCurrentPath().add(ThreadLocalRandom.current().nextInt(1, Control.getCity().getPlaces().size()));
		Control.getPlayer2().getCurrentPath().add(ThreadLocalRandom.current().nextInt(1, Control.getCity().getPlaces().size()));

		VentanaJuego.PanelMap(Control.getMapWithMarkers());

	}

	public void retoButton() {
		PlayerActual.setReto(Control.getDecR().getRandomCard());
                
		PlayerActual.setCurrentPath(this.Control.caminoMasCorto(PlayerActual.getReto().isDosRetos(), PlayerActual));
		
		nextTurn();
	}

	public void diceButton() {
                
		int dice = rollDice();
		ArrayList Path = PlayerActual.getCurrentPath();
		int index = 0;
		while (index != dice && Path.size() > 1) {
			Path.remove(0);
		}
                
                if(PlayerActual.getCurrentPath().size() > 1){
                    thisTurn(PlayerActual); 
                }
                // Lo que pasa cuando el jugador llega al lugar
                else{
                    
                    PlayerActual.setReto(null);
                    PlayerActual.addPoints(this.Control.getCity().getPlaces().get(PlayerActual.getCurrentPos()).getValor());
                    
                }
               
		nextTurn();
	}
        
        private void thisTurn(Jugador Actual){
            this.actualizarRecorridoUi(Actual);
            
            
        }
        
        private void actualizarRecorridoUi(Jugador Actual){
            int index = -1;
            int indexPlace = 0;
            List<JLabel> recorrido;
            if(this.Control.getPlayer1().equals(Actual)){
                recorrido = this.VentanaJuego.getP1Move();}
            else{recorrido = this.VentanaJuego.getP2Move();}
            
            for(JLabel JL : recorrido){
                index++;
                indexPlace = Actual.getCurrentPath().get(index);
                JL.setText(this.Control.getCity().getPlaces().get(indexPlace).getName()); 
            }
            
            
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
