import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Estrategia Greedy:
 * - Los candidatos son las máquinas disponibles, cada una con una cierta capacidad de producción.
 * - Se ordenan de forma descendente según la cantidad de piezas que producen, priorizando las más productivas.
 * - En cada paso, se elige la máquina más productiva que no exceda el objetivo de piezas a producir.
 * - Si ninguna máquina permite avanzar sin pasarse, se toma la menos productiva para intentar alcanzar el objetivo aunque se lo exceda.
 * - La estrategia no garantiza una solución óptima, pero busca una solución rápida y razonable.
 * - Se contabiliza la cantidad de candidatos considerados para evaluar la eficiencia.
 */

public class Greedy {
    private List<Maquina> maquinas;
    private Solucion solucion;
    private int candidatosConsiderados = 0;

    public Greedy(List<Maquina> maquinas) {
        this.maquinas = maquinas;
        this.solucion = new Solucion();
    }

    // Método público que se llama desde el main
    public void resolver(int objetivo) {
        greedy(objetivo);
        imprimir();
    }

    // Este método calcula la mejor secuencia de máquinas usando una estrategia
    // golosa (greedy)
    private void greedy(int objetivo) {
        // Primero ordeno las máquinas de mayor a menor según cuántas piezas producen.
        // Así me aseguro de usar siempre primero la más productiva.
        Collections.sort(this.maquinas); // Ordena de mayor a menor por piezas (usa compareTo)

        List<Maquina> secuencia = new ArrayList<>();
        int piezasAcumuladas = 0;
        int index = 0;

        // Mientras no llegue al objetivo de piezas y todavía tenga máquinas para probar
        while (piezasAcumuladas < objetivo && index < this.maquinas.size()) {
            Maquina maquinaActual = this.maquinas.get(index);
            this.candidatosConsiderados++; // Cada vez que evalúo una máquina, cuento que la consideré

            // Si al usar esta máquina no me paso del objetivo, la uso
            if (piezasAcumuladas + maquinaActual.getPiezas() <= objetivo) {
                secuencia.add(maquinaActual);
                piezasAcumuladas += maquinaActual.getPiezas();
            } else {
                // Si me paso, pruebo con la que sigue (menos productiva)
                index++;
            }

            // Si ya probé todas las máquinas y todavía no llegué, uso la más chica aunque
            // me pase
            // porque no me queda otra si quiero llegar al objetivo
            if (index == this.maquinas.size() && piezasAcumuladas < objetivo) {
                Maquina ultima = this.maquinas.get(this.maquinas.size() - 1);
                secuencia.add(ultima);
                piezasAcumuladas += ultima.getPiezas();
                this.candidatosConsiderados++;
            }
        }

        // Guardo la mejor solución que encontré
        this.solucion.setMejorCantidadMaquinas(secuencia.size());
        this.solucion.setMejorSecuencia(secuencia);
    }

    // Suma las piezas de la solución para mostrarlas
    private int calcularTotalPiezas() {
        int total = 0;
        for (Maquina m : solucion.getMejorSecuencia()) {
            total += m.getPiezas();
        }
        return total;
    }

    // Muestra todo lo que pide el enunciado
    private void imprimir() {
        System.out.println("\n<----Greedy---->");
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
                + ", cantidad de puestas en funcionamiento = " + solucion.getMejorCantidadMaquinas());

        System.out.println("Cantidad de candidatos considerados: " + candidatosConsiderados);
    }
}
