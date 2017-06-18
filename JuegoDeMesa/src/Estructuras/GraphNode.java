/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

class GraphNode implements Comparable<GraphNode> {

    int ConectadoA, Peso;

    GraphNode(int nodo, int peso) {                          //constructor
        this.ConectadoA = nodo;
        this.Peso = peso;
    }

    public int compareTo(GraphNode other) {              //es necesario definir un comparador para el correcto funcionamiento del PriorityQueue
        if (Peso > other.Peso) {
            return 1;
        }
        if (Peso == other.Peso) {
            return 0;
        }
        return -1;
    }
};
