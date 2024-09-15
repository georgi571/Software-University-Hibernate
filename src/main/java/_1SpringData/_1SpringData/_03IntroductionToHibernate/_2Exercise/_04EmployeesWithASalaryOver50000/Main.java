package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._04EmployeesWithASalaryOver50000;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s%n", employee.getFirstName());
        }
        entityManagerFactory.close();
    }
}
