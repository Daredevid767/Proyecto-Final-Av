package pruebas;

/**
 * Clase que sirve para probar el funcionamiento de las clases CSVTabla y CSVParser.
 * @autor Nicolás Sabogal 25/03/2022
 * @version 1.0
 */
public class PruebaCSVParser {
    /**
     * Lee un archivo indicado en la carpeta indicada y crea una copia en la misma carpeta con el nombre PruebaCSVParserResultado.csv.
     * @param args Arreglo de dos cadenas: [Carpeta en la cual correr el programa, Nombre del archivo a copiar]
     */
    public static void main(String[] args) {
        try {
            if (!args[0].isEmpty()) {
				if (args[0].charAt(args[0].length() - 1) == '\\')
					args[0] = args[0].substring(0, args[0].length() - 1) + "/";
				
                if (args[0].charAt(args[0].length() - 1) != '/')
                    args[0] += "/";
			}

            logica.persistencia.CSVTabla a = logica.persistencia.CSVParser.get(args[0] + args[1]);
            logica.persistencia.CSVParser.crear(args[0] + "PruebaCSVParserResultado.csv", a);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
