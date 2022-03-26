package logica.controlador;

import java.util.ArrayList;
import java.util.List;

import logica.negocio.Parada;
import logica.negocio.Ruta;

public class Controlador {

    List<Ruta> rutas;
    List<Parada> paradas;

    /**
     * Devuelve la una lista con las rutas buscadas.
     * @param rutasBuscadas Una lista de Integers con los identificadores de las rutas a buscar.
     * @return Un arreglo con las listas buscadas.
     */
    public Ruta[] getRutas(List<Integer> rutasBuscadas) {
        int buscando = 0;
        List<Ruta> rutasRetorno = new ArrayList<>();
        for (Ruta ruta: this.rutas)
            if (ruta.getId().compareTo(rutasBuscadas.get(buscando)) == 0) {
                rutasRetorno.add(ruta);
                buscando++;
                if (buscando == rutasBuscadas.size())
                    break;
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
        int buscando = 0;
        List<Parada> paradasRetorno = new ArrayList<>();
        for (Parada parada: this.paradas)
            if (parada.getId().compareTo(paradasBuscadas.get(buscando)) == 0) {
                paradasRetorno.add(parada);
                buscando++;
                if (buscando == paradasBuscadas.size())
                    break;
            }

        Parada[] retorno = new Parada[paradasRetorno.size()];
        return paradasRetorno.toArray(retorno);
    }

}
