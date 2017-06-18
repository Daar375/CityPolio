package Deck;

import Herramientas.Tools;
import java.util.ArrayList;
import java.util.Random;

public class DeckRetos extends Deck {

    private ArrayList<Reto> Cartas = new ArrayList();

    public ArrayList<Reto> getCartas() {
        return Cartas;
    }

    public Reto getRandomCard() {
        Reto reto = Cartas.get(Tools.genRandom(0, Cartas.size()));
        System.out.println(reto.getTipo());

        return reto;
    }

    public void setCartas(ArrayList<Reto> cartas) {
        Cartas = cartas;
    }

    public void Add(Reto Carta) {
        Cartas.add(Carta);

    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
