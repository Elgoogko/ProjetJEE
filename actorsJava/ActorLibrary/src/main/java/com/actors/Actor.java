package com.actors;

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