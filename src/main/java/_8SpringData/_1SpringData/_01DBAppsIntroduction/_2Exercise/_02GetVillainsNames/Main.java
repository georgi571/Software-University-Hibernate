package _8SpringData._1SpringData._01DBAppsIntroduction._2Exercise._02GetVillainsNames;

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

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT " +
                        "    v.name," +
                        "    COUNT(mv.minion_id) AS number_of_minions " +
                        "FROM minions_db.villains AS v " +
                        "    JOIN minions_db.minions_villains AS mv ON v.id = mv.villain_id " +
                        "GROUP BY v.name " +
                        "HAVING number_of_minions > 15 " +
                        "ORDER BY number_of_minions DESC;");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String villainsName = resultSet.getString("v.name");
            int numberOfMinions = resultSet.getInt("number_of_minions");

            System.out.printf("%s %d%n", villainsName, numberOfMinions);
        }
    }
}
