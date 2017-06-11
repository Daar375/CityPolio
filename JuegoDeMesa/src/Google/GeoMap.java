package Google;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import Mapa.Place;
import Mapa.Type;

public class GeoMap {


	public ArrayList loadplaces(String JsonString, Type tipe) {
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
		return lugares;
	}

	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))	+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
