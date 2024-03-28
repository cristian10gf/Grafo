package superarbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.List;

//Implementaciones Pendientes:
// Implementar la funcion de eliminacion de nodos
// Implementar la funcion de balanceo de arboles
// Arreglar la impresion de arboles para que se vea mas bonito
// hacer posible la creacion de n-trees

public class Arbol {

    public Nodo raiz;
    ArrayList<Nodo> listaNodos;

    public Arbol() {
        this.raiz = null;
        this.listaNodos = new ArrayList<Nodo>();
    }

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
        this.listaNodos = new ArrayList<Nodo>();
    }

    public Arbol(int dato) {
        this.raiz = new Nodo(dato);
        this.listaNodos = new ArrayList<Nodo>();
    }

    // _____________________________________Funciones para el funcionamiento del
    // arbol sin balancear______________________________________//

    /**
     * Adds a new node with the given data to the tree.
     * If the tree is empty, the new node becomes the root.
     * Otherwise, the new node is added recursively to the appropriate position in
     * the tree.
     * The new node is also added to the list of nodes in the tree.
     *
     * @param dato The data to be added to the tree.
     */
    public void agregar(int dato) { // agrega un nodo al arbol sin importar si es AVL o no
        Nodo nodo = new Nodo(dato);
        if (this.raiz == null) {
            this.raiz = nodo;
            this.raiz.alturaNodo = 0;
            this.raiz.padre = null;
            this.listaNodos.add(nodo);
        } else {
            agregarRecursivo(this.raiz, nodo);

        }
    }

    /**
     * Adds a node to the tree recursively.
     * 
     * @param nodo      The current node being evaluated.
     * @param nuevoNodo The new node to be added.
     */
    public void agregarRecursivo(Nodo nodo, Nodo nuevoNodo) { // agrega un nodo al arbol en base a su dato; no equilibra
                                                              // el arbol
        if (nuevoNodo.dato < nodo.dato) {
            if (nodo.izquierdo == null) {
                nodo.izquierdo = nuevoNodo;
                nodo.izquierdo.padre = nodo;
                nodo.izquierdo.alturaNodo = Alturanodo(nodo) + 1;
                this.listaNodos.add(nuevoNodo);
            } else {
                agregarRecursivo(nodo.izquierdo, nuevoNodo);
                // nodo.alturaNodo = Alturanodo(nodo);
            }
        } else if (nuevoNodo.dato > nodo.dato) {
            if (nodo.derecho == null) {
                nodo.derecho = nuevoNodo;
                nodo.derecho.padre = nodo;
                nodo.derecho.alturaNodo = Alturanodo(nodo) + 1;
                this.listaNodos.add(nuevoNodo);
            } else {
                agregarRecursivo(nodo.derecho, nuevoNodo);
                // nodo.alturaNodo = Alturanodo(nodo);
            }
        } else if (buscarnodo(nuevoNodo.dato) != null) {
            System.out.println("El nodo ya existe");
        }
    }

    /**
     * Returns the height of a given node in the tree.
     * 
     * @param nodo The node for which to calculate the height.
     * @return The height of the node. Returns 0 if the node is null.
     */
    public static int Alturanodo(Nodo nodo) { // Devuelve la altura de un nodo dado
        if (nodo == null) {
            return 0;
        }
        return nodo.alturaNodo;
    }

    public static int getcol(int h) { // Devuelve la cantidad de columnas que tendra el arbol
        if (h == 1) {
            return 1;
        }
        return getcol(h - 1) + getcol(h - 1) + 1;
    }

    public static void printTree(int[][] M, Nodo root, int col, int row, int height) { // rellena una matriz con los
                                                                                       // nodos del arbol
        if (root == null) {
            return;
        }
        M[row][col] = root.dato;
        printTree(M, root.izquierdo, col - (int) Math.pow(2, height - 2), row + 1, height - 1);
        printTree(M, root.derecho, col + (int) Math.pow(2, height - 2), row + 1, height - 1);
    }

    public void TreePrinter() { // imprime el arbol de manera rudimentaria
        int h = Alturanodo(this.raiz);
        int col = getcol(h);
        int[][] M = new int[h][col];
        printTree(M, this.raiz, col / 2, 0, h);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < col; j++) {
                if (M[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(M[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int Calcularfe(Nodo nodo) { // devuelve el factor de equilibrio
        if (nodo == null) {
            return 0;
        }
        return Alturanodo(nodo.derecho) - Alturanodo(nodo.izquierdo);

    }

    public int[][] Matrizdeposicion() { // devuelve una matriz con la posicion de los nodos en el arbol
        int h = Alturanodo(this.raiz);
        int col = getcol(h);
        int[][] M = new int[h][col];
        printTree(M, this.raiz, col / 2, 0, h);
        return M;
    }

    public void imprimir(Nodo ñodo, int nivel) {// Primera Aproximacion para imprimir el arbol; No funciona bien para
                                                // arboles grandes; No se usa
        if (ñodo != null) {
            if (ñodo.izquierdo != null) {
                imprimir(ñodo.izquierdo, nivel + 1);
            }

            if (ñodo.derecho != null) {
                imprimir(ñodo.derecho, nivel + 1);
            }
        }
        for (int i = 0; i < nivel; i++) {
            System.out.print("      ");
        }
        System.out.println(ñodo.dato);
    }

    public int AlturaMax() { // funcion para calcular la altura maxima del arbol
        int max = 0;
        for (Nodo nodo : this.listaNodos) {
            if (nodo.alturaNodo > max) {
                max = nodo.alturaNodo;
            }
        }
        return max;
    }

    public void subnodo(Nodo nodo, ArrayList<Nodo> descendencia) { // rellena un array con la descendencia de un nodo dado
        if (nodo != null) {
            descendencia.add(nodo);
        }
        if (nodo.izquierdo != null) {
            subnodo(nodo.izquierdo, descendencia);
        }
        if (nodo.derecho != null) {
            subnodo(nodo.derecho, descendencia);
        }
    }

    public ArrayList<Nodo> descendientes(Nodo nodo) { // devuelve una lista con los descendientes de un nodo dado 
        ArrayList<Nodo> descendientes = new ArrayList<Nodo>();
        subnodo(nodo, descendientes);
        descendientes.remove(nodo);
        return descendientes;
    }

    public Arbol subArbol(Nodo nodo) { // devuelve un subarbol en base a un nodo dado
        Arbol subArbol = new Arbol(nodo);
        subArbol.raiz = nodo;
        subArbol.listaNodos = new ArrayList<Nodo>();
        subArbol.listaNodos.add(nodo);
        subArbol.listaNodos.addAll(descendientes(nodo));
        return subArbol;
    }
    

    // _____________________________________Funciones para el funcionamiento del
    // arbol AVL______________________________________//
    public Nodo rotarderecha(Nodo nodo) { // rotar a la derecha; esta funcion esta testeada y funciona teoricamente
        // aux =x y =nodo
        Nodo aux = nodo.izquierdo;
        Nodo temp = aux.derecho;
        aux.derecho = nodo;
        nodo.izquierdo = temp;
        nodo.alturaNodo = Math.max(Alturanodo(nodo.izquierdo), Alturanodo(nodo.derecho)) + 1;
        aux.alturaNodo = Math.max(Alturanodo(aux.izquierdo), Alturanodo(aux.derecho)) + 1;
        return aux;
    }

    public Nodo rotarizquierda(Nodo nodo) {
        Nodo aux = nodo.derecho;
        Nodo temp = aux.izquierdo;
        aux.izquierdo = nodo;
        nodo.derecho = temp;
        nodo.alturaNodo = Math.max(Alturanodo(nodo.izquierdo), Alturanodo(nodo.derecho)) + 1;
        aux.alturaNodo = Math.max(Alturanodo(aux.izquierdo), Alturanodo(aux.derecho)) + 1;

        return aux;
    }

    public void insertar(int valor) { // insertar un nodo en el arbol; Este se usa para insertar nodos en un arbol de
                                      // tipo AVL
        if (this.raiz == null) {
            this.raiz = insertar(this.raiz, valor);
            this.raiz.peso_derecho = 1;
        } else {
            insertar(this.raiz, valor);
        }
    }

    public Nodo insertar(Nodo nodo, int valor) {// insertar un nodo en el arbol; Al insertarse en el arbol se balancea
                                                // en
                                                // base a la altura de los nodos;not working
        if (nodo == null) {
            System.out.println("Nodo nuevo");
            Nodo nuevo = new Nodo(valor);
            listaNodos.add(nuevo);
            return nuevo;

        }
        if (nodo.dato == valor) {
            System.out.println("El nodo ya existe");
            return nodo;
        } else if (valor < nodo.dato) {
            System.out.println("Insertar a la izquierda");
            if (nodo.izquierdo == null) {
                Nodo nuevo = new Nodo(valor);
                listaNodos.add(nuevo);
                nodo.izquierdo = nuevo;
                nuevo.padre = nodo;
                if (nodo.derecho == null)
                    actualizarpeso(nuevo);
                return nuevo;
            } else {
                return insertar(nodo.izquierdo, valor);
            }
        } else {
            System.out.println("Insertar a la derecha");
            if (nodo.derecho == null) {
                Nodo nuevo = new Nodo(valor);
                listaNodos.add(nuevo);
                nodo.derecho = nuevo;
                nuevo.padre = nodo;
                if (nodo.izquierdo == null)
                    actualizarpeso(nuevo);
                return nuevo;
            } else {
                return insertar(nodo.derecho, valor);
            }
        }

    }

    public Arbol balanceararbol() { // balancea el arbol en base a la altura de los nodos
        Arbol arboltemp = new Arbol();
        ArrayList<Integer> nodosordenados = new ArrayList<Integer>();
        for (Nodo nodo : this.listaNodos) {
            nodosordenados.add(nodo.dato);
        }
        nodosordenados.sort(null);
        int raiz = nodosordenados.get(nodosordenados.size() / 2);
        nodosordenados.remove(nodosordenados.size() / 2);
        arboltemp.insertar(raiz);
        for (int i = 0; i < nodosordenados.size() / 2; i++) {
            arboltemp.insertar(nodosordenados.get(i));
            System.out.println("HAce el primer for");
            arboltemp.insertar(nodosordenados.get(nodosordenados.size() - i - 1));
        }
        return arboltemp;
    }

    public ArrayList<Nodo> listanodos() { // devuelve una lista con todos los nodos del arbol
        return this.listaNodos;
    }

    public ArrayList<Nodo> buscarnodo_altura(int altura) { // devuelve una lista con todos los nodos que tienen una
                                                           // altura dada
        ArrayList<Nodo> nodos = new ArrayList<Nodo>();
        for (Nodo nodo : this.listaNodos) {
            if (nodo.alturaNodo == altura) {
                nodos.add(nodo);
            }
        }
        // ordenamos la lista de nodos por su valor de menor a mayor
        List<Integer> nodosOrdenados = new ArrayList<Integer>();
        for (Nodo nodo : nodos) {
            nodosOrdenados.add(nodo.dato);
        }
        nodosOrdenados.sort(null);
        ArrayList<Nodo> nodosOrdenadosNodos = new ArrayList<Nodo>();
        for (int i = 0; i < nodosOrdenados.size(); i++) {
            nodosOrdenadosNodos.add(buscarnodo(nodosOrdenados.get(i)));
        }
        return nodosOrdenadosNodos;
    }

    // _______________________Funciones Extra
    // No-Esenciales__________________________________//

    /**
     * Updates the weight of a node by counting the number of branches it has.
     * The branch is only counted if it is downward, meaning it does not have
     * sibling nodes when creating the branch.
     * 
     * @param nodo The node to update the weight for.
     */
    public void actualizarpeso(Nodo nodo) {// cuenta cuantas ramificaciones tiene un nodo, la ramificacion solo se
        // cuenta si es hacia bajo==que al crear la rama no tenga nodos hermanos;No se
        // usa

        if (nodo == null) {
            return;
        }
        if (nodo.padre != null) {
            if (nodo.padre.derecho == nodo) {
                nodo.padre.peso_derecho++;
                actualizarpeso(nodo.padre);
            } else {
                nodo.padre.peso_izquierdo++;
                actualizarpeso(nodo.padre);
            }
        }
    }

    public Nodo buscarnodo_recursivo(Nodo nodo, int valor) { // funcion para buscar un nodo en el arbol en base a su
                                                             // valor
        if (nodo == null) {
            return null;
        }
        if (nodo.dato == valor) {
            return nodo;
        }
        if (nodo.dato < valor) {
            return buscarnodo_recursivo(nodo.derecho, valor);
        }
        return buscarnodo_recursivo(nodo.izquierdo, valor);
    }

    public Nodo buscarnodo(int valor) { // funcion para buscar un nodo en el arbol en base a su valor
        return buscarnodo_recursivo(this.raiz, valor);
    }

    public void printer_ifwaszzz(Nodo node) { // imprime el arbol de manera pragamtica; me lo robe de github
        String str = "";
        if (node.izquierdo != null)
            str += node.izquierdo.dato + "=>";
        else
            str += "END=>";
        str += node.dato + "";
        if (node.derecho != null)
            str += "<=" + node.derecho.dato;
        else
            str += "<=END";
        System.out.println(str);
        if (node.izquierdo != null)
            printer_ifwaszzz(node.izquierdo);
        if (node.derecho != null)
            printer_ifwaszzz(node.derecho);
    }

    public void preorden(Nodo ñodo) { // Preorden recursivo
        if (ñodo == null) {
            return;

        }
        System.out.print(ñodo.dato);
        preorden(ñodo.izquierdo);
        preorden(ñodo.derecho);

    }

    // crea la funcion preorden de manera iterativa sin usar recursividad
    // usando una pila
    public void preordeniterativo(Nodo ñodo) {
        Stack<Nodo> pila = new Stack<Nodo>();
        while (true) {
            if (ñodo != null) {
                System.out.print(" " + ñodo.dato);
                pila.push(ñodo);
                ñodo = ñodo.izquierdo;
            } else {
                if (pila.isEmpty()) {
                    break;
                }
                ñodo = pila.pop();
                ñodo = ñodo.derecho;
            }
        }
    }

    // crea la funcion inorden de manera iterativa sin usar recursividad
    // usando una pila
    public void inordeniterativo(Nodo ñodo) {
        Stack<Nodo> pila = new Stack<Nodo>();
        while (true) {
            if (ñodo != null) {
                pila.push(ñodo);
                ñodo = ñodo.izquierdo;
            } else {
                if (pila.isEmpty()) {
                    break;
                }
                ñodo = pila.pop();
                System.out.print(" " + ñodo.dato);
                ñodo = ñodo.derecho;
            }
        }
    }

    // crea la funcion postorden de manera iterativa sin usar recursividad
    // usando una pila
    public void postordeniterativo(Nodo ñodo) {
        Stack<Nodo> pila = new Stack<Nodo>();
        Stack<Nodo> resultado = new Stack<Nodo>();

        pila.push(ñodo);
        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();
            resultado.push(actual);

            if (actual.izquierdo != null) {
                pila.push(actual.izquierdo);
            }
            if (actual.derecho != null) {
                pila.push(actual.derecho);
            }
        }

        while (!resultado.isEmpty()) {
            System.out.print(" " + resultado.pop().dato);
        }
    }

    public void postordeniterativo2(Nodo ñodo) {
        Stack<Nodo> pila = new Stack<Nodo>();
        Nodo ultimoVisitado = null;

        while (ñodo != null || !pila.isEmpty()) {
            if (ñodo != null) {
                pila.push(ñodo);
                ñodo = ñodo.izquierdo;
            } else {
                Nodo peekNodo = pila.peek();
                if (peekNodo.derecho != null && ultimoVisitado != peekNodo.derecho) {
                    ñodo = peekNodo.derecho;
                } else {
                    System.out.print(peekNodo.dato);
                    ultimoVisitado = pila.pop();
                }
            }
        }
    }

    public void postorden(Nodo ñodo) { // recorrido postorden recursivo
        if (ñodo == null) {
            return;

        }
        postorden(ñodo.izquierdo);
        postorden(ñodo.derecho);
        System.out.print(ñodo.dato);

    }

    class QItem {

        Nodo node;
        int hd;

        public QItem(Nodo n, int h) {
            node = n;
            hd = h;
        }
    }

    public void printTopView() { // imprime la vista superior del arbol
        // base case
        if (this.raiz == null) {
            return;
        }

        // Creates an empty hashset
        HashSet<Integer> set = new HashSet<>();

        // Create a queue and add root to it
        Queue<QItem> Q = new LinkedList<QItem>();
        Q.add(new QItem(this.raiz, 0)); // Horizontal distance of root is 0

        // Standard BFS or level order traversal loop
        while (!Q.isEmpty()) {
            // Remove the front item and get its details
            QItem qi = Q.remove();
            int hd = qi.hd;
            Nodo n = qi.node;

            // If this is the first node at its horizontal distance,
            // then this node is in top view
            if (!set.contains(hd)) {
                set.add(hd);
                System.out.print(n.dato + " ");
            }

            // Enqueue left and right children of current node
            if (n.izquierdo != null) {
                Q.add(new QItem(n.izquierdo, hd - 1));
            }
            if (n.derecho != null) {
                Q.add(new QItem(n.derecho, hd + 1));
            }
        }
    }

}
