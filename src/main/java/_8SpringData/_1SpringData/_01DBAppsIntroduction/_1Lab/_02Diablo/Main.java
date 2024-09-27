package _8SpringData._1SpringData._01DBAppsIntroduction._1Lab._02Diablo;

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

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT u.id, u.first_name, u.last_name, COUNT(ug.game_id) " +
                        "FROM diablo.users AS u " +
                        "JOIN diablo.users_games AS ug ON u.id = ug.user_id " +
                        "WHERE u.user_name = ? " +
                        "GROUP BY u.id");

        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        Object userId = resultSet.getObject("u.id");

        if (userId != null) {
            System.out.printf("User: %s%n%s %s has played %d games%n", username, resultSet.getString("u.first_name"),
                    resultSet.getString("u.last_name"), resultSet.getInt(4));
        } else {
            System.out.printf("No such user exist%n");
        }
    }
}
