import java.time.LocalDate;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        FootballApi footballApi = new FootballApi(config);
        GoogleSheets googleSheets = new GoogleSheets(config);
        int row = 2;

        List<Match> matches = footballApi.getMatches(LocalDate.of(2026, 8, 21));

        for (Match match : matches) {
            if (match.getLeague().getLeagueId() != 15) {
                continue;
            }

            System.out.println(match.getScore());
            googleSheets.updateRow(row, match.getHomeTeam().getTeamName() + " vs " + match.getAwayTeam().getTeamName(), match.getStatus(), match.getMatchDate(), match.getScore());
            row++;
        }
    }
}