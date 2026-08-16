import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private final Dotenv dotenv;

    public Config() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public String getKey() {
        return getValue("API_KEY");
    }

    public String getSpreadSheetID() {
        return getValue("SPREAD_SHEET_ID");
    }

    private String getValue(String key) {
        String value = System.getenv(key);

        if (value == null) {
            value = dotenv.get(key);
        }

        if (value == null) {
            throw new RuntimeException("Missing configuration: " + key);
        }

        return value;
    }
}
