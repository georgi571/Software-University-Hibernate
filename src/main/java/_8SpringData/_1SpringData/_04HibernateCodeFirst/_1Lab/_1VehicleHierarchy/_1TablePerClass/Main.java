package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1TablePerClass;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("code_first");

        entityManagerFactory.close();
    }
}