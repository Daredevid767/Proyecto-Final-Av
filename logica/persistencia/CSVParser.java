package logica.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase dedicada al análisis y escritura de archivos CSV.
 * @author Nicolás Sabogal 26/03/2022
 * @version 1.2
 */
public class CSVParser {

    private CSVParser() {}

    /**
     * Lee un archivo en archivo en la ruta especificada y lo carga en una CSVTabla.
     * @param direccion Ruta del archivo CSV a cargar.
     * @return Una CSVTabla con la información contenida en el archivo CSV.
     * @throws IOException Si el archivo no se encuentra en la ruta especificada o no puede ser abierto.
     */
    public static CSVTabla get(String direccion) throws IOException {

        BufferedReader lector = new BufferedReader(new FileReader(direccion));
        List<List<String>> tabla = new ArrayList<>();

        String fila = lector.readLine();
        while (fila != null) {
            tabla.add(parse(fila));
            fila = lector.readLine();
        }
        lector.close();

        return new CSVTabla(direccion, tabla);
    }

    /**
     * Analisa una línea proveniente de un archivo CSV y lo convierte en una lista de Strings.
     * @param fila Una línea proveniente de un archivo CSV.
     * @return Una lista de Strings con las celdas de la línea ingresada.
     */
    private static List<String> parse(String fila) {
        List<String> celdas = new ArrayList<>();
        while (true) {

            int indice = 0;
            boolean comillas = false;
            boolean terminado = false;
            
            if (fila.charAt(0) == '\"') {
                for (indice = 1; indice < fila.length() - 1; indice++)
                    if (    (fila.charAt(indice) == '\"')
                         && (fila.charAt(indice + 1) == ';'))
                        break;
                comillas = true;
            }

            int corte = fila.indexOf(';', indice);
            if (corte == -1) {
                corte = fila.length();
                terminado = true;
            }
            if (!comillas)
                indice = corte;

            String celda = fila.substring(0,indice);
            try {
                fila = fila.substring(corte + 1);
            }
            catch (StringIndexOutOfBoundsException e) {
                fila = "";
            }

            for (int i = 0; i < celda.length(); i++)
                if (celda.charAt(i) == '\"')
                    celda = celda.substring(0, i) + celda.substring(i + 1);

            celdas.add(celda);

            if (fila.isEmpty()) {
                if (!terminado)
                    celdas.add("");
                break;
            }
        }
        return celdas;
    }

    /**
     * Inserta la información contendia en una CSVTabla en el archivo de la dirección que indica su dirección propia.
     * @param tabla Objeto CSVTabla con una dirección propia válida.
     * @throws IOException Si la dirección es una carpeta o es inaccesible.
     */
    public static void actualizar (CSVTabla tabla) throws IOException {
        crear(tabla.getDireccion(), tabla);
    }

    /**
     * Toma la información contenida en una CSVTabla y la convierte en un archivo CSV en la ruta indicada.
     * @param direccion Dirección del archivo a crear.
     * @param tabla Objeto CSVTabla con información de tabla.
     * @throws IOException Si la dirección es una carpeta o es inaccecible.
     */
    public static void crear (String direccion, CSVTabla tabla) throws IOException {

        BufferedWriter escritor = new BufferedWriter(new FileWriter(direccion));

        escritor.write(aLineaCSV(tabla.getCabezales()));
        for (int i = 0; i < tabla.getCantidadFilas(); i++)
            escritor.write(aLineaCSV(tabla.getFila(i)));

        escritor.close();
    }

    /**
     * Toma un arreglo de Strings y las convierte en una línea de archivo CSV.
     * @param fila Arreglo de Strings a guardar.
     * @return Una cadena con el formato de una fila en CSV.
     */
    private static String aLineaCSV(String[] fila) {
        String linea = "";

        for (String celda: fila) {
            boolean comillas = false;
            for (int i = 0; i < celda.length(); i++)
                if (celda.charAt(i) == '\"' || celda.charAt(i) == ';') {
                    comillas = true;
                    if (celda.charAt(i) == '\"') {
                        celda = celda.substring(0, i) + "\"" + celda.substring(i);
                        i++;
                        continue;
                    }
                }
    
            linea += (comillas ? "\"" + celda + "\"" : celda) + ";";
        }

        return linea.substring(0, linea.length() - 1) + "\n";
    }

}