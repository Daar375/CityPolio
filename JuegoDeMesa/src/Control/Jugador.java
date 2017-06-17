package Control;

import java.util.ArrayList;

import Deck.Reto;
import Mapa.Place;

public class Jugador implements Comparable {
	private int PointsLife;
	private String Name;
	private String Contrasenha;
	private ArrayList<Integer> CurrentPath = new ArrayList();
	private Reto reto;
	private int Points;
	private int RetosCompletos = 0;
	private ArrayList<Place> Objetivo = new ArrayList();;

	public String GetObjetivoInfo() {
		String res = "";
		for (Place lugar : Objetivo) {
			res += " Objetivo: ";
			res += lugar.getName() + " Valor: " + lugar.getValor();
		}

		return res;

	}

	public int getRetosCompletos() {
		return RetosCompletos;
	}

	public void setRetosCompletos(int retosCompletos) {
		RetosCompletos = retosCompletos;
	}

	public void addPoints(int pointsadd) {
		Points = pointsadd + Points;
	}

	public void addPointsLife(int pointsadd) {
		PointsLife = pointsadd + PointsLife;
	}

	public int getPointsLife() {
		return PointsLife;
	}

	public void setPointsLife(int pointsLife) {
		PointsLife = pointsLife;
	}

	public Reto getReto() {
		return reto;
	}

	public void setReto(Reto reto) {
		this.reto = reto;
	}

	public int getCurrentPos() {
		return CurrentPath.get(0);

	}

	public String getContrasenha() {
		return Contrasenha;
	}

	public ArrayList<Integer> getCurrentPath() {
		return CurrentPath;
	}

	public void addPath(ArrayList<Integer> currentPath) {
		CurrentPath.addAll(currentPath);
	}

	public void setCurrentPath(ArrayList<Integer> currentPath) {
		CurrentPath = currentPath;
	}

	public void setContrasenha(String pContrasenha) {
		Contrasenha = pContrasenha;
	}

	public int getPoints() {
		return Points;
	}

	public ArrayList<Place> getObjetivo() {
		return Objetivo;
	}

	public void setObjetivo(ArrayList<Place> objetivo) {
		Objetivo = objetivo;
	}

	public void setPoints(int points) {
		Points = points;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
