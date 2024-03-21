
import superarbol.Nodo;
import java.util.ArrayList;

//matriz de piramide de pascal
public class Arbolo {
    Nodo raiz;
    ArrayList<Nodo> listanodos;

    public Arbolo() {
        this.raiz = null;
        this.listanodos = new ArrayList<Nodo>();
    }

    public void insertar(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            Nodo aux = raiz;
            Nodo padre;
            while (true) {
                padre = aux;
                if (dato < aux.dato) {
                    aux = aux.izquierdo;
                    if (aux == null) {
                        padre.izquierdo = nuevo;
                        nuevo.padre = padre;
                        break;
                    }
                } else {
                    aux = aux.derecho;
                    if (aux == null) {
                        padre.derecho = nuevo;
                        nuevo.padre = padre;
                        break;
                    }
                }
            }
        }
        listanodos.add(nuevo);
    }

    public void TreePrinter() {
        Nodo raiz = this.raiz;
        printNodo(raiz);
    }

    private void printNodo(Nodo root) {
        int maxLevel = maxLevel(root);
        printNodoInternal(listanodos, 1, maxLevel);
    }

    private void printNodoInternal(ArrayList<Nodo> list, int level, int maxLevel) {
        if (list.isEmpty() || isAllElementsNull(list))
            return;
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
        printWhitespaces(firstSpaces);
        ArrayList<Nodo> newNodos = new ArrayList<Nodo>();
        for (Nodo nodo : list) {
            if (nodo != null) {
                System.out.print(nodo.dato);
                newNodos.add(nodo.izquierdo);
                newNodos.add(nodo.derecho);
            } else {
                newNodos.add(null);
                newNodos.add(null);
                System.out.print(" ");
            }
            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < list.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (list.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }
                if (list.get(j).izquierdo != null)
                    System.out.print("/");
                else
                    printWhitespaces(1);
                printWhitespaces(i + i - 1);
                if (list.get(j).derecho != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);
                printWhitespaces(endgeLines + endgeLines - i);
            }
            System.out.println("");
        }}

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private int maxLevel(Nodo nodo) {
        if (nodo == null)
            return 0;
        return Math.max(maxLevel(nodo.izquierdo), maxLevel(nodo.derecho)) + 1;
    }

    private boolean isAllElementsNull(ArrayList list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Arbolo arbolito = new Arbolo();
        int[] valores = { 9,10, 5, 15, 3, 7, 12, 17, 2, 4, 6, 8, 11, 13, 16, 18};
        int x = 0;
        for (int valore : valores) {
            x++;
            arbolito.insertar(valore);
        }
        System.out.println("#______________________#");
        System.out.println("Arbol final");
        arbolito.TreePrinter();
    }


}
