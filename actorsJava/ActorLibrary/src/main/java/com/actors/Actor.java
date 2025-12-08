package com.actors;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.exceptions.ActorException;

/**
 * 
 * Abstarct class Actor
 * 
 * Setup id to allow interaction with a micro service wich don't know a speficif
 * actor
 * Example : Client and Film are in different micro service
 * So if we send an message between them with actor structur that can be
 * interpreted
 * So id allow to just take id get more information or specific information
 * later
 * 
 */
public class Actor {


    private PrintWriter writer;
    private boolean logsActives = true;

    /**
     * Unique ID of each actors choosen by the actor manager
     */
    private String id;

    /**
     * Lifetime left of the Actor in ms
     */
    private long lifetime = 5000;

    public Actor(){};

    /**
     * Open log file and be ready to write in
     * @param nameClass Name at the start of the log file
     */
    public void initializeLogs(String nameClass){

        if (!logsActives) return;
    
        try {

            String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            FileWriter fileWriter = new FileWriter(nameClass+this.id+timestamp, true);
            writer = new PrintWriter(fileWriter);
            
        } catch (IOException e) {
            System.err.println("Error when open log file : " + e.getMessage());
            logsActives = false; // Désactive les logs en cas d'erreur
        }
    }

    /**
     * Write in log file
     * @param message to put in log file
     */
    public void writeLog(String message) {
        if (!logsActives || writer == null) return;
        
        try {
            String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            writer.println("[" + timestamp + "] " + message);
            writer.flush();
            
        } catch (Exception e) {
            System.err.println("Erreur when writting logs: " + e.getMessage());
        }
    }

    /**
     * Setter for lifetime, used to reduce the lifetime or give it more time
     * @param lf lifetime in ms 
     */
    public void setLifetime(long lf) {
        if(lf < 1000){
            throw new ActorException("Lifetime can't be null, negative or below minimum => It must be atleast 1000ms");
        }
        this.lifetime = lf;
    }

    public void decrementLifetime(long decrement){
        if(decrement < 0){
            throw new ActorException("The decrement variable can't be negative => it adds more time");
        }
        this.lifetime = this.lifetime - decrement;
    }
    /**
     * return the lifetime left for the Actor
     * @return
     */
    public long getLifetime(){
        return this.lifetime;
    }
    /**
     * 
     * Send message to an actor with his id
     * 
     * @param message
     * @param id
     */
    public void send(String message, String id){};

    /**
     * 
     * Receive a message from an actor with given id
     * 
     * @param message
     * @param id
     */
    public void receive(String message, String id){};

    /**
     * getter of id
     * 
     * @return id of the actor
     */
    public String getId(){
        return this.id;
    };

    /**
     * Set the actor ID
     * @param id a non null, empty or blank string
     */
    public void setID(String id){
        if(id.isBlank() || id.isEmpty() || id == null){
            throw new ActorException("Actor ID can't be null or blank");
        }
        this.id = id;
    }
}