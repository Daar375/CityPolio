package Control;

import java.io.IOException;

import javax.sound.sampled.Control;
import javax.swing.SwingUtilities;

public class Principal {

    public static CityPoly MyControl = new CityPoly();

    public Principal() throws ClassNotFoundException, IOException {
        MyControl = new CityPoly();
        MyControl.iniciarUi();
        
        
    } 
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
				new Principal();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
    }
}