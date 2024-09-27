package _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise._10IncreaseSalaries;

import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee e JOIN e.department d WHERE d.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList();

        for (Employee employee : resultList) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
            entityManager.persist(employee);
            System.out.printf("%s %s ($%.2f)%n",employee.getFirstName(), employee.getLastName(), employee.getSalary());
        }

        entityManager.getTransaction().commit();

        entityManagerFactory.close();
    }
}
