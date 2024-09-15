package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._03ContainsEmployee;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String[] input = SCANNER.nextLine().split("\\s+");

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE firstName = :first_name AND lastName = :last_name", Employee.class)
                .setParameter("first_name", input[0])
                .setParameter("last_name", input[1])
                .getResultList();

        if (!resultList.isEmpty()) {
            System.out.printf("Yes%n");
        } else {
            System.out.printf("No%n");
        }
        entityManagerFactory.close();
    }
}
