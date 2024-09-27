package _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise._13RemoveTowns;

import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Address;
import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Employee;
import _8SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Town;
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

        String name = SCANNER.nextLine();

        List<Town> resultList = entityManager
                .createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name", name)
                .getResultList();

        if (!resultList.isEmpty()) {
            Town town = resultList.get(0);
            List<Address> resultListAddress = entityManager.createQuery("FROM Address a JOIN a.town t WHERE t.name= :town", Address.class)
                    .setParameter("town", town.getName())
                    .getResultList();
            for (Address address : resultListAddress) {
                for (Employee employee : address.getEmployees()) {
                    employee.setAddress(null);
                }
                entityManager.remove(address);
            }
            System.out.printf("%d addresses in %s deleted%n", resultListAddress.size(), town.getName());
            entityManager.remove(town);
        }

        entityManager.getTransaction().commit();

        entityManagerFactory.close();
    }
}
