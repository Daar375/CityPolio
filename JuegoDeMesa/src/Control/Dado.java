package Control;

import java.util.concurrent.ThreadLocalRandom;

public class Dado {

	
	public int rollDice(){
		return ThreadLocalRandom.current().nextInt(1, 7);
			}
}
