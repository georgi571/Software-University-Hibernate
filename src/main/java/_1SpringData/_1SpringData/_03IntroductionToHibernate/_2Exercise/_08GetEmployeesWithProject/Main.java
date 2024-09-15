package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._08GetEmployeesWithProject;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        int employeeId = Integer.parseInt(SCANNER.nextLine());

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE id = :employee_id", Employee.class)
                .setParameter("employee_id", employeeId)
                .getResultList();

        if (!resultList.isEmpty()) {
            Employee employee = resultList.get(0);
            System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
            employee.getProjects()
                    .stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(project -> System.out.printf("      %s%n",project.getName()));
        } else {
            System.out.printf("Employee didnt exists%n");
        }

        entityManagerFactory.close();
    }
}
