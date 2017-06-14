package Control;

import javax.sound.sampled.Control;
import javax.swing.SwingUtilities;

public class Principal {

    public static CityPoly MyControl = new CityPoly();

    public Principal() {
        MyControl = new CityPoly();
        MyControl.iniciarUi();
        
        
    } 
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Principal();
        });
        
    }
}