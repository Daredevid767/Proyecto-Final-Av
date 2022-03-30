package logica.persistencia;

import java.io.IOException;

/**
 * Implementación de la interfaz de Tarifas usando archivos CSV.
 * @author Nicolás Sabogal 30/03/2022
 * @version 1.0
 */
public class TarifasDAO_CSVImpl implements TarifasDAO {

    /** dirección de la base de datos a editar. */
    private String direccion;

    /** Crea una instancia con una base null, inusable. */
    public TarifasDAO_CSVImpl() {
        this.direccion = null;
    }
    /**
     * Crea una instancia con la dirección indicada como base.
     * @param direccion Dirección de la base de datos a procesar.
     */
    public TarifasDAO_CSVImpl(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Busca la fila que representa el servicio indicado en la tabla indicada.
     * @param servicio Nombre del servicio cuya fila buscar.
     * @param tabla Tabla en la cual buscar.
     * @return El número de la fila asociada al servicio buscado.
     */
    private int filaDe (String servicio, CSVTabla tabla) {
        String[] servicios = tabla.getColumna("servicio");
        if (servicios == null)
            return -1;

        int fila;
        for (fila = 0; fila < servicios.length; fila++)
            if (servicios[fila].equals(servicio))
                break;

        if (fila == servicios.length)
            return -1;

        return fila;
    }

    @Override
    public double getTarifa(String servicio, String tipo) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return -1;
        }

        int fila = this.filaDe(servicio, tabla);
        if (fila < 0)
            return -1;

        String tarifa = tabla.getCelda(tipo, fila);
        if (tarifa == null)
            return -1;

        return Double.parseDouble(tarifa);
    }

    @Override
    public double[] getTarifas(String servicio) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }

        int fila = this.filaDe(servicio, tabla);
        if (fila < 0)
            return null;

        String[] filaStrs = tabla.getFila(fila);
        double[] tarifas = new double[filaStrs.length - 1];
        for (int i = 1; i < filaStrs.length; i++) {
            try {
                tarifas[i - 1] = Double.parseDouble(filaStrs[i]);
            }
            catch (NumberFormatException nbe) {
                tarifas[i - 1] = 0;
            }
        }

        return tarifas;
    }

    @Override
    public boolean setTarifa(String servicio, String tipo, double tarifa) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        int fila = this.filaDe(servicio, tabla);
        if (fila < 0)
            return false;
        
        if (!tabla.setCelda(tipo, fila, Double.toString(tarifa)))
            return false;
        
        try {
            CSVParser.actualizar(tabla);
            return true;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setTarifas(String servicio, double[] tarifas) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        if (tarifas.length + 1 != tabla.getCabezales().length)
            return false;

        int fila = this.filaDe(servicio, tabla);
        if (fila < 0)
            return false;

        String[] tarifasStrs = new String[tarifas.length + 1];
        tarifasStrs[0] = servicio;
        for (int i = 0; i < tarifas.length; i++)
            tarifasStrs[i + 1] = Double.toString(tarifas[i]);
            
        if (!tabla.setFila(fila, tarifasStrs))
            return false;
 
        try {
            CSVParser.actualizar(tabla);
            return true;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    @Override
    public String[] getServicios() {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        
        return tabla.getColumna("servicio");
    }

    @Override
    public String[] getTipos() {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }

        String[] cabezales = tabla.getCabezales();
        String[] tipos = new String[cabezales.length - 1];
        for (int i = 0; i < tipos.length; i++)
            tipos[i] = cabezales[i + 1];

        return tipos;
    }

    @Override
    public boolean anadirServicio(String servicio) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        int indexServicio = this.filaDe(servicio, tabla);
        if (indexServicio >= 0)
            return false;

        String[] fila = new String[tabla.getCabezales().length];
        fila[0] = servicio;
        
        if (!tabla.anadirFila(fila))
            return false;
        
        try {
            CSVParser.actualizar(tabla);
            return true;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean anadirServicio(String servicio, double[] tarifas) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        int indexServicio = this.filaDe(servicio, tabla);
        if (indexServicio >= 0)
            return false;

        if (tarifas.length + 1 != tabla.getCabezales().length)
            return false;

        String[] fila = new String[tabla.getCabezales().length];
        fila[0] = servicio;
        for (int i = 0; i < tarifas.length; i++)
            fila[i + 1] = Double.toString(tarifas[i]);
        
        if (!tabla.anadirFila(fila))
            return false;
        
        try {
            CSVParser.actualizar(tabla);
            return true;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean borrarServicio(String servicio) {
        CSVTabla tabla = null;
        try {
            tabla = CSVParser.get(this.direccion);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        int indexServicio = this.filaDe(servicio, tabla);
        if (indexServicio < 0)
            return false;
        
        if (!tabla.borrarFila(indexServicio))
            return false;
        
        try {
            CSVParser.actualizar(tabla);
            return true;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    
}
