package logica.negocio;

import java.util.ArrayList;

public class Bus {
  private Ruta ruta;
  private int transbordosGenerados;
  private ArrayList<Registro> historial;

  public Bus(){
    
  }
  
  public Registro[] getHistorial(){
	  Registro[] historial = new Registro[0];
    return historial;
  }

  public Ruta getRuta(){
    return ruta;
  }
  public void parar(Parada parada){
    
  }


  
}
