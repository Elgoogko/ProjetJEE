package com.msfilm.doe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DOE {
    private static DOE instance = new DOE("f9a63c9c"); // Thread-safe method
    private String apiKey;

    public static DOE getInstance() {
        if (instance == null) {
            instance = new DOE("f9a63c9c");
        }
        return instance;
    }

    private DOE(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Function used to test the connection :
     * - first to internet (with www.google.com) for exemple
     * - second, to OMdb API
     * @param link
     * @return if the connection can be established
     */
    @SuppressWarnings("deprecation")
    public boolean testConnection(String link) throws Exception {
        if(link == ""){
            throw new IllegalArgumentException("Link can't be empty to test connection");
        }
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            int statusCode = connection.getResponseCode();
            connection.disconnect();

            if(statusCode == 200){
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public Map<String, Object> getFilm(String urlString) throws Exception {
        // --- 1. Ouvre la connexion ---
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        // --- 2. Lit la réponse ---
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        conn.disconnect();

        // --- 3. Parse le JSON en Map ---
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(response.toString(), Map.class);

        return jsonMap;
    }
}
