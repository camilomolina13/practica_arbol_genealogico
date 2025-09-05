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

    public Nodo buscarNodo(Nodo R, int cedulaBuscada) {
        Nodo p = R;
        while (p != null) {
            if (p.getCedula() == cedulaBuscada) {
                return p; // devuelve el nodo encontrado
            }
            if (p.getSw() == 1) { // bajar al hijo
                Nodo resultado = buscarNodo(p.getLigaLista(), cedulaBuscada);
                if (resultado != null) {
                    return resultado; // lo encontró en la sublista
                }
            }
            p = p.getLiga(); // siguiente hermano
        }
        return null; // no lo encontró en ninguna parte
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

    public void mostrarHijos(Nodo R, int cedulaPadre) {
        // Buscar el nodo padre en el árbol usando su cédula
        Nodo nodo = buscarNodo(R, cedulaPadre);

        // Si el nodo no existe en el árbol
        if (nodo == null) {
            System.out.println("Nodo no encontrado");
        } else {
            // Si el nodo tiene hijos (sw == 1 indica que hay sublista)
            if (nodo.getSw() == 1 && nodo.getLigaLista() != null) {
                Nodo hijo = nodo.getLigaLista(); // Primer hijo del nodo padre
                System.out.print("Hijos de " + nodo.getNombre() + ": ");

                // Recorrer todos los hijos enlazados y mostrarlos
                while (hijo != null) {
                    System.out.print(hijo.getNombre() + ", ");
                    hijo = hijo.getLiga(); // pasar al siguiente hermano
                }
                System.out.println(); // salto de línea al final
            } else {
                // El nodo existe pero no tiene hijos
                System.out.println("El nodo no tiene hijos");
            }
        }
    }

    public void mostrarHermanos(Nodo R, int cedulaHermano) {
        // Buscar el nodo en el árbol usando buscarNodo
        Nodo nodo = buscarNodo(R, cedulaHermano);

        // Si el nodo no existe, mostrar mensaje y salir
        if (nodo == null) {
            System.out.println("El nodo que busca no existe");
        }else{
            // Buscar el padre del nodo, ya que los hermanos se encuentran en la sublista del padre
            Nodo padre = buscarPadre(R, cedulaHermano);

            // Si no tiene padre, significa que es la raíz, entonces no tiene hermanos
            if (padre == null) {
                System.out.println("El nodo no tiene hermanos");
                return;
            }

            // Recorremos los hijos del padre (la lista de hermanos)
            Nodo hermano = padre.getLigaLista();
            boolean tieneHermanos = false; // Bandera para verificar si existen hermanos

            System.out.println("Hermanos de " + nodo.getNombre() + " (" + cedulaHermano + "):");

            while (hermano != null) {
                // Imprimimos todos los hijos del padre excepto el mismo nodo buscado
                if (hermano.getCedula() != cedulaHermano) {
                    tieneHermanos = true;
                    System.out.println(hermano.getNombre());
                }
                hermano = hermano.getLiga(); // Avanzamos al siguiente hermano
            }

            // Si no se encontraron hermanos distintos
            if (!tieneHermanos) {
                System.out.println("El nodo no tiene hermanos");
            }
        }
    }

    public Nodo buscarPadre(Nodo R, int cedulaHijo) {
        // Caso base: si el árbol está vacío
        if (R == null) {
            return null;
        }

        Nodo p = R;

        // Recorremos todos los nodos del nivel actual
        while (p != null) {
            // Si el nodo actual tiene sublista (es decir, tiene hijos)
            if (p.getSw() == 1) {
                Nodo hijo = p.getLigaLista();

                // Recorremos la sublista de hijos del nodo actual
                while (hijo != null) {
                    // Si alguno de los hijos coincide con la cédula buscada,
                    // entonces "p" es el padre
                    if (hijo.getCedula() == cedulaHijo) {
                        return p;
                    }
                    hijo = hijo.getLiga(); // pasar al siguiente hijo
                }

                // Si no encontramos en esta sublista, buscamos recursivamente en la sublista
                Nodo padreEnSublista = buscarPadre(p.getLigaLista(), cedulaHijo);
                if (padreEnSublista != null) {
                    return padreEnSublista; // si lo encontró en la sublista, lo retorna
                }
            }

            // Avanzamos al siguiente nodo hermano en el nivel actual
            p = p.getLiga();
        }

        // Si no encontramos nada, retornamos null
        return null;
    }

    public void mostrarPadre(Nodo R, int cedulaHijo) {
        // Validar si el nodo hijo existe en el árbol usando buscarNodo
        Nodo nodoHijo = buscarNodo(R, cedulaHijo);
        if (nodoHijo == null) {
            System.out.println("El nodo con cédula " + cedulaHijo + " no existe en el árbol.");
            return; // salimos porque no tiene sentido seguir buscando
        }

        // Si existe, buscamos el padre con el método buscarPadre
        Nodo padre = buscarPadre(R, cedulaHijo);

        if (padre == null) {
            // Si el padre es null, significa que es la raíz
            System.out.println("El nodo con cédula " + cedulaHijo + " es la raíz, no tiene padre.");
        } else {
            // Mostramos los datos del padre
            System.out.println("El padre de " + nodoHijo.getNombre() + " es: " +
                    padre.getNombre());
        }
    }
}
