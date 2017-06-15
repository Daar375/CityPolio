package Deck;

import java.util.ArrayList;

import Mapa.Place;

public class Ciudades {
	private double latitud;
	private double longitud;
	private String Name;
	private ArrayList<Place> Places;


	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getName() {
		return Name;
	}

	public ArrayList<Place> getPlaces() {
		return Places;
	}

	public void setPlaces(ArrayList<Place> places) {
		Places = places;
	}

	public void setName(String name) {
		Name = name;
	}

	
}
