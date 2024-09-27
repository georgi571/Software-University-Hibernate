package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "continets")
public class Continent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "continents")
    private Set<Country> countries;
}
