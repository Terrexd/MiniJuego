package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.SeleccionarModo;

/**
 *
 * @author javie
 */
public class SeleccionModoCtrl implements ActionListener { // interfaz ActionListener

    private final SeleccionarModo vista_sm;

    public SeleccionModoCtrl() {
        this.vista_sm = new SeleccionarModo();
        this.vista_sm.btnJugador.addActionListener(this);
        this.vista_sm.btnCpu.addActionListener(this);
        this.vista_sm.btnVolver.addActionListener(this);
    }

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_sm.setTitle("Seleccionar Modo");
        vista_sm.setVisible(true);
        vista_sm.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista

        // Jugar contra CPU
        if (e.getSource() == vista_sm.btnCpu) {
            vista_sm.setVisible(true);
            SeleccionCPUCtrl ctrl_cpu = new SeleccionCPUCtrl();
            ctrl_cpu.iniciar();
        }

        // Jugar contra Jugador
        if (e.getSource() == vista_sm.btnJugador) {
            vista_sm.setVisible(false);
            SeleccionJugadoresCtrl ctrl_j = new SeleccionJugadoresCtrl();
            ctrl_j.iniciar();
        }

        // Boton Volver
        if (e.getSource() == vista_sm.btnVolver) {
            vista_sm.setVisible(false);
            MenuPrincipalCtrl ctrl_mp = new MenuPrincipalCtrl();
            ctrl_mp.iniciar();
        }
    }
}
