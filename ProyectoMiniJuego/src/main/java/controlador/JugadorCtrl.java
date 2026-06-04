package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Jugador;
import modelo.ConsultasBD;
import vista.AñadirJugador;
import modelo.Equipo;

/**
 *
 * @author javie
 */
public class JugadorCtrl implements ActionListener { // interfaz ActionListener

    private final Jugador modelo_jugador;
    private final ConsultasBD modelo_consultas;
    private final AñadirJugador vista_aj;

    // Constructor para inicializar todo
    public JugadorCtrl() {
        this.modelo_jugador = new Jugador();
        this.modelo_consultas = new ConsultasBD();
        this.vista_aj = new AñadirJugador();
        this.vista_aj.btnAddJugador1.addActionListener(this);
        this.vista_aj.btnBorrarJugador.addActionListener(this);
        this.vista_aj.btnModJugador.addActionListener(this);
        this.vista_aj.btnVolver.addActionListener(this);
    }

    public void iniciar() { // Método para iniciar la ventana de ahora
        vista_aj.setTitle("Gestion Jugadores");
        vista_aj.setVisible(true); // sea visible
        vista_aj.setLocationRelativeTo(null);
        // Rellenamos el ComboBox con los equipos de la BD
        modelo_consultas.rellenarComboBox(vista_aj);
    }

    @Override // Aqui van a estar todas las acciones que hagas con el ratón en la vista
    public void actionPerformed(ActionEvent e) {

        // Botón Añadir jugador
        if (e.getSource() == vista_aj.btnAddJugador1) {
            modelo_jugador.setNombre(vista_aj.txtNombre.getText()); // Sascar el nombre que puso el usuario
            modelo_jugador.setDorsal(Integer.parseInt(vista_aj.txtDorsal.getText()));
            Equipo equipoSeleccionado = (Equipo) vista_aj.jComboBoxEquiposJug.getSelectedItem();
            System.out.println("ID equipo: " + equipoSeleccionado.getIdEquipo()); // Salga en la consola el ID Del equipo
            modelo_jugador.setIdEquipo(equipoSeleccionado.getIdEquipo()); // Guardar el id del equipo para la consulta

            if (modelo_consultas.addJugador(modelo_jugador)) { // para que abra una ventana si ha sido añadido o ha dado error
                JOptionPane.showMessageDialog(null, "Jugador añadido correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el jugador", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Botón Borrar Jugador
        if (e.getSource() == vista_aj.btnBorrarJugador) {
            String nombre = vista_aj.txtNombre.getText(); // Obtenemos el nombre q puso el usuario
            int dorsal = Integer.parseInt(vista_aj.txtDorsal.getText());
            Equipo equipoSeleccionado = (Equipo) vista_aj.jComboBoxEquiposJug.getSelectedItem();

            Jugador j = modelo_consultas.buscarJugador(nombre, dorsal, equipoSeleccionado.getIdEquipo());

            if (j == null) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún jugador con esos datos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int conf = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que quieres borrar a " + j.getNombre() + "?",
                    "Confirmar borrado", JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                if (modelo_consultas.borrarJugador(j.getIdJugador())) {
                    JOptionPane.showMessageDialog(null, "Jugador borrado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al borrar el jugador", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Botón Modificar Jugador
        if (e.getSource() == vista_aj.btnModJugador) {
            int dorsal = Integer.parseInt(vista_aj.txtDorsal.getText()); // Obtenemos el dorsal q puso el usuario
            Equipo equipoSeleccionado = (Equipo) vista_aj.jComboBoxEquiposJug.getSelectedItem();

            Jugador j = modelo_consultas.buscarJugadorPorDorsalYEquipo(dorsal, equipoSeleccionado.getIdEquipo());

            if (j == null) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún jugador con ese dorsal y equipo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Pedimos los nuevos datos
            String nuevoNombre = (String) JOptionPane.showInputDialog(null, "Nuevo nombre:", "Modificar Jugador",
                    JOptionPane.PLAIN_MESSAGE, null, null, j.getNombre());
            if (nuevoNombre == null) {
                return; // canceló
            }
            String nuevoDorsalStr = (String) JOptionPane.showInputDialog(null, "Nuevo dorsal:", "Modificar Jugador",
                    JOptionPane.PLAIN_MESSAGE, null, null, j.getDorsal());
            if (nuevoDorsalStr == null) {
                return; // canceló
            }
            
            // Pedimos el nuevo equipo con la combobox
            // Añadimos los datos en la array de equipos
            Equipo[] equipos = modelo_consultas.getEquipos();
            Equipo nuevoEquipo = (Equipo) JOptionPane.showInputDialog(null, "Nuevo equipo:", "Modificar Jugador",
                    JOptionPane.PLAIN_MESSAGE, null, equipos, equipoSeleccionado);
            if (nuevoEquipo == null) {
                return; // canceló
            }
            // Actualizamos el jugador
            j.setNombre(nuevoNombre);
            j.setDorsal(Integer.parseInt(nuevoDorsalStr));
            j.setIdEquipo(nuevoEquipo.getIdEquipo());

            if (modelo_consultas.modJugador(j)) {
                JOptionPane.showMessageDialog(null, "Jugador modificado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el jugador", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Botón volver
        if (e.getSource() == vista_aj.btnVolver) {
            vista_aj.setVisible(false);
            MenuPrincipalCtrl mp_ctrl = new MenuPrincipalCtrl();
            mp_ctrl.iniciar();
        }
    }
}
