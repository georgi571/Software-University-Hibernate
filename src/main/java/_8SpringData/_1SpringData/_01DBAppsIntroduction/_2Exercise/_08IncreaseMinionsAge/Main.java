package _8SpringData._1SpringData._01DBAppsIntroduction._2Exercise._08IncreaseMinionsAge;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("UserName: ");
        String user = scanner.nextLine();
        if (user.isEmpty()) {
            user = "root";
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Properties credential = new Properties();
        credential.setProperty("user", user);
        credential.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", credential);

        System.out.print("Enter minions IDs: ");

        String minionsId = scanner.nextLine().replace(" ", ", ");

        updateMinionsNamesAndAges(connection, minionsId);
        printOutput(connection);
    }

    private static void printOutput(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "m.name, " +
                        "m.age  " +
                        "FROM minions_db.minions AS m;");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("m.name"), resultSet.getInt("m.age"));
        }
    }

    private static void updateMinionsNamesAndAges(Connection connection, String minionsId) throws SQLException {
        String query = "UPDATE minions_db.minions AS m " +
                "SET m.name = LOWER(m.name)," +
                "   m.age = m.age + 1 " +
                "WHERE m.id IN (" + minionsId + ")";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
