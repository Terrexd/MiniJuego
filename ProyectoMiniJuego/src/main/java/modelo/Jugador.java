package modelo;

/**
 *
 * @author javie
 */
public class Jugador {
    // Atributos
    
    private int idJugador;
    private String nombre;
    private int dorsal;
    private int idEquipo;
    
    // Constructor vacio

    public Jugador() {
        this.idJugador = 0;
        this.nombre = "";
        this.dorsal = 0;
        this.idEquipo = 0;
    }
    
    // Constructor con parámetros
    public Jugador(int idJugador, String nombre, int dorsal, int idEquipo) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.idEquipo = idEquipo;
    }
    
    // Getters y Setters
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    
}
