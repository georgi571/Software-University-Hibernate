package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "gameId")
    private Set<PlayerAndStatistic> playerAndStatistics;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @Column(name = "date_time")
    private LocalDateTime dataAndTime;

    @Column(name = "home_team_win_bet_rate")
    private double homeTeamWinBetRate;
    @Column(name = "away_team_win_bet_rate")
    private double awayTeamWinBetRate;
    @Column(name = "draw_game_bet_rate")
    private double drawGameBetRate;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    private Round round;

    @ManyToOne
    @JoinColumn(name = "competetion_id", referencedColumnName = "id")
    private Competition competition;

}
