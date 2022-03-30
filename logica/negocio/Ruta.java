package logica.negocio;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
  
	private int id;
	private String nombre;
	private String tipoBus;
	private List<Bus> buses;
	private List<Integer> paradas;
	private List<Registro> historial;
	private logica.controlador.Controlador control;

	public Ruta(int id, String nombre, String tipoBus, int numBuses, List<Integer> paradas, List<Registro> historial){
		this.id= id;
		this.nombre=nombre;
		this.tipoBus=tipoBus;

		this.buses = new ArrayList<>();
		for (int i = 0; i < numBuses; i++)
			this.buses.add(new Bus());

		this.paradas=paradas;
		this.historial = historial;
	}

    public Integer getId() {
        return id;
    }

	public String getNombre() {
		return this.nombre;
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

	public double getPrecio(String tipoPago) {
	    String servicio;
	    switch (this.tipoBus){
			case "Troncal":
				servicio="TRONCAL";
				break;
			case "Dual" :
				servicio= "TRONCAL";
				break;
			case "Sitp":
				servicio= "URBANO";
				break;
			default:
				servicio = "";
			
	    }
		return this.control.getTarifa(servicio,tipoPago);
		
	}

	public int getParadasRestantes(Parada parada){
		
		return paradas.size() - paradas.indexOf(parada);
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
	public boolean recibirRegistro(Registro registro, Bus bus) {
		id = buses.indexOf(bus) + 1;
		registro.setBus(id);
		return this.historial.add(registro);
	}
}
