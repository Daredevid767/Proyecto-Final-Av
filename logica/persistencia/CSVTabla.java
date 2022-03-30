package logica.persistencia;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase contenedora de información proveniente de un archivo CSV.
 * @author Nicolás Sabogal 29/03/2022
 * @version 1.7
 */
public class CSVTabla {

    /** Cabezales o indicadores de la natrualeza de los datos contenidos en cada columna de la tabla. */
    private List<String> cabezales;
    /** Tabla cuya cada celda de la cada fila se relaciona con en cabezal en la posición de su columna. */
    private List<List<String>> tabla;
    /** Dirección donde se almacenará el archivo CSV. */
    private String direccion;

    /**
     * Genera una CSVTabla con dirección null y tabla vacía.
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

        boolean tablaVacia = false;
        if (tablaIn == null)
            tablaVacia = true;

        if (tablaIn.size() == 0 || tablaVacia) {
            tablaIn = new ArrayList<>();
            tablaIn.add(new ArrayList<>());
            tablaIn.get(0).add("");
        }

        this.cabezales = new ArrayList<>();
        for (int i = 0; i < tablaIn.get(0).size(); i++)
            this.cabezales.add(this.checkRepetido(0, tablaIn.get(0).get(i)));
        
        this.tabla = new ArrayList<>();
        for (int i = 1; i < tablaIn.size(); i++) {

            List<String> fila = new ArrayList<>();
            for (int j = 0; j < tablaIn.get(i).size(); j++)
                fila.add(tablaIn.get(i).get(j));

            tabla.add(fila);
        }
    }

    public CSVTabla(String[] cabezales) {
        this.cabezales = Arrays.asList(cabezales);
        this.tabla = new ArrayList<>();
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
        if (cabezal.equals(""))
            return -1;

        int index;
        for (index = 0; index < this.cabezales.size(); index++)
            if (cabezal.equals(this.cabezales.get(index)))
                break;
        
        if (index == this.cabezales.size())
            return -1;
        else
            return index;
    }

    /**
     * Remplaza el cabezal en la posición indicada.
     * @param index Índice del cabezal a remplazar.
     * @param cabezal Cadena a ingresar en la posición indicada.
     * @return False si el índice está fuera de los límites. Verdadero si el cambio fue exitoso.
     */
    public boolean setCabezal (int index, String cabezal) {
        if (index < 0 || index >= this.cabezales.size())
            return false;
        
        if (cabezal == null)
            cabezal = "";

        this.cabezales.set(index, cabezal);
        return true;
    }

    /**
     * Añade el cabezal indicado a la lista de cabezales y crea una columna para él.
     * @param cabezal Cabezal a agregar a la lista.
     * @return el número de la posición del cabezal creado.
     */
    public int anadirCabezal (String cabezal) {
        if (cabezal.equals(""))
            return -1;

        for (int i = 0; i < this.cabezales.size(); i++)
            if (this.cabezales.get(i).equals("")) {
                this.cabezales.set(i, cabezal);
                return i;
            }

        this.cabezales.add(this.checkRepetido(0, cabezal));
        for (List<String> fila: this.tabla)
            fila.add("");
        
        return this.cabezales.size() - 1;
    }

    /**
     * Elimina el cabezal indicado y la columna asociada a él.
     * @param cabezal
     * @return
     */
    public boolean eliminarCabezal (int cabezal) {
        if (cabezal < 0 || cabezal >= this.cabezales.size())
            return false;
        
        this.cabezales.set(cabezal, "");
        for(List<String> fila: this.tabla)
            fila.set(cabezal, "");

        return true;
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
        String[] retorno = new String[this.cabezales.size()];
        return this.cabezales.toArray(retorno);
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
             || cabezal >= this.cabezales.size() )
            return null;

        String[] columna = new String[this.tabla.size()];
        for (int i = 0; i < columna.length; i++)
            columna[i] = tabla.get(i).get(cabezal);
            
