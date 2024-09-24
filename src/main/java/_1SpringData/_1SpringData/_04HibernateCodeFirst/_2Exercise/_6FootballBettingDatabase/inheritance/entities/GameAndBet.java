package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

@Entity
@IdClass(GameAndBetId.class)
@Table(name = "bet_games")
public class GameAndBet {
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game gameId; // Reference to the Game entity

    @Id
    @ManyToOne
    @JoinColumn(name = "bet_id", referencedColumnName = "id")
    private Bet betId;

    @ManyToOne
    @JoinColumn(name = "prediction_id", referencedColumnName = "id")
    private ResultPrediction resultPrediction;

}
