package supergrafo;

import java.util.ArrayList;
import java.util.Arrays;

public class PRIM {
    private int V = 5; // Número de vértices en el grafo
    private static final int INF = Integer.MAX_VALUE; // Valor infinito para representar la ausencia de conexión
    public ArrayList<int[]> results;

    public PRIM(int n, int[][] graph) {
        V = n;
        results = primMST(graph);
    }

    // Función para encontrar el vértice con el mínimo valor de clave,
    // que aún no ha sido incluido en el árbol de expansión mínima
    int minKey(int[] key, boolean[] visited) {
        int min = INF, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // Imprime el árbol de expansión mínima
    ArrayList<int[]> printMST(int[] parent, int[][] graph) {
        ArrayList<int[]> result = new ArrayList<int[]>();
        //System.out.println("Arista \t Peso");
        for (int i = 1; i < V; i++) {
            int[] temp = {parent[i], i, graph[i][parent[i]]};
            result.add(temp);
        }
        return result;
    }

    // Algoritmo de Prim para encontrar el árbol de expansión mínima
    ArrayList<int[]> primMST(int[][] graph) {
        int[] parent = new int[V]; // Almacena el árbol de expansión mínima
        int[] key = new int[V]; // Almacena las claves (pesos) de los vértices
        boolean[] visited = new boolean[V]; // Almacena los vértices visitados

        // Inicializa todas las claves a infinito y todos los vértices como no visitados
        Arrays.fill(key, INF);
        Arrays.fill(visited, false);

        // La clave del primer vértice siempre es 0, ya que es el primer vértice del árbol
        key[0] = 0;
        parent[0] = -1; // No tiene padre

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, visited); // Obtiene el vértice con la clave mínima
            visited[u] = true;

            // Actualiza las claves de los vértices adyacentes si son menores que las claves actuales
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !visited[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        // Imprime el árbol de expansión mínima
        return printMST(parent, graph);
    }
}
