package _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise._09FindTheLatest10Projects;

import _1SpringData._1SpringData._03IntroductionToHibernate._2Exercise.entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss'.0'");

        List<Project> resultList = entityManager
                .createQuery("FROM Project ORDER BY startDate DESC, name", Project.class)
                .setMaxResults(10)
                .getResultList();

        for (Project project : resultList) {
            String formattedStartDate;
            if (project.getStartDate() != null) {
                formattedStartDate = project.getStartDate().format(formatter);
            } else {
                formattedStartDate = "null";
            }

            String formattedEndDate;
            if (project.getEndDate() != null) {
                formattedEndDate = project.getEndDate().format(formatter);
            } else {
                formattedEndDate = "null";
            }
            System.out.printf("Project name: %s%n", project.getName());
            System.out.printf("    Project Description: %s%n",project.getDescription());
            System.out.printf("    Project Start Date: %s%n",formattedStartDate);
            System.out.printf("    Project End Date: %s%n",formattedEndDate);
        }

        entityManagerFactory.close();
    }
}
