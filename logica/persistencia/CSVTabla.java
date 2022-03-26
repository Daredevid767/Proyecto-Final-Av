package logica.persistencia;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase contenedora de información proveniente de un archivo CSV.
 * @author Nicolás Sabogal 23/03/2022
 * @version 1.0
 */
public class CSVTabla {

    /** Cabezales o indicadores de la natrualeza de los datos contenidos en cada columna de la tabla. */
    private String[] cabezales;
    /** Tabla cuya cada celda de la cada fila se relaciona con en cabezal en la posición de su columna. */
    private List<List<String>> tabla;
    /** Dirección donde se almacenará el archivo CSV. */
    private String direccion;

    /**
     * Genera una CSVTabla con refencias null, inusable.
     */
    public CSVTabla() {
        this(null, null);
    }
    /**
     * Genera una CSVTabla con dirección null y la tabla especificada.
     * @param tablaIn Una matriz nxm de Strings.
     */
    public CSVTabla(List<List<String>> tablaIn) {
        this(null, tablaIn);
    }
    /**
     * Genera una tabla con una dirección determinada y una tabla de datos determinados.
     * @param dirIn Ruta donde se almacena el archivo.
     * @param tablaIn Una matriz nxm de Strings.
     */
    public CSVTabla(String dirIn, List<List<String>> tablaIn) {

        this.direccion = dirIn;

        if (tablaIn == null) {
            this.cabezales = null;
            this.tabla = null;
            return;
        }

        this.cabezales = new String[tablaIn.get(0).size()];
        for (int i = 0; i < tablaIn.get(0).size(); i++)
            this.cabezales[i] = this.checkRepetido(0, tablaIn.get(0).get(i));
        
        this.tabla = new ArrayList<>();
        for (int i = 1; i < tablaIn.size(); i++) {

            List<String> fila = new ArrayList<>();
            for (int j = 0; j < tablaIn.get(i).size(); j++)
                fila.add(tablaIn.get(i).get(j));

            tabla.add(fila);
        }
    }

    /**
     * Revisa si el cabezal ingresado está en la lista de cabezales propia y devuelve un cabezal con un nombre con un contador si es así.
     * @param cantidad Número de veces que se ha encontrado repetido el cabezal especificado.
     * @param cabezal Cabezal a buscar en la lista de cabezales.
     * @return Un cabezal único con un contador junto al cabezal repetido, en caso de ser necesario.
     */
    private String checkRepetido(int cantidad, String cabezal) {
        String cabezal_rep = cabezal;
        if (cantidad > 0)
            cabezal_rep += " (" + cantidad + ")";

        if (this.buscarCabezal(cabezal_rep) >= 0)
            return checkRepetido(cantidad + 1, cabezal);
        
        return cabezal_rep;
    }

    /**
     * Busca la cadena ingresada en la lista de cabezales propia.
     * @param cabezal String a buscar en el arreglo cabezales.
     * @return El indice del cabezal buscado o -1 si no fue encontrado.
     */
    private int buscarCabezal(String cabezal) {
        int index;
        for (index = 0; index < this.cabezales.length; index++)
            if (cabezal.equals(this.cabezales[index]))
                break;
        
        if (index == this.cabezales.length)
            return -1;
        else
            return index;
    }

    /**
     * Devuelve el número de filas (sin contar los cabezales) de la tabla.
     * @return Número de filas excluyendo cabezales.
     */
    public int getCantidadFilas() {
        return this.tabla.size();
    }

    /**
     * Devuelve la dirección donde será almacenado el archivo.
     * @return Dirección donde se almacenará el archivo.
     */
    public String getDireccion() {
        return this.direccion;
    }

    /**
     * Devuelve una lista de Strings con los cabezales de la tabla.
     * @return Lista de cabezales de la tabla.
     */
    public String[] getCabezales() {
        return cabezales.clone();
    }

    /**
     * Busca la cadena ingresada entre los cabezales y devuelve toda su columna si es encontrada.
     * @param cabezal String asociada a la columna a consultar.
     * @return Un arreglo con los registros de la columna consultada o null si el cabezal no fue encontrado.
     */
    public String[] getColumna(String cabezal) {
        return this.getColumna(this.buscarCabezal(cabezal));        
    }

    /**
     * Regresa un arreglo con las celdas en la columna asociada al cabezal en la pocisión indicada.
     * @param cabezal Número de posición del cabezal en la lista de cabezales.
     * @return Un arreglo con las cadenas solicitadas o null si el índice está fuera de los límites.
     */
    public String[] getColumna(int cabezal) {
        if (    cabezal < 0
             || cabezal >= this.cabezales.length )
            return null;

        String[] columna = new String[this.tabla.size()];
        for (int i = 0; i < columna.length; i++)
            columna[i] = tabla.get(i).get(cabezal);
            
        return columna;
    }

    /**
     * Devuelve un arreglo con todas las celdas en la filda indicada.
     * @param index Posición de la fila a solicitar.
     * @return Un arreglo con las celdas solicitadas o null si el índice indicado está fuera de los límites.
     */
    public String[] getFila(int index) {
        if (    index < 0
             || index >= this.tabla.size() )
            return null;

        String[] fila = new String[this.tabla.get(index).size()];
        return this.tabla.get(index).toArray(fila);
    }

    /**
     * Devuelve la celda en la fila indicada asociada al cabezal indicado.
     * @param cabezal Un cabezal contendio en la lista de cabezales.
     * @param fila Posición de la fila a consultar.
     * @return La celda solicitada o null si el cabezal no se encontró o la fila está fuera de los límites.
     */
    public String getCelda(String cabezal, int fila) {
        return this.getCelda(this.buscarCabezal(cabezal), fila);
    }

    /**
     * Devuelve la celda de la fila en la posición indicada.
     * @param cabezal Índice del cabezal asociado en la lista de cabezales.
     * @param fila Posición de la fila a consultar.
     * @return La celda solicitada o null si alguno de los índices indicado está fuera de los límites.
     */
    public String getCelda(int cabezal, int fila) {
        if (    cabezal < 0
             || cabezal >= tabla.size() )
            return null;

        if (    fila < 0
             || fila >= tabla.get(cabezal).size() )
            return null;

        return this.tabla.get(cabezal).get(fila);
    }

}
