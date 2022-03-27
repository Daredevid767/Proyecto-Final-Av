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
    private String base;

    /** Crea una instancia con una base null, inusable. */
    public ParadaDAO_CSVImpl() {
        this.base = null;
    }
    /**
     * Crea una instancia con la dirección indicada como base.
     * @param direccionBase Dirección de la base de datos a procesar.
     */
    public ParadaDAO_CSVImpl(String direccionBase) {
        this.base = direccionBase;
    }

    /**
     * Construye una instancia de Parada con información recuperada de la base de datos.
     * @param id
     * @param tabla
     * @return
     */
    private logica.negocio.Parada construirParada(int id, logica.persistencia.CSVTabla tabla) {
        String nombre = tabla.getCabezales()[id];
        List<Integer> rutas = new ArrayList<>();

        for (String idRuta: tabla.getColumna(id)) {
            if (idRuta.isEmpty())
                continue;
            rutas.add(Integer.parseInt(idRuta));
        }

        return new logica.negocio.Parada(id, nombre, rutas);
    }

    @Override
    public logica.negocio.Parada get(int id) {
        try {
            CSVTabla tabla = CSVParser.get(base);
            return this.construirParada(id, tabla);
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
            tabla = CSVParser.get(base);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        logica.negocio.Parada[] paradas = new logica.negocio.Parada[tabla.getCabezales().length];
        for (int i = 0; i < tabla.getCabezales().length; i++)
            paradas[i] = this.construirParada(i, tabla);

        return paradas;
    }

    @Override
    public void borrar(int id) {
        try {
            CSVTabla tabla = CSVParser.get(base);
            tabla.eliminarCabezal(id);
            CSVParser.actualizar(tabla);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(int id, logica.negocio.Parada parada) {
        try{
            CSVTabla tabla = CSVParser.get(base);
            
            String[] columna = new String[parada.getRutasId().size()];
            for(int i = 0; i < columna.length; i++)
                columna[i] = parada.getRutasId().get(i).toString();
            
            tabla.setCabezal(id, parada.getNombre());
            tabla.setColumna(id, columna);
            CSVParser.actualizar(tabla);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(logica.negocio.Parada parada) {
        try {
            CSVTabla tabla = CSVParser.get(base);

            String[] columna = new String[parada.getRutasId().size()];
            for(int i = 0; i < columna.length; i++)
                columna[i] = parada.getRutasId().get(i).toString();
            
            int id = tabla.anadirCabezal(parada.getNombre());
            tabla.setColumna(id, columna);
            CSVParser.actualizar(tabla);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
