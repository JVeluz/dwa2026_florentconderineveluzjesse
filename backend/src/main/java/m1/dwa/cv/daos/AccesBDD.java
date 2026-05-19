package m1.dwa.cv.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesBDD {
    private static Connection conn;

    public static Connection connexionSGBD() {
        try {
            // connexion au schéma « dwa » avec utilisateur « root »
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dwa", "root", "root");
        }
        catch (SQLException ex) {
            System.err.println("Problème de connexion :" + ex) ;
        }
        return conn;
    }
}
