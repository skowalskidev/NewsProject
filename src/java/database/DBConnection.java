package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        }
        
        System.out.println("PostgreSQL driver registered.");

        try {
            //conn = DriverManager.getConnection("jdbc:postgresql://mod-intro-databases/smk572", "smk572", pass);
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/NewsDB", "postgres", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;

    }
}
    
    