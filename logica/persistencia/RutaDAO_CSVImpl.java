package logica.persistencia;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz RutaDAO usando archivos CSV.
 * @author Nicolás Sabogal 29/03/2022
 * @version 1.0
 */
public class RutaDAO_CSVImpl implements RutaDAO {

    /** dirección de la base de datos a editar. */
    private String direccion;

    /** Crea una instancia con una base null, inusable. */
    public RutaDAO_CSVImpl() {
        this.direccion = null;
    }
    /**
     * Crea una instancia con la dirección indicada como base.
     * @param direccion Dirección de la base de datos a procesar.
     */
    public RutaDAO_CSVImpl(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Permite obtener la carpeta contendera del archivo de la base de datos.
     * @return Una cadena representando la carpeta contenedora de la base de datos.
     */
    private String getCarpeta() {
        String reversa = "";
        for (int i = 0; i < this.direccion.length(); i++)
            reversa += this.direccion.charAt(this.direccion.length() - 1 - i);

        int index;
        for (index = 0; index < reversa.length(); index++)
            if (reversa.charAt(index) == '\\' || reversa.charAt(index) == '/')
                break;

        if(index == reversa.length())
            return "";
        
        return this.direccion.substring(0, this.direccion.length() - index);
    }

    /**
     * Construye una instancia de Ruta con información recuperada de la base de datos.
     * @param id Identificador de la ruta a recuperar.
     * @param tabla Una tabla conteniendo la información de la ruta.
     * @return Una instancia de la clase Ruta con la información recuperada.
     * @throws IOException Si alguno de los archivos csv relacionados no es encontrado.
     */
    private logica.negocio.Ruta construirRuta(int id, CSVTabla tabla) throws IOException {
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
        String tipoBus = tabla.getCelda("tipoBus", id);
        int numBuses = Integer.parseInt(tabla.getCelda("numBuses", id));

        List<Integer> paradas = new ArrayList<>();
        String[] paradasStr = tabla.getCelda("paradas", id).split(",");
        for (String paradaId: paradasStr) 
            paradas.add(Integer.parseInt(paradaId));

        String dirTabla = this.getCarpeta() + nombre + ".csv";
        List<logica.negocio.Registro> historial;
        try {
            historial = construirHistorial(CSVParser.get(dirTabla));
        }
        catch (IOException ioe) {
            historial = new ArrayList<>();
        }

        return new logica.negocio.Ruta(id, nombre, tipoBus, numBuses, paradas, historial);
    }

    /**
     * Recobra el historial de la Ruta determinada en la carpeta de la base.
     * @param id Identificador de la Ruta de la cual recobrar el historial.
     * @return Una lista de registros.
     * @throws IOException Si el archivo correspondiente no se encuentra en la carpeta.
     */
    private List<logica.negocio.Registro> construirHistorial (CSVTabla tabla) throws IOException {
        List<logica.negocio.Registro> historial = new ArrayList<>();
        for (int i = 0; i < tabla.getCantidadFilas(); i++) {

            int bus = Integer.parseInt(tabla.getCelda("bus", i));
            Instant instante = Instant.parse(tabla.getCelda("instante", i));
            double valor = Double.parseDouble(tabla.getCelda("valor", i));
            boolean subsidio = Boolean.parseBoolean(tabla.getCelda("subsidio", i));
            String nombreParada = tabla.getCelda("nombreParada", i);
            boolean transbordo = Boolean.parseBoolean(tabla.getCelda("trasbordo", i));

            historial.add(new logica.negocio.Registro(bus, instante, valor, subsidio, nombreParada, transbordo));
        }

        return historial;
    }

    @Override
    public logica.negocio.Ruta get(int id) {
        try {
            return this.construirRuta(id, CSVParser.get(this.direccion));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public logica.negocio.Ruta[] getTodos() {
        CSVTabla tabla;
        try {
            tabla = CSVParser.get(this.direccion);

            logica.negocio.Ruta[] rutas = new logica.negocio.Ruta[tabla.getCantidadFilas()];
            for (int i = 0; i < tabla.getCantidadFilas(); i++)
                rutas[i] = this.construirRuta(i, tabla);

            return rutas;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void borrar(int id) {
        try {
            CSVTabla tabla = CSVParser.get(this.direccion);
            
            File historial = new File(this.getCarpeta() + tabla.getCelda("nombre", id) + ".csv");
            historial.delete();

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
    public void actualizar (int id, logica.negocio.Ruta ruta) {
        try{
            CSVTabla tabla = CSVParser.get(this.direccion);
            
            tabla.setFila(id, ruta.getAtributosCSV());
            
            String[] cabezalesHistorial = {"bus", "instante", "valor", "subsidio", "nombreParada", "trasbordo"};
            CSVTabla historial = new CSVTabla(cabezalesHistorial);
            String[][] historialArr = ruta.getHistorialCSV();
            for (String[] registro: historialArr)
                historial.anadirFila(registro);
            
            String dirHistorial = this.getCarpeta() + ruta.getNombre() + ".csv";
            CSVParser.crear(dirHistorial, historial);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(logica.negocio.Ruta ruta) {
        try {
            CSVTabla tabla = CSVParser.get(this.direccion);
            tabla.anadirFila(ruta.getAtributosCSV());
            CSVParser.actualizar(tabla);
            
            String[] cabezalesHistorial = {"bus", "instante", "valor", "subsidio", "nombreParada", "trasbordo"};
            CSVTabla historial = new CSVTabla(cabezalesHistorial);
            String[][] historialArr = ruta.getHistorialCSV();
            for (String[] registro: historialArr)
                historial.anadirFila(registro);
            
            String dirHistorial = this.getCarpeta() + ruta.getNombre() + ".csv";
            CSVParser.crear(dirHistorial, historial);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
