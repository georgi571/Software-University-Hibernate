package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

@Entity
@IdClass(PlayerAndStatisticId.class)
@Table(name = "players_and_statistics")
public class PlayerAndStatistic {
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game gameId; // Reference to the Game entity

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player playerId;

    @Column(name = "scored_goals")
    private int scoredGoals;

    @Column(name = "player_assists")
    private int playerAssists;

    @Column(name = "played_minutes")
    private int playedMinutes;

}
