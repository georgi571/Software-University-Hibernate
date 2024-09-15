package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._02ChangeCasing;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Town;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Town> resultList = entityManager
                .createQuery("FROM Town WHERE LENGTH(name) > 5", Town.class)
                .getResultList();

        for (Town town : resultList) {
            town.setName(town.getName().toUpperCase());
            entityManager.persist(town);
        }

        entityManager.getTransaction().commit();

        entityManagerFactory.close();
    }
}
