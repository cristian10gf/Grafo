package superarbol;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Arbol arbolito = new Arbol();
        int[] valores = { 9,10, 5, 15, 3, 7, 12, 17, 2, 4, 6, 8, 11, 13, 16, 18};
        int x = 0;
        for (int valore : valores) {
            x++;
           /*  System.out.println("#______________________#");
            System.out.println("Paso: " + x);
 */
            // arbolito.agregar(valore);
            arbolito.insertar(valore);
            // arbolito.TreePrinter();

        }
        System.out.println("#______________________#");
        System.out.println("Arbol final");
        arbolito.TreePrinter();
        
        System.out.println("#______________________#");
        System.out.println("Arbol balanceado");
        arbolito.printTopView();
        //arbolito.balanceararbol().TreePrinter();
        System.err.println();
        ArrayList<Nodo> lista = arbolito.listanodos;
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
