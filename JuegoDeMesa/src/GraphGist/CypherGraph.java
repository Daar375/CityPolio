/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGist;

import Estructuras.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bryan H.O.
 */
public class CypherGraph {

    private Graph Grafo;
    private String CypherNodes = "";
    private String NodeNames = "WITH ";
    private String Edges = "";
    private String MatchNode = "MATCH ";

    /**
     * Default Constructor
     *
     * @param pGrafo
     */
    public CypherGraph() {


    }


    private void addNode(CypherExportable Data) {
        if (CypherNodes != "") {
            CypherNodes += ",CREATE (" + Data.getNodeName() + ":" + Data.getNodeType() + " " + Data.getNodeProperties() + ")";
            NodeNames += Data.getNodeName() + " ";
            MatchNode += ",(" + Data.getNodeName() + ":" + Data.getNodeType() + "),";
        } else {
            CypherNodes += "CREATE (" + Data.getNodeName() + ":" + Data.getNodeType() + " " + Data.getNodeProperties() + ")";
            MatchNode += "(" + Data.getNodeName() + ":" + Data.getNodeType() + ")";
        }
        
    }
    
    private void chargeNodes(ArrayList<CypherExportable> Nodes){
        for(CypherExportable Data : Nodes){
           addNode(Data); 
        }
        
    }
    
    private void chargeEdges(ArrayList<CypherExportable> Nodes){
     
        int indiceLista;
        int indiceNodo;
        for(indiceLista = 0; indiceLista < Grafo.getAdyacencyList().size();indiceLista++ ){
            for (indiceNodo = 0; indiceNodo < Grafo.getAdyacencyList().get(indiceLista).size();indiceNodo++ ) {
                addEdge(Nodes.get(indiceLista), Nodes.get(indiceNodo));
            }
        }
        
        
    }
    
    private void addEdge(CypherExportable Origin,CypherExportable Destination ){
        Edges+= "CREATE (" + Origin.getNodeName() + ")-[:" + Origin.getNodeRelation().toUpperCase() +"]->(" + Destination.getNodeName() + ") ";
    }

    public String getCypherCode(Graph pGrafo, ArrayList<CypherExportable> Data ){
        Graph Grafo = pGrafo;
        
        chargeNodes(Data);
        chargeEdges(Data);
        
        return "CREATE " + CypherNodes + " " + NodeNames + " " + MatchNode +
                " " + Edges + " " + NodeNames + " MATCH (n) RETURN n";
        
    }
}


