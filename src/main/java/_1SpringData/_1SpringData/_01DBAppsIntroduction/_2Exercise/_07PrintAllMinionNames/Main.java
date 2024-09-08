package _1SpringData._1SpringData._01DBAppsIntroduction._2Exercise._07PrintAllMinionNames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        List<String> minionNames = collectAllMinionNames(connection);
        while (minionNames.size() > 1) {
            System.out.printf("%s%n", minionNames.remove(0));
            System.out.printf("%s%n", minionNames.remove(minionNames.size() - 1));
        }
        if (minionNames.size() == 1) {
            System.out.printf("%s%n", minionNames.remove(0));
        }
    }

    private static List<String> collectAllMinionNames(Connection connection) throws SQLException {
        List<String> minionNames = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT m.name " +
                        "FROM minions_db.minions AS m;");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            minionNames.add(resultSet.getString("m.name"));
        }
        return minionNames;
    }
}
