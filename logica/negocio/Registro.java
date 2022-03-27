package logica.negocio;

import java.time.*;

public class Registro {

	private Instant instante;
	private double valor;
	private int[] subsidios;
	private String parada;
	private boolean transbordo;
	
	
	public Registro(double valor,int[]subsidios, String parada, boolean transbordo){
		this.instante = Instant.now();
		this.valor= valor;
		this.subsidios = subsidios;
		this.parada = parada;
		this.transbordo= transbordo;
	}

	public Registro(){
		transbordo=true;
	}
	

	
	//TODO Constructor por defecto crea trasbordos.
}
