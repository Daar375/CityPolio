package Estructuras;

import java.util.*;

class Edge{
    int Izq,Der;
    
    public Edge(int v,int w){
	this.Izq=v; this.Der=w;
	
    }

    @Override
    public String toString(){
	return "("+Izq+","+Der+")";
	}
}
