import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import algoritmos.Backtracking;
import algoritmos.Greedy;
import modelo.Maquina;

public class Main {

    public static void main(String[] args) {
        String archivo = "datos.txt"; // Archivo con los datos
        int objetivo = 0;
        List<Maquina> maquinas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();

            if (linea != null) {
                objetivo = Integer.parseInt(linea.trim());
            }

            maquinas = cargarMaquinas(br);

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            return;
        }

        Backtracking backtrackingSolver = new Backtracking(maquinas);
        backtrackingSolver.resolver(objetivo);

        Greedy greedySolver = new Greedy(maquinas);
        greedySolver.resolver(objetivo);
    }

    // Método para cargar las máquinas
    public static List<Maquina> cargarMaquinas(BufferedReader br) throws IOException {
        List<Maquina> maquinas = new ArrayList<>();
        String linea;
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
        return maquinas;
    }
}
