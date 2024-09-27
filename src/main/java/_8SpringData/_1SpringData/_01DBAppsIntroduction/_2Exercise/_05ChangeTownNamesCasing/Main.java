package _8SpringData._1SpringData._01DBAppsIntroduction._2Exercise._05ChangeTownNamesCasing;

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

        System.out.print("Enter Country name: ");
        String countryName = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE minions_db.towns AS t " +
                        "SET t.name = UPPER(t.name) " +
                        "WHERE country = ?;");

        preparedStatement.setString(1, countryName);
        int updatedRows = preparedStatement.executeUpdate();

        if (updatedRows > 0) {
            System.out.printf("3 town names were affected.%n");

            preparedStatement = connection.prepareStatement(
                    "SELECT t.name " +
                            "FROM minions_db.towns AS t " +
                            "WHERE country = ?;");
            preparedStatement.setString(1, countryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> townNames = new ArrayList<>();
            while (resultSet.next()) {
                townNames.add(resultSet.getString("t.name"));
            }

            System.out.printf("%s%n", String.join(", ", townNames));
        } else {
            System.out.printf("No town names were affected.%n");
        }


    }
}
