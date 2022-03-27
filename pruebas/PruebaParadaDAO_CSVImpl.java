package pruebas;

import java.util.ArrayList;
import java.util.List;

import logica.negocio.Parada;
import logica.persistencia.ParadaDAO_CSVImpl;

/**
 * Clase de prueba para comprobar que funcione la implementación con CSV de ParabaDAO.
 * @author Nicolás Sabogal 26/03/2022
 * @version 1.0
 */
public class PruebaParadaDAO_CSVImpl {
    /**
     * Lee el archivo en la direccion especificada, procesa las paradas en su interior y añade dos paradas nuevas de pruba: cero y uno.
     * @param args args[0]: dirección de la carpeta, args[1]: nombre del archivo a manipular.
     */
    public static void main(String[] args) {
        if (!args[0].isEmpty()) {
            if (args[0].charAt(args[0].length() - 1) == '\\')
                args[0] = args[0].substring(0, args[0].length() - 1) + "/";
            
            if (args[0].charAt(args[0].length() - 1) != '/')
                args[0] += "/";
        }

        ParadaDAO_CSVImpl dao = new ParadaDAO_CSVImpl(args[0] + args[1]);
        Parada[] paradasLeidas = dao.getTodos();

        List<Integer> rutas0 = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            rutas0.add(i);
        Parada parada0 = new Parada(0, "cero", rutas0);
        
        List<Integer> rutas1 = new ArrayList<>();
        for (int i = 0; i < 4; i += 2)
            rutas1.add(i);
        Parada parada1 = new Parada(0, "uno", rutas1);

        dao.crear(parada0);
        dao.crear(parada1);
    }
}
