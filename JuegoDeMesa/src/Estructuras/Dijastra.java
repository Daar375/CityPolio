package Estructuras;

import java.util.*;


class Dijkstra{
    
    //similar a los defines de C++
    private final int MAX = 10005;  //maximo numero de vértices
    private final int INF = 1<<30;  //definimos un valor grande que represente la distancia infinita inicial, basta conque sea superior al maximo valor del peso en alguna de las aristas
    
    private PriorityQueue< Node > Q = new PriorityQueue<Node>(); //priority queue propia de Java, usamos el comparador definido para que el de menor valor este en el tope
    Graph Grafo = new Graph();
    
    Dijkstra(Graph pGrafo){
        this.Grafo = pGrafo;
        init();
    }
    
       


    //Paso de relajacion
    private void relajacion( int actual , int adyacente , int peso ){
        //Si la distancia del origen al vertice actual + peso de su arista es
        //menor a la distancia del origen al vertice adyacente
        if( Grafo.distancia[ actual ] + peso < Grafo.distancia[ adyacente ] ){
            Grafo.distancia[ adyacente ] = Grafo.distancia[ actual ] + peso;  //relajamos el vertice actualizando la distancia
            Grafo.previo[ adyacente ] = actual;                         //a su vez actualizamos el vertice previo
            Q.add( new Node( adyacente , Grafo.distancia[ adyacente ] ) ); //agregamos adyacente a la cola de prioridad
        }
    }
    
        
    
    /**
     * Inicializa los valores por defecto del grafo
     */
    public void init(){
        for( int i = 0 ; i <= Grafo.getCantVertices() ; ++i ){
            Grafo.distancia[ i ] = INF;  //inicializamos todas las distancias con valor infinito
            Grafo.visitado[ i ] = false; //inicializamos todos los vértices como no visitados
            Grafo.previo[ i ] = -1;      //inicializamos el previo del vertice i con -1
        }
    }

    /**
     * Algoritmo de dijkstra
     * @param inicial 
     */
    void dijkstra( int inicial ){
        init(); //inicializamos nuestros arreglos
        Q.add( new Node( inicial , 0 ) ); //Insertamos el vértice inicial en la Cola de Prioridad
        Grafo.distancia[ inicial ] = 0;      //Este paso es importante, inicializamos la distancia del inicial como 0
        int actual , adyacente , peso;
        while( !Q.isEmpty() ){                   //Mientras cola no este vacia
            actual = Q.element().first;            //Obtengo de la cola el nodo con menor peso, en un comienzo será el inicial
            Q.remove();                           //Sacamos el elemento de la cola
            if( Grafo.visitado[ actual ] ) continue; //Si el vértice actual ya fue visitado entonces sigo sacando elementos de la cola
            Grafo.visitado[ actual ] = true;         //Marco como visitado el vértice actual

            for( int i = 0 ; i < Grafo.getAdyacencyList().get( actual ).size() ; ++i ){ //reviso sus adyacentes del vertice actual
                adyacente = Grafo.getAdyacencyList().get( actual ).get( i ).first;   //id del vertice adyacente
                peso = Grafo.getAdyacencyList().get( actual ).get( i ).second;        //peso de la arista que une actual con adyacente ( actual , adyacente )
                if( !Grafo.visitado[ adyacente ] ){        //si el vertice adyacente no fue visitado
                    relajacion( actual , adyacente , peso ); //realizamos el paso de relajacion
                }
            }
        }

        System.out.printf( "Distancias mas cortas iniciando en vertice %d\n" , inicial );
        for( int i = 1 ; i <= Grafo.getCantVertices() ; ++i ){
            System.out.printf("Vertice %d , distancia mas corta = %d\n" , i , Grafo.distancia[ i ] );
        }
        Grafo.setDijkstraEjecutado(true);
    }
 
    /**
     * Devuelve el camino mas corto basado en el dijkstra
     * @param destino
     * @return 
     */
    public ArrayList shortestPath(int destino){
        if( !Grafo.isDijkstraEjecutado() ){
            System.out.println("Es necesario ejecutar el algorithmo de Dijkstra antes de poder imprimir el camino mas corto");
            return  null;
        }
        ArrayList<Integer> Lista = new ArrayList();
        path( destino, Lista );
        return Lista;
    }
    
    /**
     * Recursion para ver el camino mas corto en el shortestPath
     * @param destino
     * @param Lista
     * @return 
     */
    private ArrayList  path( int destino, ArrayList Lista ){
        if( Grafo.previo[ destino ] != -1 )    //si aun poseo un vertice previo
            path( Grafo.previo[ destino ], Lista );  //recursivamente sigo explorando
        System.out.printf("%d " , destino );        //terminada la recursion imprimo los vertices recorridos
        Lista.add(destino);
        return Lista;
    }

 
}
