package Deck;

import java.util.Random;

import Mapa.Type;

public class Reto {
	private Type tipo;
	private int Cantidad;

	public Type getTipo() {
		return tipo;
	}

	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
        
        @Override
        public String toString(){
            return tipo.toString() + ": " + Cantidad + " veces.";
        }

}
