package _1SpringData._1SpringData._01DBAppsIntroduction._2Exercise._04AddMinion;

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

        String minionInformation = scanner.nextLine();
        String minionName = minionInformation.split(" ")[1];
        int minionAge = Integer.parseInt(minionInformation.split(" ")[2]);
        String townName = minionInformation.split(" ")[3];

        String villainInformation = scanner.nextLine();
        String villainName = villainInformation.split(" ")[1];

        int townId = findTownIdByTownName(connection, townName);
        if (townId == 0) {
            townId = createTown(connection, townName);
            System.out.printf("Town %s was added to the database.%n",townName);
        }

        int minionId = createMinion(connection, minionName, minionAge, townId);

        int villainId = findVillainIdByVillainName(connection, villainName);
        if (villainId == 0) {
            villainId = createVillain(connection, villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        populateMinionsVillains(connection, minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
    }

    private static void populateMinionsVillains(Connection connection, int minionId, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions_db.minions_villains(minion_id, villain_id) " +
                        "VALUE (?, ?)");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();
    }

    private static int createVillain(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions_db.villains(name, evilness_factor) " +
                        "VALUE (?, ?)");
        preparedStatement.setString(1, villainName);
        preparedStatement.setString(2, "evil");
        preparedStatement.executeUpdate();

        return findVillainIdByVillainName(connection, villainName);
    }

    private static int findVillainIdByVillainName(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "    v.id " +
                        "FROM minions_db.villains AS v " +
                        "WHERE v.name = ?;");

        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("v.id");
        }
        return 0;
    }

    private static int createMinion(Connection connection, String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions_db.minions(name, age, town_id) " +
                        "VALUE (?, ?, ?)");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();

        return findMinionIdByMinionName(connection, minionName);
    }

    private static int findMinionIdByMinionName(Connection connection, String minionName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "    m.id " +
                        "FROM minions_db.minions AS m " +
                        "WHERE m.name = ?;");

        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("m.id");
        }
        return 0;
    }

    private static int createTown(Connection connection, String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions_db.towns(name) " +
                        "VALUE (?)");

        preparedStatement.setString(1, townName);
        preparedStatement.executeUpdate();

        return findTownIdByTownName(connection, townName);
    }

    private static Integer findTownIdByTownName(Connection connection, String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "    t.id " +
                        "FROM minions_db.towns AS t " +
                        "WHERE t.name = ?;");

        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("t.id");
        }
        return 0;
    }
}
