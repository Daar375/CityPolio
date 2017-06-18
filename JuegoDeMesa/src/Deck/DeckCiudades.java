package Deck;

import Herramientas.Tools;
import java.util.ArrayList;

public class DeckCiudades extends Deck {

    private ArrayList<Ciudad> Cartas = new ArrayList();

    public ArrayList<Ciudad> getCartas() {
        return Cartas;
    }

    public Ciudad getRandomCard() {
        return Cartas.get(Tools.genRandom(0, Cartas.size()));
    }

    public void setCartas(ArrayList<Ciudad> cartas) {
        Cartas = cartas;
    }

    public void add(Ciudad city) {

        Cartas.add(city);
    }

    public void addAll(ArrayList<Ciudad> adding) {
        Cartas.addAll(adding);
    }

    public void print() {
        System.out.println(Cartas.toString());
    }
}
