package logica.negocio;

public class Tarjeta {
     private int saldo;

	public Tarjeta() {
		this(0);
	}
    public Tarjeta(int saldo){
        this.saldo = saldo;
    }

    public Registro pagar(String servicio,String parada){
		
		if(servicio.equals("Troncal")||servicio.equals("Dual")){
			if(saldo>=2650){
				saldo=saldo-2650;
				return new Registro(2650.0,new int[0],parada,false);
			}
				return null;
		}
		
		if(servicio.equals("Sitp")){
			if(saldo>=2500){
				saldo=saldo-2500;
				return new Registro(2650.0,new int[0],parada,false);
			}else{
				return null;
			}
		}
		
		
		//TODO
		return null;
    }

}
