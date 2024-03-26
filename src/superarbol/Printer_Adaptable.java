package superarbol;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;

//falta arrreglar el movimiento entre las listas
//en vez de posicionar el valor del hijo posteriormente a la linea del padre, se puede posicionar al padre y enseguida añadir los valores de los hijos en base a la posicion del padre
public class Printer_Adaptable {

    List<List<String>> Primitive_Array;

    public Printer_Adaptable() {
        this.Primitive_Array = new ArrayList<List<String>>();

    }

    // funcion que devuelve el valor del string en int sin importar si contiene
    // otros simbolos o espacios
    /**
     * Traduce un String eliminando todos los caracteres que no sean dígitos y
     * convirtiéndola en un entero.
     *
     * @param dato El String a traducir.
     * @return El valor entero del String traducido.
     */
    public int translator(String dato) {
        return Integer.valueOf(dato.replaceAll("[^0-9]", ""));
    }

    /**
     * Translates the given integer into a formatted string.
     *
     * @param dato the integer to be translated
     * @return a formatted string representation of the integer
     */

    public String traductor(int dato) { // funcion que devuelve el valor del int en string con un formato especifico
        if (dato < 10)
            return "-0" + dato + "-";
        else
            return "-" + dato + "-";
    }

    public static void main(String args[]) {
        List<String> listaInterna = new ArrayList<String>();
        Printer_Adaptable printer = new Printer_Adaptable();
        Random rand = new Random();
        // --------------------------------------------------------------------------------------//
        // creamos una lista de 32 elementos con el valor - - para cada uno
        for (int i = 0; i < 32; i++)
            listaInterna.add("-  -");
        // --------------------------------------------------------------------------------------//
        // creamos una lista de 10 listas con el valor - - para cada una
        for (int i = 0; i < 12; i++)
            printer.Primitive_Array.add(new ArrayList<String>());

        for (List<String> lista : printer.Primitive_Array) {
            int idx = printer.Primitive_Array.indexOf(lista);
            // lista.add(String.valueOf(idx));
            lista.addAll(listaInterna);
            System.out.println(lista);

        }
        // --------------------------------------------------------------------------------------//
        // creamos un arbol con 10 valores aleatorios
        Arbol arbolito = new Arbol();
        ArrayList<Integer> valores = new ArrayList<Integer>();
        for (int i = 1; i <= 12; i++) {
            valores.add(rand.nextInt(50));
            // valores.add(i);
            // System.out.println(valores.get(i));
        }
        // valores.sort(Collections.reverseOrder());
        for (int valore : valores) {
            arbolito.agregar(valore);
        }
        // --------------------------------------------------------------------------------------//
        // le añadimos el valor traducido a cada lista en el orden que se encuentran en
        // el vector

        /*
         * for (List<String> lista : printer.Primitive_Array) {
         * // lista.add(lista.size()/2,
         * // printer.traductor(printer.Primitive_Array.indexOf(lista)));
         * List<String> listasub = new ArrayList<String>();
         * listasub.addAll(lista.subList(1, lista.size()));
         * listasub.set((printer.Primitive_Array.size()) - 1 -
         * printer.Primitive_Array.indexOf(lista),
         * printer.traductor(valores.get(printer.Primitive_Array.indexOf(lista))));
         * for (String x : listasub) {
         * System.out.print(x + "");
         * }
         * System.out.println();
         * }
         * for (List<String> lista : printer.Primitive_Array) {
         * List<String> listap = new ArrayList<String>();
         * listap.addAll(lista.subList(1, lista.size()));
         * listap.set(printer.Primitive_Array.indexOf(lista),
         * printer.traductor(valores.get(printer.Primitive_Array.indexOf(lista))));
         * for (String x : listap) {
         * System.out.print(x + "");
         * }
         * System.out.println();
         * }
         */

        // --------------------------------------------------------------------------------------//
        /*
         * int p = 0;
         * for (List<String> lista : printer.Primitive_Array) {
         * if (p % 2 == 0) {
         * lista.set(lista.size() / 2 + p++,
         * printer.traductor(valores.get(printer.Primitive_Array.indexOf(lista))));
         * } else {
         * lista.set(lista.size() / 2 - p++,
         * printer.traductor(valores.get(printer.Primitive_Array.indexOf(lista))));
         * }
         * for (String x : lista) {
         * System.out.print(x + "");
         * }
         * System.out.println();
         * }
         */
        // --------------------------------------------------------------------------------------//

        for (int i = 0; i <= arbolito.AlturaMax(); i++) {
            List<String> lista = printer.Primitive_Array.get(i);
            if (i == 0) {
                lista.add((lista.size() / 2),
                        printer.traductor(arbolito.buscarnodo_altura(i).get(i).dato));

            } else if (i == 1) {

                for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
                    int pospadre = printer.Primitive_Array.get(i - 1)
                            .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));

                    if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo == arbolito.buscarnodo_altura(i).get(j)) {
                        lista.set(pospadre - 2,
                                printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                    } else {
                        lista.set(pospadre + 2,
                                printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                    }
                }
            } else if (i == 2) {
                for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
                    /* if (j == 0) {
                        int pospadre = printer.Primitive_Array.get(i - 1)
                                .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));
                        if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo == arbolito.buscarnodo_altura(i)
                                .get(j)) {
                            lista.add(pospadre - 1,
                                    printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                        } else {
                            lista.add(pospadre + 1,
                                    printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));

                        }
                    } else { */
                        int pospadre = printer.Primitive_Array.get(i - 1)
                                .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));
                        int p=0;
                        if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo == arbolito.buscarnodo_altura(i)
                                .get(j)) {
                            lista.set(pospadre - 1,
                                    printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                            for (int k = 0; k < i; k++) {

                                printer.Primitive_Array.get(k).add(pospadre-1+ p++, "-  -");
                            }
                        } else {
                            lista.set(pospadre + 1,
                                    printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                            for (int k = 0; k < i; k++) {

                                printer.Primitive_Array.get(k).add(pospadre +p++, "-  -");
                            }
                        }
                    //}
                }
            } else {
                for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
                    int p=0;
                    int pospadre = printer.Primitive_Array.get(i - 1)
                            .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));

                    if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo == arbolito.buscarnodo_altura(i).get(j)) {
                        lista.add(pospadre - 1,
                                printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));

                        for (int k = 0; k < i; k++) {

                            printer.Primitive_Array.get(k).add(pospadre + p++, "-  -");
                        }

                    } else {
                        lista.add(pospadre + 1,
                                printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                        for (int k = 0; k < i; k++) {

                            printer.Primitive_Array.get(k).add(pospadre + p++, "-  -");
                        }
                    }
                }
            }
            System.out.println();
        }

        for (int i = 0; i <= arbolito.AlturaMax(); i++) {
            for (String x : printer.Primitive_Array.get(i)) {
                System.out.print(x + "");
            }
            System.out.println();
        }

        for (int i = 0; i <= arbolito.AlturaMax(); i++) {
            System.out.println("Altura: " + i);
            for (Nodo x : arbolito.buscarnodo_altura(i)) {

                System.out.print(x.dato + " ");

            }
            System.out.println();
        }
        System.out.println("Arbol final: " + arbolito.AlturaMax());
        arbolito.printer_ifwaszzz(arbolito.raiz);
        // arbolito.TreePrinter();
        // lista.add("-"+printer.translator(printer.traductor(x))+"-");
    }

}
