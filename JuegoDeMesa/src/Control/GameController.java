package Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ThreadLocalRandom;

import Estructuras.ArchivoSecuencial;
import Estructuras.Dijkstra;
import Estructuras.MergeSort;
import Mapa.HTTPPlaces;
import UI.LoginWindow;
import UI.RankingUI;
import UI.Ventana;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Deck.DeckBuilder;
import GraphGist.CypherGraph;
import Herramientas.Tools;

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

    /**
     * Actualiza en la UI todos los valores necesarios para que el usuario
     * identifique el jugador actual.
     */
    public void actualizarUiPlayer() {
        if (PlayerActual.getReto() != null) {
            VentanaJuego.getTipoLabel().setText("Tipo: " + PlayerActual.getReto().getTipo().toString());
            VentanaJuego.getCantidadLabel().setText("<html>Visitar: " + PlayerActual.getReto().getCantidad() + "  Retos Completos: " + PlayerActual.getRetosCompletos() + "/3</html>");
        }
        if (PlayerActual.getReto() == null) {
            this.VentanaJuego.getRetoB().setEnabled(true);
            VentanaJuego.getRollDice().setEnabled(false);

        } else {
            this.VentanaJuego.getRetoB().setEnabled(false);
            VentanaJuego.getRollDice().setEnabled(true);

        }
        VentanaJuego.setPuntaje(Control.getPlayer1().getPoints(), Control.getPlayer1().getPointsLife(), Control.getPlayer2().getPoints(), Control.getPlayer2().getPointsLife());
        if (PlayerActual.getObjetivo().size() != 0) {
            VentanaJuego.setPlaceLabel(PlayerActual.GetObjetivoInfo());
            VentanaJuego.PanelObjetive(PlayerActual.getObjetivo().get(0).getIcon());
        }
        JOptionPane.showMessageDialog(null, "Turno:" + PlayerActual.getName());
        VentanaJuego.PanelMap(Control.getMapWithMarkers());
    }

    /**
     * Cambia de turno y actualiza la Ui
     */
    public void nextTurn() {

        VentanaJuego.swichPlayer();
        if (PlayerActual == Control.getPlayer1()) {
            PlayerActual = Control.getPlayer2();

        } else {
            PlayerActual = Control.getPlayer1();
        }

        actualizarUiPlayer();

    }

    /**
     * Muestra el ranking con la informacion de los jugadores en el ranking
     *
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void rankingButton() throws ClassNotFoundException, IOException {
        ArchivoSecuencial rankingread = new ArchivoSecuencial();
        ArrayList<Jugador> rankingplayerlist = rankingread.leerSecuencialRanking();
        Object[][] ranking = new Object[rankingplayerlist.size()][];
        int index = 0;
        while (index != ranking.length) {
            Object[] playerdata = new Object[3];
            playerdata[0] = rankingplayerlist.get(index).getName();

            playerdata[1] = rankingplayerlist.get(index).getPointsLife();
            playerdata[2] = index + 1;
            ranking[index] = playerdata;
            index++;
        }
        RankingUI UI = new RankingUI(ranking);
        UI.setVisible(true);
    }

    /**
     * Termina el juego y bre la ventana de logIn
     *
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void logoffButton() throws ClassNotFoundException, IOException {
        Control.EndGame();
        LoginWindow login = new LoginWindow(Control);
        login.setVisible(true);

    }

    /**
     * Busca la ciudad a jugar, genera el grafo y crea las cartas de los Places
     * y los retos.
     */
    public void ciudadButton() {
        VentanaJuego.swichPlayer();

        Control.setCity(Control.getDecC().getRandomCard());
        Control.getCity().getInfo();
        VentanaJuego.PanelMap(Control.getCity().getPictureULR());
        VentanaJuego.setCiudadLabel(Control.getCity().getName());
        Control.genGrafo();
        Control.setDijkstra(new Dijkstra(Control.getGrafo()));

        Control.getPlayer1().getCurrentPath()
                .add(Tools.genRandom(1, Control.getCity().getPlaces().size()));
        Control.getPlayer2().getCurrentPath()
                .add(Tools.genRandom(1, Control.getCity().getPlaces().size()));

        VentanaJuego.PanelMap(Control.getMapWithMarkers());
        PlayerActual = Control.getPlayer1();
        DeckBuilder build = new DeckBuilder();
        Control.setDecR(build.buildRetoDeck(Control.getCity()));
         
    }

    /**
     * Le da al jugador actual una nueva carta de reto
     */
    public void retoButton() {

        PlayerActual.setReto(Control.getDecR().getRandomCard());

        PlayerActual.setCurrentPath(this.Control.caminoMasCorto(PlayerActual.getReto().isDosRetos(), PlayerActual));

        actualizarRecorridoUi(PlayerActual);
        nextTurn();
        

    }

    /**
     * Tira el dado y mueve el jugador en pantalla dependiendo de la tirada.
     */
    public void diceButton() {

        int dice = rollDice();
        JOptionPane.showMessageDialog(null, "Jugador saco: " + dice);

        ArrayList Path = PlayerActual.getCurrentPath();
        int index = 0;
        while (index != dice && Path.size() > 1) {
            Path.remove(0);
            //actualizarUiPlayer();
            /**
             * ***************************************
             */
            actualizarRecorridoUi(PlayerActual);
            index++;
        }

        //if (PlayerActual.getCurrentPath().size() > 1) {
        //	actualizarRecorridoUi(PlayerActual);
        //}
        // Lo que pasa cuando el jugador llega al lugar
        if (PlayerActual.getCurrentPath().size() <= 1) {
            ifPlayerArrive();
        }

        nextTurn();
    }

    /**
     * Si el jugador llega al destino que se dirigia configurado por el reto.
     */
    public void ifPlayerArrive() {
        Control.getCity().getPlaces().get(PlayerActual.getCurrentPos()).setVisitado(true);
        PlayerActual.setReto(null);
        PlayerActual.setObjetivo(new ArrayList<Mapa.Place>());
        PlayerActual.addPoints(Control.getCity().getPlaces().get(PlayerActual.getCurrentPos()).getValor());
        PlayerActual.setRetosCompletos(PlayerActual.getRetosCompletos() + 1);
        System.out.println(PlayerActual.getName());
        if (PlayerActual.getRetosCompletos() > 3) {
            if (PlayerActual.equals(Control.getPlayer1())) {

                PlayerActual.addPointsLife(Control.getPlayer1().getPoints());
                Control.getPlayer2().addPointsLife(Control.getPlayer2().getPoints() / 2);
                GameOver(PlayerActual, Control.getPlayer2());

            } else {
                PlayerActual.addPointsLife(Control.getPlayer2().getPoints());
                Control.getPlayer2().addPointsLife(Control.getPlayer1().getPoints() / 2);
                GameOver(PlayerActual, Control.getPlayer1());

            }
            Control.getPlayer1().setRetosCompletos(-1);
            Control.getPlayer2().setRetosCompletos(-1);
            return;
        }
    }

    /**
     * Termina el juego, suma los puntos a ambos jugadores, actualiza el
     * ranking, los valores de los jugadores e inicia LogIn para una nueva
     * partida.
     *
     * @param ganador
     * @param perdedor
     */
    private void GameOver(Jugador ganador, Jugador perdedor) {
        JOptionPane.showMessageDialog(null, "Gano " + ganador.getName() + " con " + ganador.getPoints() + " puntos");

        boolean player1bool = false;
        boolean player2bool = false;
        ArrayList<Jugador> ranking = Control.getRanking();
        int index = 0;
        while (ranking.size() != index) {
            if (ranking.get(index).getName().equals(ganador.getName())) {
                ranking.get(index).addPointsLife(ganador.getPoints());
                player1bool = true;
            } else if (ranking.get(index).getName().equals(perdedor.getName())) {
                ranking.get(index).addPointsLife(perdedor.getPoints() / 2);
                player2bool = true;
            }
            index++;
        }
        if (!player1bool) {
            ranking.add(ganador);
        }
        if (!player2bool) {
            ranking.add(perdedor);
        }

        @SuppressWarnings("unused")
        MergeSort sort = new MergeSort(ranking);
        Control.setRanking(sort.getSortedArray());
        try {
            Control.SaveTree();
            Control.SaveRanking();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CityPoly control = new CityPoly();
        try {
            control.iniciarUi();
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        VentanaJuego.dispose();
    }

    /**
     * Actualiza el recorrido en pantalla
     *
     * @param Actual
     */
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
        return Tools.genRandom(1, 7);
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
