package Deck;

import java.util.ArrayList;
import java.util.Random;

import Control.IConstants;
import Google.HTTPPlaces;
import Mapa.Type;

public class DeckBuilder implements IConstants {
	
	
	public DeckCiudades bluidCityDeck() {
		HTTPPlaces get = new HTTPPlaces();
		ArrayList City = new ArrayList();
		DeckCiudades DecC = new DeckCiudades();
		int index = 0;

		while (index != LISTA_CIUDADES.length) {
			Ciudad ciudad = new Ciudad();
			ciudad.setLatitud(Double.parseDouble(LISTA_CIUDADES[index][1]));
			ciudad.setLongitud(Double.parseDouble(LISTA_CIUDADES[index][2]));
			ciudad.setName(LISTA_CIUDADES[index][0]);

			ciudad.setPlaces(City);

			DecC.Add(ciudad);
			index++;
		}
		return DecC;

	}

	public DeckRetos bluidRetoDeck() {
		DeckRetos DecR = new DeckRetos();

		int index = 0;
		while (index != 30) {
			Reto reto = new Reto();
			reto.setTipo(DecR.randomEnum(Type.class));
			Random r = new Random();
			reto.setDosRetos(r.nextBoolean());
			DecR.Add(reto);
			index++;
		}
		return DecR;
	}
}
