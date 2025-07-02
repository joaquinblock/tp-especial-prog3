package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Maquina;

public class CargarArchivo {

    public static class Config {
        public final int objetivo;
        public final List<Maquina> maquinas;

        public Config(int objetivo, List<Maquina> maquinas) {
            this.objetivo = objetivo;
            this.maquinas = maquinas;
        }
    }

    public static Config cargarArchivo(String rutaArchivo) throws IOException {
        int objetivo = 0;
        List<Maquina> maquinas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = br.readLine();

            if (linea != null && !linea.trim().isEmpty()) {
                objetivo = Integer.parseInt(linea.trim());
            }

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
        }

        return new Config(objetivo, maquinas);
    }
}
