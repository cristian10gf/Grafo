package supergrafo;

public class SuperGrafo {
    public static void main(String[] args) {
        Grafo grafo = new Grafo("ponderado");
        
        Vertice v1 = new Vertice("S");
        Vertice v2 = new Vertice("A");
        Vertice v3 = new Vertice("B");
        Vertice v4 = new Vertice("C");
        Vertice v5 = new Vertice("D");
        Vertice v6 = new Vertice("E");
        
        grafo.conectarVertice(v1, v2,4);
        grafo.conectarVertice(v5, v6, 2);
        grafo.conectarVertice(v1, v3, 2);
        grafo.conectarVertice(v1, v4, 3);
        grafo.conectarVertice(v2, v3, 3);
        grafo.conectarVertice(v2, v4, 4);
        grafo.conectarVertice(v3, v4, 5);
        grafo.conectarVertice(v3, v5, 8);
        
        System.out.println(grafo.getModalidad());
        System.out.println(grafo.gradoMax());
        System.out.println(grafo.getColoreo());
        System.out.println(grafo.PRIM());
    }
}