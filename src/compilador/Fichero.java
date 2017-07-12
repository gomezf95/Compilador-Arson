package compilador;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Fichero {

    public String leer(String archivo) throws FileNotFoundException, IOException {
        FileReader f = new FileReader(archivo);
        String linea,Fichero = "";
        try (BufferedReader b = new BufferedReader(f)) {
            while ((linea = b.readLine()) != null) {
                Fichero += linea + "\n";
            }
            if (!"".equals(Fichero)) {
                Fichero = Fichero.substring(0, Fichero.length() - 1);
            }
        }
        return Fichero;
    }

    public void guardar(String archivo, String texto) {
        FileWriter fw;
        try {
            fw = new FileWriter(archivo);
            fw.write(texto);
            fw.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}