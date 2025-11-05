package com.msfilm.doe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestTemplate;

public class DOE {
    private static DOE instance = new DOE(0); // Thread-safe method
    private Integer apiKey = null;

    @Autowired
    private RestTemplate restTemplate;

    public static DOE getInstance() {
        if (instance == null) {
            instance = new DOE(0);
        }
        return instance;
    }

    private DOE(Integer apiKey) {
        this.apiKey = apiKey;
    }

    private boolean testConnection(){
        try{
            HttpStatusCode statusCode = restTemplate.getForEntity("https://www.google.com", String.class).getStatusCode();
            System.out.println(statusCode);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
