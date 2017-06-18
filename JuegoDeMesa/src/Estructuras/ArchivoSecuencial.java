package Estructuras;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Control.IConstants;
import Control.Jugador;
import Herramientas.FileManager;

public class ArchivoSecuencial implements IConstants {

    public void escribirSecuencialRanking(String name, String points) throws IOException {

        byte[] namebytes = fixByteSize(name.getBytes(), 10);
        byte[] pointbytes = fixByteSize(points.getBytes(), 10);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(namebytes);
        outputStream.write(pointbytes);

        byte ranking[] = outputStream.toByteArray();

        FileManager write = new FileManager();
        write.escribirArchivo(RANKINGFILE, ranking);

    }

    public void escribirSecuencialUsuarios(Jugador player) throws IOException {

        int puntos = player.getPointsLife();
        byte[] namebytes = fixByteSize(player.getName().getBytes(), 10);
        byte[] pointbytes = fixByteSize(Integer.toString(puntos).getBytes(), 10);
        byte[] passbytes = fixByteSize(player.getContrasenha().getBytes(), 10);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(namebytes);
        outputStream.write(pointbytes);
        outputStream.write(passbytes);

        byte jugadorbytes[] = outputStream.toByteArray();

        int index = 0;

        FileManager write = new FileManager();
        write.escribirArchivo(SAVEFILE, jugadorbytes);

    }

    public ArrayList leerSecuencialRanking() throws IOException, ClassNotFoundException {
        FileManager read = new FileManager();

        byte[] rankbytes = read.leerArchivo(RANKINGFILE);
        int index = 0;
        ArrayList<Jugador> Ranking = new ArrayList();
        while (index != rankbytes.length) {
            byte[] Nameb = Arrays.copyOfRange(rankbytes, 0 + index, 10 + index);
            byte[] Pointb = Arrays.copyOfRange(rankbytes, 10 + index, 20 + index);
            String NameString = new String(Nameb);
            String PointString = new String(Pointb);
            Jugador player = new Jugador();
            byte[] deletthis = new byte[1];

            NameString = NameString.replaceAll(new String(deletthis), "");
            PointString = PointString.replaceAll(new String(deletthis), "");

            player.setName(NameString);
            player.setPointsLife(Integer.valueOf(PointString));
            Ranking.add(player);
            index = index + 20;

        }
        return Ranking;

    }

    public BPlusTree leerSecuencialUsuarios() throws IOException, ClassNotFoundException {
        FileManager read = new FileManager();
        BPlusTree tree = new BPlusTree();
        byte[] userbytes = read.leerArchivo(SAVEFILE);
        int index = 0;
        ArrayList<String> Ranking = new ArrayList();
        while (index != userbytes.length) {
            
            byte[] Nameb = Arrays.copyOfRange(userbytes, 0 + index, 10 + index);
            byte[] Pointb = Arrays.copyOfRange(userbytes, 10 + index, 20 + index);
            byte[] Passb = Arrays.copyOfRange(userbytes, 20 + index, 30 + index);

            String NameString = new String(Nameb);
            String PointString = new String(Pointb);
            String PassString = new String(Passb);
            Jugador player = new Jugador();

            PointString = PointString.replaceAll("[^\\d.]", "");
            byte[] deletthis = new byte[1];
            NameString = NameString.replaceAll(new String(deletthis), "");
            PassString = PassString.replaceAll(new String(deletthis), "");

            player.setContrasenha(PassString);
            player.setName(NameString);
            player.setPointsLife(Integer.valueOf(PointString));
            index = index + 30;
            System.out.println(NameString);
            tree.insert(NameString, player);
        }
        return tree;

    }

    public byte[] fixByteSize(byte[] array, int size) {
        int index = 0;
        byte[] res = new byte[size];
        while (array.length != index) {
            res[index] = array[index];
            index++;
        }
        return res;
    }
}
