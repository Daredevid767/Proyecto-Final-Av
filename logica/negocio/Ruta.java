package logica.negocio;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
  
	private int id;
	private String nombre;
	private String tipoBus;
	private ArrayList<Integer> paradas;
	private ArrayList<Bus> buses;
	private List<Registro> registros;
	private logica.controlador.Controlador control;

	public Ruta(int id,String nombre,String tipoBus,ArrayList<Integer> paradas,ArrayList<Bus> buses,List<Registro> registros){
		this.id= id;
		this.nombre=nombre;
		this.tipoBus=tipoBus;
		this.paradas=paradas;
		this.buses=buses;
		this.registros=registros;
	}

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
	public String getTipoBus(){
		return tipoBus;
	}
	public void setRegistros(Registro reg){
		registros.add(reg);	
	}
}
