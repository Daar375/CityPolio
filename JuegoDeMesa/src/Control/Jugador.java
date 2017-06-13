package Control;

public class Jugador implements Comparable {
	private int Points;
	private String Name;
	private String Contrasenha;
	
	public String getContrasenha() {
		return Contrasenha;
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
