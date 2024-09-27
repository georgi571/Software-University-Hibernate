package _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise._05EmployeesFromDepartment;

import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String departmentName = "Research and Development";

        int departmentId = (int) entityManager
                .createQuery("SELECT d.id FROM Department d WHERE name = :name")
                .setParameter("name", departmentName)
                .getSingleResult();

        List<Employee> resultList = entityManager
                .createQuery("SELECT e FROM Employee e JOIN e.department d WHERE d.id = :department_id ORDER BY e.salary, e.id", Employee.class)
                .setParameter("department_id", departmentId)
                .getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s %s from %s - $%s%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    departmentName,
                    employee.getSalary());
        }
        entityManagerFactory.close();
    }
}
