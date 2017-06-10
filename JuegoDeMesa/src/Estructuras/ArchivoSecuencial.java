package Estructuras;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Control.IConstants;

public class ArchivoSecuencial implements IConstants {

	public void EscribirSecuancia(String name) throws IOException {
		byte[] savename = new byte[40];
		//byte[] savepoints = new byte[40];

		Serializador ser = new Serializador();

		byte[] namebytes = ser.Serializador(name);
	//	byte[] pointbytes = ser.Serializador(points);

		int index = 0;
		while (namebytes.length != index) {
			savename[index] = namebytes[index];
			index++;
		}
		index = 0;
		/*while (pointbytes.length != index) {
			savepoints[index] = pointbytes[index];
			index++;
		}*/
		FileManager write = new FileManager();
		write.escribirArchivo(RANKINGFILE, savename);
		//write.escribirArchivo(RANKINGFILE, savepoints);

	}

	public ArrayList LeerSecuencia() throws IOException, ClassNotFoundException {
		FileManager read = new FileManager();
		Serializador ser = new Serializador();

		byte[] rankbytes = read.leerArchivo(RANKINGFILE);
		int index = 0;		
		ArrayList<String> Ranking = new ArrayList();
		while (index != rankbytes.length) {
			byte[] Nameb = Arrays.copyOfRange(rankbytes, 0+index, 40+index);
			//byte[] Points = Arrays.copyOfRange(rankbytes, 40+index, 80+index);
			Ranking.add((String) ser.deseerializador(Nameb));
			index = index+40;
		}
		return Ranking;

	}
}
