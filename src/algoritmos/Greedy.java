package algoritmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.Maquina;
import modelo.Solucion;

/*
 * Estrategia Greedy:
 * - Ordena las máquinas de mayor a menor capacidad de producción.
 * - En cada paso selecciona la máquina más productiva que no exceda las piezas faltantes.
 * - Solo devuelve solución si se alcanza exactamente el número de piezas requerido.
 * - Si no es posible alcanzar exactamente el objetivo, no devuelve solución.
 * - Contabiliza correctamente los candidatos considerados.
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
        int index = 0;
        boolean solucionExacta = false;

        while (index < this.maquinas.size() && !solucionExacta) {
            Maquina maquinaActual = this.maquinas.get(index);
            this.candidatosConsiderados++;

            // Verificar si al agregar esta máquina alcanzamos exactamente el objetivo
            if (piezasAcumuladas + maquinaActual.getPiezas() == objetivo) {
                secuencia.add(maquinaActual);
                piezasAcumuladas += maquinaActual.getPiezas();
                solucionExacta = true;
            }
            // Verificar si podemos agregar la máquina sin pasarnos del objetivo
            else if (piezasAcumuladas + maquinaActual.getPiezas() < objetivo) {
                secuencia.add(maquinaActual);
                piezasAcumuladas += maquinaActual.getPiezas();
                // Reiniciamos el índice para considerar todas las máquinas nuevamente
                index = 0;
            } else {
                // Probamos con la siguiente máquina menos productiva
                index++;
            }
        }

        // Solo actualizamos la solución si encontramos una combinación exacta
        if (solucionExacta) {
            this.solucion.setMejorCantidadMaquinas(secuencia.size());
            this.solucion.setMejorSecuencia(secuencia);
        }
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
        System.out.println("\n<----Greedy---->");

        if (solucion.getMejorSecuencia().isEmpty()) {
            System.out.println("No se encontró solución exacta");
        } else {
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
        }

        System.out.println("Cantidad de candidatos considerados: " + candidatosConsiderados);
    }
}