package Estructuras;

import Control.Jugador;

public class test {
	public static void main(String[] args) throws Exception {
		Serializador ser = new Serializador ();
		
		byte[] save = new byte[50];
		
		byte[] text=ser.Serializador("hola");
		
		int index = 0;
		while(text.length!= index){
			save[index]=text[index];
			index++;
		}
		index = 0;
		while(save.length!= index){
			System.out.print(save[index]);
			index++;
		}
		System.out.println(	"  "+	ser.deseerializador(save));
	}

	
}
