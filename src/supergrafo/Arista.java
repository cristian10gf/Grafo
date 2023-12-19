package supergrafo;

public class Arista {
    private int peso; // peso de la arista
    private Vertice destino; // vértice destino
    private Vertice origen; // vértice origen
        
    /**
     * Constructor de la clase Arista.
     * 
     * @param origen el vertice de origen de la arista
     * @param destino el vertice de destino de la arista
     * @param peso el peso de la arista
     */
    public Arista(Vertice origen,Vertice destino, int peso) {
        this.peso = peso;
        this.destino=destino;
        this.origen=origen;
    }

    public int getPeso() {
        return peso;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public Vertice getDestino() {
        return destino;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }

    public boolean equals(Arista arista){
        return this.destino.equals(arista.getDestino()) && this.peso == arista.getPeso();
    }

    @Override
    public String toString() {
        return "Arista{" + "peso=" + peso + ",origen= "+origen +" , destino=" + destino + '}';
    }
}