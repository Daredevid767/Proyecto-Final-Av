package logica.negocio;

import java.util.ArrayList;

/**
 * Clase que representa una estación de transmilenio.
 * @author David Nieto, Nicolás Sabogal 26/03/2022
 * @version 0.5
 */
public class EstacionTransmi extends Parada implements PuntoAcceso {

	/** 
	 * Lista que contiene registros para asignar a los buses
	 * que visiten la estación.
	 */
	private ArrayList<Registro> paraAsignar;

	/**
	 * Devuelve una lista estimada de los usuarios que pudieron
	 * abordar el bus que visitó la parada basada en la cantidad
	 * de rutas que visitan la parada y la cantidad de buses
	 * en cada ruta.
	 * @param bus Bus sobre el cual calcular el estimado.
	 * @return Una lista estimada de registros que pudieron abordar el bus.
	 */
	public Registro[] getEstimados (Bus bus){
		return null; //TODO
	}

	/**
	 * Genera registros de trasbordo para asignarlos a buses.
	 * @cantidad Número de registros de trasbordo a generar.
	 */
	public void recibirTrasbordos (int cantidad){
    	for (int i = 0; i < cantidad; i++)
			this.paraAsignar.add(new Registro());
	}

	// Implementación del método heredado de la interfaz PuntoAcceso.
	@Override
	public boolean cobrar (Tarjeta tarjeta) {
		String servicio = ""; //TODO
		String paradaStr= this.getNombre();
		Registro registro = tarjeta.pagar(servicio, paradaStr);
		return this.paraAsignar.add(registro);
	}

}
