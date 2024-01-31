package supergrafo;

//import java.util.List;

public class SuperGrafo {
    public static void main(String[] args) {
        Grafo grafo = new Grafo("ponderado");
        System.out.println(grafo.getModalidad());
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
        
        int[] nose= Grafo.bellmanFord(grafo.getAdyacencia(),grafo.getIndiceVertice(v2));
        
        
        
        System.out.println("Belman Ford");
        for (int i = 0; i < nose.length; i++){
            System.out.println(nose[i]);
        }
        long [][] matriz = grafo.floydWarshall();
        for (int i = 0; i < matriz.length; i++){
            for (int j = 0; j < matriz.length; j++){
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
            System.out.println();
        }
        
        //grafo.imprimir_grafo();
        var camino = grafo.hayCiclo();
        System.out.println(camino);

    }
    
}
