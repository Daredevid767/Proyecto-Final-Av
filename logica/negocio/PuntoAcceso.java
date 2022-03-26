package logica.negocio;

/**
 * Interfaz que permite procesar pagos a través de una tarjeta.
 * @author Nicolás Sabogal 26/03/2022
 * @version 1.0
 */
@FunctionalInterface
public interface PuntoAcceso {

	/**
	 * Indica a la tarjeta que debe realizar un pago.
	 * @param tarjeta Tarjeta a la cual cobrar.
	 * @return True si el pago fue realizado sin problemas.
	 */
	public abstract boolean cobrar (Tarjeta tarjeta);
	
}
