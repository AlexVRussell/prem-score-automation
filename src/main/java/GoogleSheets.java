import com.google.api.services.sheets.v4.Sheets;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.util.List;
import com.google.auth.http.HttpCredentialsAdapter;
import dto.Score;

import java.io.InputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleSheets {
    private final Sheets sheets;
    private final String spreadSheetId;

    public GoogleSheets(Config config) {
        this.spreadSheetId = config.getSpreadSheetID();
        this.sheets = createSheetsService();
    }

    private Sheets createSheetsService() {
        try {

            InputStream credentials =
                    getClass().getClassLoader().getResourceAsStream("credentials.json");

            if (credentials == null) {
                throw new RuntimeException("credentials.json not found");
            }

            GoogleCredentials googleCredentials =
                    GoogleCredentials.fromStream(credentials)
                            .createScoped(Collections.singleton(
                                    "https://www.googleapis.com/auth/spreadsheets"));

            return new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(googleCredentials))
                    .setApplicationName("Prem Score Automation")
                    .build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCell(String cell, String value) {
        try {

            ValueRange body = new ValueRange()
                    .setValues(List.of(List.of(value)));

            sheets.spreadsheets()
                    .values()
                    .update(
                            spreadSheetId,
                            cell,
                            body)
                    .setValueInputOption("RAW")
                    .execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<List<Object>> getRows() {
        try {
            ValueRange response = sheets.spreadsheets()
                    .values()
                    .get(spreadSheetId, "A:D")
                    .execute();

            return response.getValues();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRow(int row, String match, String status, String date, String score) {
        try {
            ValueRange body = new ValueRange().setValues(List.of(List.of(match, status, date, score)));
            Sheets.Spreadsheets.Values values = sheets.spreadsheets().values();
            values.update(spreadSheetId, "A" + row + ":D" + row, body).setValueInputOption("RAW").execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrAddMatch(
            String match,
            String status,
            String date,
            String score) {

        match = normalizeMatchName(match);

        List<List<Object>> rows = getRows();

        for (int i = 1; i < rows.size(); i++) {

            List<Object> row = rows.get(i);

            if (!row.isEmpty()) {

                String existingMatch =
                        normalizeMatchName(row.get(0).toString());

                if (existingMatch.equals(match)) {

                    updateRow(
                            i + 1,
                            match,
                            status,
                            date,
                            score
                    );

                    return;
                }
            }
        }

        int newRow = rows.size() + 1;

        updateRow(
                newRow,
                match,
                status,
                date,
                score
        );
    }

    private String normalizeMatchName(String match) {
        return match.replace("&amp;", "&");
    }
}
