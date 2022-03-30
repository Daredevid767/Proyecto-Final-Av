package pruebas;

import java.util.Arrays;
import java.util.List;

import logica.negocio.Ruta;
import logica.persistencia.RutaDAO_CSVImpl;

/**
 * Clase de prueba para comprobar que funcione la implementación con CSV de RutaDAO.
 * @author Nicolás Sabogal 29/03/2022
 * @version 1.0
 */
public class PruebaRutaDAO_CSVImpl {
    /**
     * Lee el archivo en la direccion especificada, procesa las rutas en su interior y añade un registro de trasbordo a la primera.
     * @param args args[0]: dirección de la carpeta, args[1]: nombre del archivo a manipular.
     */
    public static void main(String[] args) {
        if (!args[0].isEmpty()) {
            if (args[0].charAt(args[0].length() - 1) == '\\')
                args[0] = args[0].substring(0, args[0].length() - 1) + "/";
            
            if (args[0].charAt(args[0].length() - 1) != '/')
                args[0] += "/";
        }

        RutaDAO_CSVImpl dao = new RutaDAO_CSVImpl(args[0] + args[1]);
        List<Ruta> paradas = Arrays.asList(dao.getTodos());

        paradas.get(0).recibirRegistro(new logica.negocio.Registro("PruebaPruebosa"));

        dao.actualizar(0, paradas.get(0));
    }
}
