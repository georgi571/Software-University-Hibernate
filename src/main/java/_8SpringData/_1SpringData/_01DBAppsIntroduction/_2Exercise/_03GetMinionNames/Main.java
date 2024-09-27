package _8SpringData._1SpringData._01DBAppsIntroduction._2Exercise._03GetMinionNames;

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
        int id = Integer.parseInt(scanner.nextLine());

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "    v.id, " +
                        "    v.name, " +
                        "    m.name, " +
                        "    m.age " +
                        "FROM minions_db.villains AS v " +
                        "    JOIN minions_db.minions_villains AS mv ON v.id = mv.villain_id " +
                        "    JOIN minions_db.minions AS m ON m.id = mv.minion_id " +
                        "WHERE v.id = ?;");

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int number = 1;
            String villainsName = resultSet.getString("v.name");
            String minionName = resultSet.getString("m.name");
            int minionAge = resultSet.getInt("m.age");
            System.out.printf("Villain: %s%n", villainsName);
            System.out.printf("%d. %s %d%n", number++ ,minionName, minionAge);
            while (resultSet.next()) {
                minionName = resultSet.getString("m.name");
                minionAge = resultSet.getInt("m.age");

                System.out.printf("%d. %s %d%n", number++ ,minionName, minionAge);
            }
        } else {
            System.out.printf("No villain with ID %d exists in the database.%n", id);
        }
    }
}
