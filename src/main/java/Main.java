import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        FootballApi footballApi = new FootballApi(config);
        GoogleSheets googleSheets = new GoogleSheets(config);

        List<Match> matches = footballApi.getMatches(LocalDate.of(2026, 8, 21));

        for (Match match : matches) {

            if (match.getLeague().getLeagueId() != 15) {
                continue;
            }

            System.out.println(match);
        }

        // logic for adding to excel
    }
}