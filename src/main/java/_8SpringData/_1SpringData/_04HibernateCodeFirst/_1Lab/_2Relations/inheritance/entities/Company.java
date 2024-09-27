package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._2Relations.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company")
    private Set<Plane> planes;
}
