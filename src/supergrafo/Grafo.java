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

    /**
     * Constructor de la clase Grafo.
     * 
     * @param modalidad la modalidad del grafo (dirigido, no dirigido, ponderado)
     * @param vertices la lista de vértices del grafo
     * @throws IllegalArgumentException si la modalidad no es válida
     */
    public Grafo(String modalidad, ArrayList<Vertice> vertices){
        if (modalidad.equalsIgnoreCase("Dirigido") || modalidad.equalsIgnoreCase("No dirigido") || modalidad.equalsIgnoreCase("Ponderado") || modalidad.equalsIgnoreCase("Dirigido Ponderado")) {
            this.modalidad = modalidad;
        } else {
            throw new IllegalArgumentException("Modalidad inválida: " + modalidad);
        }
        this.vertices = vertices;
    }

    /**
     * Constructor de la clase Grafo.
     * Inicializa la lista de vértices del grafo y establece la modalidad del grafo como "No dirigido".
     */
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
     * Devuelve un vértice del grafo según su índice.
     *
     * @param indice el índice del vértice a buscar
     * @return el vértice en el índice especificado
     * @throws IllegalArgumentException si el vértice no existe
     */
    public Vertice getVertice(int indice) {
        if (indice < 0 || indice >= vertices.size()) {
            throw new IllegalArgumentException("El vértice no existe");
        }
        return vertices.get(indice);
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
            System.out.print(vertice.getDato() + " -> ");
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
            if (v.getDato().equals(vertice.getDato())){
                mismoDato = true;
                break;
            }
        }

        if (this.vertices.contains(vertice)) {
            throw new IllegalArgumentException("El vértice ya existe");
        } else if ((validarModalidad(modalidad) == 3 || validarModalidad(modalidad) == 4) && vertice.conPeso() == false){
            throw new IllegalArgumentException("El grafo es ponderado y el vertice no tiene peso");
        } else if ((validarModalidad(modalidad) == 2 || validarModalidad(modalidad) == 1) && vertice.conPeso() == true){
            throw new IllegalArgumentException("El grafo no es ponderado y el vertice tiene peso");
        } else if ( (validarModalidad(modalidad) == 1 || validarModalidad(modalidad) == 2) && vertice.getEstado().equalsIgnoreCase("ponderado")){
            throw new IllegalArgumentException("El grafo no debe ser ponderado y el vertice tiene  aristas con peso diferente de 1");
        } else if ( (validarModalidad(modalidad) == 4 || validarModalidad(modalidad) == 3) && vertice.getEstado().equalsIgnoreCase("no ponderado")){
            throw new IllegalArgumentException("El grafo debe ser ponderado y el vertice tiene  aristas con peso igual a 1");
        } else if (vertice.getAristas().size() == 0){
            this.vertices.add(vertice);
        } else if (mismoDato){
            //throw new IllegalArgumentException("El vértice ya existe");
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
        for (Vertice vertice : vertices) {
            if (vertice.getDato().equals(dato)) {
                //throw new IllegalArgumentException("El vértice ya existe");
            }
        }
        Vertice vertice = new Vertice(dato);
        this.vertices.add(vertice);
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
        for (Vertice vertice : vertices) {
            if (vertice.getDato().equals(dato)) {
                this.vertices.remove(vertice);
                for (Vertice v : vertices) {
                    v.getAristas().remove(vertice.getArista(v));
                }
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

    public void eliminarArista(Arista arista) {
        for (Vertice v : vertices){
            if (v.getAristas().contains(arista)){
                v.eliminarArista(arista);
                break;
            }
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
        HashMap<Integer, Vertice> newVertices = new HashMap<>();
        for (int[] vertice:verticesPos){
            Vertice newVerticeOrigen = new Vertice(vertices.get(vertice[0]).getDato(), vertices.get(vertice[0]).getID());
            Vertice newVerticeDestino = new Vertice(vertices.get(vertice[1]).getDato(), vertices.get(vertice[1]).getID());
            newVertices.put(vertice[0], newVerticeOrigen);
            newVertices.put(vertice[1], newVerticeDestino);
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
     * @param modalidad la modalidad del grafo
     * @return Un nuevo grafo que representa el árbol de expansión mínima.
     * @throws IllegalArgumentException si el número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo
     * @throws IllegalArgumentException si la matriz de adyacencia no es cuadrada
     * @throws IllegalArgumentException si la modalidad no es válida
     * 
     */
    public static Grafo Kruskal(int n, int[][] adyacencia, String modalidad){
        Kruskal graph = new Kruskal(n);
        Grafo newGrafo = new Grafo(modalidad);

        if (adyacencia.length != n) {
            throw new IllegalArgumentException("El número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo");
        }

        if (adyacencia.length != adyacencia[0].length) {
            throw new IllegalArgumentException("La matriz de adyacencia no es cuadrada");
        }

        if (modalidad != "Dirigido" && modalidad != "No dirigido" && modalidad != "Ponderado" && modalidad != "No ponderado" ){
            throw new IllegalArgumentException("La modalidad no es válida");
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
        PRIM prim = new PRIM(this.vertices.size(),adyacencia);
        ArrayList<int[]> resultado = prim.results;
        HashMap<Integer, Vertice> newVertices = new HashMap<>();
        Grafo newGrafo = new Grafo(modalidad);
        for (int[] vertice:resultado){
            Vertice newVerticeOrigen = new Vertice(vertices.get(vertice[0]).getDato(), vertices.get(vertice[0]).getID());
            Vertice newVerticeDestino = new Vertice(vertices.get(vertice[1]).getDato(), vertices.get(vertice[1]).getID());
            newVertices.put(vertice[0], newVerticeOrigen);
            newVertices.put(vertice[1], newVerticeDestino);
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
     * @param modalidad la modalidad del grafo
     * @return Un nuevo grafo que representa el árbol de expansión mínima.
     * @throws IllegalArgumentException si el número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo
     * @throws IllegalArgumentException si la matriz de adyacencia no es cuadrada
     * @throws IllegalArgumentException si la modalidad no es válida
     */
    public static Grafo PRIM(int n, int[][] adyacencia, String modalidad){
        if (adyacencia.length != n) {
            throw new IllegalArgumentException("El número de vértices en la matriz de adyacencia no coincide con el número de vértices en el grafo");
        }

        if (adyacencia.length != adyacencia[0].length) {
            throw new IllegalArgumentException("La matriz de adyacencia no es cuadrada");
        }

        if (modalidad != "Dirigido" && modalidad != "No dirigido" && modalidad != "Ponderado" && modalidad != "No ponderado" ){
            throw new IllegalArgumentException("La modalidad no es válida");
        }

        PRIM prim = new PRIM(n,adyacencia);
        ArrayList<int[]> resultado = prim.results;
        HashMap<Integer, Vertice> newVertices = new HashMap<>();
        Grafo newGrafo = new Grafo(modalidad);
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



    // _________________________________________ GRAFOS Especificos _________________________________________________________________________________________________________________________

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
     * metodo que retorna un grafo Trivial con modalidad
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoTrivial(String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoCompleto(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoLineal(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoCiclo(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoRueda(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoEstrella(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoBipartitoCompleto(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
     * @param modalidad
     * @return Grafo
     */
    public static Grafo grafoBipartito(int nVertices, String modalidad){
        Grafo grafo = new Grafo(modalidad);
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
        Grafo grafo = new Grafo("Ponderado");
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
        Grafo grafo = new Grafo("Ponderado");
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
        return getAristas().size() == n*(n-1)/2;
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
    public int getColoreo(){
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
                    return -1;
                }
            }
        }
        return Arrays.stream(colores).max().getAsInt();
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