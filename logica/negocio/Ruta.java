package logica.negocio;

import java.util.ArrayList;

public class Ruta {
  
	private int id;
	private String nombre;
	private ArrayList<Integer> paradas;
	private ArrayList<Bus> buses;
	private logica.controlador.Controlador control;

    public Integer getId() {
        return id;
    }

	/**
	 * Asigna un controlador a la instancia.
	 * @param controlador Controlador a asignar.
	 */
	public void setControlador(logica.controlador.Controlador control) {
		this.control = control;
	}

	public Parada[] getParadas() {
		return this.control.getParadas(this.paradas);
	}
}
