package supergrafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;

public class Grafo {
    public static final long INFI = Integer.MAX_VALUE;
    private ArrayList<Vertice> vertices; // lista de vertices del grafo
    private int adyacencia[][]; // matriz de adyacencia del grafo
    private boolean dirigido; // si el grafo es dirigido
    private boolean ponderado; // si el grafo es ponderado
    
    /**
     * Constructor de la clase Grafo.
     *
     * @param dirigido  un booleano que indica si el grafo es dirigido (true) o no dirigido (false) 
     * @param ponderado un booleano que indica si el grafo es ponderado (true) o no ponderado (false)
     */
    public Grafo(boolean dirigido, boolean ponderado) {
        this.dirigido = dirigido;
        this.ponderado = ponderado;
        this.vertices = new ArrayList<>();
        this.adyacencia = new int[vertices.size()][vertices.size()];
    }

    /**
     * Constructor de la clase Grafo
     *
     * @param vertices  una lista de vértices del grafo
     * @param dirigido  true si el grafo es dirigido, false de lo contrario.
     * @param ponderado true si el grafo es ponderado, false de lo contrario.
     */
    public Grafo(ArrayList<Vertice> vertices, boolean dirigido, boolean ponderado){
        this.dirigido = dirigido;
        this.ponderado = ponderado;
        this.vertices = vertices;
        this.adyacencia = new int[vertices.size()][vertices.size()];
    }

    /**
     * Inicializa la lista de vértices como un ArrayList vacío.
     * Establece el grafo como no dirigido y no ponderado por defecto.
    */
    public Grafo() {
        this.vertices = new ArrayList<>();
        this.dirigido = false;
        this.ponderado = false;
        this.adyacencia = new int[vertices.size()][vertices.size()];
    }
    
    /**
      *
      * @param dirigido un booleano que indica si el grafo es dirigido (true) o no dirigido (false)
      */
    public Grafo(boolean dirigido){
        this.vertices = new ArrayList<>();
        this.dirigido = dirigido;
        this.ponderado = false;
        this.adyacencia = new int[vertices.size()][vertices.size()];
    }

    public Grafo(int[][] adyacencia, boolean dirigido, boolean ponderado){
        this.dirigido = dirigido;
        this.ponderado = ponderado;
        this.adyacencia = adyacencia;
        this.vertices = new ArrayList<>();

        for (int i = 0; i < adyacencia.length; i++) {
            Vertice vertice = new Vertice(String.valueOf(i));
            this.vertices.add(vertice);
        }

        for (int i = 0; i < adyacencia.length; i++) {
            for (int j = 0; j < adyacencia.length; j++) {
                if (dirigido && adyacencia[i][j] > 0) {
                    conectarVertice(vertices.get(i), vertices.get(j), adyacencia[i][j]);
                } 
                if (adyacencia[i][j] > 0 && i <= j && !dirigido) {
                    conectarVertice(vertices.get(i), vertices.get(j), adyacencia[i][j]);
                }
            }
        }
    }

    // ________________________________________ configuracion de grafo _________________________________________________________________________________

    /**
     * Actualiza la matriz de adyacencia del grafo.
     * Recorre todos los vértices del grafo y genera las relaciones en la matriz de adyacencia
     * a partir de las aristas de cada vértice.
     */
    private void uptade() {
        this.adyacencia = new int[vertices.size()][vertices.size()];
        for (Vertice vertice : vertices) {
            for (Arista arista : vertice.getAristas()) {
                generarRelacacionMatriz(vertice, arista.getDestino());
            }
        }
    }

    /**
     * Genera la relación en la matriz de adyacencia entre dos vértices.
     * La relación se establece según la modalidad del grafo.
     * 
     * @param v1 el primer vértice de la relación
     * @param v2 el segundo vértice de la relación
     */
    private void generarRelacacionMatriz(Vertice v1, Vertice v2) {
        int indexV1 = this.getIndiceVertice(v1);
        int indexV2 = this.getIndiceVertice(v2);

        if (adyacencia[indexV1][indexV2] != 0) return;

        if (dirigido && !ponderado) {
            adyacencia[indexV1][indexV2] = 1;
        } else if (!dirigido && !ponderado) {
            adyacencia[indexV1][indexV2] = 1;
            adyacencia[indexV2][indexV1] = 1;
        } else if (ponderado && !dirigido) {
            adyacencia[indexV1][indexV2] = v1.getArista(v2).getPeso();
            adyacencia[indexV2][indexV1] = v2.getArista(v1).getPeso();
        } else if (ponderado && dirigido) {
            adyacencia[indexV1][indexV2] = v1.getArista(v2).getPeso();
        }
    }


    //  _________________________________________geters y seters de grafo _________________________________________________________________________________________________________________________
    
    /**
     * Devuelve una lista de los vértices del grafo.
     *
     * @return una lista de los vértices del grafo.
     */
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    /**
     * Devuelve un vértice del grafo según su índice.
     *
     * @param indice el índice del vértice a buscar
     * @return el vértice en el índice especificado
     * @throws IllegalArgumentException si el vértice no existe
     */
    public Vertice getVertice(int indice) {
        if (indice < 0 || indice >= vertices.size()) {
            throw new IllegalArgumentException("El Indice del vértice no existe");
        }
        return vertices.get(indice);
    }

    /**
     * Devuelve un vértice del grafo según su dato.
     *
     * @param dato el dato del vértice a buscar
     * @return el vértice con el dato especificado
     */
    public Vertice getVertice(String dato) {
        for (Vertice vertice : vertices) {
            if (vertice.getDato().equals(dato)) {
                return vertice;
            }
        }
        return null;
    }

    /**
     * Devuelve la matriz de adyacencia del grafo.
     *
     * @return la matriz de adyacencia del grafo
     */
    public int[][] getAdyacencia() {
        return adyacencia.clone();
    }

    /**
     * Devuelve una lista de las aristas del grafo.
     *
     * @return una lista de las aristas del grafo
     */
    public ArrayList<Arista> getAristas() {
        ArrayList<Arista> aristas = new ArrayList<>();
        for (Vertice vertice : vertices) {
            for (Arista arista : vertice.getAristas()) {
                aristas.add(arista);
            }
        }
        return aristas;
    }

    /**
     * Imprime el grafo mostrando los vértices y las aristas conectadas a cada vértice.
     */
    public void imprimir_grafo() {
        uptade();
        for (Vertice vertice : vertices) {
            System.out.print(vertice.getDato() + " -> ");
            for (Arista arista : vertice.getAristas()) {
                System.out.print(arista.getDestino().getDato() + "(" + arista.getPeso() + ") ");
            }
            System.out.println("");
        }
    }


