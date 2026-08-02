import com.google.api.services.sheets.v4.Sheets;

public class GoogleSheets {
    private final Sheets sheets;
    private final String spreadSheetId;

    public GoogleSheets(Config config) {
        this.spreadSheetId = config.getSpreadSheetID();
        this.sheets = createSheetsService();
    }

    private Sheets createSheetsService() {
        return null;
    }
}
