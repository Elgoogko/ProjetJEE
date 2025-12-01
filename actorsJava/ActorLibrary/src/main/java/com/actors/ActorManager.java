package com.actors;

import java.util.HashSet;

import org.springframework.scheduling.annotation.Scheduled;
import com.exceptions.ActorManagerException;

public class ActorManager { 
    
    /**
     * List of actors currently up 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private HashSet<Actor> actors= new HashSet();
    
    /**
     * Sweeper check interval in ms
     */
    private long checkInterval = 5000;

    /**
     * Actor identifier at the begining of each actor ID
     * Works only if autoID is true
     */
    private String actorIdentifier = "AM";

    /**
     * True : the actor Manager will create unique ID for each new actors
     * False :  you have to check and create unique IDs by yourself
     */
    private boolean autoID = true;

    /**
     * The maximum number of actors currently up.
     * It is used to handle infinite loop or bots
     */
    private int maximumNumberOfActor = 1000;

    private int tempTime = 0;
    /**
     * Constructor of class ActorManger uses the Builder Pattern
     * @param builder Builder to call with ActorManager.Builder()
     */
    protected ActorManager(Builder builder) {
        this.actorIdentifier = builder.actorIdentifier;
        this.checkInterval = builder.checkInterval;
        this.autoID = builder.autoID;
        this.maximumNumberOfActor = builder.maximumNumberOfActor;
    }

    /**
     * Create a new unique ID used by the next actors according to the actors currently up
     * @return a Unique ID
     */
    private String getUniqueID() {
        int i = 0;
        HashSet<String> tempIDs= new HashSet<>();

        for (Actor actor : actors) {
            tempIDs.add(actor.getId());
        }
        
        int count = 0;
        while(count <= 10000){
            if(tempIDs.contains(actorIdentifier+Integer.toString(i))){
                i++;
                count++;
            }
            else{
                break;
            }
        }
        return actorIdentifier+Integer.toString(i);
    }

    /**
     * Add an actor to the manager. 
     * Warning : it means that the actor can be deleted according to the sweeper method !
     * @param a a valid Actor
     * @return true if the actor is new, false of it isn't
     */
    public boolean addActor(Actor a) {
        if(a == null){
            throw new ActorManagerException("Can't add a null object to actors list");
        }

        if(this.actors.size() == this.maximumNumberOfActor){
            throw new ActorManagerException("Maximum  number of actor reached");
        }
        if(isAutoID()){
            a.setID(getUniqueID());
        }
        return this.actors.add(a);
    } 

    /**
     * Find and return the actor according to the ID provied, 
     * null if the actor is not present
     * @param id 
     * @return an actor if it exists, null if not
     */
    public Actor getActorByID(String id){
        for(Actor a : actors){
            if(a.getId().equals(id)){
                return a;
            }
        }
        return null;
    }

    /**
     * Remove an actor accoriding to the instance itself
     * @param a Actor instance
     */
    public boolean deleteActor(Actor a){
        return this.actors.remove(a);
    }

    /**
     * Remove an actor according to it's id
     * @param id Actor ID
     */
    public void deleteActor(String id){
        this.actors.remove(getActorByID(id));
    }

    /**
     * Transfert a message to an actor, usally a JSON
     * @param message JSON format
     * @param actSource ID of source actor
     * @param actDest ID of destination actor
     */
    public void transfertToActor(String message, String actSource, String actDest){
        this.getActorByID(actSource).receive(message, actDest);
    }

    /**
     * Update the lifeTime of each actors and delete thoses who has expired
     */
    @Scheduled(fixedRate = 1000)
    public void checkLifeTime(){
        if(checkInterval-tempTime <= 0){
        for(Actor a : this.actors){
            a.decrementLifetime(checkInterval);
        }

        // remove all expired actors (lifetime <= 0ms)
        actors.removeIf((a) -> {
            return a.getLifetime() <= 0;
        });
        tempTime = 0;
        } else{
            tempTime = tempTime + 1000; 
        }
    }

    /**
     * The class builde is the main part of the pattern Builder. It is used to handle multiple combinaisons of values without beeing required to create a new Actor Manager
     */
    public static class Builder {
        /**
         * @see actorIdentifier in ActorManager
         */
        private String actorIdentifier = "AM";
        /**
         * @see checkInterval in ActorManager
         */
        private long checkInterval = 5000;
        /**
         * @see autoID in ActorManager
         */
        private boolean autoID = true;
           /**
         * @see maximumNumberOfActor in ActorManager
         */
        private int maximumNumberOfActor = 1000;

        /**
         * Public constructor to respect pattern Builder
         */
        public Builder(){}

        /**
         * set the beggining ID for each actors of this manager.
         * Exemple : actorIdentifier = "A" will result in : 
         * Actor 1 : "A1"
         * Actor 2 : "A2"
         * Actor n : "An"
         * @param actorIdentifier the id 
         * @return
         */
        public Builder actorIdentifier(String actorIdentifier) {
            if(actorIdentifier == null){
                throw new ActorManagerException("Actor Identifier must not be null");
            }
            else if(actorIdentifier.isEmpty() || actorIdentifier.isBlank()){
                throw new ActorManagerException("Actor Identifier must not be blank");
            }
            this.actorIdentifier = actorIdentifier;
            return this;
        }

        /**
         * The check interval is used to clean the actors list : each n seconds, all actors with no lifetime left will be deleted 
         * @param checkInterval in ms
         * @return
         */
        public Builder checkInterval(long checkInterval) {
            if(checkInterval < 0){
                throw new ActorManagerException("Checking interval cannot be null !");
            }
            this.checkInterval = checkInterval;
            return this;
        }

        /**
         * auto ID indicates if your manager sets the actors ID automatically or not.
         * Beware : if set to false, you'll have to manage each ID by your own and check if they are unique
         * @param autoID boolean
         * @return
         */
        public Builder autoID(boolean autoID) {
            this.autoID = autoID;
            return this;
        }
        
        /**
         * Set the maximum number of actors that can be instantiated.
         * It is used to prevent infinite adding of Actors
         * @param mx
         * @return
         */
        public Builder maximumNumberOfActors(int mx){
            if(mx <= 0){
                throw new ActorManagerException("Manager needs to handle atleast 1 actor");
            }
            this.maximumNumberOfActor =mx;
            return this;
        }
        /**
         * Build function, must be called to build the concrete ActorManager
         * @return
         */
        public ActorManager build() {
            return new ActorManager(this);
        }
    }

    @Override
    public String toString() {
        return "ActorManager [actors=" + actors + "]";
    }

    /**
     * Get all the actors currently present
     * @return HashList of unique actors
     */
    public HashSet<Actor> getAllActors() {
        return actors;
    }

    /**
     * get the current Check Interval
     * @return checkInterval in ms
     */
    public long getCheckInterval() {
        return checkInterval;
    }

    /**
     * Get the actor Identifier. Maybe used to have the type of actor stocked and managed
     * @return String in the begining of each actor ID, only if autoID is true
     */
    public String getActorIdentifier() {
        return actorIdentifier;
    }

    /**
     * Get the status of the actor manager : if false, you have to make the actors ID by yourself, if true
     * The class will generate and check each actor ID
     * @return
     */
    public boolean isAutoID() {
        return autoID;
    }

}