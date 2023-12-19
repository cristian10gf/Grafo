package supergrafo;

//import java.util.List;

public class SuperGrafo {
    public static void main(String[] args) {
        Grafo grafo = new Grafo("dirigido ponderado");
        Vertice v1 = new Vertice("S");
        Vertice v2 = new Vertice("A");
        Vertice v3 = new Vertice("B");
        Vertice v4 = new Vertice("C");
        Vertice v5 = new Vertice("D");
        Vertice v6 = new Vertice("E");
 
        grafo.conectarVertice(v1, v2, 2);
        v1.unirVertices(v3,4);
        v2.unirVertices(v4,7);
        v2.unirVertices(v3,1);
        v3.unirVertices(v5,3);
        v4.unirVertices(v5,1);
        v5.unirVertices(v6,2);
 
        //grafo.addVertice(v1);
        //grafo.addVertice(v2);
        grafo.addVertice(v3);
        grafo.addVertice(v4);
        grafo.addVertice(v5);
        grafo.addVertice(v6);
 
        grafo.imprimir_grafo();
        var camino = grafo.hayCiclo();
        System.out.println(camino);

    }
    
}
