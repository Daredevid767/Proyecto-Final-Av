package logica.controlador;

import java.util.ArrayList;
import java.util.List;

import logica.negocio.Parada;
import logica.negocio.Ruta;
import logica.persistencia.TarifasDAO;
import logica.persistencia.ParadaDAO;
import logica.persistencia.RutaDAO;

public class Controlador {

    List<Ruta> rutas;
    List<Parada> paradas;
    RutaDAO datosRuta;
    TarifasDAO datosTarifa;
    ParadaDAO datosParada;
    Ventana ventana;
    List<List<String>> resumenes;

    public Controlador(){
        ventana = new Ventana();
    }

    /**
     * Recibe la lista de resumenes
     * @param resumenes
     */
    public void setResumenes(List<List<String>> resumenes) {
        this.resumenes = resumenes;
        //this.ventana;
    }

    

    /**
     * Recibe la persistencia de los datos de las paradas
     * @param datosParada
     */
    public void setDatosParada(ParadaDAO datosParada) {
        this.datosParada = datosParada;
    }

    /**
     * recibe la persistencia de los datos de las ruta
     * @param datosRuta
     */
    public void setDatosRuta(RutaDAO datosRuta) {
        this.datosRuta = datosRuta;
    }

    /**
     * Recibe la persistencia de los datos de las tarifas
     * @param datosTarifa
     */
    public void setDatosTarifa(TarifasDAO datosTarifa) {
        this.datosTarifa = datosTarifa;
    }

    /**
     * Busca una ruta determinada.
     * @param id Identificador de la ruta a buscar.
     * @return La ruta solicitada o null si no se encontró el identificador.
     */
    public Ruta getRuta(Integer id) {
        for (Ruta ruta: this.rutas)
            if (ruta.getId().compareTo(id) == 0)
                return ruta;
        
        return null;
    }

    /**
     * Busca una parada determinada.
     * @param id Identificador de la parada a buscar.
     * @return La parada solicitada o null si no se encontró el identificador.
     */
    public Parada getParada(Integer id) {
        for (Parada parada: this.paradas)
            if (parada.getId().compareTo(id) == 0)
                return parada;
        
        return null;
    }

    /**
     * Busca una Tarifa determinada
     * @param servicio servicio a pagar
     * @param tipo tipo de pago
     * @return retorna la tarifa solicitada o -1 si no la encontro
     */
    public double getTarifa(String servicio, String tipo){
        return this.datosTarifa.getTarifa(servicio,tipo);
    }

    /**
     * Devuelve la una lista con las rutas buscadas.
     * @param rutasBuscadas Una lista de Integers con los identificadores de las rutas a buscar.
     * @return Un arreglo con las listas buscadas.
     */
    public Ruta[] getRutas(List<Integer> rutasBuscadas) {
        List<Ruta> rutasRetorno = new ArrayList<>();
        for (Integer rutaBuscada: rutasBuscadas) {
            Ruta rutaEncontrada = getRuta(rutaBuscada);
            if (rutaEncontrada != null)
                rutasRetorno.add(rutaEncontrada);
        }

        Ruta[] retorno = new Ruta[rutasRetorno.size()];
        return rutasRetorno.toArray(retorno);
    }

    /**
     * Devuelve la una lista con las paradas buscadas.
     * @param paradasBuscadas Una lista de Integers con los identificadores de las paradas a buscar.
     * @return Un arreglo con las listas buscadas.
     */
    public Parada[] getParadas(List<Integer> paradasBuscadas) {
        List<Parada> paradasRetorno = new ArrayList<>();
        for (Integer paradaBuscada: paradasBuscadas) {
            Parada paradaEncontrada = getParada(paradaBuscada);
            if (paradaEncontrada != null)
                paradasRetorno.add(paradaEncontrada);
        }

        Parada[] retorno = new Parada[paradasRetorno.size()];
        return paradasRetorno.toArray(retorno);
    }

    /**
     * Busca una Tarifa determinada
     * @param servicio servicio a pagar
     * @param tipo tipo de pago
     * @return retorna la tarifa solicitada o -1 si no la encontro
     */
    public Double[] getTarifas(List<String[]> servicio){
        List<Double> tarifasRetorno = new ArrayList<Double>();
        for (String[] s : servicio){
          tarifasRetorno.add(getTarifa(s[0],s[1]));
        }
        Double[] retorno = new Double[tarifasRetorno.size()];
        return tarifasRetorno.toArray(retorno);
    }

    /**
     * Actualiza las listas de paradas y rutas
     */
    public void cargar(){
        paradas = Arrays.asList(datosParada.getTodos());
        rutas = Arrays.asList(datosRuta.getTodos());
    }

    /**v
     * De
     * @param solicitud
     * @return
     */
    public List<List<String>> getInfo(List<List<String>> solicitud){
        if(solicitud.isEmpty())return null;
        List<List<String>> resultados = new ArrayList<List<String>>();
        List<List<String>> solicitudDep = new ArrayList<List<String>>();
        List<String> columnas = new ArrayList<String>();
        for (List<String> ls: solicitud) {
            if(ls.isEmpty())continue;
            int colum = resumenes.get(0).indexOf(ls.get(0));
            if(colum == -1)continue;
            solicitudDep.add(ls);
            columnas.add(ls.get(0));
        }
        if(columnas.isEmpty())return null;
        resultados.add(columnas);
        for (int i = 1; i < resumenes.size(); i++) {
            List<String> fila = new ArrayList<String>();
            for (List<String> ls: solicitudDep) {
                int colum = resumenes.get(0).indexOf(ls.get(0));
                String cadena = resumenes.get(i).get(colum);
                if(ls.indexOf(cadena)==-1 && ls.size()>1)
                    break;
                fila.add(cadena);
            }
            if(fila.size()==solicitudDep.size())resultados.add(fila);
        }
        return resultados;
    }
}