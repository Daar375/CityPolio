package Deck;

import java.util.ArrayList;

import Mapa.Place;

public class Cuidad {
	private double latitud;
	private double longitud;
	private String Name;
	private ArrayList<Place> Restaurantes;
	private ArrayList<Place> Iglecias;
	private ArrayList<Place> Parques;

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

	public void setName(String name) {
		Name = name;
	}

	public ArrayList<Place> getRestaurantes() {
		return Restaurantes;
	}

	public void setRestaurantes(ArrayList<Place> restaurantes) {
		Restaurantes = restaurantes;
	}

	public ArrayList<Place> getIglecias() {
		return Iglecias;
	}

	public void setIglecias(ArrayList<Place> iglecias) {
		Iglecias = iglecias;
	}

	public ArrayList<Place> getParques() {
		return Parques;
	}

	public void setParques(ArrayList<Place> parques) {
		Parques = parques;
	}
}
