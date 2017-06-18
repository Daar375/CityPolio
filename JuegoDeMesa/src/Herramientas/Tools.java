package Herramientas;

import java.util.concurrent.ThreadLocalRandom;

public class Tools {
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))	+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		dist = dist *1000;	
		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
        
        /**
         * Genera un int random en el rango indicado
         * @param rangoI
         * @param rangoF
         * @return 
         */
	public static int genRandom(int rangoI, int rangoF) {
		return ThreadLocalRandom.current().nextInt(rangoI, rangoF);
	}

}
