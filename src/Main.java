
import java.nio.file.Path;
import java.util.List;

import algoritmos.Backtracking;
import algoritmos.Greedy;
import modelo.Maquina;
import utils.LectorArchivo;

public class Main {

    private static final Path ARCHIVO_DATOS = Path.of("caso1.txt");

    public static void main(String[] args) {
        try {
            List<String> lineas = LectorArchivo.leerArchivo(ARCHIVO_DATOS.toString());

            if (lineas.isEmpty()) {
                System.err.println("El archivo está vacío o no tiene datos válidos.");
                return;
            }

            int objetivo = Integer.parseInt(lineas.get(0).trim());
            List<Maquina> maquinas = LectorArchivo.parsearMaquinas(lineas.subList(1, lineas.size()));

            if (maquinas.isEmpty()) {
                System.err.println("No se encontraron máquinas válidas en el archivo.");
                return;
            }

            System.out.println("==========================================\n");

            // Ejecutar Backtracking
            new Backtracking(maquinas).resolver(objetivo);
            // Ejecutar Greedy
            new Greedy(maquinas).resolver(objetivo);

            System.out.println("==========================================\n");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
