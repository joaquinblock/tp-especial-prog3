
import java.util.List;

public class Backtracking {
    private List<Maquina> maquinas;
    private Solucion solucion;
    private int estadosGenerados;
    private int maxPiezasPorMaquina;

    public Backtracking(List<Maquina> maquinas) {
        this.maquinas = maquinas;
        this.solucion = new Solucion();
        this.estadosGenerados = 0;
        this.maxPiezasPorMaquina = calcularMaxPiezas();
    }

    public void resolver(int objetivo) {
        backtrack(new Estado(), objetivo);
        imprimir();
    }

    private void backtrack(Estado estado, int objetivo) {
        this.estadosGenerados++;

        if (poda(estado, objetivo)) return;

        if (estado.getPiezasAcumuladas() == objetivo) {
            solucion.intentarActualizar(estado);
            return;
        }

        for (Maquina m : this.maquinas) {
            estado.usarMaquina(m);
            backtrack(estado, objetivo);
            estado.deshacerMaquina();
        }
    }

    private boolean poda(Estado estado, int objetivo) {
        // Cuántas piezas me faltan para llegar al objetivo
        int piezasFaltantes = objetivo - estado.getPiezasAcumuladas();

        // Si ya me pasé del objetivo, no tiene sentido seguir explorando
        if (piezasFaltantes < 0) return true;

        // Si ya usé igual o más máquinas que la mejor solución que tengo hasta ahora, tampoco vale la pena seguir
        if (estado.getCantidadMaquinasUsadas() >= this.solucion.getMejorCantidadMaquinas()) return true;

        // Calculo cuántas activaciones más como mínimo necesito para llegar al objetivo,
        // usando la máquina que más piezas produce (la más eficiente)
        int activacionesMinimasRestantes = (piezasFaltantes + this.maxPiezasPorMaquina - 1) / this.maxPiezasPorMaquina;

        // Sumo las máquinas que ya usé más las mínimas que podría necesitar de acá en adelante
        int estimacionTotal = estado.getCantidadMaquinasUsadas() + activacionesMinimasRestantes;

        // Si con esta estimación ya me paso de la mejor solución, corto por acá
        return estimacionTotal >= this.solucion.getMejorCantidadMaquinas();
    }

    private int calcularMaxPiezas() {
        int max = 0;
        for (Maquina m : this.maquinas) {
            if (m.getPiezas() > max) max = m.getPiezas();
        }
        return max;
    }

    private int calcularTotalPiezas(Solucion solucion) {
        int total = 0;
        for (Maquina m : solucion.getMejorSecuencia()) {
            total += m.getPiezas();
        }
        return total;
    }

    public void imprimir() {
        System.out.println("<----Backtracking---->");
        System.out.print("Secuencia de máquinas: [");

        List<Maquina> secuencia = this.solucion.getMejorSecuencia();
        for (int i = 0; i < secuencia.size(); i++) {
            System.out.print(secuencia.get(i).getNombre());
            if (i < secuencia.size() - 1) {
                System.out.print(",");
            }
        }

        System.out.println("]");

        int totalPiezas = calcularTotalPiezas(this.solucion);
        System.out.println("Solución obtenida: cantidad de piezas producidas = " + totalPiezas
                + ", cantidad de puestas en funcionamiento = " + this.solucion.getMejorCantidadMaquinas());

        System.out.println("Cantidad de estados generados: " + estadosGenerados);
    }

}
