package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ConsultasBD;
import vista.SeleccionJugadores;

/**
 *
 * @author javie
 */
public class SeleccionJugadoresCtrl implements ActionListener { // interfaz ActionListener

    private final SeleccionJugadores vista_selJ;
    private final ConsultasBD modelo_consultas;

    public SeleccionJugadoresCtrl() {
        this.vista_selJ = new SeleccionJugadores();
        this.modelo_consultas = new ConsultasBD();
        this.vista_selJ.btnComenzar.addActionListener(this);
        this.vista_selJ.btnVolver.addActionListener(this);
    }

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_selJ.setTitle("Seleccionar Jugadores");
        vista_selJ.setVisible(true);
        vista_selJ.setLocationRelativeTo(null);
        // Rellenamos los dos ComboBox con los jugadores de la BD
        modelo_consultas.rellenarComboBoxJugadores2(vista_selJ);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista

        // COMENZAR PARTIDA PVP
        if (e.getSource() == vista_selJ.btnComenzar) {
            String jugador1 = vista_selJ.ComboBoxJugador1.getSelectedItem().toString();
            String jugador2 = vista_selJ.ComboBoxJugador2.getSelectedItem().toString();
            
            // Comprobar que no sean el mismo jugador
            if (jugador1.equals(jugador2)) {
               JOptionPane.showMessageDialog(null, "Los dos jugadores no pueden ser el mismo", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                vista_selJ.setVisible(false);
                JuegoCtrl ctrl_juego = new JuegoCtrl(jugador1, jugador2, "PVP");
                ctrl_juego.iniciar();
            }
        }

        // VOLVER A SELECCION DE MODO
        if (e.getSource() == vista_selJ.btnVolver) {
            vista_selJ.setVisible(false);
            SeleccionModoCtrl ctrl_sm = new SeleccionModoCtrl();
            ctrl_sm.iniciar();
        }
    }
}
