package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Maquina;

public class LectorArchivo {

    public static List<String> leerArchivo(String archivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea);
                }
            }
        }
        return lineas;
    }

    public static List<Maquina> parsearMaquinas(List<String> lineas) {
        List<Maquina> maquinas = new ArrayList<>();
        for (String linea : lineas) {
            String[] partes = linea.split(",");
            if (partes.length == 2) {
                try {
                    String nombre = partes[0].trim();
                    int piezas = Integer.parseInt(partes[1].trim());
                    maquinas.add(new Maquina(nombre, piezas));
                } catch (NumberFormatException e) {
                    System.err.println("Línea ignorada por error numérico: " + linea);
                }
            } else {
                System.err.println("Línea ignorada (esperado 'nombre,piezas'): " + linea);
            }
        }
        return maquinas;
    }
}
