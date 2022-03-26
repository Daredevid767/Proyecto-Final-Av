package logica.persistencia;

/**
 * Interfaz de acceso a datos de Ruta.
 * @author Nicolás Sabogal 25/03/2022
 * @version 1.0
 */
public interface RutaDAO {
    
    /**
     * Devuelve la ruta con el identificador indicado.
     * @param id Identificador de la ruta a recuperar.
     * @return Ruta solicitada.
     */
    public abstract logica.negocio.Ruta get(int id);

    /**
     * Devuelve un arreglo con todas las rutas en la base de datos.
     * @return Un arreglo con todas las rutas en la base de datos.
     */
    public abstract logica.negocio.Ruta[] getTodos();

    /**
     * Elimina de la base de datos la ruta asociada al identificador ingresado.
     * @param id Identificador asociado a la ruta a eliminar.
     */
    public abstract void borrar(int id);

    /**
     * Agrega la ruta solicitada a la base de datos y la asocia al identifiador facilitado.
     * @param id Identificador a asociar.
     * @param ruta Ruta a añadir a la base de datos.
     */
    public abstract void actualizar(int id, logica.negocio.Ruta ruta);

    /**
     * Agrega la ruta a la base de datos y le asigna un nuevo identificador.
     * @param ruta Ruta a añadir a la base de datos.
     */
    public abstract void crear(logica.negocio.Ruta ruta);

}
