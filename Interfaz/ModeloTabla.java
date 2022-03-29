import javax.swing.table.DefaultTableModel;
import java.util.*;

public class ModeloTabla extends DefaultTableModel{
    private static String[] columnas = {"Tipo de ruta",
                                        "nombre de la ruta",
                                        "Dinero de la ruta",
                                        "Dinero por bus",
                                        "Dinero/hora",
                                        "Pasajeros de la ruta",
                                        "Pasajeros por bus",
                                        "Pasajeros/hora",
                                        "Pagos Especiales de la ruta",
                                        "Pagos Especiales por bus",
                                        "Pagos Especiales/tiempo",
                                        "Ubicacion"
                                        };
    public ModeloTabla(List<List<String>> lista,boolean[] columnasAct){
        // Carga las columnas
        for (int i = 0; i < columnas.length; i++)
            if(columnasAct[i])this.addColumn(ModeloTabla.columnas[i]);

        for (List<String> list : lista) {
            String[] fila = new String[this.getColumnCount()];
            for (int i = 0; i < columnas.length; i++) {
                if(columnasAct[i])fila[i]=list.get(i);
                System.out.println(list.get(i));
            }
            this.addRow(fila);
        }
        System.out.println(this.getColumnCount()+"   "+this.getRowCount());
    }
}