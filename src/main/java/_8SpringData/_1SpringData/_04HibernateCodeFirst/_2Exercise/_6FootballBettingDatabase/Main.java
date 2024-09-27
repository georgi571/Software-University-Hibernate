package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("football_bookmarks_system");


        entityManagerFactory.close();
    }
}
