package Deck;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import Mapa.Place;
import Mapa.Type;

public class Ciudad {
	private double latitud;
	private double longitud;
	private String Name;
	private ArrayList<Place> Places;
	private BufferedImage PictureULR;

	public void loadplaces(String JsonString, Type tipe) {
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
				if(tempdouble.getClass()==Double.class){
					Double d = (Double) tempdouble;
					place.setValor(d.intValue());

				}else{
					place.setValor((int) tempdouble);

				}
				

			}
			else{
				place.setValor(ThreadLocalRandom.current().nextInt(0, 6));
			}
			lugares.add(place);
			index++;
		}
		Places.addAll(lugares);
	}
	
	public BufferedImage getPictureULR() {
		return PictureULR;
	}

	public void setPictureULR(BufferedImage pictureULR) {
		PictureULR = pictureULR;
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
        public String toString(){
            return Name + ": Cantidad de Places: " + Places.size();
        }

	
}
