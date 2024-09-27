package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

import java.io.Serializable;

public class PlayerAndStatisticId implements Serializable {
    private Game gameId;

    private Player playerId;

    public PlayerAndStatisticId(Game gameId, Player playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
}
