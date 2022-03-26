package logica.persistencia;

/**
 * Interfaz de acceso a datos de Parada.
 * @author Nicolás Sabogal 25/03/2022
 * @version 1.0
 */
public interface ParadaDAO {
    
    /**
     * Devuelve la parada con el identificador indicado.
     * @param id Identificador de la parada a recuperar.
     * @return Parada solicitada.
     */
    public abstract logica.negocio.Parada get(int id);

    /**
     * Devuelve un arreglo con todas las paradas en la base de datos.
     * @return Un arreglo con todas las paradas en la base de datos.
     */
    public abstract logica.negocio.Parada[] getTodos();

    /**
     * Elimina de la base de datos la parada asociada al identificador ingresado.
     * @param id Identificador asociado a la parada a eliminar.
     */
    public abstract void borrar(int id);

    /**
     * Agrega la parada solicitada a la base de datos y la asocia al identifiador facilitado.
     * @param id Identificador a asociar.
     * @param parada Parada a añadir a la base de datos.
     */
    public abstract void actualizar(int id, logica.negocio.Parada parada);

    /**
     * Agrega la parada a la base de datos y le asigna un nuevo identificador.
     * @param parada Parada a añadir a la base de datos.
     */
    public abstract void crear(logica.negocio.Parada parada);

}
