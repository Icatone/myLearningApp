package another;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static private Connection connection;
    public DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            checkOrCreateConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            checkOrCreateConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    private void checkOrCreateConnection() throws SQLException {
        if(connection==null||!connection.isValid(5)){
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webcardsitedb","joe","MqR_Gh1u");
        }
    }
}
