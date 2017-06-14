package Control;

import java.util.ArrayList;

import Deck.Reto;

public class Jugador implements Comparable {
	private int Points;
	private String Name;
	private String Contrasenha;
	private ArrayList<Integer> CurrentPath;
	private Reto reto;
	
	
	public Reto getReto() {
		return reto;
	}
	public void setReto(Reto reto) {
		this.reto = reto;
	}
	public int  getCurrentPos(){
		return CurrentPath.get(0);
		
	}
	public String getContrasenha() {
		return Contrasenha;
	}
	public ArrayList<Integer> getCurrentPath() {
		return CurrentPath;
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
