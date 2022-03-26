package logica.controlador;

import java.util.ArrayList;
import java.util.List;

import logica.negocio.Parada;
import logica.negocio.Ruta;

public class Controlador {

    List<Ruta> rutas;
    List<Parada> paradas;

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

}
