package Google;

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

	public static String getplaces(String X, String Y, String tipo) {
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

	public static BufferedImage getmap(String X, String Y) {
		String result = null;

		HttpClient httpclient = HttpClients.createDefault();
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + X + "," + Y
					+ "&zoom=17&size=1500x1500&key=" + MCS_IDKEY);
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
