import java.net.http.HttpClient;
import java.util.List;

public class FootballApi {
    private final String apiKey;
    private final HttpClient httpClient;

    public FootballApi(Config config) {
        this.apiKey = config.getKey();
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<Match> getTodayMatches() {
    }
}
