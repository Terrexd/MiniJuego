package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConsultasBD;
import vista.SeleccionCPU;

/**
 *
 * @author javie
 */
public class SeleccionCPUCtrl implements ActionListener { // interfaz ActionListener

    private final SeleccionCPU vista_selCpu;
    private final ConsultasBD modelo_consultas;
    
    public SeleccionCPUCtrl() {
        this.vista_selCpu = new SeleccionCPU();
        this.modelo_consultas = new ConsultasBD();
        this.vista_selCpu.btnComenzar.addActionListener(this);
        this.vista_selCpu.btnVolver.addActionListener(this);
    }

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_selCpu.setTitle("Jugar contra CPU");
        vista_selCpu.setVisible(true);
        vista_selCpu.setLocationRelativeTo(null);
        // Para rellenar el ComboBox de la BD
        modelo_consultas.rellenarComboBoxJugadores(vista_selCpu);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista

        // Comenzar partida contra cpu
        if (e.getSource() == vista_selCpu.btnComenzar) {
            // Cogemos el jugador seleccionado
            String jugador  = vista_selCpu.ComboBoxJugador.getSelectedItem().toString();
            vista_selCpu.setVisible(false);
            // Abrimos la pantalla
            JuegoCtrl ctrl_juego = new JuegoCtrl(jugador, "CPU", "CPU");
            ctrl_juego.iniciar();
        }

     

        // Boton Volver
        if (e.getSource() == vista_selCpu.btnVolver) {
            vista_selCpu.setVisible(false);
            MenuPrincipalCtrl ctrl_mp = new MenuPrincipalCtrl();
            ctrl_mp.iniciar();
        }
    }
}
