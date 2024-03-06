package edu.esprit.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OneSignalNotificationSender {

    private static final String ONE_SIGNAL_API_KEY = "YOUR_ONE_SIGNAL_API_KEY";
    private static final String APP_ID = "YOUR_ONE_SIGNAL_APP_ID";

    public static void envoyerNotificationAdmin(String message) {
        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + ONE_SIGNAL_API_KEY);

            String jsonBody = "{"
                    + "\"app_id\": \"" + APP_ID + "\","
                    + "\"included_segments\": [\"Admins\"],"
                    + "\"contents\": {\"en\": \"" + message + "\"}"
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
