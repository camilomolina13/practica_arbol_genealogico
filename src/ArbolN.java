public class ArbolN {
    private Nodo raiz;

    public ArbolN() {
        this.raiz = null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void insertar(String nombre, int cedula, int edad){
        Nodo nuevoNodo = new Nodo(nombre, cedula, edad);
        if (estaVacio()) {
            raiz = nuevoNodo;
        }
    }

    public void insertar(Nodo R, String nombre, int cedula, int edad, int cedulaPadre){
        Nodo P = R;
        Nodo nuevo = new Nodo(nombre, cedula, edad);

        while (P != null) {
            if (P.getCedula() == cedulaPadre) {
                // Si el nodo padre tiene una sublista
                if (P.getSw() == 1) {
                    Nodo temp = P.getLigaLista();
                    // Añadir el nuevo nodo al final de la sublista
                    while (temp.getLiga() != null) {
                        temp = temp.getLiga();
                    }
                    temp.setLiga(nuevo);
                } else {
                    // Si el nodo padre no tiene una sublista
                    P.setSw(1); // Indica que tiene una sublista
                    P.setLigaLista(nuevo); // Inicializa la sublista con el nuevo nodo
                }
                return;
            } else if (P.getSw() == 1) {
                // Si el nodo tiene sublista, buscar en la sublista
                insertar(P.getLigaLista(), nombre, cedula, edad, cedulaPadre);
            }
            // Avanzar al siguiente hermano
            P = P.getLiga();
        }
    }

    public void mostrarArbol(Nodo R,int nivel) {
        if (estaVacio()) {
            System.out.println("El árbol no tiene datos para mostrar");
            return;
        }
        Nodo P = R;
        boolean esPrimero = true;
        String tabularNivel = "";
        for (int i = nivel; i > 0; i--) {
            tabularNivel+=" ‖ ";
        }

        // Mientras haya nodos en la lista
        while (P != null) {
            if (!esPrimero) {
                System.out.print(", ");
            }

            // Si es un nodo con información (sw == 0), imprimirlo directamente
            System.out.print("\n" +tabularNivel+ "[" + P.getNombre() + ", " + P.getCedula() + ", " + P.getEdad() + "]");

            // Si el nodo apunta a una sublista (sw == 1), abrir paréntesis y mostrar la sublista
            if (P.getSw() == 1) {
                mostrarArbol(P.getLigaLista(),nivel+1);
            }

            P = P.getLiga();
            esPrimero = false;
        }
    }

}
