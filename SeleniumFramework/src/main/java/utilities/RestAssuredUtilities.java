package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class RestAssuredUtilities {

    // Utility method to send PATCH requests
    public static Response sendPatchRequest(String url, String jsonBody) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .patch(url)
                .then()
                .extract()
                .response();
    }

    // Utility method to send GET requests
    public static Response sendGetRequest(String url) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    // Utility method to send POST requests using HttpsURLConnection
    public static String sendPostRequestUsingHttpUrlConnection(String body, String postUrl) throws Exception {
        URL url = new URL(postUrl);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Send request body
        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        // Check response code
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Handle non-2xx responses
        if (responseCode != 200 && responseCode != 201) {
            System.err.println("Error response received:");
            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }
            }
            throw new RuntimeException("Failed with HTTP code: " + responseCode);
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }}