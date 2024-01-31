package supergrafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Grafo {

    public static final long INFI = Integer.MAX_VALUE;

    private String modalidad; // dirigido, no dirigido, ponderado, dirigido ponderado
    private ArrayList<Vertice> vertices; // lista de vertices del grafo
    private int adyacencia[][]; // matriz de adyacencia del grafo

    
    /**
    * Constructor de la clase Grafo.
    * 
    * @param modalidad la modalidad del grafo (dirigido, no dirigido, ponderado)
    * @throws IllegalArgumentException si la modalidad no es válida
    */ 
    public Grafo(String modalidad) {
        if (modalidad.equalsIgnoreCase("Dirigido") || modalidad.equalsIgnoreCase("No dirigido") || modalidad.equalsIgnoreCase("Ponderado") || modalidad.equalsIgnoreCase("Dirigido Ponderado")) {
            this.modalidad = modalidad;
        } else {
            throw new IllegalArgumentException("Modalidad inválida: " + modalidad);
        }
        this.vertices = new ArrayList<>();
    }

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.modalidad = "No dirigido";
    }


    // ________________________________________ configuracion de grafo _________________________________________________________________________________
    
    /**
     * Devuelve la modalidad del grafo.
     *
     * @return la modalidad del grafo
     */
    public void setModalidad(String modalidad) {
        if (validarModalidad(modalidad) != 0){
            this.modalidad = modalidad;
        }else{
            throw new IllegalArgumentException("Modalidad inválida: " + modalidad);
        }
    }

    private int validarModalidad(String modalidad) {
        if (modalidad.equalsIgnoreCase("Dirigido")) {
            return 2;
        } else if (modalidad.equalsIgnoreCase("No dirigido")) {
            return 1;
        } else if (modalidad.equalsIgnoreCase("Ponderado") || modalidad.equalsIgnoreCase("no dirigido Ponderado")) {
            return 3;
        } else if (modalidad.equalsIgnoreCase("Dirigido Ponderado") || modalidad.equalsIgnoreCase("Ponderado Dirigido")) {
            return 4;
        } else {
            return 0;
        }
    }

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
        if (validarModalidad(modalidad) == 2) {
            adyacencia[this.getIndiceVertice(v1)][this.getIndiceVertice(v2)] = 1;
        } else if (validarModalidad(modalidad)  == 1) {
            adyacencia[this.getIndiceVertice(v1)][this.getIndiceVertice(v2)] = 1;
            adyacencia[this.getIndiceVertice(v2)][this.getIndiceVertice(v1)] = 1;
        } else if (validarModalidad(modalidad)  == 3) {
            adyacencia[this.getIndiceVertice(v1)][this.getIndiceVertice(v2)] = v1.getArista(v2).getPeso();
            adyacencia[this.getIndiceVertice(v2)][this.getIndiceVertice(v1)] = v2.getArista(v1).getPeso();
        } else if (validarModalidad(modalidad)  == 4) {
            adyacencia[this.getIndiceVertice(v1)][this.getIndiceVertice(v2)] = v1.getArista(v2).getPeso();
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
     * Devuelve la modalidad del grafo.
     *
     * @return la modalidad del grafo
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * Devuelve la matriz de adyacencia del grafo.
     *
     * @return la matriz de adyacencia del grafo
     */
    public int[][] getAdyacencia() {
        return adyacencia;
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
        this.adyacencia = new int[vertices.size()][vertices.size()];
        for (Vertice vertice : vertices) {
            System.out.print(vertice.getDato() + " ");
            for (Arista arista : vertice.getAristas()) {
                generarRelacacionMatriz(vertice, arista.getDestino());
                System.out.print(arista.getDestino().getDato() + "(" + arista.getPeso() + ") ");
            }
            System.out.println("");
        }
    }


    /**
     * Muestra la matriz de adyacencia del grafo.
     */
    public void mostrarMatrizA() {
        System.out.println("matriz de adyacencia");
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(adyacencia[i][j]);
            }
            System.out.println("");
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
     * @return una lista de los vértices de mayot grado en el grafo
     */
    public ArrayList<Vertice> getVerticesMayorGrado(){
        ArrayList<Vertice> verticesMayorGrado = new ArrayList<>();
        int max = 0;
        for (Vertice vertice : vertices) {
            if (vertice.getAristas().size() > max) {
                max = vertice.getAristas().size();
            }
        }
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
        int min = 0;
        for (Vertice vertice : vertices) {
            if (vertice.getAristas().size() < min) {
                min = vertice.getAristas().size();
            }
        }
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



    // _________________________________________metodos de grafo _________________________________________________________________________________________________________________________

    /**
     * Agrega un vértice al grafo.
     * 
     * @param vertice el vértice a agregar
     * @throws IllegalArgumentException si el vértice ya existe en el grafo
     */
    public void addVertice(Vertice vertice) {
        if (this.vertices.contains(vertice)) {
            //throw new IllegalArgumentException("El vértice ya existe");
        } else if ((validarModalidad(modalidad) == 3 || validarModalidad(modalidad) == 4) && vertice.conPeso() == false){
            throw new IllegalArgumentException("El grafo es ponderado y el vertice no tiene peso");
        } else if ((validarModalidad(modalidad) == 2 || validarModalidad(modalidad) == 1) && vertice.conPeso() == true){
            throw new IllegalArgumentException("El grafo no es ponderado y el vertice tiene peso");
        } else if ( (validarModalidad(modalidad) == 1 || validarModalidad(modalidad) == 2) && vertice.getEstado().equalsIgnoreCase("ponderado")){
            throw new IllegalArgumentException("El grafo no debe ser ponderado y el vertice tiene  aristas con peso diferente de 1");
        } else if ( (validarModalidad(modalidad) == 4 || validarModalidad(modalidad) == 3) && vertice.getEstado().equalsIgnoreCase("no ponderado")){
            throw new IllegalArgumentException("El grafo debe ser ponderado y el vertice tiene  aristas con peso igual a 1");
        } else {
            this.vertices.add(vertice);
        }
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
        Vertice vertice = new Vertice(dato);
        if (this.vertices.contains(vertice)) {
            //throw new IllegalArgumentException("El vértice ya existe");
        } else {
            this.vertices.add(vertice);
        }
    }



    /**
     * Conecta dos vértices en el grafo con un peso dado, según la modalidad establecida.
     * Si la modalidad es 2, se conecta el vértice v1 con el vértice v2.
     * Si la modalidad es 1, se conecta el vértice v1 con el vértice v2 y viceversa.
     * Si la modalidad es 3, se conecta el vértice v1 con el vértice v2 y viceversa, con el peso especificado.
     * Si la modalidad es 4, se conecta el vértice v1 con el vértice v2, con el peso especificado.
     * Si alguno de los vértices no está presente en el grafo, se añade al grafo.
     * Finalmente, se actualiza el grafo.
     *
     * @param v1 El primer vértice a conectar.
     * @param v2 El segundo vértice a conectar.
     * @param peso El peso de la conexión.
     */
    public void conectarVertice(Vertice v1, Vertice v2, int peso) {
        if (validarModalidad(modalidad) == 2) {
            v1.unirVertices(v2);
        } else if (validarModalidad(modalidad)  == 1) {
            v1.unirVertices(v2);
            v2.unirVertices(v1);
        } else if (validarModalidad(modalidad)  == 3) {
            v1.unirVertices(v2, peso);
            v2.unirVertices(v1, peso);
        } else if (validarModalidad(modalidad)  == 4) {
            v1.unirVertices(v2, peso);
        }
        if (!this.vertices.contains(v1)) {
            this.vertices.add(v1);
        } 
        if (!this.vertices.contains(v2)) {
            this.vertices.add(v2);
        }
        uptade();
    }

    /**
     * Conecta dos vértices en el grafo, dependiendo de la modalidad del grafo.
     * Si la modalidad es 2, se conecta el vértice v1 con el vértice v2.
     * Si la modalidad es 1, se conecta el vértice v1 con el vértice v2 y viceversa.
     * Si alguno de los vértices no está presente en el grafo, se añade al grafo.
     * 
     * @param v1 el primer vértice a conectar
     * @param v2 el segundo vértice a conectar
     */
    public void conectarVertice(Vertice v1, Vertice v2) {
        if (validarModalidad(modalidad) == 2 || validarModalidad(modalidad) == 4){
            v1.unirVertices(v2);
        } else if (validarModalidad(modalidad)  == 1 || validarModalidad(modalidad) == 3){
            v1.unirVertices(v2);
            v2.unirVertices(v1);
        } 
        if (!this.vertices.contains(v1)) {
            this.vertices.add(v1);
        } 
        if (!this.vertices.contains(v2)) {
            this.vertices.add(v2);
        }
        uptade();
    }



    /**
     * Elimina un vértice del grafo.
     *
     * @param vertice el vértice a eliminar
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public void eliminarVertice(Vertice vertice) {
        if (this.vertices.contains(vertice)) {
            this.vertices.remove(vertice);
            for (Vertice v : vertices) {
                v.getAristas().remove(vertice.getArista(v));
            }
        } else {
            throw new IllegalArgumentException("El vértice no existe");
        }
        uptade();
    }

    /**
     * Elimina un conjunto de vértices del grafo.
     *
     * @param vertices el conjunto de vértices a eliminar
     */
    public void eliminarVertice(ArrayList<Vertice> vertices) {
        for (Vertice vertice : vertices) {
            if (this.vertices.contains(vertice)) {
                this.vertices.remove(vertice);
                for (Vertice v : vertices) {
                    v.getAristas().remove(vertice.getArista(v));
                }
            }
        }
        uptade();
    }

    /**
     * Elimina un vértice del grafo.
     *
     * @param dato el dato del vértice a eliminar
     * funcion aun experimental puede dar comportamientos no esperados
     */
    public void eliminarVertice(String dato) {
        Vertice vertice = new Vertice(dato);
        if (this.vertices.contains(vertice)) {
            this.vertices.remove(vertice);
            for (Vertice v : vertices) {
                v.getAristas().remove(vertice.getArista(v));
            }
        }
        uptade();
    }



    /**
     * Elimina una arista del grafo.
     *
     * @param v1 el primer vértice de la arista
     * @param v2 el segundo vértice de la arista
     * @throws IllegalArgumentException si la arista no existe en el grafo
     */
    public void eliminarArista(Vertice v1, Vertice v2) {
        if (this.vertices.contains(v1) && this.vertices.contains(v2)) {
            v1.getAristas().remove(v1.getArista(v2));
            if (validarModalidad(modalidad) == 1 || validarModalidad(modalidad) == 3) {
                v2.getAristas().remove(v2.getArista(v1));
            }
        } else {
            throw new IllegalArgumentException("La arista no existe");
        }
        uptade();
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
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (distancia[i][j] == INFI) {
                    System.out.print("INF \t");

                } else {
                    System.out.print(distancia[i][j] + "\t");
                }
            }
            System.out.println();
        }
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
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (distancia[i][j] == INFI) {
                    System.out.print("INF \t");

                } else {
                    System.out.print(distancia[i][j] + "\t");
                }
            }
            System.out.println();
        }
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
     * 
     * @param inicio el vértice de inicio del camino
     * @param fin el vértice de destino del camino
     * @return una lista de vértices que representa el camino más corto desde el vértice de inicio hasta el vértice de destino,
     *         o una lista vacía si no hay camino
     */
    public List<Vertice> dijkstra(Vertice inicio, Vertice fin) {
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
     * 
     * @param inicio el índice del vértice de inicio del camino
     * @param fin el índice del vértice de destino del camino
     * @return una lista de vértices que representa el camino más corto desde el vértice de inicio hasta el vértice de destino,
     *         o una lista vacía si no hay camino
     */
    public List<Integer> dijkstra(int inicio, int fin) {
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
        for(Vertice vertice:vertices){
            for (Arista arista:vertice.getAristas()){
                graph.addEdge(getIndiceVertice(vertice), getIndiceVertice(arista.getDestino()), arista.getPeso());
            }
        }
        Grafo newGrafo = new Grafo(modalidad);
        ArrayList<int[]> verticesPos= graph.kruskalMST();
        for (int[] vertice:verticesPos){
            Vertice newVerticeOrigen = new Vertice(vertices.get(vertice[0]).getDato(), vertices.get(vertice[0]).getID());
            Vertice newVerticeDestino = new Vertice(vertices.get(vertice[1]).getDato(), vertices.get(vertice[1]).getID());
            newGrafo.conectarVertice(newVerticeOrigen, newVerticeDestino, vertice[2]);
        }
        return newGrafo;
    }
    
    public void PRIM(){
        PRIM prim = new PRIM();
        prim.primMST(adyacencia);
    }



    
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
    


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grafo)) return false;
        Grafo grafo = (Grafo) o;
        return modalidad == grafo.modalidad && vertices.equals(grafo.vertices);
    }

    @Override
    public String toString() {
        return "Grafo{" +
                "modalidad=" + modalidad +
                ", vertices=" + vertices +
                '}';
    }
}