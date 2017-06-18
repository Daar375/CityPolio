package Estructuras;

import java.util.*;

public class Dijkstra {

    //similar a los defines de C++
    private final int MAX = 10005;  //maximo numero de vértices
    private final int INF = 1 << 30;  //definimos un valor grande que represente la Distancia infinita inicial, basta conque sea superior al maximo valor del peso en alguna de las aristas

    private PriorityQueue< GraphNode> Q = new PriorityQueue<GraphNode>(); //priority queue propia de Java, usamos el comparador definido para que el de menor valor este en el tope
    Graph Grafo;

    public Dijkstra(Graph pGrafo) {
        this.Grafo = pGrafo;
        init();
    }

    public Dijkstra(int V) {
        this.Grafo = new Graph(V);
        init();
    }

    //Paso de visitarAdyacente
    private void visitarAdyacente(int actual, int adyacente, int peso) {
        //Si la Distancia del origen al vertice actual + peso de su arista es
        //menor a la Distancia del origen al vertice adyacente
        if (Grafo.Distancia[actual] + peso < Grafo.Distancia[adyacente]) {
            Grafo.Distancia[adyacente] = Grafo.Distancia[actual] + peso;  //relajamos el vertice actualizando la Distancia
            Grafo.Previos[adyacente] = actual;                         //a su vez actualizamos el vertice Previos
            Q.add(new GraphNode(adyacente, Grafo.Distancia[adyacente])); //agregamos adyacente a la cola de prioridad
        }
    }

    /**
     * Inicializa los valores por defecto del grafo
     */
    public void init() {
        for (int i = 0; i <= Grafo.getCantVertices(); ++i) {
            Grafo.Distancia[i] = INF;  //inicializamos todas las distancias con valor infinito
            Grafo.Visitado[i] = false; //inicializamos todos los vértices como no visitados
            Grafo.Previos[i] = -1;      //inicializamos el Previos del vertice i con -1
        }
    }

    /**
     * Algoritmo de dijkstra
     *
     * @param inicial
     */
    public void dijkstra(int inicial) {
        init(); //inicializamos nuestros arreglos
        Q.add(new GraphNode(inicial, 0)); //Insertamos el vértice inicial en la Cola de Prioridad
        Grafo.Distancia[inicial] = 0;      //Este paso es importante, inicializamos la Distancia del inicial como 0
        int actual, adyacente, peso;
        while (!Q.isEmpty()) {                   //Mientras cola no este vacia
            actual = Q.element().ConectadoA;            //Obtengo de la cola el nodo con menor peso, en un comienzo será el inicial
            Q.remove();                           //Sacamos el elemento de la cola
            if (Grafo.Visitado[actual]) {
                continue; //Si el vértice actual ya fue Visitado entonces sigo sacando elementos de la cola
            }
            Grafo.Visitado[actual] = true;         //Marco como Visitado el vértice actual

            for (int i = 0; i < Grafo.getAdyacencyList().get(actual).size(); ++i) { //reviso sus adyacentes del vertice actual
                adyacente = Grafo.getAdyacencyList().get(actual).get(i).ConectadoA;   //id del vertice adyacente
                peso = Grafo.getAdyacencyList().get(actual).get(i).Peso;        //peso de la arista que une actual con adyacente ( actual , adyacente )
                if (!Grafo.Visitado[adyacente]) {        //si el vertice adyacente no fue Visitado
                    visitarAdyacente(actual, adyacente, peso); //realizamos el paso de visitarAdyacente
                }
            }
        }

        for (int i = 1; i <= Grafo.getCantVertices(); ++i) {
        }
        Grafo.setDijkstraEjecutado(true);
    }

    /**
     * Devuelve el camino mas corto basado en el dijkstra
     *
     * @param destino
     * @return
     */
    public ArrayList shortestPath(int destino) {
        if (!Grafo.isDijkstraEjecutado()) {
            return null;
        }
        ArrayList<Integer> Lista = new ArrayList();
        path(destino, Lista);
        return Lista;
    }

    /**
     * Recursion para ver el camino mas corto en el shortestPath
     *
     * @param destino
     * @param Lista
     * @return
     */
    private ArrayList path(int destino, ArrayList Lista) {
        if (Grafo.Previos[destino] != -1) //si aun poseo un vertice Previos
        {
            path(Grafo.Previos[destino], Lista);  //recursivamente sigo explorando
        }
        Lista.add(destino);
        return Lista;
    }

}