        return columna;
    }

    /**
     * Busca la cadena ingresada entre los cabezales y remplaza toda su columna si es encontrada.
     * @param cabezal String asociada a la columna a consultar.
     * @param columna Arreglo de cadenas a ingresar en la base de datos.
     * @return Si el cambio fue hecho o no.
     */
    public boolean setColumna(String cabezal, String[] columna) {
        return this.setColumna(this.buscarCabezal(cabezal), columna);        
    }

    /**
     * Remplaza la columna indicada por la facilitada.
     * @param cabezal Número de posición del cabezal asociado a la columna a modificar.
     * @param columna Arreglo de cadenas a ingresar en la base de datos.
     * @return Si el cambio fue hecho o no. False si el cabezal en la posición indicada está vacío.
     */
    public boolean setColumna(int cabezal, String[] columna) {
        if (    cabezal < 0
             || cabezal >= this.cabezales.size())
            return false;

        if (this.cabezales.get(cabezal).isEmpty())
            return false;

        while (this.tabla.size() < columna.length) {
            this.tabla.add(new ArrayList<>());
            for (int i = 0; i < this.cabezales.size(); i++)
                this.tabla.get(this.tabla.size() - 1).add("");
        }

        for (int i = 0; i < this.tabla.size(); i++) {
            if (i >= columna.length) {
                this.tabla.get(i).set(cabezal, "");
                continue;
            }

            if (columna[i] == null)
                columna[i] = "";

            this.tabla.get(i).set(cabezal, columna[i]);
        }

        return true;
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
     * Remplaza la fila indicada por la fila ingresada.
     * @param index Número de la fila a remplazar.
     * @param fila Un arreglo de cadenas.
     * @return Falso si el índice está fuera de los límites o si el número de cadenas en la fila no coincide
     * con el número de cabezales. Verdadero si el cambio fue exitoso.
     */
    public boolean setFila(int index, String[] fila) {
        if (index < 0 || index >= this.tabla.size())
            return false;
        if (fila.length != this.cabezales.size())
            return false;

        for (int i = 0; i < this.cabezales.size(); i++)
            this.tabla.get(index).set(i, fila[i]);
        
        return true;
    }

    /**
     * Añade la fila indicada a la tabla.
     * @param fila Un arreglo de cadenas.
     * @return Falso si el número de cadenas no coincide con el número de cabezales en la tabla.
     * Verdadero si el cambio es exitoso.
     */
    public boolean anadirFila(String[] fila) {
        if (fila.length != this.cabezales.size())
            return false;

        List<String> filaList = new ArrayList<>(fila.length);
        for (int i = 0; i < fila.length; i++) {
            if (fila[i] == null)
                fila[i] = "";

            filaList.add(fila[i]);
        }

        return this.tabla.add(filaList);
    }

    /**
     * Devuelve la celda en la fila indicada asociada al cabezal indicado.
     * @param cabezal Un cabezal contendio en la lista de cabezales.
     * @param fila Posición de la fila a consultar.
     * @return La celda solicitada o null si el cabezal no se encontró o la fila está fuera de los límites.
     */
    public String getCelda (String cabezal, int fila) {
        return this.getCelda(this.buscarCabezal(cabezal), fila);
    }

    /**
     * Devuelve la celda de la fila en la posición indicada.
     * @param cabezal Índice del cabezal asociado en la lista de cabezales.
     * @param fila Posición de la fila a consultar.
     * @return La celda solicitada o null si alguno de los índices indicado está fuera de los límites.
     */
    public String getCelda (int cabezal, int fila) {
        if (    cabezal < 0 || cabezal >= this.cabezales.size()
             ||    fila < 0 ||    fila >= this.tabla.size()     )
            return null;

        return this.tabla.get(fila).get(cabezal);
    }

    /**
     * Remplaza la celda en la posición indicada.
     * @param cabezal Índice del cabezal asociado a la columna indicada.
     * @param fila Índice de la fila indicada.
     * @param cadena Cadena a instertar en la posición indicada.
     * @return Falso si alguno de los índices se sale de los límites o el cabezal está vacío.
     * Verdadero si el cambio fue exitoso.
     */
    public boolean setCelda (int cabezal, int fila, String cadena) {
        if (    cabezal < 0 || cabezal >= this.cabezales.size()
             ||    fila < 0 ||    fila >= this.tabla.size()     )
            return false;

        if (this.cabezales.get(cabezal).isEmpty())
            return false;

        if (cadena == null)
            cadena = "";

        this.tabla.get(fila).set(cabezal, cadena);
        return true;
    }

    /**
     * Remplaza la celda en la posición indicada.
     * @param cabezal Índice del cabezal asociado a la columna indicada.
     * @param fila Índice de la fila indicada.
     * @param cadena Cadena a instertar en la posición indicada.
     * @return Falso si alguno de los índices se sale de los límites o el cabezal está vacío o no es encontrado.
     * Verdadero si el cambio fue exitoso.
     */
    public boolean setCelda (String cabezal, int fila, String cadena) {
        int index = this.buscarCabezal(cabezal);
        if (index < 0)
            return false;
        return this.setCelda(index, fila, cadena);
    }

}
