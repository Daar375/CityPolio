package Google;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class HTTPPlaces {

	public static String post(String X, String Y, int radio,String tipo, String key)
	{
		String result = null;
		
		HttpClient httpclient = HttpClients.createDefault();
        try
        {
            URIBuilder builder = new URIBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+X+","+Y+"&radius="+radio+"&type="+tipo+"&key="+key);
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) 
            {
            	return EntityUtils.toString(entity);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
		return "Error";
	}
}
	

