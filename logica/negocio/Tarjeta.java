package logica.negocio;



public class Tarjeta {
     private int saldo;

	public Tarjeta() {
		this(0);
	}
    public Tarjeta(int saldo){
        this.saldo = saldo;
    }
	
	
    public Registro pagar(String servicio,String parada,double precio){

		if(saldo>=precio){
			saldo=saldo-precio;
			return new Registro(precio,false,parada,false);
		}
		return null;
    }
	public String getTipoPago(){
		return "completo";
	}

}
