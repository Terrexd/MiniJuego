package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasBD;
import vista.Estadisticas;

/**
 *
 * @author javie
 */
public class EstadisticasCtrl implements ActionListener { // interfaz ActionListener

    private final Estadisticas vista_est;
    private final ConsultasBD modelo_consultas;

    public EstadisticasCtrl() {
        this.vista_est = new Estadisticas();
        this.modelo_consultas = new ConsultasBD();
        this.vista_est.btnVolver.addActionListener(this);
    }

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_est.setTitle("Estadísticas");
        vista_est.setVisible(true);
        vista_est.setLocationRelativeTo(null);
        // Cargamos las estadísticas en la tabla
        cargarEstadisticas();
    }

    public void cargarEstadisticas() {
        // Limpiamos la tabla antes de cargar
        DefaultTableModel modelo = (DefaultTableModel) vista_est.tablaEstadisticas.getModel();
        modelo.setRowCount(0);
        // Cargamos los datos de la BD
        modelo_consultas.listarEstadisticas(vista_est);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista

        // boton volver al menu principal
        if (e.getSource() == vista_est.btnVolver) {
            vista_est.setVisible(false);
            MenuPrincipalCtrl ctrl_mp = new MenuPrincipalCtrl();
            ctrl_mp.iniciar();
        }
    }
}
