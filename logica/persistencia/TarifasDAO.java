package logica.persistencia;

/**
 * Servicio dedicado a la obtención de servicios y tarifas.
 * @author Nicolás Sabogal 29/03/2022
 * @version 1.0
 */
public interface TarifasDAO {
    
    /**
     * Obtiene una tarifa de un servicio y tipo determinado.
     * @param servicio Nombre del servicio a consultar.
     * @param tipo Tipo de la tarifa a consultar.
     * @return El valor de la tarifa consultada. -1, si la tarifa no pudo ser recuperada.
     */
    public abstract double getTarifa (String servicio, String tipo);
    /**
     * Obtiene todas las tarifas de un servicio determiado.
     * @param servicio Nombre del servicio a consultar.
     * @return Un arreglo con los valores de las tarifas asociadas al servicio.
     */
    public abstract double[] getTarifas (String servicio);

    /**
     * Establece el valor de una tarifa de servicio y tipo determinado.
     * @param servicio Nombre del servicio sobre el cual modificar la tarifa.
     * @param tipo Tipo de la tarifa a la cual modificar el valor.
     * @param tarifa Nuevo valor de la tarifa.
     * @return Verdadero si el cambio es satisfactorio.
     */
    public abstract boolean setTarifa (String servicio, String tipo, double tarifa);
    /**
     * Establece el valor a todas la tarifas asociadas a un servicio.
     * @param servicio Nombre del servicio sobre el cual modificar las tarifas.
     * @param tarifas Arreglo con los nuevos valores para las tarifas.
     * @return Verdadero si el cambio fue satisfactorio.
     */
    public abstract boolean setTarifas (String servicio, double[] tarifas);

    /**
     * Devuelve una lista con todos los servicios disponibles.
     * @return Una arreglo de Strings con los nombres de los servicios.
     */
    public abstract String[] getServicios ();
    /**
     * Devuelve una lista con todos los tipos de tarifa disponibles.
     * @return Un arreglo de Strings con los nombres de los tipos de tarifa.
     */
    public abstract String[] getTipos();

    /**
     * Añade el servicio indicado a la tabla de tarifas y asigna cero a cada tarifa asociada.
     * @param servicio Nombre del servicio a agregar.
     * @return Verdadero si el el servicio fue añadido correctamente.
     */
    public abstract boolean anadirServicio (String servicio);
    /**
     * Añade el servicio a la tabla de tarifas con las tarifas indicadas.
     * @param servicio Nombre del servicio a agregar.
     * @param tarifas Valor de las tarifas asociadas según los tipos.
     * @return Verdadero si el servicio fue añadido correctamente.
     */
    public abstract boolean anadirServicio (String servicio, double[] tarifas);

    /**
     * Borra el servicio indicado de la tabla de tarifas.
     * @param servicio Servicio a eliminar de la tabla de tarifas.
     * @return Verdadero si el servicio fue eliminado correctamente.
     */
    public abstract boolean borrarServicio (String servicio);

}
