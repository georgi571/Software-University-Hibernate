package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "result_prediction")
public class ResultPrediction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "position_description", nullable = false)
    private Predition prediction;

    @OneToMany(mappedBy = "resultPrediction")
    private Set<GameAndBet> gamesAndBets;
}
