package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._6AddingANewAddressAndUpdatingTheEmployee;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Address;
import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Town;
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

        entityManager.getTransaction().begin();

        String input = SCANNER.nextLine();

        Town town = entityManager.find(Town.class, 32);
        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);
        entityManager.persist(address);

        List<Employee> resultList = entityManager
                .createQuery("FROM Employee WHERE lastName = :last_name", Employee.class)
                .setParameter("last_name", input)
                .getResultList();

        if (!resultList.isEmpty()) {
            Employee employee = resultList.get(0);
            employee.setAddress(address);
            entityManager.persist(employee);
        } else {
            System.out.printf("Employee didnt exists%n");
        }

        entityManager.getTransaction().commit();
        entityManagerFactory.close();
    }
}
