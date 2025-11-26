package com.actors;

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
public abstract class Actor {

    /**
     * Unique ID of each actors choosen by the actor manager
     */
    private String id;

    /**
     * Lifetime left of the Actor in ms
     */
    private long lifetime = 5000;

    /**
     * Setter for lifetime, used to reduce the lifetime or give it more time
     * @param lf lifetime in ms 
     */
    public void setLifetime(long lf){
        this.lifetime = lf;
    }

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

    public void SetID(String id){
        this.id = id;
    }
}