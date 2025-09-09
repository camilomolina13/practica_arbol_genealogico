public class Main {
    public static void main(String[] args) {

        ArbolN arbol = new ArbolN();
//        boolean showMenu = true;
//        int opcionMenu = 0;
//
//            while (showMenu!=true) {
//
//                lineaBonita();
//                System.out.println("Seleccione lo que más le gusta ϖ");
//                lineaBonita();
//
//                switch (opcionMenu) {
//                    case 0:
//                        datosDummies(arbol);
//                    case 1:
//                        arbol.mostrarArbol(arbol.getRaiz(),0);
//
//                    case 9:
//                        System.out.println("Bye mor");
//                        showMenu=false;
//
//
//                }
//            }

        datosDummies(arbol);
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
        arbol.actualizarNodo(arbol.getRaiz(), 300, 300, "Camilo", 99);
        arbol.mostrarArbol(arbol.getRaiz(),0);
    }

    public static void lineaBonita() {
        System.out.println("₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪₪");
    }

    public static void datosDummies(ArbolN arbol){
        arbol.insertar("Pedro", 100, 89);
        arbol.insertar(arbol.getRaiz(), "Jose", 300, 65, 100);
        arbol.insertar(arbol.getRaiz(), "Ana", 150, 71, 100);
        arbol.insertar(arbol.getRaiz(), "Maria", 200, 67, 100);
        arbol.insertar(arbol.getRaiz(), "Simon", 250, 69, 100);
        arbol.insertar(arbol.getRaiz(), "Rosa", 400, 41, 300);
        arbol.insertar(arbol.getRaiz(), "Sara", 550, 47, 300);
        arbol.insertar(arbol.getRaiz(), "Hugo", 350, 47, 200);
        arbol.insertar(arbol.getRaiz(), "Paco", 600, 47, 200);
        arbol.insertar(arbol.getRaiz(), "Laura", 450, 47, 200);
        arbol.insertar(arbol.getRaiz(), "Luis", 750, 47, 200);
        arbol.insertar(arbol.getRaiz(), "Nora", 700, 47, 350);
        arbol.insertar(arbol.getRaiz(), "Lola", 500, 47, 350);
        arbol.insertar(arbol.getRaiz(), "Pablo", 650, 47, 350);
    }
}