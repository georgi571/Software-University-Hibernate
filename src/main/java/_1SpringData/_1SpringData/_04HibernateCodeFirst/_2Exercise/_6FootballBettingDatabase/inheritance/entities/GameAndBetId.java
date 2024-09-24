package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import java.io.Serializable;

public class GameAndBetId implements Serializable {
    private Game gameId;

    private Bet betId;

    public GameAndBetId(Game gameId, Bet betId) {
        this.gameId = gameId;
        this.betId = betId;
    }
}
