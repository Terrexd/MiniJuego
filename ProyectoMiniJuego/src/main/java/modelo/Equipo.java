package modelo;

/**
 *
 * @author javie
 */
public class Equipo {

    // Atributos
    private int idEquipo;
    private String nombre;

    // Constructor vacio
    public Equipo() {
        this.idEquipo = 0;
        this.nombre = "";
    }

    // Constructor con parámetros
    public Equipo(int idEquipo, String nombre) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
