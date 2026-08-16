import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Config config = new Config();
        FootballApi footballApi = new FootballApi(config);
        GoogleSheets googleSheets = new GoogleSheets(config);

        LocalDate today = LocalDate.of(2026, 8, 23);

        LocalDate from;
        LocalDate to;

        if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
            from = today.minusDays(3);
            to = today;
        } else {
            from = today.minusDays(
                    today.getDayOfWeek().getValue() - DayOfWeek.FRIDAY.getValue()
            );
            to = from.plusDays(3);
        }

        System.out.println("Today: " + today);
        System.out.println("From: " + from);
        System.out.println("To: " + to);

        List<Match> matches = footballApi.getFixtures(from, to);

        int row = 2;

        for (Match match : matches) {
            if (match.getLeague().getLeagueId() != 15) {
                continue;
            }

            System.out.println(
                    "Match: " +
                            match.getHomeTeam().getTeamName() +
                            " vs " +
                            match.getAwayTeam().getTeamName() +
                            " - Score: " +
                            match.getScore() +
                            " - Date: " +
                            match.getMatchDate()
            );

            googleSheets.updateRow(
                    row,
                    match.getHomeTeam().getTeamName() +
                            " vs " +
                            match.getAwayTeam().getTeamName(),
                    match.getStatus(),
                    match.getMatchDate(),
                    match.getScore()
            );

            row++;
        }
    }
}