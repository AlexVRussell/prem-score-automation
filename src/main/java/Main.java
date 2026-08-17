import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Config config = new Config();
        FootballApi footballApi = new FootballApi(config);
        GoogleSheets googleSheets = new GoogleSheets(config);

        LocalDate today = LocalDate.now();

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

        LocalDate nextFrom = from.plusDays(7);
        LocalDate nextTo = to.plusDays(7);

        System.out.println("Today: " + today);
        System.out.println("From: " + from);
        System.out.println("To: " + to);

        // Current match week
        List<Match> matches =
                footballApi.getFixtures(from, to);

        for (Match match : matches) {

            if (match.getLeague().getLeagueId() != 15) {
                continue;
            }

            String matchName =
                    match.getHomeTeam().getTeamName() +
                            " vs " +
                            match.getAwayTeam().getTeamName();

            System.out.println(
                    "Match: " +
                            matchName +
                            " - Score: " +
                            match.getScore() +
                            " - Date: " +
                            match.getMatchDate()
            );

            googleSheets.updateOrAddMatch(
                    matchName,
                    match.getStatus(),
                    match.getMatchDate(),
                    match.getScore()
            );
        }

        // Monday: add the following match week
        if (today.getDayOfWeek() == DayOfWeek.MONDAY) {

            System.out.println(
                    "Next match week: " +
                            nextFrom +
                            " -> " +
                            nextTo
            );

            List<Match> nextMatches =
                    footballApi.getFixtures(nextFrom, nextTo);

            for (Match match : nextMatches) {

                if (match.getLeague().getLeagueId() != 15) {
                    continue;
                }

                String matchName =
                        match.getHomeTeam().getTeamName() +
                                " vs " +
                                match.getAwayTeam().getTeamName();

                System.out.println(
                        "Adding next week: " +
                                matchName +
                                " - Score: " +
                                match.getScore() +
                                " - Date: " +
                                match.getMatchDate()
                );

                googleSheets.updateOrAddMatch(
                        matchName,
                        match.getStatus(),
                        match.getMatchDate(),
                        match.getScore()
                );
            }
        }
    }
}