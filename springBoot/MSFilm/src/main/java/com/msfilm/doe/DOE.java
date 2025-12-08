package com.msfilm.doe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DOE {
    private String apiKey = "f9a63c9c";
    private static final String LINK_TO_OMDB = "http://www.omdbapi.com/?apikey=";

    /**
     * Tests the connection to a given URL.
     * 
     * @param link The URL to test.
     * @return true if the connection is successful, false otherwise.
     * @throws Exception If the connection fails.
     */
    public boolean testConnection(String link) {
        if (link == null || link.trim().isEmpty()) {
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
            return statusCode == 200;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Constructs the URL for the OMDB API based on the search type and parameters.
     * 
     * @param filmName The film name or ID.
     * @param t        The search type (ID, EXACT_NAME, APPX_SEARCH).
     * @param page     The page number for approximate search (optional).
     * @return The constructed URL.
     */
    private String constructUrl(String filmName, searchType t, int... page) {
        StringBuilder urlBuilder = new StringBuilder(LINK_TO_OMDB + this.apiKey);

        switch (t) {
            case ID:
                urlBuilder.append("&i=").append(filmName).append("&type=movie");
                break;
            case EXACT_NAME:
                urlBuilder.append("&t=").append(filmName).append("&type=movie");
                break;
            case APPX_SEARCH:
                urlBuilder.append("&s=").append(filmName).append("&type=movie");
                if (page.length > 0 && page[0] > 1) {
                    urlBuilder.append("&page=").append(page[0]);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid search type");
        }
        return urlBuilder.toString();
    }

    /**
     * Reads the JSON response from the HTTP connection.
     * 
     * @param conn The HTTP connection.
     * @return The JSON response as a StringBuilder.
     * @throws Exception If an error occurs while reading the response.
     */
    private StringBuilder readJson(HttpURLConnection conn) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response;
    }

    /**
     * Parses the JSON string into a Map.
     * 
     * @param jsonData The JSON string.
     * @return The parsed Map.
     * @throws Exception If an error occurs while parsing the JSON.
     */
    private Map<String, Object> parseJson(StringBuilder jsonData) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonData.toString());
        return jsonObject.toMap();
    }

    /**
     * Uses the OMDB API to fetch data.
     * 
     * @param url The URL to fetch data from.
     * @return The parsed Map from the JSON response.
     * @throws Exception If an error occurs while fetching or parsing data.
     */
    private Map<String, Object> useAPI(URL url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        StringBuilder response = readJson(conn);
        conn.disconnect();
        return parseJson(response);
    }

    /**
     * Fetches a film by its exact name or ID.
     * 
     * @param filmName The film name or ID.
     * @param t        The search type (ID or EXACT_NAME).
     * @return The film data as a Map.
     * @throws Exception If an error occurs.
     */
    public Map<String, Object> getFilm(String filmName, searchType t) throws Exception {
        if (filmName == null || filmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du film ne peut pas être null ou vide.");
        }
        return useAPI(new URL(constructUrl(filmName, t)));
    }

    /**
     * Fetches a list of films based on an approximate search.
     * 
     * @param filmName The approximate film name.
     * @param page     The page number (must be > 1).
     * @return The search results as a Map.
     * @throws Exception If an error occurs.
     */
    public Map<String, Object> getSearchResult(String filmName, int page) throws Exception {
        if (filmName == null || filmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du film ne peut pas être null ou vide.");
        }
        if (page < 1 || page > 100) {
            throw new IllegalArgumentException("La page ne peut pas être < 0");
        }
        return useAPI(new URL(constructUrl(filmName, searchType.APPX_SEARCH, page)));
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
