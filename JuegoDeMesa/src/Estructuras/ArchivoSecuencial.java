package Estructuras;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Control.IConstants;
import Control.Jugador;

public class ArchivoSecuencial implements IConstants {

	public void EscribirSecuancialRanking(String name,String points) throws IOException {

		byte[] namebytes = FixByteSize(name.getBytes(),10);
		byte[] pointbytes = FixByteSize(points.getBytes(),10);
				
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		outputStream.write( namebytes );
		outputStream.write( pointbytes );

		byte ranking[] = outputStream.toByteArray( );
		
		
		
		
		FileManager write = new FileManager();
		write.escribirArchivo(SAVEFILE, ranking);

	}	
	public void EscribirSecuancialUsuarios(Jugador  player) throws IOException {


		int puntos  = player.getPoints();
		byte[] namebytes = FixByteSize(player.getName().getBytes(),10);
		byte[] pointbytes =  FixByteSize(Integer.toString(puntos).getBytes(),10) ;
		byte[] passbytes = FixByteSize(player.getContrasenha().getBytes(),10);
				
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		outputStream.write( namebytes );
		outputStream.write( pointbytes );
		outputStream.write( passbytes );

		byte jugadorbytes[] = outputStream.toByteArray( );
		
		
		
		
		FileManager write = new FileManager();
		write.escribirArchivo(SAVEFILE, jugadorbytes);

	}

	public ArrayList LeerSecuenciaRanking() throws IOException, ClassNotFoundException {
		FileManager read = new FileManager();

		byte[] rankbytes = read.leerArchivo(RANKINGFILE);
		int index = 0;		
		ArrayList<String> Ranking = new ArrayList();
		while (index != rankbytes.length) {
			byte[] Nameb = Arrays.copyOfRange(rankbytes, 0+index, 10+index);
			byte[] Pointb = Arrays.copyOfRange(rankbytes, 10+index, 20+index);
			String NameString = new String (Nameb);
			String PointString = new String (Pointb);

			Ranking.add(NameString);
			index = index+20;
			
		}
		return Ranking;

	}
	public BPlusTree LeerSecuenciaUsuarios() throws IOException, ClassNotFoundException {
		FileManager read = new FileManager();
		BPlusTree tree = new BPlusTree();
		byte[] userbytes = read.leerArchivo(SAVEFILE);
		int index = 0;		
		ArrayList<String> Ranking = new ArrayList();
		while (index != userbytes.length) {
			byte[] Nameb = Arrays.copyOfRange(userbytes, 0+index, 10+index);
			byte[] Pointb = Arrays.copyOfRange(userbytes, 10+index, 20+index);
			byte[] Passb = Arrays.copyOfRange(userbytes, 20+index, 30+index);

			String NameString = new String (Nameb);
			String PointString = new String (Pointb);
			String PassString = new String (Passb);

			Jugador player = new Jugador();
			player.setContrasenha(PassString);
			player.setName(NameString);
			player.setPoints(Integer.valueOf(PointString));
			index = index+30;
			tree.insert(NameString, player);
		}
		return tree;

	}
	
	public  byte[] FixByteSize(byte[] array,int size){
		int index=0;
		byte[] res = new byte[size];
		while(array.length!= index){
			res[index]=array[index];
			index++;
		}
		return res;
	}
}