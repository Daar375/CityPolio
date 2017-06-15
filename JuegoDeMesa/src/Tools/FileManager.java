package Tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * --------- Convertir esta clase a estatica para ------------ --------- usar
 * los metodos sin instanciarla ------------
 */
public class FileManager {

	public byte[]  leerArchivo(File Archivo) throws IOException, ClassNotFoundException {
		Path path = Paths.get(Archivo.getPath());
		byte[] data = Files.readAllBytes(path);
		return  data;
	}

	public void escribirArchivo(File Archivo, byte[] obj) throws IOException {
		FileOutputStream output = new FileOutputStream(Archivo, true);
		try {
		   output.write(obj);
		} finally {
		   output.close();
		}
	}

}
