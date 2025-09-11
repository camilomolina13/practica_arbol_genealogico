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

    public void mostrarAltura(Nodo R) {
        int altura = calcularAltura(R);
        System.out.println("La altura del árbol es: " + altura);
    }

    private int calcularAltura(Nodo R) {
        if (R == null) {
            return 0; // Altura de un árbol vacío es 0 porque no tiene raíz
        }

        if (R.getSw() == 0) {
            return 1; // Altura de una hoja es 1
        }

        int maxAltura = 1;
        Nodo hijo = R.getLigaLista();
        while (hijo != null) {
            int alturaHijo = calcularAltura(hijo);
            if (alturaHijo > maxAltura) {
                maxAltura = alturaHijo;
            }
            hijo = hijo.getLiga();
        }

        return maxAltura + 1; // Altura del nodo actual es 1 más la altura máxima de sus hijos
    }

    public void actualizarNodo(Nodo R, int cedulaBuscada, int cedula, String nombre, int edad) {
        Nodo nodo = buscarNodo(R, cedulaBuscada);
        if (nodo == null) {
            System.out.println("El nodo no existe");
        }else{
            System.out.println("Nodo: "+ nodo.getNombre() + " actualizado correctamente");
            nodo.setNombre(nombre);
            nodo.setCedula(cedula);
            nodo.setEdad(edad);
            System.out.println("Nodo actualizado "+ "Nombre: "+nodo.getNombre()+ " Cédula: " + nodo.getCedula() +" Edad: "+nodo.getEdad());
        }
    }

    public Nodo obtenerNodoConMayorGrado(Nodo R) {
        if (R == null) {
            return null; // Caso base: árbol vacío
        } else {
            // Inicialmente el mejor nodo es el actual
            Nodo mejor = R;
            int gradoMejor = 0;

            // Contar los hijos directos del nodo actual
            Nodo hijoTemp = R.getLigaLista();

            while (hijoTemp != null) {
                gradoMejor++;
                hijoTemp = hijoTemp.getLiga();
            }

            // Revisar en cada hijo si hay un mejor candidato
            Nodo hijo = R.getLigaLista();
            while (hijo != null) {
                Nodo candidato = obtenerNodoConMayorGrado(hijo);

                if (candidato != null) {
                    // Contar hijos del candidato
                    int gradoCandidato = 0;
                    Nodo aux = candidato.getLigaLista();
                    while (aux != null) {
                        gradoCandidato++;
                        aux = aux.getLiga();
                    }

                    if (gradoCandidato > gradoMejor) {
                        mejor = candidato;
                        gradoMejor = gradoCandidato;
                    } else {
                        // Si el grado del candidato no es mayor, se conserva el R
                        mejor = mejor;
                    }
                } else {
                    // Si el candidato es nulo, no hacemos nada
                    mejor = mejor;
                }

                hijo = hijo.getLiga();
            }

            return mejor;
        }
    }

    public void mostrarAncestros(int cedulaBuscada) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        System.out.println("Ancestros de " + cedulaBuscada + ":");
        if (!mostrarAncestrosRecursivo(raiz, cedulaBuscada)) {
            System.out.println("No se encontró el nodo con cédula " + cedulaBuscada);
        }
    }

    private boolean mostrarAncestrosRecursivo(Nodo actual, int cedulaBuscada) {
        if (actual == null) return false;

        // Caso base: encontramos el nodo
        if (actual.getCedula() == cedulaBuscada) {
            return true;
        }

        // Buscar en sus hijos
        Nodo hijo = actual.getLigaLista();
        while (hijo != null) {
            if (mostrarAncestrosRecursivo(hijo, cedulaBuscada)) {
                // Si el hijo contiene el nodo buscado, este "actual" es un ancestro
                System.out.println(" → " + actual.getNombre() + " (Cédula: " + actual.getCedula() + ")");
                return true;
            }
            hijo = hijo.getLiga();
        }

        return false;
    }

    public void mostrarNodoConMayorGrado(Nodo R) {
        if (R == null) {
            System.out.println("El árbol está vacío");
        } else {
            Nodo nodoMayor = obtenerNodoConMayorGrado(R);

            if (nodoMayor != null) {
                // Contar hijos para mostrar el grado
                int grado = 0;
                Nodo hijo = nodoMayor.getLigaLista();
                while (hijo != null) {
                    grado++;
                    hijo = hijo.getLiga();
                }

                System.out.println("El nodo con mayor grado es: "
                        + nodoMayor.getNombre()
                        + " con grado: " + grado);
            } else {
                System.out.println("No se encontró ningún nodo en el árbol");
            }
        }
    }

    public void mostrarNodoConMayorNivel(Nodo R) {
        if (R == null) {
            System.out.println("El árbol está vacío");
            return;
        }

        int altura = calcularAltura(R); // Ya lo tienes implementado
        int nivelObjetivo = altura - 1; // El nivel más profundo

        Nodo nodoMayorNivel = obtenerNodoEnNivel(R, 0, nivelObjetivo);

        if (nodoMayorNivel != null) {
            System.out.println("El nodo con mayor nivel es: "
                    + nodoMayorNivel.getNombre()
                    + " en el nivel: " + (nivelObjetivo +1));
        } else {
            System.out.println("No se encontró ningún nodo en el árbol");
        }
    }

    private Nodo obtenerNodoEnNivel(Nodo actual, int nivelActual, int nivelObjetivo) {
        if (actual == null) {
            return null;
        }

        Nodo resultado = null;

        if (nivelActual == nivelObjetivo) {
            resultado = actual; // Guarda este nodo, pero no retorna inmediatamente
        }

        Nodo hijo = actual.getLigaLista();
        while (hijo != null) {
            Nodo candidato = obtenerNodoEnNivel(hijo, nivelActual + 1, nivelObjetivo);
            if (candidato != null) {
                resultado = candidato; // Sobrescribe con el último encontrado
            }
            hijo = hijo.getLiga();
        }

        return resultado;
    }

    public void mostrarDescendientes(Nodo raiz, int cedulaBuscada) {
        Nodo nodo = buscarNodo(raiz, cedulaBuscada);
        if (nodo == null) {
            System.out.println("No se encontró el nodo con cédula " + cedulaBuscada);
            return;
        }

        System.out.println("Descendientes de " + nodo.getNombre() + " (Cédula: " + nodo.getCedula() + "):");
        mostrarDescendientesRecursivo(nodo.getLigaLista());
    }

    private void mostrarDescendientesRecursivo(Nodo hijo) {
        while (hijo != null) {
            System.out.println(" → " + hijo.getNombre() + " (Cédula: " + hijo.getCedula() + ")");
            // Recursión con los hijos de este nodo
            if (hijo.getLigaLista() != null) {
                mostrarDescendientesRecursivo(hijo.getLigaLista());
            }
            hijo = hijo.getLiga(); // pasar al siguiente hermano
        }
    }

    public int mostrarNivelDeRegistro(Nodo raiz, int cedulaBuscada) {
        int nivel = buscarNivel(raiz, cedulaBuscada, 0);
        if (nivel == -1) {
            System.out.println("No se encontró el nodo con cédula " + cedulaBuscada);
        } else {
            System.out.println("El nodo con cédula " + cedulaBuscada + " está en el nivel " + nivel);
        }
        return nivel;
    }

    private int buscarNivel(Nodo actual, int cedula, int nivel) {
        if (actual == null) return -1;

        if (actual.getCedula() == cedula) return nivel;

        Nodo hijo = actual.getLigaLista();
        while (hijo != null) {
            int nivelEncontrado = buscarNivel(hijo, cedula, nivel + 1);
            if (nivelEncontrado != -1) return nivelEncontrado;
            hijo = hijo.getLiga();
        }
        return -1;
    }

    public void mostrarRegistrosDeNivel(Nodo raiz, int nivelBuscado) {
        System.out.println("Registros en el nivel " + nivelBuscado + ":");
        mostrarEnNivel(raiz, 0, nivelBuscado);
    }

    private void mostrarEnNivel(Nodo actual, int nivelActual, int nivelBuscado) {
        if (actual == null) return;

        if (nivelActual == nivelBuscado) {
            System.out.println(" → " + actual.getNombre() + " (Cédula: " + actual.getCedula() + ")");
        }

        Nodo hijo = actual.getLigaLista();
        while (hijo != null) {
            mostrarEnNivel(hijo, nivelActual + 1, nivelBuscado);
            hijo = hijo.getLiga();
        }
    }

    public void eliminarNivel(Nodo R, int nivel) {
        if (R == null) return;

        if (nivel == 1) {
            // Caso especial: eliminar el nivel raíz → se pierde todo
            R = null;
            return;
        }

        eliminarNivelRecursivo(R, 1, nivel);
    }

    private void eliminarNivelRecursivo(Nodo actual, int nivelActual, int nivelBuscado) {
        if (actual == null) return;

        if (nivelActual + 1 == nivelBuscado && actual.getSw() == 1) {
            Nodo primerNodoNivel = actual.getLigaLista();

            if (primerNodoNivel == null) return;

            Nodo nuevoInicio = null;
            Nodo fin = null;

            // Recorremos los nodos del nivel que se va a eliminar
            Nodo cursor = primerNodoNivel;
            while (cursor != null) {
                if (cursor.getSw() == 1) {
                    // hijos de este nodo
                    Nodo hijos = cursor.getLigaLista();

                    if (hijos != null) {
                        if (nuevoInicio == null) {
                            nuevoInicio = hijos;  // Primer hijo
                            fin = hijos;
                            while (fin.getLiga() != null) {
                                fin = fin.getLiga();
                            }
                        } else {
                            // Conectar los hijos al final
                            fin.setLiga(hijos);
                            while (fin.getLiga() != null) {
                                fin = fin.getLiga();
                            }
                        }
                    }
                }
                cursor = cursor.getLiga();
            }

            // Reemplazar el nivel eliminado con los hijos encontrados
            actual.setLigaLista(nuevoInicio);
            if (nuevoInicio != null) {
                actual.setSw(1);
            } else {
                actual.setSw(0);
            }

            return;
        }

        // Seguir recorriendo normalmente
        Nodo hijo = actual.getLigaLista();
        while (hijo != null) {
            eliminarNivelRecursivo(hijo, nivelActual + 1, nivelBuscado);
            hijo = hijo.getLiga();
        }
    }

    public Nodo eliminarNodo(Nodo raiz, int cedulaBuscada) {
        if (raiz == null) return null;

        Nodo hijo = raiz.getLigaLista();
        Nodo anterior = null;

        while (hijo != null) {
            if (hijo.getCedula() == cedulaBuscada) {
                // caso: eliminar hijo actual
                if (anterior == null) {
                    // el nodo eliminado es el primer hijo
                    raiz.setLigaLista(hijo.getLiga());
                } else {
                    anterior.setLiga(hijo.getLiga());
                }

                // Reubicar hijos del nodo eliminado
                if (hijo.getLigaLista() != null) {
                    Nodo primerHijo = hijo.getLigaLista();
                    Nodo ultimoHijo = primerHijo;
                    while (ultimoHijo.getLiga() != null) {
                        ultimoHijo = ultimoHijo.getLiga();
                    }
                    // enlazar los hijos al hermano mayor
                    ultimoHijo.setLiga(hijo.getLiga());
                    if (anterior == null) {
                        raiz.setLigaLista(primerHijo);
                    } else {
                        anterior.setLiga(primerHijo);
                    }
                }

                return raiz;
            }

            eliminarNodo(hijo, cedulaBuscada);
            anterior = hijo;
            hijo = hijo.getLiga();
        }
        return raiz;
    }

}
