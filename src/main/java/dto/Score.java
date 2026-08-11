package dto;

public class Score {
    private Integer home;

    private Integer away;

    public Score(Integer home, Integer away) {
        this.home = home;
        this.away = away;
    }

    public Integer getHome() {
        return home;
    }

    public Integer getAway() {
        return away;
    }

    @Override
    public String toString() {
        return home + "-" + away;
    }

}
