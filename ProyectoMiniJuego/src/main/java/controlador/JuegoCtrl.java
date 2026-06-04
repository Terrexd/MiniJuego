package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import modelo.ConsultasBD;
import vista.Juego;

/**
 *
 * @author javie
 */
public class JuegoCtrl implements ActionListener { // interfaz ActionListener

    private final Juego vista_juego;
    private final ConsultasBD modelo_consultas;

    // Datos de los jugadores
    private final String jugador1;
    private final String jugador2;
    private final String modo;

    // Variables del juego
    private int golesJugador1 = 0;
    private int golesJugador2 = 0;
    private int penaltiActual = 1;
    private int zonaElegidaTirador = -1;
    private boolean esperandoPortero = false;
    private static final int TOTAL_PENALTIS = 5;

    // Constructor con parámetros
    public JuegoCtrl(String jugador1, String jugador2, String modo) {
        this.vista_juego = new Juego();
        this.modelo_consultas = new ConsultasBD();
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.modo = modo;
        this.vista_juego.btnZona1.addActionListener(this);
        this.vista_juego.btnZona2.addActionListener(this);
        this.vista_juego.btnZona3.addActionListener(this);
        this.vista_juego.btnZona4.addActionListener(this);
        this.vista_juego.btnZona5.addActionListener(this);
    }
    

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_juego.setTitle("Juego de Penaltis");
        vista_juego.setVisible(true);
        vista_juego.setLocationRelativeTo(null);
        actualizarVista();
    }

    // Actualiza los textos del juego
    private void actualizarVista() {
        if (!esperandoPortero) {
            // Le toca al tirador
            vista_juego.lblTurno1.setText("Turno de: " + jugador1 + " (Tirador)");
        } else {
            // Le toca al portero
            if (modo.equals("CPU")) {
                vista_juego.lblTurno1.setText("Turno de: CPU (Portero)");
            } else {
                vista_juego.lblTurno1.setText("Turno de: " + jugador2 + " (Portero)");
            }
        }
        
        vista_juego.lblMarcador.setText(golesJugador1 + " - " + golesJugador2);
        vista_juego.lblPenalti.setText("Penalti " + penaltiActual + " de " + TOTAL_PENALTIS);
        vista_juego.lblResultado.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista
        int zonaElegida = 0;

        // Ponemos en que boton le ha dado el usuario
        if (e.getSource() == vista_juego.btnZona1) {
            zonaElegida = 1;
        }
        if (e.getSource() == vista_juego.btnZona2) {
            zonaElegida = 2;
        }
        if (e.getSource() == vista_juego.btnZona3) {
            zonaElegida = 3;
        }
        if (e.getSource() == vista_juego.btnZona4) {
            zonaElegida = 4;
        }
        if (e.getSource() == vista_juego.btnZona5) {
            zonaElegida = 5;
        }

        if (!esperandoPortero) {
            // TURNO DEL TIRADOR
            zonaElegidaTirador = zonaElegida;
            esperandoPortero = true;

            if (modo.equals("CPU")) {
                // La CPU elige zona aleatoria
                int zonaCPU = new Random().nextInt(5) + 1;
                resolverPenalti(zonaElegidaTirador, zonaCPU);
            } else {
                // Esperamos a que el portero elija
                actualizarVista();
            }
        } else {
            // TURNO DEL PORTERO (solo en PVP)
            resolverPenalti(zonaElegidaTirador, zonaElegida);
        }
    }

    // Si es gol o ha parado el portero
    private void resolverPenalti(int zonaTirador, int zonaPortero) {
        if (zonaTirador == zonaPortero) {
            // PARADO
            vista_juego.lblResultado.setText("¡PARADO!");
            golesJugador2++;
        } else {
            // GOL
            vista_juego.lblResultado.setText("¡GOL!");
            golesJugador1++;
        }

        penaltiActual++;
        esperandoPortero = false;

        // Comprobamos si ha terminado la partida
        if (penaltiActual > TOTAL_PENALTIS) {
            finPartida();
        } else {
            actualizarVista();
        }
    }

    // Fin de la partida
    private void finPartida() {
        String ganador;
        if (golesJugador1 > golesJugador2) {
            ganador = jugador1;
        } else if (golesJugador2 > golesJugador1) {
            ganador = modo.equals("CPU") ? "CPU" : jugador2;
        } else {
            ganador = "Empate";
        }

        // Mostramos el resultado final
        JOptionPane.showMessageDialog(null, "Fin de la partida!\n" + jugador1 + ": " + golesJugador1 + " goles\n" + (modo.equals("CPU") ? "CPU" : jugador2) + ": " + golesJugador2 + " goles\n"
        + "Ganador: " + ganador,
        "Resultado Final",
        JOptionPane.INFORMATION_MESSAGE);

        // Actualizar estadísticas jugador 1 (tirador)
        modelo_consultas.actualizarEstadisticas(jugador1, golesJugador1, 0, golesJugador1 > golesJugador2);

    // Actualizar estadísticas jugador 2 (portero)
        if (!modo.equals("CPU")) {
            modelo_consultas.actualizarEstadisticas(jugador2, 0, golesJugador2, golesJugador2 > golesJugador1);
        }
        // Guardamos la partida en la BD
        modelo_consultas.guardarPartida(jugador1, jugador2, modo, ganador);

        // Volvemos al menú principal
        vista_juego.setVisible(false);
        MenuPrincipalCtrl ctrl_mp = new MenuPrincipalCtrl();
        ctrl_mp.iniciar();
    }
}
