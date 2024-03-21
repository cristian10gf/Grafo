package superarbol;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Arbol arbolito = new Arbol();
        Random random = new Random();
        ArrayList<Integer> valores = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            valores.add(random.nextInt(10));
            System.out.println(valores.get(i));
        }
        int x = 0;
        for (int valore : valores) {
            x++;
           /*  System.out.println("#______________________#");
            System.out.println("Paso: " + x);
 */
             arbolito.agregar(valore);
            //arbolito.insertar(valore);
            // arbolito.TreePrinter();

        }
        System.out.println("#______________________#");
        System.out.println("Arbol final");
        arbolito.TreePrinter();
        
        System.out.println("#______________________#");
        System.out.println("Arbol balanceado");
        arbolito.printer_ifwaszzz(arbolito.raiz);
        //arbolito.balanceararbol().TreePrinter();
        System.err.println();
        TreePrinter printer = new TreePrinter();
        TreePrinter.printNode(arbolito.raiz);

        /*
         * for (Nodo nodes : lista) {
         * System.out.println("#______________________#");
         * System.out.println("Nodo: " + nodes.dato);
         * if (nodes.padre != null)
         * System.out.println("  Padre: " + nodes.padre.dato);
         * if (nodes.izquierdo != null)
         * System.out.println("  Izquierdo: " + nodes.izquierdo.dato);
         * if (nodes.derecho != null)
         * System.out.println("  Derecho: " + nodes.derecho.dato);
         * System.out.println("  Altura: " + nodes.alturaNodo);
         * System.out.println("  Peso Izquierdo: " + nodes.peso_izquierdo);
         * System.out.println("  Peso Derecho: " + nodes.peso_derecho);
         * 
         * }
         */

    }
}
