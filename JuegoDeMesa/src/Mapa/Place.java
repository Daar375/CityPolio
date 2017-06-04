package Mapa;

import org.json.JSONObject;

public class Place {
	private double Longitud;
	private double Latitud;
	private int Valor;
	private Type Tipo;
	private JSONObject info;
	

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
	public JSONObject getInfo() {
		return info;
	}
	public void setInfo(JSONObject info) {
		this.info = info;
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
