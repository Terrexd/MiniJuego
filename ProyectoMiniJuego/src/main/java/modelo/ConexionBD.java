package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author javie
 */

public class ConexionBD {

    private final String bd = "penaltis_db";
    private final String user = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost/" + bd;
    private Connection con = null;

    public Connection getConexion() {
        try {
            con = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return con;
    }
}

