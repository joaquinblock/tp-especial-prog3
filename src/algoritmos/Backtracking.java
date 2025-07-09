package algoritmos;

import java.util.List;

import modelo.Estado;
import modelo.Maquina;
import modelo.Solucion;

/*
 * Estrategia Backtracking:
 * - Explora todas las combinaciones posibles de máquinas sin repetición de orden ([M1,M2] pero no [M2,M1])
 * - Aplica podas eficientes:
 *    Evita continuar si no puede mejorar la mejor solución actual
 * - Restriccion:
 *    Evita superar el objetivo de piezas
 * - Contabiliza solo los estados realmente explorados (después de las podas)
 */

public class Backtracking {
    private List<Maquina> maquinas;
    private Solucion solucion;
    private int estadosGenerados;

    public Backtracking(List<Maquina> maquinas) {
        this.maquinas = maquinas;
        this.solucion = new Solucion();
        this.estadosGenerados = 0;
    }

    public void resolver(int objetivo) {
        backtrack(new Estado(), objetivo, 0);
        imprimir();
    }

    private void backtrack(Estado estado, int objetivo, int indiceInicio) {
        // Verificar si encontramos una solución
        if (estado.getPiezasAcumuladas() == objetivo) {
            solucion.intentarActualizar(estado);
            return;
        }

        // Explorar máquinas desde indiceInicio para evitar permutaciones redundantes
        for (int i = indiceInicio; i < this.maquinas.size(); i++) {
            Maquina m = this.maquinas.get(i);
            int nuevasPiezas = estado.getPiezasAcumuladas() + m.getPiezas();

            // Restriccion: No superar el objetivo
            if (nuevasPiezas > objetivo) {
                continue;
            }

            // Poda: No puede mejorar la mejor solución actual
            // Se suma +1 porque estamos por agregar una nueva máquina al estado actual.
            // Si al agregarla ya igualamos o superamos la mejor cantidad de máquinas usada
            if (solucion.getMejorCantidadMaquinas() != Integer.MAX_VALUE // solucion.mejorCantidadMaquinas se inicializa
                                                                         // con Integer.MAX_VALUE
                    && estado.getCantidadMaquinasUsadas() + 1 >= solucion.getMejorCantidadMaquinas()) {
                continue;
            }

            // Generar nuevo estado (solo si pasó todas las podas)
            this.estadosGenerados++;
            estado.usarMaquina(m);
            backtrack(estado, objetivo, i);
            estado.deshacerMaquina();
        }
    }

    private int calcularTotalPiezas() {
        int total = 0;
        for (Maquina m : this.solucion.getMejorSecuencia()) {
            total += m.getPiezas();
        }
        return total;
    }

    public void imprimir() {
        StringBuilder sb = new StringBuilder();
        List<Maquina> secuencia = this.solucion.getMejorSecuencia();
        int totalPiezas = calcularTotalPiezas();
        int totalMaquinas = this.solucion.getMejorCantidadMaquinas();

        sb.append("\n╔══════════════════════════════════════╗\n");
        sb.append("║       RESULTADO BACKTRACKING         ║\n");
        sb.append("╚══════════════════════════════════════╝\n");

        sb.append("Secuencia de máquinas: ");
        if (secuencia.isEmpty()) {
            sb.append("No se encontró una solución.\n");
        } else {
            sb.append("[");
            for (int i = 0; i < secuencia.size(); i++) {
                sb.append(secuencia.get(i).getNombre());
                if (i < secuencia.size() - 1)
                    sb.append(", ");
            }
            sb.append("]\n");

            sb.append("Total de piezas producidas..........: ").append(totalPiezas).append("\n");
            sb.append("Cantidad de máquinas utilizadas.....: ").append(totalMaquinas).append("\n");
        }

        sb.append("Estados generados...................: ").append(estadosGenerados).append("\n");

        System.out.println(sb);
    }

}