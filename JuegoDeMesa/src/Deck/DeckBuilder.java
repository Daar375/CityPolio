package Deck;

import java.util.ArrayList;
import java.util.Random;

import Control.IConstants;
import Mapa.HTTPPlaces;
import Herramientas.Tools;
import Mapa.Type;

public class DeckBuilder implements IConstants {

    /**
     * Construye el Deck de ciudades basado en la lista de ciudades en
     * IConstants.
     *
     * @return
     */
    public DeckCiudades buildCityDeck() {
        HTTPPlaces get = new HTTPPlaces();
        DeckCiudades DecC = new DeckCiudades();
        int index = 0;

        while (index != LISTA_CIUDADES.length) {
            Ciudad ciudad = new Ciudad();
            ciudad.setLatitud(Double.parseDouble(LISTA_CIUDADES[index][1]));
            ciudad.setLongitud(Double.parseDouble(LISTA_CIUDADES[index][2]));
            ciudad.setName(LISTA_CIUDADES[index][0]);
            ciudad.setCityNumber(index);
            DecC.add(ciudad);
            index++;
        }
        return DecC;

    }

    /**
     * Construye el Deck de REtos basado en la ciudad elegida.
     *
     * @param city
     * @return
     */
    public DeckRetos buildRetoDeck(Ciudad city) {
        DeckRetos DecR = new DeckRetos();
        ArrayList<Type> permitidos = city.TiposPermitidos();
        
        if(permitidos.isEmpty()){
            System.out.println("El lugar no tiene suficientes places, elija otra ciudad");
        }
        
        int index = 0;
        while (index != 30) {
            //System.out.println(permitidos);
            int randomnumber = Tools.genRandom(0, permitidos.size());

            Reto reto = new Reto();
            reto.setTipo(permitidos.get(randomnumber));
            Random r = new Random();
            reto.setDosRetos(r.nextBoolean());
            DecR.Add(reto);
            index++;
        }
        return DecR;
    }
}
