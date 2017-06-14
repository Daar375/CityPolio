/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructuras;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;



/**
 *
 * @author Bryan H.O.
 */
public class Graph {

    //similar a los defines de C++
    private final int MAX = 10005;  //maximo numero de vértices
    private final int INF = 1<<30;  //definimos un valor grande que represente la distancia infinita inicial, basta conque sea superior al maximo valor del peso en alguna de las aristas
    
    private List< List< GraphNode > > AdyacencyList = new ArrayList< List< GraphNode > >(); //lista de adyacencia
    public int distancia[ ] = new int[ MAX ];          //distancia[ u ] distancia de vértice inicial a vértice con ID = u
    public boolean visitado[ ] = new boolean[ MAX ];   //para vértices visitados
    private int CantVertices;                                      //numero de vertices
    public int previo[] = new int[ MAX ];              //para la impresion de caminos
    private boolean dijkstraEjecutado;

    //public Graph() {
    //}
    
    
    
    /**
     * Default Constructor
     * @param
     */
    public Graph(int V) {
        
        this.CantVertices = V;
        for( int i = 0 ; i <= V ; ++i ) 
            AdyacencyList.add(new ArrayList<GraphNode>()) ; //inicializamos lista de adyacencia
        dijkstraEjecutado = false;
    }
    

    
       
    public void addEdge( int origen , int destino , int peso , boolean dirigido ){
        AdyacencyList.get( origen ).add( new GraphNode( destino , peso ) );    //grafo diridigo
        if( !dirigido )
            AdyacencyList.get( destino ).add( new GraphNode( origen , peso ) ); //no dirigido
    }
    
    public int getNumberOfVertices() {
        return CantVertices;
    }

    public void setNumberOfVertices(int numeroDeVertices) {
        CantVertices = numeroDeVertices;
    }

    public List<List<GraphNode>> getAdyacencyList() {
        return AdyacencyList;
    }

    public void setAdyacencyList(List<List<GraphNode>> AdyacencyList) {
        this.AdyacencyList = AdyacencyList;
    }

    public int getCantVertices() {
        return CantVertices;
    }

    public void setCantVertices(int CantVertices) {
        this.CantVertices = CantVertices;
    }

    public boolean isDijkstraEjecutado() {
        return dijkstraEjecutado;
    }

    public void setDijkstraEjecutado(boolean dijkstraEjecutado) {
        this.dijkstraEjecutado = dijkstraEjecutado;
    }
    
    
        
        
        
        
    }

