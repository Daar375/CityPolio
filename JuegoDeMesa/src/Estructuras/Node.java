/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructuras;

    class Node implements Comparable<Node>{
        int first, second;
        Node( int d , int p ){                          //constructor
            this.first = d;
            this.second = p;
        }
        public int compareTo( Node other){              //es necesario definir un comparador para el correcto funcionamiento del PriorityQueue
            if( second > other.second ) return 1;
            if( second == other.second ) return 0;
            return -1;
        }
    };