    /**
     * Muestra la matriz de adyacencia del grafo.
     */
    public void imprimirAdyacencia(){
        uptade();
        for (int i = 0; i < adyacencia.length; i++) {
            for (int j = 0; j < adyacencia.length; j++) {
                System.out.print(adyacencia[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * Devuelve el índice de un vértice en la lista de vértices del grafo.
     *
     * @param vertice el vértice a buscar
     * @return el índice del vértice en la lista de vértices del grafo
     */ 
    public int getIndiceVertice(Vertice vertice) {
        return vertices.indexOf(vertice);
    }
    

    /**
     * Calcula y devuelve una matriz de recorrido a partir de la matriz de adyacencia del grafo.
     * Si no hay una conexión directa entre dos vértices, se asigna el valor INFI.
     * 
     * @return la matriz de recorrido del grafo
     */
    public long[][] matrizRecorrido() {
        uptade();
        long[][] matriz = new long[adyacencia.length][adyacencia.length];
        for (int i = 0; i < adyacencia.length; i++) {
            for (int j = 0; j < adyacencia.length; j++) {
                if (adyacencia[i][j] != 0) {
                    matriz[i][j] = adyacencia[i][j];
                } else {
                    matriz[i][j] = INFI;
                }
            }
        }
        return matriz;
    }


    /**
     * Devuelve el grado máximo del grafo.
     * El grado máximo se refiere al número máximo de aristas que tiene un vértice en el grafo.
     *
     * @return El grado máximo del grafo.
     */
    public int gradoMax(){
        int max = 0;
        for (Vertice vertice : vertices) {
            if (vertice.getAristas().size() > max) {
                max = vertice.getAristas().size();
            }
        }
        return max;
    }

    /**
     * Devuelve el grado mínimo del grafo.
     * El grado mínimo se refiere al número mínimo de aristas que tiene un vértice en el grafo.
     *
     * @return el grado mínimo del grafo
     */
    public int gradoMin(){
        int min = 0;
        for (Vertice vertice : vertices) {
            if (vertice.getAristas().size() < min) {
                min = vertice.getAristas().size();
            }
        }
        return min;
    }



    /**
     * Devuelve una lista de los vértices de mayor grado en el grafo.
     * El grado de un vértice se refiere al número de aristas que tiene el vértice en el grafo.
     *
     * @return una lista de los vértices de mayor grado en el grafo
     */
    public ArrayList<Vertice> getVerticesMayorGrado(){
        ArrayList<Vertice> verticesMayorGrado = new ArrayList<>();
        int max = gradoMax();
        for (Vertice vertice : vertices) {
            if (vertice.getAristas().size() == max) {
                verticesMayorGrado.add(vertice);
            }
        }
        return verticesMayorGrado;
    }

    /**
     * Devuelve una lista de los vértices de menor grado en el grafo.
     * El grado de un vértice se refiere al número de aristas que tiene el vértice en el grafo.
     *
     * @return una lista de los vértices de menor grado en el grafo
     */
    public ArrayList<Vertice> getVerticesMenorGrado(){
        ArrayList<Vertice> verticesMenorGrado = new ArrayList<>();
        int min = gradoMin();
        for (Vertice vertice : vertices) {
            if (vertice.getAristas().size() == min) {
                verticesMenorGrado.add(vertice);
            }
        }
        return verticesMenorGrado;
    }


    /**
     * Devuelve el grado de un vértice en el grafo.
     * El grado de un vértice se refiere al número de aristas que tiene el vértice en el grafo.
     *
     * @param vertice el vértice a buscar
     * @return el grado del vértice en el grafo
     */
    public int grado(Vertice vertice){
        return vertice.grado();
    }

    /**
     * Devuelve el grado de un vértice en el grafo.
     * El grado de un vértice se refiere al número de aristas que tiene el vértice en el grafo.
     *
     * @param vertice el índice del vértice a buscar
     * @return el grado del vértice en el grafo
     */
    public int grado(int vertice){
        return vertices.get(vertice).grado();
    }

  
    /**
     * Devuelve una arista entre dos vértices en el grafo.
     * 
     * @param v1 el primer vértice de la arista
     * @param v2 el segundo vértice de la arista
     * @return la arista entre los vértices v1 y v2
     * @throws IllegalArgumentException si alguno de los vértices no existe en el grafo
     */
    public Arista getArista(Vertice v1, Vertice v2){
        if (!this.vertices.contains(v1) || !this.vertices.contains(v2)){
            throw new IllegalArgumentException("El vértice no existe");
        }
        return v1.getArista(v2);
    }

    /**
     * Devuelve una arista entre dos vértices en el grafo.
     * 
     * @param v1 el índice del primer vértice de la arista
     * @param v2 el índice del segundo vértice de la arista
     * @return la arista entre los vértices v1 y v2
     * @throws IllegalArgumentException si alguno de los vértices no existe en el grafo
     */
    public Arista getArista(int v1, int v2){
        if (v1 < 0 || v1 >= vertices.size() || v2 < 0 || v2 >= vertices.size()){
            throw new IllegalArgumentException("El vértice no existe");
        }
        return vertices.get(v1).getArista(vertices.get(v2));
    }

    /**
       *

     * @return true si el grafo es dirigido, false de lo contrario.

      */
    public boolean esDirigido(){
        return dirigido;
    }

    /**
     * Verifica si el grafo es ponderado.
     *
     * @return true si el grafo es ponderado, false en caso contrario.
     */
    public boolean esPonderado(){
        return ponderado;
    }

    // _________________________________________metodos de grafo _________________________________________________________________________________________________________________________

    /**
     * Agrega un vértice al grafo.
     * 
     * @param vertice el vértice a agregar
     * @throws IllegalArgumentException si el vértice ya existe en el grafo
     */
    public void addVertice(Vertice vertice) {
        boolean mismoDato = false;
        for (Vertice v : vertices){
            if (v == vertice){
                mismoDato = true;
                break;
            }
        }

        if (this.vertices.contains(vertice)) {
            throw new IllegalArgumentException("El vértice ya existe");
        } else if (!ponderado && vertice.getEstado().equalsIgnoreCase("ponderado")){
            throw new IllegalArgumentException("El grafo no debe ser ponderado y el vertice tiene  aristas con peso diferente de 1");
        } else if (mismoDato){
            throw new IllegalArgumentException("El vértice ya existe");
        } 
            
        this.vertices.add(vertice);
        this.adyacencia = new int[vertices.size()][vertices.size()];
    }

    /**
     * Agrega un conjunto de vértices al grafo.
     * 
     * @param vertices el conjunto de vértices a agregar
     */
    public void addVertice(ArrayList<Vertice> vertices) {
        for (Vertice vertice : vertices) {
            if (!this.vertices.contains(vertice)) {
                this.addVertice(vertice);;
            }
        }
    }

    /**
     * Agrega un vértice al grafo.
     * 
     * @param dato el dato del vértice a agregar
     * funcion aun experimental puede dar comportamientos no esperados
     */
    public void addVertice(String dato) {
        for (Vertice vertice : vertices) {
            if (vertice.getDato().equals(dato)) {
                throw new IllegalArgumentException("El vértice ya existe");
            }
        }
        Vertice vertice = new Vertice(dato);
        this.vertices.add(vertice);

        uptade();
    }



    /**
     * El método maneja cuatro casos:
        * 1. Grafo dirigido y no ponderado: Conecta v1 a v2.
        * 2. Grafo no dirigido y no ponderado: Conecta v1 a v2 y v2 a v1.
        * 3. Grafo no dirigido y ponderado: Conecta v1 a v2 y v2 a v1 con el peso especificado.
        * 4. Grafo dirigido y ponderado: Conecta v1 a v2 con el peso especificado.
     * @param v1 El primer vértice a conectar.
     * @param v2 El segundo vértice a conectar.
     * @param peso El peso de la arista que conecta los vértices. Este parámetro se usa solo si el grafo es ponderado.
     */
    public void conectarVertice(Vertice v1, Vertice v2, int peso) {
        if (v1.getArista(v2) != null || (v2.getArista(v1) != null && !dirigido) ) {
            System.out.println("La arista ya existe");
            return;
        }
        
        if (dirigido && !ponderado) {
            v1.unirVertices(v2);
        } else if (!dirigido && !ponderado) {
            v1.unirVertices(v2);
            v2.unirVertices(v1);
        } else if (ponderado && !dirigido) {
            v1.unirVertices(v2, peso);
            v2.unirVertices(v1, peso);
        } else if (ponderado && dirigido) {
            v1.unirVertices(v2, peso);
        }

        if (!this.vertices.contains(v1)) this.vertices.add(v1);
        
        if (!this.vertices.contains(v2)) this.vertices.add(v2);
        
        uptade();
    }

    /**
     * Conecta dos vértices en el grafo.
     * 
     * @param v1 el primer vértice a conectar
     * @param v2 el segundo vértice a conectar
     */
    public void conectarVertice(Vertice v1, Vertice v2) {
        conectarVertice(v1, v2, 1);
    }



    /**
     * Elimina un vértice del grafo.
     *
     * @param vertice el vértice a eliminar
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public void eliminarVertice(Vertice vertice) {
        if (!this.vertices.contains(vertice)) throw new IllegalArgumentException("El vértice no existe");
        
        this.vertices.remove(vertice);
        for (Vertice v : vertices) v.eliminarArista(vertice.getArista(v));
       
        uptade();
    }

    /**
     * Elimina un conjunto de vértices del grafo.
     *
     * @param vertices el conjunto de vértices a eliminar
     */
    public void eliminarVertice(ArrayList<Vertice> vertices) {
        for (Vertice vertice : vertices) {
            eliminarVertice(vertice);
        }
    }

    /**
     * Elimina un vértice del grafo.
     *
     * @param dato el dato del vértice a eliminar
     * funcion aun experimental puede dar comportamientos no esperados
     */
    public void eliminarVertice(String dato) {
        Vertice vertice = getVertice(dato);
        if (vertice == null) {
            throw new IllegalArgumentException("El vértice no existe");
        }

        eliminarVertice(vertice);
    }



    /**
     * Elimina una arista del grafo.
     *
     * @param v1 el primer vértice de la arista
     * @param v2 el segundo vértice de la arista
     * @throws IllegalArgumentException si la arista no existe en el grafo
     */
    public void eliminarArista(Vertice v1, Vertice v2) {
        if (!this.vertices.contains(v1) || !this.vertices.contains(v2)) {
            throw new IllegalArgumentException("La arista no existe");
        }

        v1.eliminarArista(v1.getArista(v2));
        if (!dirigido) v2.eliminarArista(v2.getArista(v1));
    
        uptade();
    }

    /**
     * Elimina una arista del grafo.
     *
     * @param arista la arista a eliminar
     * @throws IllegalArgumentException si la arista no existe en el grafo
     */
    public void eliminarArista(Arista arista) {
        eliminarArista(arista.getOrigen(), arista.getDestino());
    }



    /**
     * Realiza un recorrido en anchura (BFS) en el grafo desde un vértice de inicio hasta un vértice de destino.
     * Devuelve una lista de enteros que representa la ruta desde el vértice de inicio hasta el vértice de destino.
     *
     * @param inicio el vértice de inicio del recorrido (su indice)
     * @param fin el vértice de destino del recorrido (su indice)
     * @return una lista de enteros que representa la ruta desde el vértice de inicio hasta el vértice de destino
     */
    public ArrayList<Integer> BFS(int inicio, int fin) {
        uptade();
        ArrayList<Integer> ruta = new ArrayList<>();
        Queue<Integer> cola = new LinkedList<>();
        boolean[] visitados = new boolean[vertices.size()];
        int[] padre = new int[vertices.size()];
    
        Arrays.fill(padre, -1);
    
        cola.offer(inicio);
        visitados[inicio] = true;
    
        while (!cola.isEmpty()) {
            int actual = cola.poll();
    
            if (actual == fin) {
                break;
            }
    
            for (int i = 0; i < vertices.size(); i++) {
                if (adyacencia[actual][i] > 0 && !visitados[i]) {
                    cola.offer(i);
                    visitados[i] = true;
                    padre[i] = actual;
                }
            }
        }
    
        if (!visitados[fin]) {
            return ruta; // Retorna una lista vacía si no hay camino
        }
    
        for (int i = fin; i != -1; i = padre[i]) {
            ruta.add(0, i);
        }
    
        return ruta;
    }

    /**
     * Realiza un recorrido en anchura (BFS) en el grafo desde un vértice de inicio hasta un vértice de destino.
     * Devuelve una lista de vértices que representa la ruta desde el vértice de inicio hasta el vértice de destino.
     *
     * @param inicio el vértice de inicio del recorrido
     * @param fin el vértice de destino del recorrido
     * @return una lista de vértices que representa la ruta desde el vértice de inicio hasta el vértice de destino
     */
    public ArrayList<Vertice> BFS(Vertice inicio, Vertice fin) {
        uptade();
        int inicioIndice = getIndiceVertice(inicio);
        int finIndice = getIndiceVertice(fin);

        ArrayList<Vertice> ruta = new ArrayList<>();
        Queue<Integer> cola = new LinkedList<>();
        boolean[] visitados = new boolean[vertices.size()];
        int[] padre = new int[vertices.size()];

        Arrays.fill(padre, -1);

        cola.offer(inicioIndice);
        visitados[inicioIndice] = true;

        while (!cola.isEmpty()) {
            int actual = cola.poll();

            if (actual == finIndice) {
                break;
            }

            for (int i = 0; i < vertices.size(); i++) {
                if (adyacencia[actual][i] > 0 && !visitados[i]) {
                    cola.offer(i);
                    visitados[i] = true;
                    padre[i] = actual;
                }
            }
        }

        if (!visitados[finIndice]) {
            return ruta; // Retorna una lista vacía si no hay camino
        }

        for (int i = finIndice; i != -1; i = padre[i]) {
            ruta.add(0, vertices.get(i));
        }

        return ruta;
    }


    
    /**
     * Realiza un recorrido en profundidad (DFS) en el grafo a partir de un vértice de inicio.
     * Devuelve una lista con los vértices visitados en el orden en que fueron encontrados.
     *
     * @param inicio el vértice de inicio para el recorrido DFS (su indice)
     * @return una lista con los vértices visitados en el orden en que fueron encontrados
     */
    public ArrayList<Integer> DFS(int inicio) {
        uptade();
        boolean[] visitados = new boolean[vertices.size()];
        ArrayList<Integer> ruta = new ArrayList<>();
        Stack<Integer> pila = new Stack<>();
        pila.push(inicio);

        while (!pila.isEmpty()) {
            int actual = pila.pop();

            if (!visitados[actual]) {
                visitados[actual] = true;
                ruta.add(actual);

                for (int i = 0; i < vertices.size(); i++) {
                    if (adyacencia[actual][i] > 0 && !visitados[i]) {
                        pila.push(i);
                    }
                }
            }
        }

        return ruta;
    }

    /**
     * Realiza un recorrido en profundidad (DFS) en el grafo a partir de un vértice de inicio.
     * Devuelve una lista de vértices visitados en el orden en que fueron encontrados.
     *
     * @param inicio el vértice de inicio para el recorrido DFS
     * @return una lista de vértices visitados en el orden en que fueron encontrados
     */
    public ArrayList<Vertice> DFS(Vertice inicio) {
        uptade();
        int inicioIndice = getIndiceVertice(inicio);
        boolean[] visitados = new boolean[vertices.size()];
        ArrayList<Vertice> ruta = new ArrayList<>();
        Stack<Integer> pila = new Stack<>();
        pila.push(inicioIndice);

        while (!pila.isEmpty()) {
            int actual = pila.pop();

            if (!visitados[actual]) {
                visitados[actual] = true;
                ruta.add(vertices.get(actual));

                for (int i = 0; i < vertices.size(); i++) {
                    if (adyacencia[actual][i] > 0 && !visitados[i]) {
                        pila.push(i);
                    }
                }
            }
        }

        return ruta;
    }



    /**
     * Verifica si hay un ciclo en el grafo.
     * 
     * @return true si hay un ciclo, false de lo contrario.
     */
    public boolean hayCiclo() {
        boolean[] visitados = new boolean[vertices.size()];
        boolean[] pila = new boolean[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            if (hayCiclo(i, visitados, pila)) {
                return true;
            }
        }

        return false;
    }

    private boolean hayCiclo(int actual, boolean[] visitados, boolean[] pila) {
        if (pila[actual]) {
            return true;
        }

        if (visitados[actual]) {
            return false;
        }

        visitados[actual] = true;
        pila[actual] = true;

        for (int i = 0; i < vertices.size(); i++) {
            if (adyacencia[actual][i] > 0 && hayCiclo(i, visitados, pila)) {
                return true;
            }
        }

        pila[actual] = false;

        return false;
    }




 // _________________________________________ caminos minimos _________________________________________________________________________________________________________________________

    /**
     * Aplica el algoritmo de Floyd-Warshall para encontrar las distancias mínimas entre todos los pares de nodos en un grafo ponderado.
     * 
     * @param grafo la matriz de adyacencia que representa el grafo ponderado
     * @return la matriz de distancias mínimas entre todos los pares de nodos
     */
    public static long[][] floydWarshall(long[][] grafo) {
        int V = grafo.length;
        long[][] distancia = new long[V][V];

        // Inicializamos la matriz de distancias con los valores del grafo
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                distancia[i][j] = grafo[i][j];
            }
        }

        // Calcular las distancias mínimas entre todos los pares de nodos
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (distancia[i][k] + distancia[k][j] < distancia[i][j]) {
                        distancia[i][j] = distancia[i][k] + distancia[k][j];
                    }
                }
            }
        }

        // Imprimir la matriz de distancias mínimas
        /* for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (distancia[i][j] == INFI) {
                    System.out.print("INF \t");

                } else {
                    System.out.print(distancia[i][j] + "\t");
                }
            }
            System.out.println();
        } */
        return distancia;
    }

