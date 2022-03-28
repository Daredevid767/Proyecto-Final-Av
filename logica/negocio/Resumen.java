package logica.negocio;

public class Resumen {

	private String nombreRuta;
	private int gananciaTotal;
	private int gananciaPorBus;
	private int ganaciaPorTiempo;
	private int pasajerosTotales;
	private int pasajerosPorBus;
	private int pasajerosPorTiempo;

	public Resumen(String nombreRuta,int gananciaTotal,int gananciaPorBus,int gananciaPorTiempo,int pasajerosTotales,int pasajerosPorBus,int pasajerosPorTiempo){
		
		this.nombreRuta=nombreRuta;
		this.gananciaTotal=gananciaTotal;
		this.gananciaPorBus=gananciaPorBus;
		this.gananciaPorTiempo=gananciaPorTiempo;
		this.pasajerosTotales=pasajerosTotales;
		this.pasajerosPorBus=pasajerosPorBus;
		this.pasajerosPorTiempo=pasajerosPorTiempo;

	}
}
