package _1SpringData._1SpringData._01DBAppsIntroduction._2Exercise._09IncreaseAgeStoredProcedure;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

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

        System.out.print("Enter minions ID: ");

        int minionsId = Integer.parseInt(scanner.nextLine());

        updateMinionAge(connection, minionsId);
        printOutput(connection, minionsId);
    }

    private static void printOutput(Connection connection, int minionsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "m.name, " +
                        "m.age  " +
                        "FROM minions_db.minions AS m " +
                        "WHERE m.id = ?;");
        preparedStatement.setInt(1, minionsId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("Name: %s, Age: %d%n", resultSet.getString("m.name"), resultSet.getInt("m.age"));
        }
    }

    private static void updateMinionAge(Connection connection, int minionsId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(
                "CALL minions_db.usp_get_older(?)");
        callableStatement.setInt(1, minionsId);
        callableStatement.executeUpdate();
    }


}
