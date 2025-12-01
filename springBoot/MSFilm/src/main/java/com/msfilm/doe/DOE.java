package com.msfilm.doe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
 
@Component
public class DOE {
    /**
     * Key to access OMDb API
     */
    private String apiKey = "f9a63c9c";

    /**
     * Default beggining of URL
     */
    private static String linkToOMDB = "http://www.omdbapi.com/?apikey=";

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
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);

        int statusCode = connection.getResponseCode();
        connection.disconnect();

        if (statusCode == 200) {
            return true;
        }
        return false;
    }

    /**
     * Construct the URL according to parameters apiKey and film name
     * Exemple :
     * apikey = 0
     * filmtitle = A
     * return : https://www.omdbapi.com/?apikey=0&A
     * @param filmName an idea, film name or approximate film name
     * @return
     */
    private String constructUrl(String filmName, searchType t) {
        switch (t) {
            case ID:
                return linkToOMDB+this.apiKey+"&i"+filmName+"&type=movie";
            case APPX_SEARCH:
                return linkToOMDB+this.apiKey+"&s"+filmName+"&type=movie";
            case EXACT_NAME:
                return linkToOMDB+this.apiKey+"&t"+filmName+"&type=movie";
        }
        return null;
    }

    /**
     * Will read the JSON on the link (conn) and create a entire string from it
     * @param conn a page sent by OMDB api, 
     * @return
     * @throws Exception
     */
    private StringBuilder readJson(HttpURLConnection conn) throws Exception{
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
     * Cast the string builder into a Java Map
     * @param jsonData JSON in a string format
     * @return A cast of the JSON String int a Map
     * @throws Exception
     */
    private Map<String, Object> parseJson(StringBuilder jsonData) throws Exception{
        JSONObject jsonObject = new JSONObject(jsonData.toString());
        Map<String, Object> jsonMap = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            jsonMap.put(key, jsonObject.get(key));
        }
        return jsonMap;
    }
    
    /**
     * 
     * @param filmName
     * @param t
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    public Map<String, Object> getFilm(String filmName, searchType t) throws Exception {
        if (filmName == null || filmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du film ne peut pas être null ou vide.");
        }

        URL url = new URL(this.constructUrl(filmName, t));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        
        StringBuilder response = readJson(conn);
        
        conn.disconnect();

        Map<String, Object> jsonMap = parseJson(response);

        return jsonMap;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}