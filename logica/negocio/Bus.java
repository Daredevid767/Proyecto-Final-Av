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
	private Parada paradaActual;

	/** pasajeros actuales*/
	private int pasajerosActuales;

	/** pasajeros que se han bajado*/
	private int pasajerosQueSeHanBajado;


	/** Lista de paradas a recorrer y recolector de historial. */
	private Ruta ruta;
	/** Conjunto de registros de pago generados. */
	private int registrosRecibidos;

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
		this.registrosRecibidos = 0;
		this.pasajerosActuales = 0;
		this.pasajerosQueSeHanBajado=0;
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
		/**Es la cantidad de personas que salen del bus en la parada actual*/
		int salientes=0;
		/**se define la parada actual*/
		this.paradaActual= parada;

		if (	parada instanceof EstacionTransmi 
		     && (parada!=(ruta.getParadas()[ruta.getParadas().length - 1])) ) {
			
			Registro[] entrantes = parada.getEntrantes(this);
			
			regitrosRecibidos += entrantes.length;
			for (Registro entrante: entrantes) 
				ruta.recibirRegistro(entrante, this);
		}
		
		/**Se calcula la cantidad de pasajeros que se bajan
		*/
		
		pasajerosActuales = registrosRecibidos - pasajerosQueSeHanBajado;
		
		salientes=(pasajerosActuales/(ruta.getParadasRestantes(parada)));

		pasajerosQueSeHanBajado += salientes;
		
		
		if (!(parada instanceof EstacionTransmi))
			return;

		int trasbordos = 0;
		for (int i = 0; i < salientes; i++)
			if (Math.random() < parada.getPosiblidadTrasbordo())
				trasbordos++;
		
		((EstacionTransmi)parada).recibirTrasbordos(trasbordos);
	}

	// Implementación del método heredado en la interfaz PuntoAcceso.
	@Override
	public boolean cobrar (Tarjeta tarjeta) {
		registrosRecibidos+=1;
		String servicio = ruta.getTipoBus();
		String paradaStr = paradaActual.getNombre();
		double precio= ruta.getPrecio(tarjeta.getTipoPago());
		Registro registro = tarjeta.pagar(servicio, paradaStr,precio);
		registro.setNombreBus(ruta.getTipoBus());
		ruta.setRegistros(registro);
		return this.historial.add(registro);
	}
  
}
