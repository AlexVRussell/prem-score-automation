import com.google.gson.Gson;

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

    public List<Match> getFixtures(LocalDate from, LocalDate to) {
        String url = buildFixturesUrl(from, to);
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = sendRequest(request);

        System.out.println("STATUS: " + response.statusCode());
        System.out.println("BODY: " + response.body());

        System.out.println(response.body());
        return parseMatches(response.body());
    }

    private List<Match> parseMatches(String json) {
        Gson gson = new Gson();

        ApiResponse response =
                gson.fromJson(json, ApiResponse.class);

        return response.getData().getMatches();
    }


    private String buildFixturesUrl(LocalDate from, LocalDate to) {

        return BASE_URL + "/fixtures/upcoming?league_id=15" + "&from=" + from + "&to=" + to;
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
