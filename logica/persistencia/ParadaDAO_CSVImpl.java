package logica.persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz ParadaDAO usando archivos CSV.
 * @author Nicolás Sabogal 26/03/2022
 * @version 1.0
 */
public class ParadaDAO_CSVImpl implements ParadaDAO {

    /** dirección de la base de datos a editar. */
    private String direccion;

    /** Crea una instancia con una base null, inusable. */
    public ParadaDAO_CSVImpl() {
        this.direccion = null;
    }
    /**
     * Crea una instancia con la dirección indicada como base.
     * @param direccion Dirección de la base de datos a procesar.
     */
    public ParadaDAO_CSVImpl(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Construye una instancia de Parada con información recuperada de la base de datos.
     * @param id Fila de la instancia en la base de datos.
     * @param tabla Base de datos en clase CSVTabla.
     * @return Una instancia de Parada con la información recuperada.
     */
    private logica.negocio.Parada construirParada(int id, logica.persistencia.CSVTabla tabla) {
        if (id < 0 || id > tabla.getCantidadFilas())
            return null;

        String[] fila = tabla.getFila(id);
        for (int i = 0; i < fila.length; i++) {
            if (!fila[i].isEmpty())
                break;
            
            if (i == fila.length - 1)
                return null;
        }

        String nombre = tabla.getCelda("nombre", id);
        List<Integer> rutas = new ArrayList<>();

        String[] rutasArr = tabla.getCelda("rutas", id).split(",");
        for (String idRuta: rutasArr)
            rutas.add(Integer.parseInt(idRuta));

        return new logica.negocio.Parada(id, nombre, rutas);
    }

    @Override
    public logica.negocio.Parada get(int id) {
        try {
            return this.construirParada(id, CSVParser.get(this.direccion));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public logica.negocio.Parada[] getTodos() {
        CSVTabla tabla;
        try {
            tabla = CSVParser.get(direccion);

            List<logica.negocio.Parada> paradasList = new ArrayList<>();
            for (int i = 0; i < tabla.getCantidadFilas(); i++) {
                logica.negocio.Parada parada = this.construirParada(i, tabla);
                if (parada == null)
                    continue;
                paradasList.add(parada);
            }
        
            logica.negocio.Parada[] paradas = new logica.negocio.Parada[paradasList.size()];
            return paradasList.toArray(paradas);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void borrar(int id) {
        try {
            CSVTabla tabla = CSVParser.get(direccion);

            String[] filaVacia = new String[tabla.getCabezales().length];
            for (int i = 0; i < filaVacia.length; i++)
                filaVacia[i] = "";
            
            tabla.setFila(id, filaVacia);
            CSVParser.actualizar(tabla);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(int id, logica.negocio.Parada parada) {
        try{
            CSVTabla tabla = CSVParser.get(direccion);
            
            tabla.setFila(id, parada.getAtributosCSV());
            
            CSVParser.actualizar(tabla);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(logica.negocio.Parada parada) {
        try {
            CSVTabla tabla = CSVParser.get(direccion);

            tabla.anadirFila(parada.getAtributosCSV());
            
            CSVParser.actualizar(tabla);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
