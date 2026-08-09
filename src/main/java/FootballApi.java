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
    private static final String BASE_URL = "https://footballdata.io/api/v1";
    private static final String FIXTURES_ENDPOINT = "/fixtures";
    private static final int PREMIER_LEAGUE_ID = 15;
    private static final int CURRENT_SEASON = 2025;


    public FootballApi(Config config) {
        this.apiKey = config.getKey();
        this.client = HttpClient.newHttpClient();
    }

    public List<Match> getTodayMatches() {
        String url = buildFixturesUrl(LocalDate.of(2025, 8, 21));
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = sendRequest(request);
        System.out.println(response.statusCode());
        System.out.println(response.body());

        return null;
    }


    private String buildFixturesUrl(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return BASE_URL +
                "/fixtures/today" +
                "?league_id=" + PREMIER_LEAGUE_ID +
                "&date=" + formattedDate;
    }

    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
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
