package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import modelo.Jugador;
import modelo.ConsultasBD;
import vista.AñadirJugador;
/**
 *
 * @author javie
 */
public class ListadoJugadoresCtrl implements ActionListener { // interfaz ActionListener

    private final ConsultasBD modelo_consultas;
    private final AñadirJugador vista_aj;
    public static LinkedList<Jugador> miListaDeJugadores;
    int indice;

    public ListadoJugadoresCtrl() {
        this.modelo_consultas = new ConsultasBD();
        this.vista_aj = new AñadirJugador();
        ListadoJugadoresCtrl.miListaDeJugadores = new LinkedList<>();
        this.indice = 0;
        this.vista_aj.btnVolver.addActionListener(this);
    }

    public void iniciar() {  // Método para iniciar la ventana de ahora
        vista_aj.setTitle("Listado de Jugadores");
        vista_aj.setVisible(true);
        vista_aj.setLocationRelativeTo(null);
        modelo_consultas.listarJugadores(); // llamar a la lista de jugadores de la BD

        if (!miListaDeJugadores.isEmpty()) {
            vista_aj.txtNombre.setText(miListaDeJugadores.get(indice).getNombre()); // añade el nombre desde el indice 0
            vista_aj.txtDorsal.setText(String.valueOf(miListaDeJugadores.get(indice).getDorsal()));
            vista_aj.jComboBoxEquiposJug.setSelectedItem(miListaDeJugadores.get(indice).getIdEquipo());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Aqui van a estar todas las acciones que hagas con el ratón en la vista

        // VOLVER AL MENÚ PRINCIPAL
        if (e.getSource() == vista_aj.btnVolver) {
            vista_aj.setVisible(false);
            MenuPrincipalCtrl ctrl_mp = new MenuPrincipalCtrl();
            ctrl_mp.iniciar();
        }
    }
}
