package logica.negocio;

import java.util.List;
import java.util.ArrayList;

/**
 * Objeto contenedor de información de parada.
 * @author David Nieto, Nicolás Sabogal 26/03/2022
 * @version 0.6
 */
public class Parada {

	/** Identificador de la parada */
	private int id;
	/** Nombre de la parada */
	private String nombre;
	/** Compendio de rutas que visitan la parada. */
	private List<Integer> rutas;
	/** Controlador que administra las clases */
	private logica.controlador.Controlador control;

	/** Crea una Parada con identificador cero y nombre indeterminado. */
	public Parada() {
		this(0, "INDETERMINADO");
	}
	/** Crea una Parada con los identificadores y nombres indicados.
	 * @param id Identificado de la Parada.
	 * @param nombre Nombre de la Parada.
	 */
	public Parada(int id, String nombre) {
		this(id, nombre, null);
	}
	/** Crea una Parada con el id, nombre y lista de rutas indicadas.
	 * @param id Identificador de la Parada.
	 * @param nombre Nombre de la Parada.
	 * @param rutas Lista de rutas que visita la parada.
	 */
	public Parada(int id, String nombre, List<Integer> rutas) {
		this.id = id;
		this.nombre = nombre;
		
		if (rutas == null)
			this.rutas = new ArrayList<>();
		else
			this.rutas = rutas.subList(0, rutas.size());
	}

	/**
	 * Asigna un controlador a la instancia.
	 * @param controlador Controlador a asignar.
	 */
	public void setControlador(logica.controlador.Controlador control) {
		this.control = control;
	}

	/**
	 * Devuelve el identificador de la Parada.
	 * @return Un entero con el identificador de la Parada.
	 */
	public Integer getId() { return this.id; }

	/**
	 * Devuelve el nombre de la Parada.
	 * @return Una cadena con el nombre de la Parada.
	 */
	public String getNombre() { return this.nombre; }

	/**
	 * Devuelve una lista con las rutas que visitan la Parada.
	 * @return Un arreglo de Rutas con las rutas que visitan la parada.
	 */
	public Ruta[] getRutas() {
		return control.getRutas(this.rutas);
	}

	/**
	 * Devuelve una copia de la lista de identificadores de las rutas que visitan la parada.
	 * @return Una lista con los identificadores de las rutas que visitan la parada.
	 */
	public List<Integer> getRutasId() {
		return this.rutas.subList(0, this.rutas.size());
	}

	/**
	 * Retorna los atributos en un arreglo de Strings para que sean procesados y guardados en un archivo csv.
	 * @return Un arreglo de Strings con el valor de los atributos.
	 */
	public String[] getAtributosCSV() {
		String[] atributos = new String[2];

		atributos[0] = nombre;

		atributos[1] = "";
		for (int i = 0; i < this.rutas.size(); i++)
			atributos[1] += rutas.get(i) + ",";
		if (!atributos[1].isEmpty())
			atributos[1] = atributos[3].substring(0, atributos[1].length() - 1);

		return atributos;
	}
	
}
