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
    private String CypherNodes;
    private String NodeNames;
    private String Edges;
    private String MatchNode;
    private ClipBoardManager ClipBoard;
    private String AsciiCypher;

    /**
     * Default Constructor
     *
     * @param pGrafo
     */
    public CypherGraph() {
        ClipBoard = new ClipBoardManager();
        MatchNode = "MATCH ";
        Edges = "";
        NodeNames = "WITH ";
        CypherNodes = "";
    }


    private void addNode(CypherExportable Data,int index) {
        if (CypherNodes != "") {
            CypherNodes += ", (" + Data.getNodeType() + index + ":" + Data.getNodeType() + " " + Data.getNodeProperties() + ")";
            NodeNames += ", " +Data.getNodeType() + index;
            MatchNode += ",(" + Data.getNodeType() + index + ":" + Data.getNodeType() + ")";
        } else {
            CypherNodes += " (" + Data.getNodeType() + index + ":" + Data.getNodeType() + " " + Data.getNodeProperties() + ")";
            NodeNames += Data.getNodeType() + index + " ";
            MatchNode += "(" + Data.getNodeType() + index + ":" + Data.getNodeType() + ")";
        }
        
    }
    
    private void chargeNodes(ArrayList<CypherExportable> Nodes){
        int index = 0;
        for(CypherExportable Data : Nodes){
           addNode(Data,index); 
           index++;
        }
        
    }
    
    private void chargeEdges(ArrayList<CypherExportable> Nodes){
     
        int indiceLista;
        int indiceNodo;
        for(indiceLista = 0; indiceLista < Grafo.getAdyacencyList().size()-1;indiceLista++ ){
            for (indiceNodo = 0; indiceNodo < Grafo.getAdyacencyList().get(indiceLista).size() -1;indiceNodo++ ) {
                addEdge(Nodes.get(indiceLista), Nodes.get(indiceNodo),indiceLista,indiceNodo);
            }
        }
        
        
    }
    
    private void addEdge(CypherExportable Origin,CypherExportable Destination, int indexOrigin,int indexDestination ){
        Edges+= "CREATE (" + Origin.getNodeType() + indexOrigin + ")-[:" + Origin.getNodeRelation().toUpperCase() +"]->(" + Destination.getNodeType() + indexDestination + ") ";
    }

    public String getCypherCode(Graph pGrafo, ArrayList<CypherExportable> Data ){
        Grafo = pGrafo;
        
        chargeNodes(Data);
        chargeEdges(Data);
        
        String FinalT = "";
        FinalT+= " :neo4j-version: 3.2.1\n :author: BryanHernandez/DanielAlvarez\n :twitter: @malabaryan\n :style: #54A835/#1078B5/white:Place(name)\n\n";
        FinalT +="[source,cypher]\n----\nCREATE " + CypherNodes + " \n" + NodeNames + " \n\n" + MatchNode +
                " \n\n" + Edges + " \n\n" + NodeNames + "\nMATCH (n) RETURN n\n----\n\n\n//graph_result";
        ClipBoard.setClipboard(FinalT);
        this.AsciiCypher = FinalT;
        return FinalT;
        
    }

    public String getAsciiCypher() {
        return AsciiCypher;
    }

    
    
}


