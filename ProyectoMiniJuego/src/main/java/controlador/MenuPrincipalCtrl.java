package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.MenuPrincipal;

/**
 *
 * @author javie
 */
public class MenuPrincipalCtrl implements ActionListener { // interfaz ActionListener

    private final MenuPrincipal vista_mp;

    public MenuPrincipalCtrl() {
        this.vista_mp = new MenuPrincipal();
        this.vista_mp.btnJugar.addActionListener(this);
        this.vista_mp.btnJugadores.addActionListener(this);
        this.vista_mp.btnEstadisticas.addActionListener(this);
        this.vista_mp.btnSalir.addActionListener(this);
    }

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_mp.setTitle("Juego de Penaltis");
        vista_mp.setVisible(true);
        vista_mp.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista

        // JUGAR
        if (e.getSource() == vista_mp.btnJugar) {
            vista_mp.setVisible(false);
            SeleccionModoCtrl ctrl_sm = new SeleccionModoCtrl();
            ctrl_sm.iniciar();
        }

        // GESTIÓN JUGADORES
        if (e.getSource() == vista_mp.btnJugadores) {
            vista_mp.setVisible(false);
            JugadorCtrl ctrl_aj = new JugadorCtrl();
            ctrl_aj.iniciar();
        }

        // ESTADÍSTICAS
        if (e.getSource() == vista_mp.btnEstadisticas) {
            vista_mp.setVisible(false);
            EstadisticasCtrl ctrl_est = new EstadisticasCtrl();
            ctrl_est.iniciar();
        }

        // SALIR
        if (e.getSource() == vista_mp.btnSalir) {
            System.exit(0);
        }
    }
}
