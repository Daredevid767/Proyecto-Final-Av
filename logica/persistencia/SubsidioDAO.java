package logica.persistencia;

/**
 * Interfaz de acceso a datos de Subsidio.
 * @author Nicolás Sabogal 25/03/2022
 * @version 1.0
 */
public interface SubsidioDAO {
    
    /**
     * Devuelve el subsidio con el identificador indicado.
     * @param id Identificador del subsidio a recuperar.
     * @return Subsidio solicitado.
     */
    public abstract logica.negocio.Subsidio get(int id);

    /**
     * Devuelve un arreglo con todos los subsidios en la base de datos.
     * @return Un arreglo con todos los subisdios en la base de datos.
     */
    public abstract logica.negocio.Subsidio[] getTodos();

    /**
     * Elimina de la base de datos el subsidio asociado al identificador ingresado.
     * @param id Identificador asociado al subsidio a eliminar.
     */
    public abstract void borrar(int id);

    /**
     * Agrega el subsidio solicitado a la base de datos y lo asocia al identifiador facilitado.
     * @param id Identificador a asociar.
     * @param subsidio Subsidio a añadir a la base de datos.
     */
    public abstract void actualizar(int id, logica.negocio.Subsidio subsidio);

    /**
     * Agrega el subsidio a la base de datos y le asigna un nuevo identificador.
     * @param subsidio Subsidio a añadir a la base de datos.
     */
    public abstract void crear(logica.negocio.Subsidio subsidio);

}
