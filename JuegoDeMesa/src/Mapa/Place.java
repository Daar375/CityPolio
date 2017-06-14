package Mapa;

import org.json.JSONObject;

public class Place {
	private double Longitud;
	private double Latitud;
	private String Name;
	private String icon;
    public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	private boolean visitado;
        
        
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	private int Valor;
	private Type Tipo;
	
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}


	public double getLongitud() {
		return Longitud;
	}

	public void setLongitud(double longitud) {
		Longitud = longitud;
	}

	public double getLatitud() {
		return Latitud;
	}

	public void setLatitud(double latitud) {
		Latitud = latitud;
	}



	public int getValor() {
		return Valor;
	}

	public void setValor(int valor) {
		Valor = valor;
	}

	public Type getTipo() {
		return Tipo;
	}

	public void setTipo(Type tipo) {
		Tipo = tipo;
	}

}
