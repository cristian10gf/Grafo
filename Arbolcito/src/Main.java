public class Main {

    public static void main(String[] args) {

        Arbol arbolito= new Arbol();
        int [] valores= {20,30,40,50,55,56,57,58};
        for (int valore: valores) {
            arbolito.agregar(valore);
            arbolito.TreePrinter();
        }
        arbolito.TreePrinter();

        /*System.out.println("");
        arbolito.preordeniterativo(arbolito.raiz);
        System.out.println("");
        arbolito.inordeniterativo(arbolito.raiz);
        System.out.println("");
        arbolito.postordeniterativo(arbolito.raiz);*/





    }
}

