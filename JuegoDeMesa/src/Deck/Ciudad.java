package Deck;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import Control.IConstants;
import Google.HTTPPlaces;
import Mapa.Place;
import Mapa.Type;

public class Ciudad implements IConstants {
	private double latitud;
	private double longitud;
	private int CityNumber;
	private String Name;
	private ArrayList<Place> Places = new ArrayList();
	private BufferedImage PictureULR;

	public void getInfo() {
		PictureULR = HTTPPlaces.getmap(LISTA_CIUDADES[CityNumber][1], LISTA_CIUDADES[CityNumber][2], "0", "0", "0",
				"0");
		for (Type tipo : Type.values()) {

			loadplaces(
					HTTPPlaces.getplaces(LISTA_CIUDADES[CityNumber][1], LISTA_CIUDADES[CityNumber][2], tipo.toString()),
					tipo);
		}
	}

	public BufferedImage getPictureULR() {

		return PictureULR;
	}

	public void setPictureULR(BufferedImage pictureULR) {
		PictureULR = pictureULR;
	}

	public int getCityNumber() {
		return CityNumber;
	}

	public void setCityNumber(int cityNumber) {
		CityNumber = cityNumber;
	}

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

	@Override
	public String toString() {
		return Name + ": Cantidad de Places: " + Places.size();
	}

	public ArrayList TiposPermitidos() {
		ArrayList res = new ArrayList();
		for (Type tipe : Type.values()) {
			int index = 0;
			int cantidad=0;
			while (index != Places.size()) {
				if(Places.get(index).getTipo()==tipe){
					cantidad++;
				}
				index++;
			}
			if(cantidad>4){
				res.add(tipe);
			}
		}

		return res;

	}

	private void loadplaces(String JsonString, Type tipe) {
		JSONObject placesjson = new JSONObject(JsonString);
		JSONArray Array = placesjson.getJSONArray("results");
		int index = 0;
		ArrayList lugares = new ArrayList();

		while (index != Array.length()) {
			Place place = new Place();
			place.setName((String) Array.getJSONObject(index).get("name"));
			place.setLatitud(
					(double) Array.getJSONObject(index).getJSONObject("geometry").getJSONObject("location").get("lat"));
			place.setLongitud(
					(double) Array.getJSONObject(index).getJSONObject("geometry").getJSONObject("location").get("lng"));
			place.setIcon(Array.getJSONObject(index).getString("icon"));
			place.setTipo(tipe);
			if (Array.getJSONObject(index).has("rating")) {
				Object tempdouble = Array.getJSONObject(index).get("rating");
				if (tempdouble.getClass() == Double.class) {
					Double d = (Double) tempdouble;
					place.setValor(d.intValue());

				} else {
					place.setValor((int) tempdouble);

				}

			} else {
				place.setValor(ThreadLocalRandom.current().nextInt(0, 6));
			}
			lugares.add(place);
			index++;
		}
		System.out.println(tipe.toString() + " " + lugares.size());
		Places.addAll(lugares);
	}
}
