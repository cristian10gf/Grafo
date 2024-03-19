package superarbol;

public class Nodo {
    int dato;
    Nodo izquierdo;
    Nodo derecho;
    Nodo padre;
    int alturaNodo;
    int peso_izquierdo;
    int peso_derecho;
    public Nodo(int dato) {
        this.dato = dato;
        this.izquierdo= null;
        this.derecho= null;
        this.padre= null;
        this.alturaNodo=0;
        this.peso_izquierdo=0;
        this.peso_derecho=0;

    }
}