package supergrafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    int V; // Número de vértices del grafo
    List<Edge> edges; // Lista de aristas

    Kruskal(int v) {
        V = v;
        edges = new ArrayList<>();
    }

    void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    // Función para encontrar el conjunto al que pertenece un vértice
    int find(int[] parent, int i) {
        if (parent[i] == -1) {
            return i;
        }
        return find(parent, parent[i]);
    }

    // Función para unir dos conjuntos en un único conjunto
    void union(int[] parent, int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }

    // Algoritmo de Kruskal para encontrar el árbol de expansión mínima
    public ArrayList<int[]> kruskalMST() {
        List<Edge> result = new ArrayList<>();
        int[] parent = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = -1;
        }

        // Ordena las aristas en orden ascendente de peso
        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));

        int edgeCount = 0;
        int i = 0;
        while (edgeCount < V - 1 && i < edges.size()) {
            Edge nextEdge = edges.get(i++);
            int x = find(parent, nextEdge.src);
            int y = find(parent, nextEdge.dest);

            // Si agregar la arista no forma un ciclo, agrégala al árbol de expansión mínima
            if (x != y) {
                result.add(nextEdge);
                union(parent, x, y);
                edgeCount++;
            }
        }

        /* for (Edge edge : result) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
        } */
        ArrayList<int[]> aristas = new ArrayList<>();
        for (Edge edge : result) {
            int [] valores = {edge.src,edge.dest,edge.weight};
            aristas.add(valores);
        }
        return aristas;
    }
}

class Edge {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}
