package pruebas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import logica.persistencia.TarifasDAO_CSVImpl;

/**
 * Clase de prueba para comprobar que funcione la implementación con CSV de ParabaDAO.
 * @author Nicolás Sabogal 30/03/2022
 * @version 1.0
 */
public class PruebaTarifasDAO_CSVImpl {
    /**
     * Lee el archivo en la direccion especificada, crea una copia, borra el primer imprime las tarifas del segundo y añade uno nuevo.
     * @param args args[0]: dirección de la carpeta, args[1]: nombre del archivo a manipular.
     */
    public static void main(String[] args) {
        if (!args[0].isEmpty()) {
            if (args[0].charAt(args[0].length() - 1) == '\\')
                args[0] = args[0].substring(0, args[0].length() - 1) + "/";
            
            if (args[0].charAt(args[0].length() - 1) != '/')
                args[0] += "/";
        }

        File original = new File(args[0] + args[1]);
        File copia = new File(args[0] + "PruebaTarifas.csv");
        try {
            Files.copy(original.toPath(), copia.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        TarifasDAO_CSVImpl tarifas = new TarifasDAO_CSVImpl(copia.toString());

        tarifas.borrarServicio(tarifas.getServicios()[0]);

        String servicio = tarifas.getServicios()[0];
        for (String tipo: tarifas.getTipos())
            System.out.println(servicio + ", " + tipo + ": " + tarifas.getTarifa(servicio, tipo));

        System.out.println(tarifas.anadirServicio("PRUEBA"));
    }
}
