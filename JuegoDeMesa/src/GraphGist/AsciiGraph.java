/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphGist;

import static org.asciidoctor.Asciidoctor.Factory.create;
import org.asciidoctor.Asciidoctor;
import java.util.*;
/**
 *
 * @author Bryan H.O.
 */
public class AsciiGraph {

    /**
     * Default Constructor
     * @param
     */
    public AsciiGraph() {
        
        

    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Asciidoctor asciidoctor = create();
        
        String rendered = asciidoctor.render("*This* is it.", Collections.EMPTY_MAP);
        System.out.println(rendered);
        
        
    }

}
