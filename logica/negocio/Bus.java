package logica.negocio;

public class Bus {
  private Ruta ruta;
  private int transbordosGenerados;
  private ArrayList<Registro> historial;

  public Bus(){
    
  }
  
  public Registro[] getHistorial(){
    return historial;
  }

  public Ruta getRuta(){
    return ruta;
  }
  public void parar(Parada parada){
    
  }


  
}
