package Google;

public class tester {

	public static void main(String[] args) throws Exception {
		String res = HTTPPlaces.post("-33.8670522", "151.1957362", 500, "restaurant",
				"AIzaSyCbb9-AtcNbXpRLRWaR-fuqDha0SEoss6k");
		System.out.println(res);

	}

}
