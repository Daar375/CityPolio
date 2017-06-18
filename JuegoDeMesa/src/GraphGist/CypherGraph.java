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


    private void addNode(CypherExportable Data) {
        if (CypherNodes != "") {
            CypherNodes += ", (" + Data.getNodeName() + ":" + Data.getNodeType() + " " + Data.getNodeProperties() + ")";
            NodeNames += ", " +Data.getNodeName() ;
            MatchNode += ",(" + Data.getNodeName() + ":" + Data.getNodeType() + "),";
        } else {
            CypherNodes += " (" + Data.getNodeName() + ":" + Data.getNodeType() + " " + Data.getNodeProperties() + ")";
            NodeNames += Data.getNodeName() + " ";
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


