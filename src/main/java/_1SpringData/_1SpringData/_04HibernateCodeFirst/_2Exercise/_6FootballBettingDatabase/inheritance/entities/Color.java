package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "colors")
public class Color {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "primaryKitColor")
    private Set<Team> primaryTeamColor;

    @OneToMany(mappedBy = "secondaryKitColor")
    private Set<Team> secondaryTeamColor;
}
