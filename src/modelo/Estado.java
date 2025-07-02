package modelo;

import java.util.ArrayList;
import java.util.List;

public class Estado {
    private List<Maquina> secuencia;
    private int piezasAcumuladas;

    public Estado() {
        this.secuencia = new ArrayList<>();
        this.piezasAcumuladas = 0;
    }

    public void usarMaquina(Maquina m) {
        secuencia.add(m);
        piezasAcumuladas += m.getPiezas();
    }

    public void deshacerMaquina() {
        if (!secuencia.isEmpty()) {
            Maquina ultima = secuencia.remove(secuencia.size() - 1);
            piezasAcumuladas -= ultima.getPiezas();
        }
    }

    public List<Maquina> getSecuencia() {
        List<Maquina> tmp = new ArrayList<>();
        for (Maquina m : this.secuencia) {
            tmp.add(m);
        }
        return tmp;
    }

    public int getPiezasAcumuladas() {
        return piezasAcumuladas;
    }

    public int getCantidadMaquinasUsadas() {
        return secuencia.size();
    }
}
