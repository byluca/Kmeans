package DB;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;
import DB.DatabaseConnectionException;


public class DbAccess {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DBMS = "jdbc:mysql";
    private static final String SERVER = "localhost";
    private static final String DATABASE = "MapDB";
    private static final int PORT = 3306;
    private static final String USER_ID = "MapUser";
    private static final String PASSWORD = "map";
    private Connection conn;

    public void initConnection() throws DatabaseConnectionException {
        try {
            String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;
            Class.forName(DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(url, USER_ID, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Errore durante la connessione al database.");
        }
    }
    public Connection getConnection() {
        return conn;
    }
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // Gestione dell'eccezione in caso di errore durante la chiusura della connessione
            e.printStackTrace();
        }
    }
}