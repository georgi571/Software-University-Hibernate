package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._3UniversitySystem;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("university_system");



        entityManagerFactory.close();
    }
}