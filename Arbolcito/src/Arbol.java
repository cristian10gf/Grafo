import java.util.ArrayList;
import java.util.Stack;


public class Arbol {

    public Nodo raiz;
    ArrayList<Nodo> listanodos;

    public Arbol() {
        this.raiz = null;
        this.listanodos= new ArrayList<Nodo>();
    }
    public static int alturaArbol(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return Math.max(alturaArbol(nodo.izquierdo), alturaArbol(nodo.derecho)) + 1;
    }

    public void agregar(int dato) {
        Nodo nodo = new Nodo(dato);
        if (this.raiz == null) {
            this.raiz = nodo;
            this.raiz.alturaNodo=0;
            this.listanodos.add(nodo);
        } else {
            agregarRecursivo(this.raiz, nodo);
            this.listanodos.add(nodo);
        }
    }
    public void agregarRecursivo(Nodo nodo, Nodo nuevoNodo) {
        if (nuevoNodo.dato < nodo.dato) {
            if (nodo.izquierdo == null) {
                nodo.izquierdo = nuevoNodo;
                nodo.izquierdo.alturaNodo=Alturanodo(nodo)+1;
            } else {
                agregarRecursivo(nodo.izquierdo, nuevoNodo);
                nodo.alturaNodo=Alturanodo(nodo);
            }
        } else {
            if (nodo.derecho == null) {
                nodo.derecho = nuevoNodo;
                nodo.derecho.alturaNodo=Alturanodo(nodo)+1;
            } else {
                agregarRecursivo(nodo.derecho, nuevoNodo);
                nodo.alturaNodo=Alturanodo(nodo);
            }
        }
    }
    public static int getcol(int h) {
        if (h == 1) {
            return 1;
        }
        return getcol(h - 1) + getcol(h - 1) + 1;
    }
    public static void printTree(int[][] M, Nodo root, int col, int row, int height) {
        if (root == null) {
            return;
        }
        M[row][col] = root.dato;
        printTree(M, root.izquierdo, col - (int) Math.pow(2, height - 2), row + 1, height - 1);
        printTree(M, root.derecho, col + (int) Math.pow(2, height - 2), row + 1, height - 1);
    }
    public void TreePrinter() {
        int h = Altura(this.raiz);
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
    public int [][] Matrizdeposicion() {
        int h = alturaArbol(this.raiz);
        int col = getcol(h);
        int[][] M = new int[h][col];
        printTree(M, this.raiz, col / 2, 0, h);
        return M;
    }
    
    public void imprimir(Nodo ñodo, int nivel) {
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

    public void preorden(Nodo ñodo) {
        if (ñodo == null) {
            return;

        }
        System.out.print(ñodo.dato);
        preorden(ñodo.izquierdo);
        preorden(ñodo.derecho);

        /* preorden(ñodo.izquierdo);
        System.out.println(ñodo.dato);
        preorden(ñodo.derecho);

        preorden(ñodo.izquierdo);
        preorden(ñodo.derecho);
        System.out.println(ñodo.dato);*/

    }

    //crea la funcion preorden de manera iterativa sin usar recursividad
    //usando una pila
    public void preordeniterativo(Nodo ñodo) {
        Stack<Nodo> pila = new Stack<Nodo>();
        while (true) {
            if (ñodo != null) {
                System.out.print(" "+ñodo.dato);
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
    //crea la funcion inorden de manera iterativa sin usar recursividad
    //usando una pila
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
                System.out.print(" "+ñodo.dato);
                ñodo = ñodo.derecho;
            }
        }
    }
    //crea la funcion postorden de manera iterativa sin usar recursividad
    //usando una pila
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
            System.out.print(" "+resultado.pop().dato);
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

    public void postorden(Nodo ñodo) {
        if (ñodo == null) {
            return;

        }
        postorden(ñodo.izquierdo);
        postorden(ñodo.derecho);
        System.out.print(ñodo.dato);

    }
    public static int Altura(Nodo nodo){
        if (nodo==null) {
            return 0;
        }
        return Math.max(Altura(nodo.izquierdo), Altura(nodo.derecho))+1;
    }
    public static int Alturanodo(Nodo nodo){
        if (nodo==null) {
            return 0;
        }
        return nodo.alturaNodo;
    }
   
    public Nodo buscarnodo(Nodo nodo, int valor){
        if (nodo==null) {
            return null;
        }
        if (nodo.dato==valor) {
            return nodo;
        }
        if (nodo.dato<valor) {
            return buscarnodo(nodo.derecho, valor);
        }
        return buscarnodo(nodo.izquierdo, valor);
    }

    
    public Nodo rotarderecha(Nodo nodo){
        //aux =x  y =nodo
        Nodo aux=nodo.izquierdo;
        Nodo temp= aux.derecho;
        aux.derecho=nodo;
        nodo.izquierdo=temp;
        aux.alturaNodo=Math.max(Alturanodo(aux.izquierdo), Alturanodo(aux.derecho))+1;
        nodo.alturaNodo=Math.max(Alturanodo(nodo.izquierdo), Alturanodo(nodo.derecho))+1;
        return aux;
    }
    public Nodo rotarizquierda(Nodo nodo){
        Nodo aux=nodo.derecho;
        Nodo temp= aux.izquierdo;
        aux.izquierdo=nodo;
        nodo.derecho=temp;
        aux.alturaNodo=Math.max(Alturanodo(aux.izquierdo), Alturanodo(aux.derecho))+1;
        nodo.alturaNodo=Math.max(Alturanodo(nodo.izquierdo), Alturanodo(nodo.derecho))+1;
        return aux;
    }
    public Nodo insertar(Nodo nodo, int valor){
        if(this.raiz==null){
            this.raiz= new Nodo(valor);
        }
        if (nodo==null) {
            Nodo nuevo= new Nodo(valor);
            nuevo.izquierdo=null;
            nuevo.derecho=null;
            nuevo.dato=valor;
            return nuevo;

        }
        int valueder =(nodo.derecho != null)?nodo.derecho.dato:0;
        int valueizq =(nodo.izquierdo != null)?nodo.izquierdo.dato:0;
        if (valor<valueizq) {
            insertar(nodo.izquierdo, valor);
        } else if (valor>valueder) {
            insertar(nodo.derecho, valor);

        } else {
            System.out.println("Dato duplicado");

        }
        int altura=Alturanodo(nodo);
        int fe=Calcularfe(nodo);
        if (fe>1 && valor<nodo.dato){
            System.out.println("rotar derecha");
            return rotarderecha(nodo);
        }
        if (fe<-1 && valor>nodo.dato){
            System.out.println("rotar izquierda");
            return rotarizquierda(nodo);
        }
        if (fe>1 && valor>nodo.dato){
            System.out.println("Rotar izquierda y luego derecha");
            nodo.izquierdo=rotarizquierda(nodo.izquierdo);
            return rotarderecha(nodo);
        }
        if (fe<-1 && valor<nodo.dato){
            System.out.println("Rotar derecha y luego izquierda");
            nodo.derecho=rotarderecha(nodo.derecho);
            return rotarizquierda(nodo);
        }
        return nodo;
    }
    public void insertar(int valor){
        this.raiz=insertar(this.raiz, valor);
    }
    public int Calcularfe(Nodo nodo){
        if (nodo==null) {
            return 0;
        }
        return Alturanodo(nodo.derecho)-Alturanodo(nodo.izquierdo);

    }
}