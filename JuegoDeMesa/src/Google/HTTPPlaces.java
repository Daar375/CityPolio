package Google;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import Control.IConstants;

public class HTTPPlaces implements IConstants{

	public static String post(String X, String Y, String tipo) {
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
}
