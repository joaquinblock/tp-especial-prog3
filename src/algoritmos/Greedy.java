package algoritmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.Maquina;
import modelo.Solucion;

/*
 * Estrategia Greedy Corregida:
 * - Ordena las máquinas de mayor a menor capacidad de producción.
 * - En cada paso selecciona la máquina más productiva que no exceda las piezas faltantes.
 * - Permite reutilización de máquinas.
 * - Solo devuelve solución si se alcanza exactamente el número de piezas requerido.
 * - Si no es posible alcanzar exactamente el objetivo, no devuelve solución.
 * - Evita bucles infinitos y contabiliza correctamente los candidatos considerados.
 */

public class Greedy {
    private List<Maquina> maquinas;
    private Solucion solucion;
    private int candidatosConsiderados;

    public Greedy(List<Maquina> maquinas) {
        this.maquinas = new ArrayList<>(maquinas);
        this.solucion = new Solucion();
        this.candidatosConsiderados = 0;
    }

    public void resolver(int objetivo) {
        greedy(objetivo);
        imprimir();
    }

    private void greedy(int objetivo) {
        // Ordenar máquinas de mayor a menor capacidad
        Collections.sort(this.maquinas);

        List<Maquina> secuencia = new ArrayList<>();
        int piezasAcumuladas = 0;

        while (piezasAcumuladas < objetivo) {
            boolean maquinaEncontrada = false;
            int piezasFaltantes = objetivo - piezasAcumuladas;

            // Buscar la máquina más productiva que no exceda las piezas faltantes
            for (int i = 0; i < this.maquinas.size(); i++) {
                Maquina maquina = this.maquinas.get(i);
                this.candidatosConsiderados++;

                if (maquina.getPiezas() <= piezasFaltantes) {
                    secuencia.add(maquina);
                    piezasAcumuladas += maquina.getPiezas();
                    maquinaEncontrada = true;
                    break;
                }
            }

            if (!maquinaEncontrada) {
                break;
            }
        }

        // Solo actualizamos la solución si encontramos una combinación exacta
        if (piezasAcumuladas == objetivo) {
            this.solucion.setMejorCantidadMaquinas(secuencia.size());
            this.solucion.setMejorSecuencia(secuencia);
        }
        // Si no se alcanza exactamente el objetivo, no se devuelve solución
        // (la solución permanece vacía)
    }

    private int calcularTotalPiezas() {
        if (!solucion.getMejorSecuencia().isEmpty()) {
            int total = 0;
            for (Maquina m : solucion.getMejorSecuencia()) {
                total += m.getPiezas();
            }
            return total;
        }
        return 0;
    }

    private void imprimir() {
        StringBuilder sb = new StringBuilder();
        List<Maquina> secuencia = this.solucion.getMejorSecuencia();
        int totalPiezas = calcularTotalPiezas();
        int totalMaquinas = this.solucion.getMejorCantidadMaquinas();

        sb.append("\n╔════════════════════════════════════╗\n");
        sb.append("║         RESULTADO GREEDY           ║\n");
        sb.append("╚════════════════════════════════════╝\n");

        if (secuencia.isEmpty()) {
            sb.append("No se encontró una solución exacta.\n");
        } else {
            sb.append("Secuencia de máquinas: [");
            for (int i = 0; i < secuencia.size(); i++) {
                sb.append(secuencia.get(i).getNombre());
                if (i < secuencia.size() - 1)
                    sb.append(", ");
            }
            sb.append("]\n");

            sb.append("Total de piezas producidas..........: ").append(totalPiezas).append("\n");
            sb.append("Cantidad de máquinas utilizadas.....: ").append(totalMaquinas).append("\n");
        }

        sb.append("Candidatos considerados.............: ").append(candidatosConsiderados).append("\n");
        System.out.println(sb);
    }
}