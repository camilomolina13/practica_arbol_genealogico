
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        // ===== Crear árbol e insertar datos de prueba =====
        ArbolN arbol = new ArbolN();
        datosDummies(arbol);
        //arbol.mostrarArbol(arbol.getRaiz(), 0);

        // ===== Menú principal =====
        boolean ejecutarMenu = true;
        while (ejecutarMenu) {

            //Mostrar arbol (temporal)///////////////////////////////
            //arbol.mostrarArbol(arbol.getRaiz(), 0);

            String opcion;
            opcion = JOptionPane.showInputDialog(
                    null,
                    """
                                    ╔════════════════════════════════════╗
                                             🌳 MENÚ PRINCIPAL 🌳         
                                    ╠════════════════════════════════════╣
                                    ║ 1. 👥 Gestión de Personas            
                                    ║ 2. 👨‍ Consultar Relaciones         
                                    ║ 3. 🏗 Consultar Estructura           
                                    ║ 4. 🗑Eliminación de Niveles         
                                    ║ 5. 🚪 Salir                          
                                    ╚════════════════════════════════════╝
                                                         👉 Selecciona una opción:
                                    """,
                    "🌿 Sistema de Árbol Genealógico 🌿",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == null) {
                ejecutarMenu = false;
            } else {
                switch (opcion) {
                    case "1" ->
                        menuGestionPersonas(arbol);
                    case "2" ->
                        menuRelacionesFamiliares(arbol);
                    case "3" ->
                        menuEstructura(arbol);
                    case "4" ->
                        menuEliminacionNiveles(arbol);
                    case "5" -> {
                        ejecutarMenu = false;
                        JOptionPane.showMessageDialog(null, "👋 Saliendo del programa...");
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "⚠ Opción no válida.");
                }
            }
        }
    }

    /* ==========================================================
       1) SUBMENÚ: GESTIÓN DE PERSONAS
    ========================================================== */
    private static void menuGestionPersonas(ArbolN arbol) {
        boolean back = false;
        while (!back) {
            var opcion = JOptionPane.showInputDialog(
                    null,
                    """
                ╔════════════════════════════════════
                ║       👥 Gestión de Personas        
                ╠════════════════════════════════════
                ║ 1. ➕ Registrar Persona              
                ║ 2. ❌ Eliminar Persona               
                ║ 3. ♻  Actualizar Persona             
                ║ 4. 🔙 Volver al Menú Principal       
                ╚════════════════════════════════════
                
                👉 Selecciona una opción:
                """,
                    "👥 Gestión de Personas",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == null || opcion.equals("4")) {
                back = true;
            } else {
                switch (opcion) {
                    case "1" -> {
                        // Pedimos los datos
                        String cedulaStr = JOptionPane.showInputDialog("Ingrese la cédula:");
                        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
                        String edadStr = JOptionPane.showInputDialog("Ingrese la edad:");
                        int cedula = Integer.parseInt(cedulaStr);
                        int edad = Integer.parseInt(edadStr);
                        // Validamos y convertimos a números donde corresponda
                        try {
                            if (arbol.estaVacio()) {
                                arbol.insertar(nombre, cedula, edad);
                                // Mostramos un resumen de lo ingresado
                                JOptionPane.showMessageDialog(null,
                                        "✅ Datos registrados:\n"
                                                + "Cédula: " + cedula + "\n"
                                                + "Nombre: " + nombre + "\n"
                                                + "Edad: " + edad
                                );
                            } else {
                                String cedulaPadreStr = JOptionPane.showInputDialog("Ingrese la cédula del padre:");
                                int cedulaPadre = Integer.parseInt(cedulaPadreStr);
                                arbol.insertar(arbol.getRaiz(), nombre, cedula, edad, cedulaPadre);
                                // Mostramos un resumen de lo ingresado
                                JOptionPane.showMessageDialog(null,
                                        "✅ Datos registrados:\n"
                                                + "Cédula: " + cedula + "\n"
                                                + "Nombre: " + nombre + "\n"
                                                + "Cédula del padre: " + cedulaPadre + "\n"
                                                + "Edad: " + edad
                                );
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "⚠️ Error: Debe ingresar valores numéricos válidos para cédula y edad.");
                        }
                    }
                    case "2" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula de la persona a eliminar");
                        if (cedulaStr != null) {
                            try {
                                int cedulaBuscada = Integer.parseInt(cedulaStr);
                                JOptionPane.showMessageDialog(null,arbol.eliminarNodoYReordenar(arbol.getRaiz(), cedulaBuscada));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    case "3" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula de la persona a actualizar");
                        if (cedulaStr != null) {
                            try {
                                int cedulaBuscada = Integer.parseInt(cedulaStr);

                                // Pedir los nuevos datos
                                String nuevaCedulaStr = JOptionPane.showInputDialog(null, "Ingrese la nueva cédula:");
                                String nuevoNombre = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre:");
                                String nuevaEdadStr = JOptionPane.showInputDialog(null, "Ingrese la nueva edad:");

                                // Convertir a números
                                int nuevaCedula = Integer.parseInt(nuevaCedulaStr);
                                int nuevaEdad = Integer.parseInt(nuevaEdadStr);

                                // Llamada al método del árbol
                                JOptionPane.showMessageDialog(null, arbol.actualizarNodo(arbol.getRaiz(), cedulaBuscada, nuevaCedula, nuevoNombre, nuevaEdad));

                                JOptionPane.showMessageDialog(null, "✅ Datos actualizados correctamente.");

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ Error: debe ingresar valores numéricos válidos para cédula y edad.");
                            }
                        }

                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "⚠ Opción no válida.");
                }
            }
        }
    }

    /* ==========================================================
       2) SUBMENÚ: RELACIONES FAMILIARES
    ========================================================== */
    private static void menuRelacionesFamiliares(ArbolN arbol) {
        boolean back = false;
        while (!back) {
            String opcion = JOptionPane.showInputDialog(
                    null,
                    """
                ╔════════════════════════════════════
                ║   👨‍👩‍👧 Consultar Relaciones Familiares  
                ╠════════════════════════════════════
                ║ 1. 👨‍👩 Padres                       
                ║ 2. 👶 Hijos                        
                ║ 3. 🤝 Hermanos                      
                ║ 4. 🧓 Ancestros                     
                ║ 5. 🌱 Descendientes                 
                ║ 6. 🔙 Volver al Menú Principal       
                ╚════════════════════════════════════
                
                👉 Selecciona una opción:
                """,
                    "🔍 Consultar Relaciones Familiares",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == null || opcion.equals("6")) {
                back = true;
            } else {
                switch (opcion) {
                    case "1" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula del hijo:");
                        if (cedulaStr != null) {
                            try {
                                int cedulaHijo = Integer.parseInt(cedulaStr);
                                String mostrarPadre = arbol.mostrarPadre(arbol.getRaiz(), cedulaHijo);
                                JOptionPane.showMessageDialog(null, mostrarPadre);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    case "2" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula del padre:");
                        if (cedulaStr != null) {
                            try {
                                int cedulaPadre = Integer.parseInt(cedulaStr);
                                String mostrarHijos = arbol.mostrarHijos(arbol.getRaiz(), cedulaPadre);
                                JOptionPane.showMessageDialog(null, mostrarHijos);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    case "3" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula del hermano:");
                        if (cedulaStr != null) {
                            try {
                                int cedulaHermano = Integer.parseInt(cedulaStr);
                                String mostrarHermanos = arbol.mostrarHermanos(arbol.getRaiz(), cedulaHermano);
                                JOptionPane.showMessageDialog(null, mostrarHermanos);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    case "4" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula del pariente descendiente:");
                        if (cedulaStr != null) {
                            try {
                                int cedulaPariente = Integer.parseInt(cedulaStr);
                                String ancestros = arbol.mostrarAncestros(cedulaPariente);
                                JOptionPane.showMessageDialog(null, ancestros);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    case "5" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula del pariente ancestro:");
                        if (cedulaStr != null) {
                            try {
                                int cedulaPariente = Integer.parseInt(cedulaStr);
                               JOptionPane.showMessageDialog(null,arbol.mostrarDescendientes(arbol.getRaiz(), cedulaPariente));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "⚠ Opción no válida.");
                }
            }
        }
    }

    /* ==========================================================
       3) SUBMENÚ: CONSULTAR ESTRUCTURA
    ========================================================== */
    private static void menuEstructura(ArbolN arbol) {
        boolean back = false;
        while (!back) {
            String opcion = JOptionPane.showInputDialog(
                    null,
                    """
                ╔════════════════════════════════════
                ║       🏗  Estructura del Árbol       
                ╠════════════════════════════════════
                ║ 1. 🌲 Mostrar árbol                  
                ║ 2. 🔝 Nodo con mayor grado           
                ║ 3. 📏 Nodo con mayor nivel           
                ║ 4. 🌳 Altura del árbol               
                ║ 5. 🎯 Nivel de un registro           
                ║ 6. 📂 Registros por nivel            
                ║ 7. 🔙 Volver al Menú Principal       
                ╚════════════════════════════════════
                
                👉 Selecciona una opción:
                """,
                    "🏗 Consultar Estructura",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == null || opcion.equals("7")) {
                back = true;
            } else {
                switch (opcion) {
                    case "1" ->
                            JOptionPane.showMessageDialog(null, arbol.mostrarArbol()); // Nuevo método en la primera opción
                    case "2" ->
                            JOptionPane.showMessageDialog(null, arbol.mostrarNodoConMayorGrado(arbol.getRaiz()));
                    case "3" ->
                            JOptionPane.showMessageDialog(null, arbol.mostrarNodoConMayorNivel(arbol.getRaiz()));
                    case "4" ->
                            JOptionPane.showMessageDialog(null,arbol.mostrarAltura(arbol.getRaiz()));
                    case "5" -> {
                        String cedulaStr = JOptionPane.showInputDialog(null, "Ingrese la cédula de la persona a consultar:");
                        if (cedulaStr != null) {
                            try {
                                int cedula = Integer.parseInt(cedulaStr);
                                JOptionPane.showMessageDialog(null,arbol.mostrarNivelDeRegistro(arbol.getRaiz(), cedula));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    case "6" -> {
                        String nivel = JOptionPane.showInputDialog(null, "Ingrese el nivel a consultar:");
                        if (nivel != null) {
                            try {
                                int nivelaMostrar = Integer.parseInt(nivel);
                                JOptionPane.showMessageDialog(null, arbol.mostrarRegistrosDeNivel(arbol.getRaiz(), nivelaMostrar));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ El nivel debe ser un número válido.");
                            }
                        }
                    }
                    default ->
                            JOptionPane.showMessageDialog(null, "⚠ Opción no válida.");
                }
            }
        }
    }

    /* ==========================================================
       4) SUBMENÚ: ELIMINACIÓN DE NIVELES
    ========================================================== */
    private static void menuEliminacionNiveles(ArbolN arbol) {
        boolean back = false;
        while (!back) {
            String opcion = JOptionPane.showInputDialog(
                    null,
                    """
                ╔════════════════════════════════════
                ║       🗑  Eliminación de Niveles      
                ╠════════════════════════════════════
                ║ 1. ❌ Eliminar todos los nodos       
                ║    de un nivel específico            
                ║ 2. 🔙 Volver al Menú Principal       
                ╚════════════════════════════════════
                
                👉 Selecciona una opción:
                """,
                    "🗑 Eliminación de Niveles",
                    JOptionPane.WARNING_MESSAGE
            );

            if (opcion == null || opcion.equals("2")) {
                back = true;
            } else {
                switch (opcion) {
                    case "1" -> {
                        String nivelEliminar = JOptionPane.showInputDialog(null, "Ingrese el nivel a eliminar:");
                        if (nivelEliminar != null) {
                            try {
                                int nivel = Integer.parseInt(nivelEliminar);
                                JOptionPane.showMessageDialog(null,arbol.eliminarNivel(arbol.getRaiz(), nivel));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "⚠ La cédula debe ser un número válido.");
                            }
                        }
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "⚠ Opción no válida.");
                }
            }
        }
    }

    /* ==========================================================
       DATOS DE PRUEBA
    ========================================================== */
    public static void datosDummies(ArbolN arbol) {
        arbol.insertar("Pedro", 100, 89);
        arbol.insertar(arbol.getRaiz(), "Jose", 300, 65, 100);
        arbol.insertar(arbol.getRaiz(), "Ana", 150, 71, 100);
        arbol.insertar(arbol.getRaiz(), "Maria", 200, 67, 100);
        arbol.insertar(arbol.getRaiz(), "Simon", 250, 69, 100);
        arbol.insertar(arbol.getRaiz(), "Rosa", 400, 41, 300);
        arbol.insertar(arbol.getRaiz(), "Sara", 550, 42, 300);
        arbol.insertar(arbol.getRaiz(), "Hugo", 350, 47, 200);
        arbol.insertar(arbol.getRaiz(), "Paco", 600, 51, 200);
        arbol.insertar(arbol.getRaiz(), "Laura", 450, 49, 200);
        arbol.insertar(arbol.getRaiz(), "Luis", 750, 50, 200);
        arbol.insertar(arbol.getRaiz(), "Nora", 700, 43, 350);
        arbol.insertar(arbol.getRaiz(), "Lola", 500, 44, 350);
        arbol.insertar(arbol.getRaiz(), "Pablo", 650, 45, 350);
    }
}

//arbol.mostrarArbol(arbol.getRaiz(),0);
//arbol.mostrarHijos(arbol.getRaiz(),300);
//arbol.mostrarHijos(arbol.getRaiz(),100);
//arbol.mostrarHijos(arbol.getRaiz(),650);
//arbol.mostrarHermanos(arbol.getRaiz(),400);
//arbol.mostrarHermanos(arbol.getRaiz(),600);
//arbol.mostrarHijos(arbol.getRaiz(),10000);
//arbol.mostrarPadre(arbol.getRaiz(),100);
//arbol.mostrarAltura(arbol.getRaiz());
//arbol.mostrarArbol(arbol.getRaiz(),0);
//arbol.actualizarNodo(arbol.getRaiz(), 300, 300, "Camilo", 99);
//arbol.mostrarArbol(arbol.getRaiz(),0);
//arbol.mostrarNodoConMayorNivel(arbol.getRaiz());
//arbol.mostrarDescendientes(arbol.getRaiz(), 200);
//arbol.mostrarAncestros(500);
//arbol.mostrarNivelDeRegistro(arbol.getRaiz(), 600);
//arbol.mostrarRegistrosDeNivel(arbol.getRaiz(), 700);
//arbol.eliminarNodo(arbol.getRaiz(), 350);
//arbol.eliminarNivel(arbol.getRaiz(),3);
//arbol.mostrarArbol(arbol.getRaiz(), 0);
