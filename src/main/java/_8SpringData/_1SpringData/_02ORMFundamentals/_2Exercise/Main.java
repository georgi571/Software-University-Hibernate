package _8SpringData._1SpringData._02ORMFundamentals._2Exercise;

import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.entities.Order;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.entities.User;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.EntityManager;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("UserName: ");
        String user = scanner.nextLine();
        if (user.isEmpty()) {
            user = "root";
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();

        MyConnector.createConnection(user, password, "mini_orm");
        Connection connection = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
        userEntityManager.doCreate(User.class);
        User georgi = new User("georgi", 31, LocalDate.now());
        georgi.setEmail("test@abv.bg");
        userEntityManager.doAlter(georgi);
        userEntityManager.persist(georgi);

        Iterable<User> users = userEntityManager.find(User.class, "age > 30");
        for (User currentUser : users) {
            System.out.printf("%s", currentUser);
        }

        User userToDelete = userEntityManager.findFirst(User.class);
        userEntityManager.delete(userToDelete);

        EntityManager<Order> orderEntityManager = new EntityManager<>(connection);
//        orderEntityManager.doCreate(Order.class);
        Order order = new Order("mn123b4", LocalDate.now());
        orderEntityManager.persist(order);

        Iterable<Order> orders = orderEntityManager.find(Order.class);
        for (Order currentOrder : orders) {
            System.out.printf("%s", currentOrder);
        }
    }
}
