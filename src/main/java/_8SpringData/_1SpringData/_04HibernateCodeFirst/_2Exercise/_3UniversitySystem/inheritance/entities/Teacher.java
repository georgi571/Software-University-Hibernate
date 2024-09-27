package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._3UniversitySystem.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "salary_per_hour", nullable = false)
    private double salaryPerHour;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;
}
