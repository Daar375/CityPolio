package Deck;

import java.util.Random;

import Mapa.Type;

public class Reto {
	private Type tipo;
	private boolean DosRetos;

	public Type getTipo() {
		return tipo;
	}

	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}

    public boolean isDosRetos() {
        return DosRetos;
    }

    public void setDosRetos(boolean DosRetos) {
        this.DosRetos = DosRetos;
    }

    public int getCantidad(){
        if(DosRetos) return 2;
        return 1;
    }
        
        @Override
        public String toString(){
            return tipo.toString() + ": " + getCantidad() + " veces.";
        }

}
