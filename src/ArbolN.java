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

    public void insertar(Nodo R, String nombre, int cedula, int edad, int cedulaPadre) {
        Nodo P = R;
        Nodo nuevo = new Nodo(nombre, cedula, edad);

        while (P != null) {
            if (P.getCedula() == cedulaPadre) {
                // Si el nodo padre ya tiene sublista de hijos
                if (P.getSw() == 1) {
                    Nodo temp = P.getLigaLista();
                    Nodo anterior = null;

                    // Buscar posición donde insertar ordenado por cédula
                    while (temp != null && temp.getCedula() < cedula) {
                        anterior = temp;
                        temp = temp.getLiga();
                    }

                    if (anterior == null) {
                        // Insertar al inicio de la sublista
                        nuevo.setLiga(P.getLigaLista());
                        P.setLigaLista(nuevo);
                    } else {
                        // Insertar en medio o al final
                        nuevo.setLiga(temp);
                        anterior.setLiga(nuevo);
                    }
                } else {
                    // Si el nodo padre no tiene hijos aún
                    P.setSw(1);
                    P.setLigaLista(nuevo);
                } // Importante: salir después de insertar
            } else if (P.getSw() == 1) {
                // Buscar recursivamente en la sublista
                insertar(P.getLigaLista(), nombre, cedula, edad, cedulaPadre);
            }

            // Pasar al siguiente hermano
            P = P.getLiga();
        }
    }

    public String mostrarArbol() {
        if (raiz == null) {
            return "El árbol está vacío.";
        }
        StringBuilder sb = new StringBuilder();
        recorrer(raiz, sb, 0);
        return sb.toString();
    }

    private void recorrer(Nodo nodo, StringBuilder sb, int nivel) {
        if (nodo == null) return;

        // Identación según el nivel
        for (int i = 0; i < nivel; i++) {
            sb.append("||    ");
        }

        // Nodo actual
        sb.append("[").append(nodo.getCedula()).append("] ")
                .append(nodo.getEdad()).append(", ")
                .append(nodo.getNombre())
                .append("\n");

        // Recorremos la sublista (hijos)
        if (nodo.getSw() == 1 && nodo.getLigaLista() != null) {
            recorrer(nodo.getLigaLista(), sb, nivel + 1);
        }

        // Recorremos el hermano
        if (nodo.getLiga() != null) {
            recorrer(nodo.getLiga(), sb, nivel);
        }
    }

    public String mostrarHijos(Nodo R, int cedulaPadre) {
        // Buscar el nodo padre en el árbol usando su cédula
        Nodo nodo = buscarNodo(R, cedulaPadre);

        if (nodo == null) {
            return "Nodo no encontrado";
        }

        // Si no tiene hijos
        if (nodo.getSw() != 1 || nodo.getLigaLista() == null) {
            return "El nodo no tiene hijos";
        }

        // Si tiene hijos
        String mensaje = "Hijos de " + nodo.getNombre() + ":\n";
        Nodo hijo = nodo.getLigaLista();

        while (hijo != null) {
            //mientras la sublista no esté vacía, muestra sus hijos
            mensaje += "- Nombre: " + hijo.getNombre() +", cédula: "+ hijo.getCedula() + ", edad: " + hijo.getEdad() + "\n";
            hijo = hijo.getLiga();
        }

        return mensaje;
    }

    public String mostrarHermanos(Nodo R, int cedulaHermano) {
        // Se busca el nodo con la cédula indicada en el árbol
        Nodo nodo = buscarNodo(R, cedulaHermano);

        // Si el nodo no existe en el árbol
        if (nodo == null) {
            return "El nodo que busca no existe";
        } else {
            // Se busca el padre del nodo encontrado
            Nodo padre = buscarPadre(R, cedulaHermano);

            // Si el padre es null, significa que el nodo es la raíz → no tiene hermanos
            if (padre == null) {
                return "El nodo no tiene hermanos";
            }

            // Se obtiene la lista de hijos del padre
            Nodo hermano = padre.getLigaLista();

            // Bandera para verificar si realmente hay hermanos
            boolean tieneHermanos = false;

            // Se inicializa el mensaje con el nombre y cédula del nodo consultado
            String mensaje = "Hermanos de " + nodo.getNombre() + " (" + cedulaHermano + "):\n";

            // Recorremos la lista de hijos del padre
            while (hermano != null) {
                // Evitamos mostrar al mismo nodo consultado
                if (hermano.getCedula() != cedulaHermano) {
                    tieneHermanos = true; // ✅ Confirmamos que sí tiene hermanos
                    // Agregamos la información del hermano encontrado al mensaje
                    mensaje += "- Nombre: " + hermano.getNombre()
                            + ", cédula: " + hermano.getCedula()
                            + ", edad: " + hermano.getEdad() + "\n";
                }
                // Avanzamos al siguiente hermano en la lista
                hermano = hermano.getLiga();
            }

            // Si no se encontró ningún hermano, se devuelve un mensaje específico
            if (!tieneHermanos) {
                mensaje = "El nodo no tiene hermanos";
            }

            // Retornamos el mensaje final con la información encontrada
            return mensaje;
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

    public String mostrarPadre(Nodo R, int cedulaHijo) {
        // Validar si el nodo hijo existe en el árbol usando buscarNodo
        Nodo nodoHijo = buscarNodo(R, cedulaHijo);
        if (nodoHijo == null) {
            return "El nodo con cédula " + cedulaHijo + " no existe en el árbol.";
        }else{
            // Si existe, buscamos el padre con el método buscarPadre
            Nodo padre = buscarPadre(R, cedulaHijo);

            if (padre == null) {
                // Si el padre es null, significa que es la raíz
                return "El nodo con cédula " + cedulaHijo + " es la raíz, no tiene padre.";
            } else {
                // Mostramos los datos del padre
                return "El padre de " + nodoHijo.getNombre() + " es: " + padre.getNombre();
            }
        }
    }

    public String mostrarAltura(Nodo R) {
        // Calcular la altura del árbol usando el método existente
        int altura = calcularAltura(R);

        // Retornar la altura como mensaje
        return "La altura del árbol es: " + altura;
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

    public String actualizarNodo(Nodo R, int cedulaBuscada, int cedula, String nombre, int edad) {
        Nodo nodo = buscarNodo(R, cedulaBuscada);

        if (nodo == null) {
            return "El nodo con cédula " + cedulaBuscada + " no existe.";
        } else {
            // Actualizar los datos del nodo
            nodo.setNombre(nombre);
            nodo.setCedula(cedula);
            nodo.setEdad(edad);

            // Retornar mensaje de confirmación usando toString()
            return "Nodo actualizado correctamente: \n" + nodo.toString();
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

    public String mostrarAncestros(int cedulaBuscada) {
        if (raiz == null) {
            return "El árbol está vacío.";
        }

        String ancestros = mostrarAncestrosRecursivo(raiz, cedulaBuscada);
        String resultado = "Ancestros de " + cedulaBuscada + ":\n";

        if (ancestros.isEmpty()) {
            return "No se encontró el nodo con cédula " + cedulaBuscada;
        }

        return resultado + ancestros;
    }

    private String mostrarAncestrosRecursivo(Nodo actual, int cedulaBuscada) {
        if (actual == null) return "";

        // Caso base: encontramos el nodo
        if (actual.getCedula() == cedulaBuscada) {
            return "✓ Nodo encontrado\n"; // Marcamos el final de la búsqueda
        }

        // Buscar en sus hijos
        Nodo hijo = actual.getLigaLista();
        while (hijo != null) {
            String resultadoHijo = mostrarAncestrosRecursivo(hijo, cedulaBuscada);
            if (!resultadoHijo.isEmpty()) {
                // Este "actual" es un ancestro → usamos toString() del nodo
                return actual.toString() + resultadoHijo;
            }
            hijo = hijo.getLiga();
        }

        return "";
    }

    public String mostrarNodoConMayorGrado(Nodo R) {
        // Si el árbol está vacío
        if (R == null) {
            return "El árbol está vacío";
        } else {
            // Obtener el nodo con mayor grado
            Nodo nodoMayor = obtenerNodoConMayorGrado(R);

            // Si se encontró un nodo
            if (nodoMayor != null) {
                // Contar hijos para calcular el grado del nodo
                int grado = 0;
                Nodo hijo = nodoMayor.getLigaLista();
                while (hijo != null) {
                    grado++;
                    hijo = hijo.getLiga();
                }

                // Retornar información del nodo con mayor grado
                return "El nodo con mayor grado es: "
                        + nodoMayor.getNombre()
                        + " con grado: " + grado;
            } else {
                // No se encontró ningún nodo
                return "No se encontró ningún nodo en el árbol";
            }
        }
    }

    public String mostrarNodoConMayorNivel(Nodo R) {
        // Verificar si el árbol está vacío
        if (R == null) {
            return "El árbol está vacío";
        }

        // Calcular la altura del árbol
        int altura = calcularAltura(R); // Método previamente implementado

        // Determinar el nivel más profundo (mayor nivel)
        int nivelObjetivo = altura - 1;

        // Obtener un nodo que se encuentre en el nivel más profundo
        Nodo nodoMayorNivel = obtenerNodoEnNivel(R, 0, nivelObjetivo);

        // Verificar si se encontró el nodo
        if (nodoMayorNivel != null) {
            // Retornar información del nodo con mayor nivel
            return "El nodo con mayor nivel es: "
                    + nodoMayorNivel.getNombre()
                    + " en el nivel: " + (nivelObjetivo + 1);
        } else {
            // Caso en que no hay nodos en el árbol
            return "No se encontró ningún nodo en el árbol";
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

    public String mostrarDescendientes(Nodo raiz, int cedulaBuscada) {
        // Buscar el nodo en el árbol
        Nodo nodo = buscarNodo(raiz, cedulaBuscada);

        // Si no existe
        if (nodo == null) {
            return "No se encontró el nodo con cédula " + cedulaBuscada;
        }

        // Si existe, se empieza a construir el mensaje
        String mensaje = "Descendientes de " + nodo.getNombre()
                + " (Cédula: " + nodo.getCedula() + "):\n";

        // Concatenamos los descendientes usando recursión
        mensaje += mostrarDescendientesRecursivo(nodo.getLigaLista());

        return mensaje;
    }

    private String mostrarDescendientesRecursivo(Nodo hijo) {
        if (hijo == null) return "";

        String resultado = "";
        while (hijo != null) {
            // Aquí usamos directamente el toString() del nodo
            resultado += " → " + hijo.toString();

            // Llamada recursiva para los hijos de este nodo
            if (hijo.getLigaLista() != null) {
                resultado += mostrarDescendientesRecursivo(hijo.getLigaLista());
            }

            // Pasar al siguiente hermano
            hijo = hijo.getLiga();
        }

        return resultado;
    }

    public String mostrarNivelDeRegistro(Nodo raiz, int cedulaBuscada) {
        // Buscar el nivel del nodo con la cédula indicada, empezando desde nivel 0
        int nivel = buscarNivel(raiz, cedulaBuscada, 0);

        // Verificar si el nodo fue encontrado
        if (nivel == -1) {
            // Nodo no encontrado
            return "No se encontró el nodo con cédula " + cedulaBuscada;
        } else {
            // Nodo encontrado, retornar mensaje con el nivel
            return "El nodo con cédula " + cedulaBuscada + " está en el nivel " + nivel;
        }
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

    public String mostrarRegistrosDeNivel(Nodo raiz, int nivelBuscado) {
        // Inicializar el mensaje con el encabezado del nivel
        String mensaje = "Registros en el nivel " + nivelBuscado + ":\n";

        // Llamar al método auxiliar que devuelve los registros en ese nivel
        mensaje += mostrarEnNivelComoString(raiz, 0, nivelBuscado);

        // Retornar el mensaje completo
        return mensaje;
    }

    private String mostrarEnNivelComoString(Nodo nodo, int nivelActual, int nivelBuscado) {
        // Si el nodo es nulo, no hay nada que mostrar
        if (nodo == null) return "";

        String resultado = "";

        // Si el nodo actual está en el nivel buscado, agregar su información
        if (nivelActual == nivelBuscado) {
            resultado += nodo.toString();
        }

        // Recorrer los hijos del nodo
        if (nodo.getSw() == 1) {
            Nodo hijo = nodo.getLigaLista();
            while (hijo != null) {
                resultado += mostrarEnNivelComoString(hijo, nivelActual + 1, nivelBuscado);
                hijo = hijo.getLiga();
            }
        }

        // Retornar los resultados acumulados
        return resultado;
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

    public String eliminarNodoYReordenar(Nodo raiz, int cedulaBuscada) {
        // Verificar si el árbol está vacío
        if (raiz == null) return "El árbol está vacío";

        // Verificar si se intenta eliminar la raíz
        if (raiz.getCedula() == cedulaBuscada) {
            return "No se puede eliminar la raíz del árbol.";
        }

        Nodo hijo = raiz.getLigaLista();
        Nodo anterior = null;

        while (hijo != null) {
            if (hijo.getCedula() == cedulaBuscada) {
                // Nodo encontrado, proceder a eliminar
                Nodo hijosDelEliminado = hijo.getLigaLista(); // Hijos que se van a heredar
                Nodo siguienteHermano = hijo.getLiga();       // Hermanos restantes

                // Conectar hermanos del nodo eliminado
                if (anterior == null) {
                    raiz.setLigaLista(siguienteHermano);
                } else {
                    anterior.setLiga(siguienteHermano);
                }

                // Si el nodo eliminado tiene hijos, asignarlos al hermano de mayor edad
                if (hijosDelEliminado != null && siguienteHermano != null) {
                    Nodo hermanoMayor = siguienteHermano;
                    Nodo temp = siguienteHermano;

                    // Buscar el hermano con mayor edad
                    while (temp != null) {
                        if (temp.getEdad() > hermanoMayor.getEdad()) {
                            hermanoMayor = temp;
                        }
                        temp = temp.getLiga();
                    }

                    // Heredar los hijos: agregarlos al inicio de la sublista de hijos del hermano mayor
                    if (hermanoMayor.getSw() == 1) {
                        Nodo ultimoHijo = hijosDelEliminado;
                        while (ultimoHijo.getLiga() != null) {
                            ultimoHijo = ultimoHijo.getLiga();
                        }
                        ultimoHijo.setLiga(hermanoMayor.getLigaLista());
                    }
                    hermanoMayor.setLigaLista(hijosDelEliminado);
                    hermanoMayor.setSw(1);
                }

                // Retornar mensaje de confirmación
                return "Nodo con cédula " + cedulaBuscada + " eliminado correctamente y hijos heredados al hermano de mayor edad.";
            }

            // Recursión en la sublista de hijos
            String resultado = eliminarNodoYReordenar(hijo, cedulaBuscada);
            if (!resultado.startsWith("Nodo con cédula") && !resultado.equals("No se puede eliminar la raíz del árbol.")) {
                // Si el nodo no fue eliminado en esta sublista, continuar
                anterior = hijo;
                hijo = hijo.getLiga();
            } else {
                // Nodo eliminado o mensaje de raíz, retornar resultado
                return resultado;
            }
        }

        // Si el nodo no fue encontrado en este nivel
        return "No se encontró el nodo con cédula " + cedulaBuscada;
    }

}