    /**
     * Aplica el algoritmo de Floyd-Warshall para encontrar las distancias mínimas entre todos los pares de nodos en el grafo.
     * 
     * @return una matriz de distancias mínimas entre todos los pares de nodos en el grafo.
     */
    public long[][] floydWarshall() {
        int V = this.matrizRecorrido().length;
        long[][] distancia = new long[V][V];

        // Inicializamos la matriz de distancias con los valores del grafo
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                distancia[i][j] = this.matrizRecorrido()[i][j];
            }
        }

        // Calcular las distancias mínimas entre todos los pares de nodos
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (distancia[i][k] + distancia[k][j] < distancia[i][j]) {
                        distancia[i][j] = distancia[i][k] + distancia[k][j];
                    }
                }
            }
        }

        // Imprimir la matriz de distancias mínimas
        /* for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (distancia[i][j] == INFI) {
                    System.out.print("INF \t");

                } else {
                    System.out.print(distancia[i][j] + "\t");
                }
            }
            System.out.println();
        } */
        return distancia;
    }



    /**
     * Obtiene una lista de los vecinos del vértice actual en el grafo.
     *
     * @param actual el índice del vértice actual
     * @return una lista de los índices de los vecinos del vértice actual
     */
    private List<Integer> getVecinos(int actual) {
        List<Integer> vecinos = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            if (adyacencia[actual][i] > 0) {
                vecinos.add(i);
            }
        }

        return vecinos;
    }

    /**
     * Obtiene la distancia entre dos nodos del grafo.
     *
     * @param actual el nodo actual
     * @param vecino el nodo vecino
     * @return la distancia entre los nodos actual y vecino
     */
    private int getDistancia(int actual, int vecino) {
        return adyacencia[actual][vecino];
    }

    /**
     * Aplica el algoritmo de Dijkstra para encontrar el camino más corto entre dos vértices en el grafo.
     * la funcion aun esta en desarrollo y no se ha probado su correcto funcionamiento
     * 
     * @param inicio el vértice de inicio del camino
     * @param fin el vértice de destino del camino
     * @return una lista de vértices que representa el camino más corto desde el vértice de inicio hasta el vértice de destino, o una lista vacía si no hay camino
     */
    public List<Vertice> dijkstra(Vertice inicio, Vertice fin) {
        uptade();
        int n = vertices.size();
        int[] distancias = new int[n];
        int[] predecesores = new int[n];
        boolean[] visitados = new boolean[n];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesores, -1);

        // ...

        int inicioIndice = getIndiceVertice(inicio);
        int finIndice = getIndiceVertice(fin);

        distancias[inicioIndice] = 0;

        PriorityQueue<Integer> cola = new PriorityQueue<>(Comparator.comparingInt(i -> distancias[i]));
        cola.add(inicioIndice);

        while (!cola.isEmpty()) {
            int actual = cola.poll();

            if (actual == finIndice) {
                break;
            }

            if (!visitados[actual]) {
                visitados[actual] = true;

                for (int vecino : getVecinos(actual)) {
                    int distancia = distancias[actual] + getDistancia(actual, vecino);

                    if (distancia < distancias[vecino]) {
                        distancias[vecino] = distancia;
                        predecesores[vecino] = actual;
                        cola.add(vecino);
                    }
                }
            }
        }

        if (predecesores[finIndice] == -1) {
            return new ArrayList<>(); // Retorna una lista vacía si no hay camino
        }

        List<Vertice> camino = new ArrayList<>();
        for (int i = finIndice; i != -1; i = predecesores[i]) {
            camino.add(0, vertices.get(i));
        }

        return camino;
    }

    /**
     * Aplica el algoritmo de Dijkstra para encontrar el camino más corto entre dos vértices en el grafo.
     * la funcion aun esta en desarrollo y no se ha probado su correcto funcionamiento
     * 
     * @param inicio el índice del vértice de inicio del camino
     * @param fin el índice del vértice de destino del camino
     * @return una lista de vértices que representa el camino más corto desde el vértice de inicio hasta el vértice de destino,
     *         o una lista vacía si no hay camino
     */
    public List<Integer> dijkstra(int inicio, int fin) {
        uptade();
        int n = vertices.size();
        int[] distancias = new int[n];
        int[] predecesores = new int[n];
        boolean[] visitados = new boolean[n];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesores, -1);

        // ...

        int inicioIndice = inicio;
        int finIndice = fin;

        distancias[inicioIndice] = 0;

        PriorityQueue<Integer> cola = new PriorityQueue<>(Comparator.comparingInt(i -> distancias[i]));
        cola.add(inicioIndice);

        while (!cola.isEmpty()) {
            int actual = cola.poll();

            if (actual == finIndice) {
                break;
            }

            if (!visitados[actual]) {
                visitados[actual] = true;

                for (int vecino : getVecinos(actual)) {
                    int distancia = distancias[actual] + getDistancia(actual, vecino);

                    if (distancia < distancias[vecino]) {
                        distancias[vecino] = distancia;
                        predecesores[vecino] = actual;
                        cola.add(vecino);
                    }
                }
            }
        }

        if (predecesores[finIndice] == -1) {
            return new ArrayList<>(); // Retorna una lista vacía si no hay camino
        }

        List<Integer> camino = new ArrayList<>();
        for (int i = finIndice; i != -1; i = predecesores[i]) {
            camino.add(0, i);
        }

        return camino;
    }



    /**
     * Aplica el algoritmo de Bellman-Ford para encontrar las distancias más cortas desde un nodo fuente a todos los demás nodos en un grafo ponderado.
     * Si hay ciclos negativos en el grafo, devuelve null.
     *
     * @param matrizRecorridos la matriz de adyacencia que representa el grafo ponderado.
     * @param source el nodo fuente desde el cual se calculan las distancias más cortas.
     * @return un arreglo de distancias más cortas desde el nodo fuente a todos los demás nodos, o null si hay ciclos negativos.
     */
    public static int[] bellmanFord(int[][] matrizRecorridos, int source) {
        int n = matrizRecorridos.length; // Obtener el número de nodos en el grafo

        // Inicializamos un arreglo de distancias con infinito y un arreglo de visitados a false
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] visited = new boolean[n];

        // Inicializamos una cola con el nodo de origen
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);

        // Realizamos el proceso n-1 veces (n es el número de nodos)
        for (int i = 0; i < n - 1; i++) {
            while (!queue.isEmpty()) {
                // Extraemos un nodo de la cola
                int u = queue.poll();

                // Si el nodo ya ha sido visitado, lo ignoramos
                if (visited[u]) {
                    continue;
                }

                // Marcamos el nodo como visitado
                visited[u] = true;

                // Actualizamos las distancias de los nodos adyacentes
                for (int v = 0; v < n; v++) {
                    int w =(int) matrizRecorridos[u][v]; // Peso de la arista

                    // Si la nueva distancia es menor que la distancia actual, la actualizamos
                    if (w != 0 && dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        queue.add(v);
                    }
                }
            }
        }

        // Verificamos si hay ciclos negativos en el grafo
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                int w = (int)matrizRecorridos[u][v]; // Peso de la arista

                // Si existe un camino más corto, entonces hay un ciclo negativo
                if (w != 0 && dist[v] > dist[u] + w) {
                    return null;
                }
            }
        }

        // Devolvemos el arreglo de distancias más cortas
        return dist;
    }
  
    /**
     * Aplica el algoritmo de Bellman-Ford para encontrar las distancias más cortas desde un nodo fuente a todos los demás nodos en el grafo.
     * Si hay ciclos negativos en el grafo, devuelve null.
     *
     * @param source el nodo fuente desde el cual se calculan las distancias más cortas
     * @return un arreglo de enteros que representa las distancias más cortas desde el nodo fuente a todos los demás nodos en el grafo,
     *         o null si hay ciclos negativos en el grafo
     */
    public int[] bellmanFord(int source) {
        uptade();
        int n = adyacencia.length; // Obtener el número de nodos en el grafo

        // Inicializamos un arreglo de distancias con infinito y un arreglo de visitados a false
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] visited = new boolean[n];

        // Inicializamos una cola con el nodo de origen
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);

        // Realizamos el proceso n-1 veces (n es el número de nodos)
        for (int i = 0; i < n - 1; i++) {
            while (!queue.isEmpty()) {
                // Extraemos un nodo de la cola
                int u = queue.poll();

                // Si el nodo ya ha sido visitado, lo ignoramos
                if (visited[u]) {
                    continue;
                }

                // Marcamos el nodo como visitado
                visited[u] = true;

                // Actualizamos las distancias de los nodos adyacentes
                for (int v = 0; v < n; v++) {
                    int w =(int) adyacencia[u][v]; // Peso de la arista

                    // Si la nueva distancia es menor que la distancia actual, la actualizamos
                    if (w != 0 && dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        queue.add(v);
                    }
                }
            }
        }

        // Verificamos si hay ciclos negativos en el grafo
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                int w = (int)adyacencia[u][v]; // Peso de la arista

                // Si existe un camino más corto, entonces hay un ciclo negativo
                if (w != 0 && dist[v] > dist[u] + w) {
                    return null;
                }
            }
        }

        // Devolvemos el arreglo de distancias más cortas
        return dist;
    }



 // _________________________________________ arbol de expansion minimo _________________________________________________________________________________________________________________________

        
    /**
     * Método que aplica el algoritmo de Kruskal para obtener un árbol de expansión mínima del grafo.
     * @return Un nuevo grafo que representa el árbol de expansión mínima.
     */
    public Grafo kruscal(){
        Kruskal graph = new Kruskal(this.vertices.size());
        for (Arista arista:getAristas()){
            graph.addEdge(getIndiceVertice(arista.getOrigen()), getIndiceVertice(arista.getDestino()), arista.getPeso());
        }
        
        Grafo newGrafo = new Grafo(dirigido, ponderado);
        ArrayList<int[]> verticesPos= graph.kruskalMST();

        ArrayList<Vertice> newVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++){
            Vertice newVertice = new Vertice(vertices.get(i).getDato());
            newVertices.add(newVertice);
        }
        
        for (int[] vertice:verticesPos){
            newGrafo.conectarVertice(newVertices.get(vertice[0]), newVertices.get(vertice[1]), vertice[2]);
        }
        return newGrafo;
    }
    
    /**
     * Método que aplica el algoritmo de Kruskal para obtener un árbol de expansión mínima del grafo.
     * 
     * @param n el número de vértices en el grafo
     * @param adyacencia la matriz de adyacencia del grafo
     * @param ponderado si el grafo es ponderado
     * @param dirigido si el grafo es dirigido
     * @return Un nuevo grafo que representa el árbol de expansión mínima.
     * @throws IllegalArgumentException si el número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo
     * @throws IllegalArgumentException si la matriz de adyacencia no es cuadrada
     * @throws IllegalArgumentException si la modalidad no es válida
     * 
     */
    public static Grafo Kruskal(int n, int[][] adyacencia, boolean ponderado, boolean dirigido){
        Kruskal graph = new Kruskal(n);
        Grafo newGrafo = new Grafo(dirigido, ponderado);

        if (adyacencia.length != n) {
            throw new IllegalArgumentException("El número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo");
        }

        if (adyacencia.length != adyacencia[0].length) {
            throw new IllegalArgumentException("La matriz de adyacencia no es cuadrada");
        }

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (adyacencia[i][j] != 0){
                    graph.addEdge(i, j, adyacencia[i][j]);
                }
            }
        }

        ArrayList<int[]> verticesPos= graph.kruskalMST();
        HashMap<Integer, Vertice> newVertices = new HashMap<>();
        for (int[] vertice:verticesPos){
            Vertice newVerticeOrigen = new Vertice(Integer.toString(vertice[0]));
            Vertice newVerticeDestino = new Vertice(Integer.toString(vertice[1]));
            newVertices.put(vertice[0], newVerticeOrigen);
            newVertices.put(vertice[1], newVerticeDestino);
        }
        for (int[] vertice:verticesPos){
            newGrafo.conectarVertice(newVertices.get(vertice[0]), newVertices.get(vertice[1]), vertice[2]);
        }
        return newGrafo;
    }


    /**
     * Método que aplica el algoritmo de PRIM para obtener un árbol de expansión mínima del grafo.
     * @return Un nuevo grafo que representa el árbol de expansión mínima.
     */
    public Grafo PRIM(){
        ArrayList<int[]> resultado = PRIM_methoth(adyacencia, this.vertices.size());
        Grafo newGrafo = new Grafo(dirigido, ponderado);

        ArrayList<Vertice> newVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++){
            Vertice newVertice = vertices.get(i);
            newVertices.add(new Vertice(newVertice.getDato()));
        }
        
        for (int[] vertice:resultado){
            newGrafo.conectarVertice(newVertices.get(vertice[0]), newVertices.get(vertice[1]), vertice[2]);
        }
        return newGrafo;
    }

    /**
     * Método que aplica el algoritmo de PRIM para obtener un árbol de expansión mínima del grafo.
     * 
     * @param n el número de vértices en el grafo
     * @param adyacencia la matriz de adyacencia del grafo
     * @param ponderado si el grafo es ponderado
     * @param dirigido si el grafo es dirigido
     * @return Un nuevo grafo que representa el árbol de expansión mínima.
     * @throws IllegalArgumentException si el número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo
     * @throws IllegalArgumentException si la matriz de adyacencia no es cuadrada
     * @throws IllegalArgumentException si la modalidad no es válida
     */
    public static Grafo PRIM(int n, int[][] adyacencia, boolean ponderado, boolean dirigido){
        if (adyacencia.length != n) {
            throw new IllegalArgumentException("El número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo");
        }

        if (adyacencia.length != adyacencia[0].length) {
            throw new IllegalArgumentException("La matriz de adyacencia no es cuadrada");
        }

        ArrayList<int[]> resultado = PRIM_methoth(adyacencia, n);
        HashMap<Integer, Vertice> newVertices = new HashMap<>();
        Grafo newGrafo = new Grafo(dirigido, ponderado);
        for (int[] vertice:resultado){
            Vertice newVerticeOrigen = new Vertice(Integer.toString(vertice[0]), vertice[0]);
            Vertice newVerticeDestino = new Vertice(Integer.toString(vertice[0]), vertice[1]);
            newVertices.put(vertice[0], newVerticeOrigen);
            newVertices.put(vertice[1], newVerticeDestino);
        }
        for (int[] vertice:resultado){
            newGrafo.conectarVertice(newVertices.get(vertice[0]), newVertices.get(vertice[1]), vertice[2]);
        }
        return newGrafo;
    }

    // Función para encontrar el vértice con el mínimo valor de clave,
    // que aún no ha sido incluido en el árbol de expansión mínima
    private static int minKey(int[] key, boolean[] visited, int V) {
        int min = Integer.parseInt(INFI+""), minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // retorna el árbol de expansión mínima
    private static ArrayList<int[]> printMST(int[] parent, int[][] graph) {
        ArrayList<int[]> result = new ArrayList<int[]>();
        for (int i = 1; i < graph.length; i++) {
            int[] temp = {parent[i], i, graph[i][parent[i]]};
            result.add(temp);
        }
        return result;
    }

    // Algoritmo de Prim para encontrar el árbol de expansión mínima
    private static ArrayList<int[]> PRIM_methoth(int[][] graph, int V) {
        int[] parent = new int[V]; // Almacena el árbol de expansión mínima
        int[] key = new int[V]; // Almacena las claves (pesos) de los vértices
        boolean[] visited = new boolean[V]; // Almacena los vértices visitados

        // Inicializa todas las claves a infinito y todos los vértices como no visitados
        Arrays.fill(key, Integer.parseInt(INFI+""));
        Arrays.fill(visited, false);

        // La clave del primer vértice siempre es 0, ya que es el primer vértice del árbol
        key[0] = 0;
        parent[0] = -1; // No tiene padre

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, visited, V); // Obtiene el vértice con la clave mínima
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

    // _________________________________________ GRAFOS Especificos _________________________________________________________________________________________________________________________

    private static Grafo getGrafoPonderado() {
        return new Grafo(false, true);
    }

    /**
     * metodo que retorna un grafo Trivial no dirigido y no ponderado
     * @return Grafo
     */
    public static Grafo grafoTrivial(){
        Grafo grafo = new Grafo();
        Vertice vertice = new Vertice("0");
        grafo.addVertice(vertice);
        return grafo;
    }


    /**
     * metodo que retorna un grafo Nulo no dirigido y no ponderado
     * @return Grafo
     */
    public static Grafo grafoNulo(){
        Grafo grafo = new Grafo();
        return grafo;
    }

    
    /**
     * metodo que retorna un grafo vacio con n vertices y no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoVacio(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        return grafo;
    }


    /**
     * metodo que retorna un grafo completo con n vertices y no ponderado
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoCompleto(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            for (int j = i+1; j < nVertices; j++){
                grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(j));
            }
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo completo con n vertices No dirigido y No ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoCompleto(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            for (int j = i+1; j < nVertices; j++){
                grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(j));
            }
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo completo con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoCompletoPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            for (int j = i+1; j < nVertices; j++){
                grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(j), (int)(Math.random()*100));
            }
        }
        return grafo;
    }

    
    /**
     * metodo que retorna un grafo lineal con n vertices y no ponderado
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoLineal(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo lineal con n vertices no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoLineal(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo lineal con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoLinealPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1), (int)(Math.random()*100));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo ciclo con n vertices y no ponderado
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoCiclo(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0));
        return grafo;
    }

    /**
     * metodo que retorna un grafo ciclo con n vertices no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoCiclo(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0));
        return grafo;
    }

    /**
     * metodo que retorna un grafo ciclo con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoCicloPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1), (int)(Math.random()*100));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0), (int)(Math.random()*100));
        return grafo;
    }

    /**
     * metodo que retorna un grafo rueda con n vertices y no ponderado
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoRueda(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0));
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i));
        }
        return grafo;
    }


    /**
     * metodo que retorna un grafo rueda con n vertices no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoRueda(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0));
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo rueda con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoRuedaPonderado(int nVertices){
        Grafo grafo = new Grafo(false, true);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1), (int)(Math.random()*100));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0), (int)(Math.random()*100));
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i), (int)(Math.random()*100));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo estrella con n vertices y no ponderado
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoEstrella(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo estrella con n vertices no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoEstrella(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i));
        }
        return grafo;

    }

    /**
     * metodo que retorna un grafo estrella con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoEstrellaPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i), (int)(Math.random()*100));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo Bipartito completo con n vertices y no ponderado
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoBipartitoCompleto(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("A"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("B"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            for (int j = 0; j < nVertices; j++){
                grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(j+nVertices));
            }
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo Bipartito completo con n vertices no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoBipartitoCompleto(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("A"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("B"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            for (int j = 0; j < nVertices; j++){
                grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(j+nVertices));
            }
        }
        return grafo;

    }

    /**
     * metodo que retorna un grafo Bipartito completo con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoBipartitoCompletoPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("A"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("B"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            for (int j = 0; j < nVertices; j++){
                grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(j+nVertices), (int)(Math.random()*100));
            }
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo Bipartito con n vertices
     * @param nVertices
     * @param dirigido
     * @return Grafo
     */
    public static Grafo grafoBipartito(int nVertices, boolean dirigido){
        Grafo grafo = new Grafo(dirigido);
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("A"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("B"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+nVertices));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo Bipartito con n vertices no dirigido y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoBipartito(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("A"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("B"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+nVertices));
        }
        return grafo;
    }
    

    /**
     * metodo que retorna un grafo Bipartito con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoBipartitoPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("A"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice("B"+Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+nVertices), (int)(Math.random()*100));
        }
        return grafo;
    }
    
   
    
    
        // _________________________________________ flujo maximo _________________________________________________________________________________________________________________________
        
        /**
         * Calcula el flujo máximo utilizando el algoritmo de Ford-Fulkerson en un grafo dado.
         * 
         * @param graph el grafo representado como una matriz de adyacencia
         * @param source el nodo fuente del grafo
         * @param fin el nodo final del grafo
         * @return el flujo máximo en el grafo
         */
        public static int fordFulkerson(int graph[][], int source, int fin) {
            // source: inicio, fin: final
            int maxFlow = 0;
            int parent[] = new int[graph.length];
    
            // Encuentra un camino aumentante en el grafo residual y actualiza el flujo máximo
            while (Grafo.bfs(graph, source, fin, parent)) {
                int pathFlow = Integer.MAX_VALUE;
                int s = fin;
    
                // Encuentra el flujo mínimo en el camino encontrado por BFS
                while (s != source) {
                    int u = parent[s];
                    pathFlow = Math.min(pathFlow, graph[u][s]);
                    s = u;
                }
    
                // Actualiza las capacidades residuales del camino y el flujo máximo
                int v = fin;
                while (v != source) {
                    int u = parent[v];
                    graph[u][v] -= pathFlow;
                    graph[v][u] += pathFlow;
                    v = u;
                }
    
                maxFlow += pathFlow;
            }
    
            return maxFlow;
        }
    
        private static boolean bfs(int graph[][], int source, int fin, int parent[]) {
            boolean visited[] = new boolean[graph.length];
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(source);
            visited[source] = true;
            parent[source] = -1;
    
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v = 0; v < graph.length; v++) {
                    if (!visited[v] && graph[u][v] > 0) {
                        queue.add(v);
                        parent[v] = u;
                        visited[v] = true;
                    }
                }
            }
    
            return visited[fin];
        }
        
    
    // _________________________________________ COSAS NO PROBADAS __________________________________________________________________________________________________________________

 
    /**
     * metodo que retorna un grafo plano con n vertices y no ponderado
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoPlano(int nVertices){
        Grafo grafo = new Grafo();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0));
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i));
        }
        return grafo;
    }

    /**
     * metodo que retorna un grafo plano con n vertices no dirigido y ponderado
     * los pesos de las aristas son aleatorios enteros entre 0 y 100
     * @param nVertices
     * @return Grafo
     */
    public static Grafo grafoPlanoPonderado(int nVertices){
        Grafo grafo = getGrafoPonderado();
        for (int i = 0; i < nVertices; i++){
            Vertice vertice = new Vertice(Integer.toString(i));
            grafo.addVertice(vertice);
        }
        for (int i = 0; i < nVertices-1; i++){
            grafo.conectarVertice(grafo.vertices.get(i), grafo.vertices.get(i+1), (int)(Math.random()*100));
        }
        grafo.conectarVertice(grafo.vertices.get(nVertices-1), grafo.vertices.get(0), (int)(Math.random()*100));
        for (int i = 1; i < nVertices; i++){
            grafo.conectarVertice(grafo.vertices.get(0), grafo.vertices.get(i), (int)(Math.random()*100));
        }
        return grafo;
    }


    // _________________________________________ estados del Grafo _________________________________________________________________________________________________________________________

    /**
     * revisa si el grafo es un grafo conexo
     * 
     * @return true si el grafo es conexo, false de lo contrario
     */
    public boolean esConexo(){
        int n = vertices.size();
        boolean[] visitados = new boolean[n];
        dfs(0, visitados);
        for (int i = 0; i < n; i++){
            if (!visitados[i]){
                return false;
            }
        }
        return true;
    }

    private void dfs(int actual, boolean[] visitados){
        visitados[actual] = true;
        for (int vecino : getVecinos(actual)){
            if (!visitados[vecino]){
                dfs(vecino, visitados);
            }
        }
    }

    /**
     * Comprueba si el grafo es un árbol.
     * No funciona correctamente aun
     * @return true si el grafo es un árbol, false de lo contrario
     */
    public boolean esArbol(){
        return esConexo() && vertices.size() == getAristas().size() + 1;
    }

    /**
     * Comprueba si el grafo es un grafo completo.
     * 
     * @return true si el grafo es completo, false de lo contrario
     */
    public boolean esCompleto(){
        int n = vertices.size();
        int m = getAristas().size();
        return !dirigido ? m == n*(n-1) : m == n*(n-1)/2;
    }

    /**
     * Comprueba si el grafo es un grafo regular.
     * 
     * @return true si el grafo es regular, false de lo contrario
     */
    public boolean esRegular(){
        int grado = vertices.get(0).grado();
        for (Vertice vertice : vertices){
            if (vertice.grado() != grado){
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si el grafo es un grafo bipartito.
     * 
     * @return true si el grafo es bipartito, false de lo contrario
     */
    public boolean esBipartito(){
        int n = vertices.size();
        int[] colores = new int[n];
        Arrays.fill(colores, -1);
        colores[0] = 0;
        Queue<Integer> cola = new LinkedList<>();
        cola.add(0);
        while (!cola.isEmpty()){
            int actual = cola.poll();
            for (int vecino : getVecinos(actual)){
                if (colores[vecino] == -1){
                    colores[vecino] = 1 - colores[actual];
                    cola.add(vecino);
                } else if (colores[vecino] == colores[actual]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Comprueba si el grafo es un grafo euleriano.
     * 
     * @return true si el grafo es euleriano, false de lo contrario
     */
    public boolean esEuleriano(){
        if (!esConexo()){
            return false;
        }
        int nImpares = 0;
        for (Vertice vertice : vertices){
            if (vertice.grado() % 2 != 0){
                nImpares++;
            }
        }
        return nImpares == 0 || nImpares == 2;
    }

    /**
     * Comprueba si el grafo es un grafo hamiltoniano.
     * 
     * @return true si el grafo es hamiltoniano, false de lo contrario
     */
    public boolean esHamiltoniano(){
        int n = vertices.size();
        if (n < 3){
            return false;
        }
        for (int i = 0; i < n; i++){
            if (getVecinos(i).size() < n/2){
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si el grafo es un grafo planar.
     * 
     * @return true si el grafo es planar, false de lo contrario
     */
    public boolean esPlanar(){
        int n = vertices.size();
        int m = getAristas().size();
        if (n <= 4){
            return true;
        }
        if (m > 3*n - 6){
            return false;
        }
        if (m > 2*n - 4){
            for (int i = 0; i < n; i++){
                if (vertices.get(i).grado() >= 6){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * da el coloreo de un grafo
     *  El coloreo de un grafo es una asignación de colores a los vértices de un grafo de manera que dos vértices adyacentes no tengan el mismo color.
     *  El número cromático de un grafo es el número mínimo de colores necesarios para colorear el grafo.
     *
     * @return el número de colores necesarios para colorear el grafo
     *        -1 si el grafo no es coloreable
     */
    public int getColoreo() {
        int n = vertices.size();
        int[] colores = new int[n];
        Arrays.fill(colores, -1);
        colores[0] = 0;

        boolean[] available = new boolean[n];
        Arrays.fill(available, true);

        for (int u = 1; u < n; u++) {
            for (int vecino : getVecinos(u)) {
                if (colores[vecino] != -1) {
                    available[colores[vecino]] = false;
                }
            }

            int cr;
            for (cr = 0; cr < n; cr++) {
                if (available[cr]) break;  
            }

            colores[u] = cr;
            Arrays.fill(available, true);
        }

        return Arrays.stream(colores).max().getAsInt() + 1;
    }

    
    /**
     * Calcula el radio de un grafo.
     * El radio de un grafo es el mínimo de los máximos de las distancias entre un vértice y todos los demás vértices.
     * 
     * @return el radio del grafo
     */
    public int radio(){
        int n = vertices.size();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++){
            int max = 0;
            for (int j = 0; j < n; j++){
                if (i != j){
                    ArrayList<Integer> bfsResult = BFS(i, j);
                    if (bfsResult.isEmpty()) {
                        return -1;
                    }
                    int distancia = bfsResult.size() - 1;
                    max = Math.max(max, distancia);
                }
            }
            min = Math.min(min, max);
        }
        return min;
    }

    /**
     * Calcula el diámetro del grafo, que es la mayor distancia entre cualquier par de vértices.
     * Utiliza el algoritmo de búsqueda en anchura (BFS) para encontrar la distancia más larga.
     *
     * @return el diámetro del grafo. Si el grafo no está conectado, retorna -1.
     */
    public int diametro(){
        int n = vertices.size();
        int max = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                ArrayList<Integer> bfsResult = BFS(i, j);
                if (bfsResult.isEmpty()) {
                    return -1;
                }
                int distancia = bfsResult.size() - 1;
                max = Math.max(max, distancia);
            }
        }
        return max;
    }

    /**
     * Calcula el diámetro del grafo y devuelve los vértices por donde pasa y la distancia del diámetro.
     * 
     * @return un array donde el primer elemento es la lista de vértices por donde pasa el diámetro y el segundo elemento es la distancia del diámetro
     */
    public Object[] diametroConVertices() {
        int n = vertices.size();
        int maxDistancia = 0;
        ArrayList<Vertice> caminoMax = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ArrayList<Vertice> bfsResult = BFS(vertices.get(i), vertices.get(j));
                if (bfsResult.isEmpty()) {
                    return new Object[]{new ArrayList<>(), -1};
                }
                int distancia = bfsResult.size() - 1;
                if (distancia > maxDistancia) {
                    maxDistancia = distancia;
                    caminoMax = bfsResult;
                }
            }
        }
        return new Object[]{caminoMax, maxDistancia};
    }

    private boolean isConectado(int u, int v){
        return getVecinos(u).contains(v);
    }

    /**
     * Calcula el tamaño de la clique máxima en el grafo.
     *
     * Una clique es un subconjunto de vértices de un grafo tal que cada par de vértices en el subconjunto
     * está conectado por una arista. Este método encuentra el tamaño de la clique más grande en el grafo.
     *
     * @return el tamaño de la clique máxima en el grafo.
     */
    public int clique() {
        int n = vertices.size();
        int maxCliqueSize = 0;
    
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> vecinos = (ArrayList<Integer>) getVecinos(i);
            int cliqueSize = 1; // Incluir el vértice actual en el tamaño de la clique
    
            for (int j = 0; j < vecinos.size(); j++) {
                boolean isClique = true;
                for (int k = 0; k < j; k++) {
                    if (!isConectado(vecinos.get(j), vecinos.get(k))) {
                        isClique = false;
                        break;
                    }
                }
                if (isClique) {
                    cliqueSize++;
                }
            }
            maxCliqueSize = Math.max(maxCliqueSize, cliqueSize);
        }
        return maxCliqueSize;
    }

    /**
     * Comprueba que dos grafos sean iguales.
     * 
     * @param o el objeto con el que se compara
     * @return true si los grafos son iguales, false de lo contrario
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grafo)) return false;
        Grafo grafo = (Grafo) o;
        return dirigido == grafo.dirigido && vertices.equals(grafo.vertices) && ponderado == grafo.ponderado;
    }

    @Override
    public String toString() {
        return "Grafo{" +
                "dirigido=" + dirigido +
                ", ponderado=" + ponderado +
                ", vertices=" + vertices +
                '}';
    }
}