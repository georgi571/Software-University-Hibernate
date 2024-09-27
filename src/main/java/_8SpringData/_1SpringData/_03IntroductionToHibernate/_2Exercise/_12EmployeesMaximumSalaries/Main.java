package _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise._12EmployeesMaximumSalaries;

import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Department;
import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Department> resultList = entityManager
                .createQuery("FROM Department ", Department.class)
                .getResultList();

        for (Department department : resultList) {
            Set<Employee> employees = department.getEmployees();
            BigDecimal salary = employees.stream()
                    .map(Employee::getSalary)
                    .max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
            if (salary.compareTo(BigDecimal.valueOf(30000)) < 0 || salary.compareTo(BigDecimal.valueOf(70000)) > 0) {
                System.out.printf("%s %.2f%n", department.getName(), salary);
            }
        }

        entityManagerFactory.close();
    }
}
