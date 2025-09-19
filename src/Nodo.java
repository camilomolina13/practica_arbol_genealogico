public class Nodo {
    private int sw;
    private Nodo ligaLista;
    private String nombre;
    private int cedula;
    private int edad;
    private Nodo liga;

    public Nodo(String nombre, int cedula, int edad) {
        this.sw = 0;
        this.ligaLista = null;
        this.nombre = nombre;
        this.cedula = cedula;
        this.edad = edad;
        this.liga = null;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public Nodo getLigaLista() {
        return ligaLista;
    }

    public void setLigaLista(Nodo ligaLista) {
        this.ligaLista = ligaLista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Nodo getLiga() {
        return liga;
    }

    public void setLiga(Nodo liga) {
        this.liga = liga;
    }

    public String toString() {
        return "- Nombre: " + this.getNombre() +
                ", cédula: " + this.getCedula() +
                ", edad: " + this.getEdad() + "\n";
    }

}
