package _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/%s";
    private static Connection connection;

    private MyConnector() {
    }

    public static void createConnection(String username, String password, String dbName) throws SQLException {
        String jdbcString = String.format(CONNECTION_STRING, dbName);

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(jdbcString, properties);
    }

    public static Connection getConnection() {
        return connection;
    }

}
