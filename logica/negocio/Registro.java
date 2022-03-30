package logica.negocio;

import java.time.Instant;

public class Registro {

	private int bus;
	private Instant instante;
	private double valor;
	private boolean subsidio;
	private String nombreParada;
	private boolean trasbordo;

	public Registro(String nombreParada) {
		this(0, null, 0, false, nombreParada, true);
	}
	
	public Registro(double valor, boolean subsidio, String nombreParada, boolean trasbordo) {
		this(0, null, valor, subsidio, nombreParada, trasbordo);
	}

	public Registro(int bus, Instant instante, double valor, boolean subsidio, String nombreParada, boolean trasbordo){
		this.bus = bus;
		this.instante = (instante == null ? Instant.now() : instante);
		this.valor= valor;
		this.subsidio = subsidio;
		this.nombreParada = nombreParada;
		this.trasbordo= trasbordo;
	}

	/**
	 * Asigna el número de bus que hizo el registro.
	 * @param id Número del bus que hizo el registro.
	 * @return Verdadero si el número es igual o mayor a cero.
	 */
	public boolean setBus (int id) {
		if (id < 0)
			return false;
		this.bus = id;
		return true;
	}

	/**
	 * Retorna los atributos en un arreglo de Strings para que sean procesados y guardados en un archivo csv.
	 * @return Un arreglo de Strings con el valor de los atributos.
	 */
	public String[] getAtributosCSV() {
		String[] atributos = new String[6];

		atributos[0] = Integer.toString(this.bus);
		atributos[1] = this.instante.toString();
		atributos[2] = Double.toString(this.valor);
		atributos[3] = Boolean.toString(this.subsidio);
		atributos[4] = this.nombreParada;
		atributos[5] = Boolean.toString(this.trasbordo);

		return atributos;
	}
	
	//TODO Constructor por defecto crea trasbordos.
}
