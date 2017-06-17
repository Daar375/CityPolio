package Deck;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Control.IConstants;
import Google.HTTPPlaces;
import Mapa.Type;

public class DeckBuilder implements IConstants {
	
	
	public DeckCiudades bluidCityDeck() {
		HTTPPlaces get = new HTTPPlaces();
		DeckCiudades DecC = new DeckCiudades();
		int index = 0;

		while (index != LISTA_CIUDADES.length) {
			Ciudad ciudad = new Ciudad();
			ciudad.setLatitud(Double.parseDouble(LISTA_CIUDADES[index][1]));
			ciudad.setLongitud(Double.parseDouble(LISTA_CIUDADES[index][2]));
			ciudad.setName(LISTA_CIUDADES[index][0]);
			ciudad.setCityNumber(index);
			DecC.Add(ciudad);
			index++;
		}
		return DecC;

	}

	public DeckRetos bluidRetoDeck(Ciudad city) {
		DeckRetos DecR = new DeckRetos();
		ArrayList<Type> permitidos = city.TiposPermitidos();
		int index = 0;
		while (index != 30) {
			System.out.println(permitidos);
			int randomnumber = 	ThreadLocalRandom.current().nextInt(0, permitidos.size());
			
			
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
