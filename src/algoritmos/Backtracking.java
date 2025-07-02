package algoritmos;

import java.util.List;

import modelo.Estado;
import modelo.Maquina;
import modelo.Solucion;

/*
 * Estrategia Backtracking:
 * - Explora todas las combinaciones posibles de máquinas sin repetición de orden ([M1,M2] pero no [M2,M1])
 * - Aplica podas eficientes:
 *   1. Evita superar el objetivo de piezas
 *   2. Evita continuar si no puede mejorar la mejor solución actual
 *   3. Estima el mínimo de máquinas necesarias para alcanzar el objetivo
 * - Contabiliza solo los estados realmente explorados (después de las podas)
 */

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

            // Poda 1: No superar el objetivo
            if (nuevasPiezas > objetivo) {
                continue;
            }

            // Poda 2: No puede mejorar la mejor solución actual
            if (solucion.getMejorCantidadMaquinas() != Integer.MAX_VALUE &&
                    estado.getCantidadMaquinasUsadas() + 1 >= solucion.getMejorCantidadMaquinas()) {
                continue;
            }

            // Poda 3: Estimación optimista de máquinas necesarias
            int piezasFaltantes = objetivo - nuevasPiezas;
            int activacionesMinimas = piezasFaltantes == 0 ? 0
                    : (piezasFaltantes + this.maxPiezasPorMaquina - 1) / this.maxPiezasPorMaquina;

            if (solucion.getMejorCantidadMaquinas() != Integer.MAX_VALUE &&
                    estado.getCantidadMaquinasUsadas() + 1 + activacionesMinimas >= solucion
                            .getMejorCantidadMaquinas()) {
                continue;
            }

            // Generar nuevo estado (solo si pasó todas las podas)
            this.estadosGenerados++;
            estado.usarMaquina(m);
            backtrack(estado, objetivo, i); // Mantener el índice i para evitar retroceder
            estado.deshacerMaquina();
        }
    }

    private int calcularMaxPiezas() {
        int max = 0;
        for (Maquina m : this.maquinas) {
            if (m.getPiezas() > max) {
                max = m.getPiezas();
            }
        }
        return max;
    }

    private int calcularTotalPiezas() {
        int total = 0;
        for (Maquina m : this.solucion.getMejorSecuencia()) {
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

        int totalPiezas = calcularTotalPiezas();
        System.out.println("Solución obtenida: cantidad de piezas producidas = " + totalPiezas
                + ", cantidad de puestas en funcionamiento = " + this.solucion.getMejorCantidadMaquinas());

        System.out.println("Cantidad de estados generados: " + estadosGenerados);
    }
}