import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private final Dotenv dotenv = Dotenv.load();

    public String getKey() {
        return dotenv.get("API_KEY");
    }

    public String getSpreadSheetID() {
        return dotenv.get("SPREAD_SHEET_ID");
    }
}
