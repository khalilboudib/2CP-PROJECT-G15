package jsr268gp.sampleclient;


import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.Gson;

public class UtilRequest {

    public static int sendRequest(String requestType, String data, String urlString,String result) throws Exception {
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
            OutputStream os = null;
            try {
                os = con.getOutputStream();
                byte[] input = data.getBytes("UTF-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                // Handle IOException
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        // Handle IOException
                        e.printStackTrace();
                    }
                }
            }
        }

        int responseCode = con.getResponseCode();
        //System.out.println("Response Code: " + responseCode);

        // Read response if any
        if (responseCode == HttpURLConnection.HTTP_OK) {
        	BufferedReader in = null;
        	try {
        	    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        	    String inputLine;
        	    StringBuilder response = new StringBuilder();
        	    while ((inputLine = in.readLine()) != null) {
        	        response.append(inputLine);
        	    }
        	    result = response.toString();
        	} catch (IOException e) {
        	    // Handle IOException
        	    e.printStackTrace();
        	} finally {
        	    if (in != null) {
        	        try {
        	            in.close();
        	        } catch (IOException e) {
        	            // Handle IOException
        	            e.printStackTrace();
        	        }
        	    }
        	}
        }
//        } else {
//            System.out.println("Error in HTTP request: " + responseCode);
//        }

        con.disconnect(); // Close the connection
        return responseCode;
    }
    
    public static int sendRequest(String requestType, byte[] data, String urlString,String result) throws Exception {
        if (requestType == null || urlString == null) {
            throw new IllegalArgumentException("Request type and URL cannot be null");
        }

        // If there's data to be sent and the request type is GET, add it to the URL
        if (data != null && requestType.equals("GET")) {
            urlString += "?" + URLEncoder.encode(data.toString(), "UTF-8");
        }

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestType);

        // If there's data to be sent and the request type allows it
        if (data != null && requestType.equals("POST")) {
            con.setDoOutput(true);
            OutputStream os = null;
            try {
                os = con.getOutputStream();
                byte[] input = data.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                // Handle IOException
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        // Handle IOException
                        e.printStackTrace();
                    }
                }
            }
        }

        int responseCode = con.getResponseCode();
        //System.out.println("Response Code: " + responseCode);

        // Read response if any
        if (responseCode == HttpURLConnection.HTTP_OK) {
        	BufferedReader in = null;
        	try {
        	    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        	    String inputLine;
        	    StringBuilder response = new StringBuilder();
        	    while ((inputLine = in.readLine()) != null) {
        	        response.append(inputLine);
        	    }
        	    result = response.toString();
        	} catch (IOException e) {
        	    // Handle IOException
        	    e.printStackTrace();
        	} finally {
        	    if (in != null) {
        	        try {
        	            in.close();
        	        } catch (IOException e) {
        	            // Handle IOException
        	            e.printStackTrace();
        	        }
        	    }
        	}
        }
//        } else {
//            System.out.println("Error in HTTP request: " + responseCode);
//        }

        con.disconnect(); // Close the connection
        return responseCode;
    }
    
    public static <K,V> String mapToJsonString(Map<K, V> map) {
        // Convert map to JSON string
        Gson gson = new Gson();
        return gson.toJson(map);
    }
    public static <K, V> Map<K, V> jsonStringToMap(String jsonString) {
        // Convert JSON string to map
        Gson gson = new Gson();
        Type type = new com.google.gson.reflect.TypeToken<Map<K, V>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }
    
}