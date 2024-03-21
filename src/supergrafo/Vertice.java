package supergrafo;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Vertice {
    String dato; // dato almacenado en el vértice
    ArrayList<Arista> aristas; // lista de aristas conectadas al vértice
    int ID; // identificador único del vértice
    static int cd; // contador de vértices


    /**
     * Constructor de la clase Vertice.
     * 
     * @param dato el dato a almacenar en el vértice
     */
    public Vertice(String dato) {
        this.dato = dato;
        this.aristas = new ArrayList<>();
        this.ID = cd++;
    }

    /**
     * Constructor de la clase Vertice.
     * @param dato el dato a almacenar en el vértice.
     * @param aristas la lista de aristas conectadas al vértice.
     */
    public Vertice(String dato, ArrayList<Arista> aristas) {
        this.dato = dato;
        this.aristas = aristas;
        this.ID = cd++;
    }

    /**
     * Constructor de la clase Vertice.
     * 
     * @param dato El dato a almacenar en el vértice.
     * @param ID El identificador único del vértice.
     */
    public Vertice(String dato, int ID) {
        this.dato = dato;
        this.aristas = new ArrayList<>();
        this.ID = ID;
    }

    /**
     * Constructor de la clase Vertice.
     * 
     * @param dato El dato a almacenar en el vértice.
     * @param aristas La lista de aristas conectadas al vértice.
     * @param ID El identificador único del vértice.
     */
    public Vertice(String dato, ArrayList<Arista> aristas, int ID) {
        this.dato = dato;
        this.aristas = aristas;
        this.ID = ID;
    }
    

    /**
     * Returns the ID of the vertex.
     *
     * @return the ID of the vertex
     */
    public int getID() {
        return ID;
    }

    /**
     * Retorna el dato asociado al vértice.
     *
     * @return el dato asociado al vértice
     */
    public String getDato() {
        return dato;
    }

    /**
     * Devuelve la lista de aristas asociadas a este vértice.
     * 
     * @return la lista de aristas asociadas a este vértice
     */
    public ArrayList<Arista> getAristas() {
        return aristas;
    }

    /**
     * Devuelve la arista que conecta este vértice con el vértice destino especificado.
     * 
     * @param destino el vértice destino con el que se desea obtener la arista.
     * @return la arista que conecta este vértice con el vértice destino, o null si no existe una arista entre ellos.
     */
    public Arista getArista(Vertice destino) {
        for (Arista arista: aristas){
            if (arista.getDestino().equals(destino)){
                return arista;
            }
        }
        return null;
    }

    public int grado(){
        return this.getAristas().size();
    }

    /**
     * Verifica si el vértice tiene aristas con peso distinto de 1.
     * 
     * @return true si el vértice tiene aristas con peso distinto de 1, false de lo contrario.
     */
    public boolean conPeso(){
        for (Arista arista: aristas){
            if (arista.getPeso() >= 1){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Agrega una arista al vértice.
     * 
     * @param destino el vértice destino de la arista.
     * @param peso el peso de la arista.
     */
    public void unirVertices(Vertice destino,int peso){
        aristas.add(new Arista(this,destino,peso));
        //destino.agregar(this., peso);
    } 

    /**
     * Agrega una arista al vértice.
     * 
     * @param destino el vértice destino de la arista.
     */
    public void unirVertices(Vertice destino){
        aristas.add(new Arista(this,destino,1));
        //destino.agregar(this., peso);
    } 

    public void eliminarArista(Vertice destino){
        for (Arista arista: aristas){
            if (arista.getDestino().equals(destino)){
                aristas.remove(arista);
                break;
            }
        }
    }
    
    /**
     * Devuelve una lista de vértices adyacentes a este vértice.
     * 
     * @return una lista de vértices adyacentes
     */
    public ArrayList<Vertice> getAdyacente() {
        ArrayList<Vertice> adyacente = new ArrayList<>();
        for (Arista arista: aristas){
            adyacente.add(arista.getDestino());
        }
        return adyacente;
    }

    public boolean equals(Vertice v) {
        if (v.getDato() == this.dato && v.getID() == this.ID) {
            return true;
        }
        return false;
    }
    

    /**
        * Agrega una arista al vértice.
        * 
        * @param arista la arista a agregar
        * @throws IllegalArgumentException si la arista ya existe o no pertenece a este vértice
        */
    public void addArista(Arista arista){
        if (arista.getOrigen() == this) {
            if (this.aristas.contains(arista) == true && arista.getPeso() != this.getArista(arista.getDestino()).getPeso()) {
                this.aristas.add(arista);
            } else {
                throw new IllegalArgumentException("La arista ya existe");
            }
            
        } else {
            throw new IllegalArgumentException("La arista no pertenece a este vértice");
        }
    }

    public String toString() {
        return "Vertice{" +
            "dato=" + dato +
            ",adyacentes="+ this.getAdyacente().stream().map(Vertice::getDato).map(Object::toString).collect(Collectors.joining(", ")) +
            '}';
    }

    public String getEstado(){
        int estado = 0;
        for (Arista arista: aristas){
            if (arista.getPeso() != 1){
                estado = 1;
                break;
            }
        }
        if (estado == 0){
            return "no ponderado";
        } else {
            return "ponderado";
        }
    }
}
