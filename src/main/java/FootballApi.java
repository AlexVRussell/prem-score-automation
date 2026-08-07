import java.net.http.HttpClient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FootballApi {
    private final String apiKey;
    private final HttpClient client;
    private static final String BASE_URL = "https://v3.football.api-sports.io";
    private static final String FIXTURES_ENDPOINT = "/fixtures";
    private static final int PREMIER_LEAGUE_ID = 39;
    private static final int CURRENT_SEASON = 2025;


    public FootballApi(Config config) {
        this.apiKey = config.getKey();
        this.client = HttpClient.newHttpClient();
    }

    public List<Match> getTodayMatches() {
        String url = buildFixturesUrl();
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = sendRequest(request);
        System.out.println(response.statusCode());
        System.out.println(response.body());

        return null;
    }

    private String buildFixturesUrl() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        return BASE_URL +
                FIXTURES_ENDPOINT +
                "?league=" + PREMIER_LEAGUE_ID +
                "&season=" + CURRENT_SEASON +
                "&date=" + today;
    }

    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer" + apiKey)
                .GET()
                .build();
    }
    private HttpResponse<String> sendRequest(HttpRequest request) {

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
