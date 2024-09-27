package _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise._11FindEmployeesByFirstName;

import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
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

        String letters = SCANNER.nextLine();

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE firstName LIKE CONCAT(:letters, '%')", Employee.class)
                .setParameter("letters", letters)
                .getResultList();

        if (!resultList.isEmpty()) {
            for (Employee employee : resultList) {
                System.out.printf("%s %s - %s - ($%.2f)%n",employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary());
            }
        } else {
            System.out.printf("Employee didnt exists%n");
        }

        entityManagerFactory.close();
    }
}
