package _8SpringData._1SpringData._01DBAppsIntroduction._1Lab._01SoftUni;

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

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM soft_uni.employees WHERE salary > ? LIMIT 10");
        preparedStatement.setDouble(1, 17000.00);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");

            System.out.printf("%s %s%n", firstName, lastName);
        }
    }
}
