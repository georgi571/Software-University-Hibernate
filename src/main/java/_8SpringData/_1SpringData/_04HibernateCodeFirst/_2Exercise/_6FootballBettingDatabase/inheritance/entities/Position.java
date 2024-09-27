package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Column(name = "id", length = 2)
    private String id;

    @Column(name = "position_description", nullable = false)
    private String positionDescription;

    @OneToMany(mappedBy = "position")
    private Set<Player> players;
}
