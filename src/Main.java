import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String archivo = "datos.txt"; // Archivo con los datos

        int objetivo = 0;
        List<Maquina> maquinas = new ArrayList<>();

        // Leer archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();

            // Primera línea es el objetivo (cantidad de piezas)
            if (linea != null) {
                objetivo = Integer.parseInt(linea.trim());
            }

            // Leer las máquinas y su producción
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length == 2) {
                        String nombre = partes[0].trim();
                        int piezas = Integer.parseInt(partes[1].trim());
                        maquinas.add(new Maquina(nombre, piezas));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            return;
        }

        // Resolver con Backtracking
        Backtracking backtrackingSolver = new Backtracking(maquinas);
        backtrackingSolver.resolver(objetivo);

        // Resolver con Greedy
        Greedy greedySolver = new Greedy(maquinas);
        greedySolver.resolver(objetivo);

    }
}

