package _8SpringData._1SpringData._01DBAppsIntroduction._2Exercise._06RemoveVillain;

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

        System.out.print("Enter Villain ID: ");
        int villainId = Integer.parseInt(scanner.nextLine());

        String villainName = findVillainNameByVillainId(connection, villainId);

        if (villainName.isEmpty()) {
            System.out.printf("No such villain was found%n");
        } else {
            int releaseMinionsCount = releaseMinions(connection, villainId);
            deleteVillain(connection, villainId);
            System.out.printf("%s was deleted%n", villainName);
            System.out.printf("%d minions released%n", releaseMinionsCount);
        }
    }

    private static void deleteVillain(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM minions_db.villains AS v " +
                        "WHERE v.id = ?;");
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();
    }

    private static int releaseMinions(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM minions_db.minions_villains AS mv " +
                        "WHERE mv.villain_id = ?;");
        preparedStatement.setInt(1, villainId);
        return preparedStatement.executeUpdate();
    }

    private static String findVillainNameByVillainId(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "    v.name " +
                        "FROM minions_db.villains AS v " +
                        "WHERE v.id = ?;");

        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("v.name");
        }
        return "";
    }
}
