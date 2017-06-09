package Google;

import org.json.JSONArray;
import org.json.JSONObject;

public class tester {

	public static void main(String[] args) throws Exception {
		String res = HTTPPlaces.getplaces("9.9354028", "-84.0753903", "restaurant");
		
		JSONObject placesjson = new JSONObject(res);
		 JSONArray Array = placesjson.getJSONArray("results");
		 int index = 0;
		while(index!=Array.length()){
			Object tempdouble = Array.getJSONObject(index).get("rating");
			tempdouble.getClass();
			System.out.println(	tempdouble.getClass()==Double.class);

			index++;
		}

	}

}
