package logica.negocio;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
  
	private int id;
	private String nombre;
	private String tipoBus;
	private ArrayList<Integer> paradas;
	private ArrayList<Bus> buses;
	private List<Registro> historial;
	private logica.controlador.Controlador control;

	public Ruta(int id,String nombre,String tipoBus,ArrayList<Integer> paradas,ArrayList<Bus> buses,List<Registro> registros){
		this.id= id;
		this.nombre=nombre;
		this.tipoBus=tipoBus;
		this.paradas=paradas;
		this.buses=buses;
		this.historial=registros;
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
		historial.add(reg);	
	}

	/**
	 * Retorna los atributos en un arreglo de Strings para que sean procesados y guardados en un archivo csv.
	 * @return Un arreglo de Strings con el valor de los atributos.
	 */
	public String[] getAtributosCSV() {
		String[] atributos = new String[4];

		atributos[0] = this.nombre;
		atributos[1] = this.tipoBus;
		atributos[2] = Integer.toString(this.buses.size());

		atributos[3] = "";
		for (int i = 0; i < this.paradas.size(); i++)
			atributos[3] += this.paradas.get(i) + ',';
		if (!atributos[3].isEmpty())
			atributos[3] = atributos[3].substring(0, atributos[3].length() - 1);

		return atributos;
	}

	/**
	 * Retorna los atributos en un arreglo de arreglos de strings con los registros para ser almacenados en un archivo csv.
	 * @return Un arreglo de Strings con el valor de los atributos.
	 */
	public String[][] getHistorialCSV() {
		String[][] registros = new String[this.historial.size()][];
		for (int i = 0; i < registros.length; i++)
			registros[i] = this.historial.get(i).getAtributosCSV();
		
		return registros;
	}

	/**
	 * Recibe un registro y lo añade al historial.
	 * @param registro Registro a añadir.
	 * @return Verdadero, si el registro fue añadido.
	 */
	public boolean recibirRegistro(Registro registro) {
		return this.historial.add(registro);
	}
}
