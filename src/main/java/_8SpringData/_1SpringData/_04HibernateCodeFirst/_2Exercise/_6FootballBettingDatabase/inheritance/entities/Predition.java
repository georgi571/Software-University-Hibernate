package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._6FootballBettingDatabase.inheritance.entities;

public enum Predition {
    HOME_TEAM_WIN("Home Team Win"),
    DRAW_GAME("Draw Game"),
    AWAY_TEAM_WIN("Away Team Win");

    private final String description;

    Predition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
