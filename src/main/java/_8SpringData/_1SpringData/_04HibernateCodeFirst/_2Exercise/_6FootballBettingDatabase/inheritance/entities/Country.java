package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cuntries")
public class Country {
    @Id
    @Column(name = "id", length = 3)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "countries_continents",
    joinColumns = @JoinColumn (name = "countries_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn (name = "continent_id", referencedColumnName = "id"))
    private Set<Continent> continents;

    @OneToMany(mappedBy = "country")
    private Set<Town> towns;
}
