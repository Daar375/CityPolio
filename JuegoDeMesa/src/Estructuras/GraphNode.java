/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructuras;

    class GraphNode implements Comparable<GraphNode>{
        int first, second;
        GraphNode( int d , int p ){                          //constructor
            this.first = d;
            this.second = p;
        }
        public int compareTo( GraphNode other){              //es necesario definir un comparador para el correcto funcionamiento del PriorityQueue
            if( second > other.second ) return 1;
            if( second == other.second ) return 0;
            return -1;
        }
    };