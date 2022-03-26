package logica.negocio;  

public class TarjetaPersonalizada extends Tarjeta {
	private int[] subsidios;
	private Instant ultimoViaje;
	private int transbordosActuales;

	public double calcularServicio(String servicio){
    	return 0d;
	}  
  
	public int[] getSusidios(){
    	return this.subsidios;
	}
	public int getTransbordosActuales(){
    	return this.transbordosActuales;
	}
}
