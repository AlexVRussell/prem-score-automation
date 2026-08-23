import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import dto.League;
import dto.Score;
import dto.Team;

public class Match {
    private League league;
    private Team home_team;
    private Team away_team;
    private Score score;
    private String status;
    private String match_date;

    public League getLeague() {
        return league;
    }

    public Team getHomeTeam() {
        return home_team;
    }

    public Team getAwayTeam() {
        return away_team;
    }

    public String getScore() {
        return score.toString();
    }

    public String getMatchDate () {
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("EEEE d, MMM yyyy, hh:mm");
        LocalDateTime dateTime = LocalDateTime.parse(match_date, input);
        return dateTime.atZone(ZoneId.of("America/Toronto")).format(output) + " " + (dateTime.atZone(ZoneId.of("America/Toronto")).getHour() < 12 ? "AM" : "PM") + " EDT";
    }

    public String getStatus() {
        return status;
    }


    // look into other statuses, to make response shape have the best context
    @Override
    public String toString() {
        if (status.equals("incomplete")) {
            return "incomplete";
        } else {
            return home_team.getTeamName()
                    + " "
                    + score.getHome()
                    + "-"
                    + score.getAway()
                    + " "
                    + away_team.getTeamName();
        }
    }
}
