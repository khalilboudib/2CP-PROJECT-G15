package application.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UtilRequest {

    public void sendRequest(String requestType, String data, String urlString,String result) throws Exception {
        if (requestType == null || urlString == null) {
            throw new IllegalArgumentException("Request type and URL cannot be null");
        }

        // If there's data to be sent and the request type is GET, add it to the URL
        if (data != null && requestType.equals("GET")) {
            urlString += "?" + URLEncoder.encode(data, "UTF-8");
        }

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestType);

        // If there's data to be sent and the request type allows it
        if (data != null && requestType.equals("POST")) {
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = data.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }
        }

        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Read response if any
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                result = response.toString();
            }
        } else {
            System.out.println("Error in HTTP request: " + responseCode);
        }

        con.disconnect(); // Close the connection
    }
    
}
