package supergrafo;

public class SuperGrafo {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(false, true);
        
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

        grafo.imprimir_grafo();
        grafo.imprimirAdyacencia();

        System.out.println(grafo.getIndiceVertice(v1));

        Grafo grafo2 = Grafo.grafoCompleto(5);
        grafo2.imprimir_grafo();
        grafo2.imprimirAdyacencia();

        Grafo copia = new Grafo(grafo.getAdyacencia(), false, true);
        copia.imprimir_grafo();

        long[][] matriz = grafo.floydWarshall();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

        Grafo grafo3 = copia.PRIM();
        grafo3.imprimir_grafo();

        System.out.println(Grafo.fordFulkerson(grafo.getAdyacencia(), 3,5));

        System.out.println(grafo.dijkstra(3,5));

        System.out.println(grafo.gradoMin());
        System.out.println(grafo.gradoMax());
        System.out.println(grafo2.esCompleto());
        System.out.println(grafo2.esConexo());
        System.out.println(grafo2.esPlanar());
        System.out.println(grafo.esEuleriano());
        System.out.println(grafo.esHamiltoniano());
        System.out.println(grafo.getColoreo());
        System.out.println(grafo.radio());
        var vertices = grafo.diametroConVertices();
        System.out.println("Diametro: " + vertices[0] + " - " + vertices[1]);
        System.out.println(grafo.clique());
    }
}