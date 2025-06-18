import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Solucion {
    private List<Maquina> mejorSecuencia;

    private int mejorCantidadMaquinas;

    public Solucion() {
        this.mejorSecuencia = new ArrayList<>();
        this.mejorCantidadMaquinas = Integer.MAX_VALUE;
    }

    public void intentarActualizar(Estado estado) {
        int cantidad = estado.getCantidadMaquinasUsadas();
        if (cantidad < mejorCantidadMaquinas) {
            mejorCantidadMaquinas = cantidad;
            mejorSecuencia = new ArrayList<>(estado.getSecuencia()); // Copia modificable
        }
    }

    public List<Maquina> getMejorSecuencia() {
        List<Maquina> tmp = new ArrayList<>();
        for (Maquina m : this.mejorSecuencia){
            tmp.add(m);
        }
        return tmp;
    }

    public int getMejorCantidadMaquinas() {
        return mejorCantidadMaquinas;
    }

    public void setMejorSecuencia(List<Maquina> secuencia) {
        mejorSecuencia.clear();
        for(Maquina m : secuencia){
            this.mejorSecuencia.add(m);
        }
    }

    public void setMejorCantidadMaquinas(int mejorCantidadMaquinas) {
        this.mejorCantidadMaquinas = mejorCantidadMaquinas;
    }
}


