package modelo;

import controlador.ListadoJugadoresCtrl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import vista.AñadirJugador;
import vista.Estadisticas;
import vista.SeleccionCPU;
import vista.SeleccionJugadores;
import modelo.Equipo;

/**
 *
 * @author javie
 */
public class ConsultasBD extends ConexionBD {

    // Añadir jugador a la BBDD
    public boolean addJugador(Jugador j) {
        Connection con = getConexion();
        String sql = "INSERT INTO JUGADORES(NOMBRE, DORSAL, ID_EQUIPO) VALUES (?,?,?)";

        try {
            // Añadir al jugador mediante consulta
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, j.getNombre());
            ps.setInt(2, j.getDorsal());
            ps.setInt(3, j.getIdEquipo());
            // Para que se actualice con el executeUpdate y se guarden las filas afectadas
            System.out.println("SQL: INSERT jugador - nombre=" + j.getNombre() + " dorsal=" + j.getDorsal() + " idEquipo=" + j.getIdEquipo());
            int rowsInserted = ps.executeUpdate();
            System.out.println("FIlas afectadas: " + rowsInserted);
            
            // Insertar fila de estadísticas para el nuevo jugador
            String sqlStats = "INSERT INTO estadisticas (id_jugador) VALUES (LAST_INSERT_ID())";
            PreparedStatement psStats = con.prepareStatement(sqlStats);
            psStats.executeUpdate();
            psStats.close();
            // Cerramos para que funcione
            ps.close();
            con.close();
            // Devolvemos el true por que ha funcionado
            return true;
        } catch (Exception e) {
            // Si hay algun error que se muestre y nos de false
            System.out.println(e);
            return false;
        }
    }

    // Borrar Jugador por ID
    public Jugador buscarJugador(String nombre, int dorsal, int idEquipo) {
        Connection con = getConexion();
        String sql = "SELECT id_jugador, nombre, dorsal, id_equipo FROM jugadores WHERE nombre = ? AND dorsal = ? AND id_equipo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, dorsal);
            ps.setInt(3, idEquipo);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return new Jugador(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4));
            }
            
            res.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean borrarJugador(int idJugador) {
        Connection con = getConexion();
        String sql = "DELETE FROM JUGADORES WHERE id_jugador = ?";

        try {
            // Preparar el jugador borrado con los datos de la consulta de arriba
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idJugador);
            // Para que se actualice con el executeUpdate y se guarden las filas afectadas

            int rowsInserted = ps.executeUpdate();
            System.out.println("FIlas afectadas: " + rowsInserted);
            // Cerramos para que funcione
            ps.close();
            con.close();
            // Devolvemos el true por que ha funcionado
            return true;
        } catch (Exception e) {
            // Si hay algun error que se muestre y nos de false
            System.out.println(e);
            return false;
        }
    }

    public Jugador buscarJugadorPorDorsalYEquipo(int dorsal, int idEquipo) {
        Connection con = getConexion();
        String sql = "SELECT id_jugador, nombre, dorsal, id_equipo FROM jugadores WHERE dorsal = ? AND id_equipo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dorsal);
            ps.setInt(2, idEquipo);
            ResultSet res = ps.executeQuery();
            if (res.next()) { // Si encuentra al jugador con ese dorsal y el equipo que devuelva todo del jugador
                return new Jugador(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4));
            }
            // Cerramos para que funcione
            res.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Equipo[] getEquipos() {
        Connection con = getConexion();
        String sql = "SELECT id_equipo, nombre FROM equipos ORDER BY nombre ASC";
        java.util.List<Equipo> lista = new java.util.ArrayList<>(); // Creamos una arraylist temporal
        try {
            Statement s = con.createStatement(); // Utilizamos el statement por que no tenemos las ?
            ResultSet res = s.executeQuery(sql);
            while (res.next()) {
                lista.add(new Equipo(res.getInt(1), res.getString(2))); // Mientras que haya jugadores que se vayan añadiendo a la lista
            }
            // Cerramos para que funcione
            res.close();
            s.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista.toArray(new Equipo[0]);
    }

    // Modificar Jugador por ID
    public boolean modJugador(Jugador j) {
        Connection con = getConexion();
        String sql = "UPDATE JUGADORES SET NOMBRE = ?, DORSAL = ?, ID_EQUIPO = ? WHERE ID_JUGADOR = ?";

        try {
            // Preparar el jugador actualizado con los datos de la consulta de arriba
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, j.getNombre());
            ps.setInt(2, j.getDorsal());
            ps.setInt(3, j.getIdEquipo());
            ps.setInt(4, j.getIdJugador());
            // Para que se actualice con el executeUpdate y se guarden las filas afectadas
            int rowsInserted = ps.executeUpdate();
            System.out.println("FIlas afectadas: " + rowsInserted);
            // Cerramos para que funcione
            ps.close();
            con.close();
            // Devolvemos el true por que ha funcionado
            return true;
        } catch (Exception e) {
            // Si hay algun error que se muestre y nos de false
            System.out.println(e);
            return false;
        }
    }

    // Listar todos los jugadores
    public void listarJugadores() {
        Connection con = getConexion();
        String sql = "SELECT * FROM JUGADORES";
        try {
            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(sql);

            while (res.next()) {
                int id = res.getInt(1);
                String nombre = res.getString(2);
                int dorsal = res.getInt(3);
                int equipo = res.getInt(4);
                // Creamos al jugador con los datos añadidos
                Jugador j = new Jugador(id, nombre, dorsal, equipo);
                // Y lo metemos al linkedlist de la clase ListadoJugadoresCtrl
                ListadoJugadoresCtrl.miListaDeJugadores.add(j);
            }
            res.close();
            s.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Rellenar combobox de EQUIPOS
    public void rellenarComboBox(AñadirJugador vista) {
        Connection con = getConexion();
        String sql = "SELECT id_equipo, nombre FROM EQUIPOS ORDER BY NOMBRE ASC";

        try {
            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(sql);

            while (res.next()) {
                // Creamos el objeto equipo con los datos del equipo ya metidos (id y nombre)
                Equipo eq = new Equipo(res.getInt(1), res.getString(2));
                // Y lo añadimos al ComboBox ya creado
                vista.jComboBoxEquiposJug.addItem(eq);
            }

            res.close();
            s.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Rellenar combobx JUGADORES VS CPU
    public void rellenarComboBoxJugadores(SeleccionCPU vista) {
        Connection con = getConexion();
        String sql = "SELECT NOMBRE FROM JUGADORES ORDER BY NOMBRE ASC";
        try {
            // Limpiar antes de rellenar
            vista.ComboBoxJugador.removeAllItems();
            
            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(sql);
            while (res.next()) {
                // Aqui solo le añadimos el nombre
                vista.ComboBoxJugador.addItem(res.getString(1));
            }
            res.close();
            s.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Rellenar los dos combobox de JUGADOR VS JUGADOR
    public void rellenarComboBoxJugadores2(SeleccionJugadores vista) {
        Connection con = getConexion();
        String sql = "SELECT nombre FROM jugadores ORDER BY nombre ASC";
        try {
            // Limpiar antes de rellenar
            vista.ComboBoxJugador1.removeAllItems();
            vista.ComboBoxJugador2.removeAllItems();

            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(sql);
            while (res.next()) {
                 // Aqui solo le añadimos el nombre de los 2 jugadores
                String nombre = res.getString(1);
                vista.ComboBoxJugador1.addItem(nombre);
                vista.ComboBoxJugador2.addItem(nombre);
            }
            res.close();
            s.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Listar estadísticas en la tabla
    public void listarEstadisticas(Estadisticas vista) {
        Connection con = getConexion();
        String sql = "SELECT j.nombre, e.partidas_jugadas, e.partidas_ganadas, "
                + "e.goles_marcados, e.goles_parados "
                + "FROM estadisticas e "
                + "JOIN jugadores j ON e.id_jugador = j.id_jugador "
                + "ORDER BY e.partidas_ganadas DESC";
        try {
            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(sql);
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaEstadisticas.getModel();
            while (res.next()) {
                Object[] fila = {
                    res.getString(1), // nombre
                    res.getInt(2), // partidas jugadas
                    res.getInt(3), // partidas ganadas
                    res.getInt(4), // goles marcados
                    res.getInt(5) // goles parados
                };
                modelo.addRow(fila);
            }
            res.close();
            s.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Guardar partida en la BD
    public void guardarPartida(String jugador1, String jugador2, String modo, String ganador) {
        Connection con = getConexion();
        String sql = "INSERT INTO partidas (id_jugador1, id_jugador2, modo, ganador) "
                + "VALUES ((SELECT id_jugador FROM jugadores WHERE nombre = ?), "
                + "(SELECT id_jugador FROM jugadores WHERE nombre = ?), ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, jugador1);
            // Si juegas contra la CPU, el jugador 2 se guarda como vacío (null) en la BD
            ps.setString(2, modo.equals("CPU") ? null : jugador2);
            ps.setString(3, modo);
            ps.setString(4, ganador);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Actualizar las estadisticas después del partido
    public void actualizarEstadisticas(String nombreJugador, int golesMarcados, int golesParados, boolean gano) {
    Connection con = getConexion();
    String sql = "UPDATE estadisticas SET " +
                 "partidas_jugadas = partidas_jugadas + 1, " +
                 "partidas_ganadas = partidas_ganadas + ?, " +
                 "goles_marcados = goles_marcados + ?, " +
                 "goles_parados = goles_parados + ? " +
                 "WHERE id_jugador = (SELECT id_jugador FROM jugadores WHERE nombre = ?)";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, gano ? 1 : 0); // Si ganó sumamos 1 a partidas ganadas, si perdió sumamos 0
        ps.setInt(2, golesMarcados);
        ps.setInt(3, golesParados);
        ps.setString(4, nombreJugador);
        ps.executeUpdate();
        ps.close();
        con.close();
    } catch (Exception e) {
        System.out.println(e);
    }
}
}
