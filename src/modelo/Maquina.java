package modelo;

public class Maquina implements Comparable<Maquina> {
    private String nombre;
    private int piezas;

    public Maquina(String nombre, int piezas) {
        this.nombre = nombre;
        this.piezas = piezas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPiezas() {
        return piezas;
    }

    // Implementamos compareTo para ordenar máquinas de mayor a menor según piezas
    @Override
    public int compareTo(Maquina otra) {
        // Orden descendente: si esta máquina tiene más piezas, va antes
        return Integer.compare(otra.getPiezas(), this.piezas);
    }
}
