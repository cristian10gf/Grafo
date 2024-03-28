package superarbol;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;

//falta arrreglar el movimiento entre las listas
//en vez de posicionar el valor del hijo posteriormente a la linea del padre, se puede posicionar al padre y enseguida añadir los valores de los hijos en base a la posicion del padre
// hay que calcular el espacio entre los valores actuales de una lista y en base a eso añadir los respectivos espacios
//Nueva aproximacion; Imprimir el arbol por niveles y que cada subarbol se imprima de 3 niveles cada uno.(entre mas niveles se impriman, mas se podra ver la estructura del arbol)
//Ej:
//Nivel 0: ///////////////////////////////////////////////
//                 10
//       5                 15
//Nivel 1; ///////////////////////////////////////////////
//Nivel 1; Subarbol 5:
//                 5
//       2                 8
//Nivel 1; Subarbol 15:
//                 15
//       12                 18
//Nivel 2; ///////////////////////////////////////////////
//Nivel 2; Subarbol 2:
//                 2
//       1                 3
//Nivel 2; Subarbol 8:
//                 8
//       6                 9
//Nivel 2; Subarbol 12:
//                 12
//       11                 13
//Nivel 2; Subarbol 18:
//                 18
//       16                 19
//Nivel 3; ///////////////////////////////////////////////
//Nivel 3; Subarbol 1:
//                 1
//Nivel 3; Subarbol 3:
//                 3
//Nivel 3; Subarbol 6:
//                 6
//Nivel 3; Subarbol 9:
//                 9
//    7                 
//Nivel 3; Subarbol 11:
//                 11
//Nivel 3; Subarbol 13:
//                

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

    public void subArbol_imprimir_niveles(Arbol sub, int nivel) { // imprime un subarbol en base a un nodo y cantidad de
                                                                  // // niveles a imprimir dados
        if (nivel <= 0) {
            System.out.println("No puede imprimirse un arbol con menos de 1 nivel");
            return;
        }
        Printer_Adaptable printer = new Printer_Adaptable();
        printer.startPrinter(nivel, 9);
        // peor caso: arbol con 3 niveles completos
        printer.Primitive_Array.get(0).set(printer.Primitive_Array.get(0).size() / 2, printer.traductor(sub.raiz.dato));
        if (sub.raiz.izquierdo != null && nivel >= 2) {
            printer.Primitive_Array.get(1).set(printer.Primitive_Array.get(1).size() / 2 - 2,
                    printer.traductor(sub.raiz.izquierdo.dato));
            if (sub.raiz.izquierdo.izquierdo != null && nivel >= 3) {
                printer.Primitive_Array.get(2).set(printer.Primitive_Array.get(2).size() / 2 - 3,
                        printer.traductor(sub.raiz.izquierdo.izquierdo.dato));
            }
            if (sub.raiz.izquierdo.derecho != null && nivel >= 3) {
                printer.Primitive_Array.get(2).set(printer.Primitive_Array.get(2).size() / 2  -1,
                        printer.traductor(sub.raiz.izquierdo.derecho.dato));
            }
        }
        if (sub.raiz.derecho != null && nivel >= 2) {
            printer.Primitive_Array.get(1).set(printer.Primitive_Array.get(1).size() / 2 + 2,
                    printer.traductor(sub.raiz.derecho.dato));
            if (sub.raiz.derecho.izquierdo != null && nivel >= 3) {
                printer.Primitive_Array.get(2).set(printer.Primitive_Array.get(2).size() / 2 + 1,
                        printer.traductor(sub.raiz.derecho.izquierdo.dato));
            }
            if (sub.raiz.derecho.derecho != null && nivel >= 3) {
                printer.Primitive_Array.get(2).set(printer.Primitive_Array.get(2).size() / 2 + 3,
                        printer.traductor(sub.raiz.derecho.derecho.dato));
            }
        }
        System.out.println("Subarbol de " + sub.raiz.dato);
        for (int i = 0; i < nivel; i++) {
            for (String x : printer.Primitive_Array.get(i)) {
                System.out.print(x + "");
            }
            System.out.println();
        }
    }

    /**
     * Initializes the printer with a specific number of lines and width.
     * <p> ¡Tambien se puede usar para personalizar el printer!
     * @param lineas The number of lines for the printer.
     * @param ancho The width of each line.
     */
    public void startPrinter(int lineas, int ancho) { // inicializa el printer con una cantidad de lineas y ancho especificas
        List<String> lista = new ArrayList<String>();
        for (int j = 0; j < ancho; j++) {
            lista.add("-  -");
        }
        for (int i = 0; i < lineas; i++) {
            this.Primitive_Array.add(new ArrayList<String>());
            this.Primitive_Array.get(i).addAll(lista);
        }
    }

    public void print(Arbol arbol) {// es necesario que el printer NO este inicializado previamente; Plenamente funcional
        int cuentanivel = 0; // lleva la cuenta de en que nivel se encuentra el arbol
        if (arbol.AlturaMax() >= 0) {
            while (cuentanivel > -1) {
                ArrayList<Nodo> nodos = arbol.buscarnodo_altura(cuentanivel); // nodos en el nivel actual
                for (Nodo nodo : nodos) { // se recorren los nodos en el nivel actual y se imprimen sus subarboles
                    Arbol subarbol = arbol.subArbol(nodo);
                    subArbol_imprimir_niveles(subarbol, 3);
                }
                if (cuentanivel >= arbol.AlturaMax() - 2) { // si se llega al penultimo nivel se termina el ciclo
                    cuentanivel = -1;
                } else { // si no se aumenta el nivel
                    cuentanivel = cuentanivel + 2;
                }
            }
        }
    }

    public static void main(String args[]) {

        Printer_Adaptable printer = new Printer_Adaptable();
        printer.startPrinter(12, 24);
        Random rand = new Random();

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
        // añadimos los valores de los nodos en las listas
        for (int i = 0; i <= arbolito.AlturaMax(); i++) {
            List<String> lista = printer.Primitive_Array.get(i);
            if (i == 0) {
                printer.Primitive_Array.get(i).add((lista.size() / 2),
                        printer.traductor(arbolito.buscarnodo_altura(i).get(i).dato));

                int pospadre = printer.Primitive_Array.get(i)
                        .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(i).dato));

                if (arbolito.buscarnodo_altura(i).get(i).izquierdo != null) {
                    printer.Primitive_Array.get(i + 1).set(pospadre - 2,
                            printer.traductor(arbolito.buscarnodo_altura(i).get(i).izquierdo.dato));
                }
                if (arbolito.buscarnodo_altura(i).get(i).derecho != null) {
                    printer.Primitive_Array.get(i + 1).set(pospadre + 2,
                            printer.traductor(arbolito.buscarnodo_altura(i).get(i).derecho.dato));
                }
            } else if (i >= 1) {
                for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
                    int posnodoactual = printer.Primitive_Array.get(i)
                            .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
                    if (arbolito.buscarnodo_altura(i).get(j).izquierdo != null) {
                        printer.Primitive_Array.get(i + 1).add(posnodoactual - 1,
                                printer.traductor(arbolito.buscarnodo_altura(i).get(j).izquierdo.dato));

                        // se añade un espacio vacio al centro de la lista
                        printer.Primitive_Array.get(i + 1).add(printer.Primitive_Array.get(i + 1).size() / 2, "-  -");

                        // despues de agregar el nodo se agrega un espacio a la derecha a todos los
                        // nodos superiores
                        /*
                         * Nodo padretemp = arbolito.buscarnodo_altura(i).get(j).padre; // nodo padre
                         * del actual
                         * int k = i - 1;// array anterior donde se encuentra el padre
                         * while (padretemp != null && k >= 0) {// mientras haya un padre al cual
                         * agregar un espacio
                         * 
                         * int pospadretemp = printer.Primitive_Array.get(k)
                         * .indexOf(printer.traductor(padretemp.dato));
                         * printer.Primitive_Array.get(k).add(pospadretemp + 1, "-  -");
                         * padretemp = padretemp.padre;
                         * k--;
                         * }
                         */
                    }

                    if (arbolito.buscarnodo_altura(i).get(j).derecho != null) {
                        printer.Primitive_Array.get(i + 1).add(posnodoactual + 1,
                                printer.traductor(arbolito.buscarnodo_altura(i).get(j).derecho.dato));
                        printer.Primitive_Array.get(i + 1).add(printer.Primitive_Array.get(i + 1).size() / 2, "-  -");
                        // despues de agregar el nodo se agrega un espacio a la derecha a todos los
                        // nodos superiores
                        /*
                         * Nodo padretemp = arbolito.buscarnodo_altura(i).get(j).padre; // nodo padre
                         * del actual
                         * int k = i - 1;// array anterior donde se encuentra el padre
                         * while (padretemp != null && k >= 0) {// mientras haya un padre al cual
                         * agregar un espacio
                         * 
                         * int pospadretemp = printer.Primitive_Array.get(k)
                         * .indexOf(printer.traductor(padretemp.dato));
                         * printer.Primitive_Array.get(k).add(pospadretemp + 1, "-  -");
                         * padretemp = padretemp.padre;
                         * k--;
                         * }
                         */
                    }

                }
            }
            System.out.println();
        }

        /*
         * for (int i = 0; i <= arbolito.AlturaMax(); i++) {
         * List<String> lista = printer.Primitive_Array.get(i);
         * if (i == 0) {
         * lista.add((lista.size() / 2),
         * printer.traductor(arbolito.buscarnodo_altura(i).get(i).dato));
         * 
         * } else if (i == 1) {
         * 
         * for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
         * int pospadre = printer.Primitive_Array.get(i - 1)
         * .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));
         * 
         * if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo ==
         * arbolito.buscarnodo_altura(i).get(j)) {
         * lista.set(pospadre - 2,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * } else {
         * lista.set(pospadre + 2,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * }
         * }
         * } else if (i == 2) {
         * for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
         * ///////////////////////////////////////////////////////////////////
         * if (j == 0) {
         * int pospadre = printer.Primitive_Array.get(i - 1)
         * .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));
         * if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo ==
         * arbolito.buscarnodo_altura(i)
         * .get(j)) {
         * lista.add(pospadre - 1,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * } else {
         * lista.add(pospadre + 1,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * 
         * }
         * }
         * ///////////////////////////////////////////////////////////////////
         * int pospadre = printer.Primitive_Array.get(i - 1)
         * .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));
         * int p = 0;
         * if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo ==
         * arbolito.buscarnodo_altura(i)
         * .get(j)) {
         * lista.set(pospadre - 1,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * for (int k = 0; k < i; k++) {
         * 
         * printer.Primitive_Array.get(k).add(pospadre - 1 + p++, "-  -");
         * }
         * } else {
         * lista.set(pospadre + 1,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * for (int k = 0; k < i; k++) {
         * 
         * printer.Primitive_Array.get(k).add(pospadre + p++, "-  -");
         * }
         * }
         * // }
         * }
         * } else {
         * for (int j = 0; j < arbolito.buscarnodo_altura(i).size(); j++) {
         * int p = 0;
         * int pospadre = printer.Primitive_Array.get(i - 1)
         * .indexOf(printer.traductor(arbolito.buscarnodo_altura(i).get(j).padre.dato));
         * 
         * if (arbolito.buscarnodo_altura(i).get(j).padre.izquierdo ==
         * arbolito.buscarnodo_altura(i).get(j)) {
         * lista.add(pospadre - 1,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * 
         * for (int k = 0; k < i; k++) {
         * 
         * printer.Primitive_Array.get(k).add(pospadre + p++, "-  -");
         * }
         * 
         * } else {
         * lista.add(pospadre + 1,
         * printer.traductor(arbolito.buscarnodo_altura(i).get(j).dato));
         * for (int k = 0; k < i; k++) {
         * 
         * printer.Primitive_Array.get(k).add(pospadre + p++, "-  -");
         * }
         * }
         * }
         * }
         * System.out.println();
         * }
         */
        // --------------------------------------------------------------------------------------//
        // imprimimos el arbol
        for (int i = 0; i <= arbolito.AlturaMax(); i++) {
            for (String x : printer.Primitive_Array.get(i)) {
                System.out.print(x + "");
            }
            System.out.println();
        }
        // --------------------------------------------------------------------------------------//
        // imprimimos los nodos en cada nivel del arbol
        for (int i = 0; i <= arbolito.AlturaMax(); i++) {
            System.out.println("Altura: " + i);
            for (Nodo x : arbolito.buscarnodo_altura(i)) {

                System.out.print(x.dato + " ");

            }
            System.out.println();
        }
        System.out.println("Arbol final: " + arbolito.AlturaMax());
        arbolito.printer_ifwaszzz(arbolito.raiz);
        System.out.println("Version Con subarboles");
        Printer_Adaptable printer2 = new Printer_Adaptable();
        printer2.print(arbolito);
    }

}
