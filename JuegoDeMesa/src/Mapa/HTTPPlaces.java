package Mapa;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import Control.IConstants;

public class HTTPPlaces implements IConstants {


	public static String getPlaces(String X, String Y, String tipo) {
		String result = null;

		HttpClient httpclient = HttpClients.createDefault();
		try {
			URIBuilder builder = new URIBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
					+ X + "," + Y + "&radius=" + 500 + "&type=" + tipo + "&key=" + MCS_IDKEY);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "Error";
	}

	public static BufferedImage getMap(String X, String Y,String Player1Markerlat,String Player1Markerlong,String Player2Markerlat,String Player2Markerlong) {
		String result = null;

		HttpClient httpclient = HttpClients.createDefault();
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + X + "," + Y+"&markers=color:blue%7Clabel:P1%7C"+Player1Markerlat+","+Player1Markerlong+
					"&markers=color:red%7Clabel:P2%7C"+Player2Markerlat+","+Player2Markerlong +"&zoom=15&size=1500x1500&key=" + MCS_IDKEY);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedImage image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;

	}
	
}
