package logica.negocio;

import java.util.ArrayList;

/**
 * Clase encargada de recolectar y administrar registros de pago
 * en paradas del sistema de transporte.
 * @author David Nieto, Nicolás Sabogal 26/03/2022
 * @version 0.5
 */
public class Bus implements PuntoAcceso {

	/** parada actual*/
	private Parada parada;

	/** Lista de paradas a recorrer y recolector de historial. */
	private Ruta ruta;
	/** Número de trasbordos generados durante el recorrido de la ruta. */
	private int trasbordosGenerados;
	/** Conjunto de registros de pago generados. */
	private ArrayList<Registro> historial;

	/**
	 * Genera una instancia sin una ruta. Inusable.
	 */
	public Bus () {
		this(null);
	}
	/**
	 * Genera una instancia asociada a la ruta indicada.
	 * @param ruta Ruta contenedora del bus.
	 */
	public Bus (Ruta ruta) {
		this.ruta = ruta;
		this.trasbordosGenerados = 0;
		this.historial = new ArrayList<>();
	}

	/**
	 * Devuelve la lista de registros hechos por la instancia.
	 * @return Un arreglo con los Registros generados por la instancia.
	 */
  	public Registro[] getHistorial (){
		Registro[] historialRegistros = new Registro[this.historial.size()];
		return this.historial.toArray(historialRegistros);
  	}

	/**
	 * Devuelve la ruta contenedora del bus.
	 * @return Un objeto de tipo ruta que contiene al bus.
	 */
	public Ruta getRuta () {
		return ruta;
	}

	/**
	 * Indica que el bus a llegado a una parada.
	 * Si la parada es una estación, intercambia registros con ella.
	 * @param Parada a la que llegó el bus.
	 */
	public void parar (Parada parada) {

		this.parada= parada;
		//TODO ¿Ultima estación visitada?
		if (!(parada instanceof EstacionTransmi))
			return;

		Registro[] registros = ((EstacionTransmi)parada).getEstimados(this);
		Registro registr= new Registro();
		registr.setNombreBus(ruta.getTipoBus());
		ruta.setRegistros(registr);
		for (Registro registro: registros){
			this.historial.add(registro);
		}
			
		
		int cantidad = 0; //TODO
		this.trasbordosGenerados += cantidad;
		((EstacionTransmi)parada).recibirTrasbordos(cantidad);
	}

	// Implementación del método heredado en la interfaz PuntoAcceso.
	@Override
	public boolean cobrar (Tarjeta tarjeta) {
		String servicio = ruta.getTipoBus(); //TODO
		String paradaStr = parada.getNombre();
		Registro registro = tarjeta.pagar(servicio, paradaStr);
		registro.setNombreBus(ruta.getTipoBus());
		ruta.setRegistros(registro);
		return this.historial.add(registro);
	}
  
}
