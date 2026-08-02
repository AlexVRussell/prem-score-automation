import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();

        GoogleSheets sheets = new GoogleSheets(config);

        sheets.updateCell(
                "A1",
                "Hello World"
        );
    }
}