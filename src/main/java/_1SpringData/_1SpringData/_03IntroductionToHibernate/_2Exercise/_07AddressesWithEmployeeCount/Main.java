package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._07AddressesWithEmployeeCount;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Address> resultList = entityManager
                .createQuery("FROM Address ORDER BY SIZE(employees) DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address address : resultList) {
            System.out.printf("%s, %s - %d employees %n",
                    address.getText(),
                    address.getTown().getName(),
                    address.getEmployees().size());
        }

        entityManagerFactory.close();
    }
}
