package com.actors;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public abstract class ActorManager { 
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private HashSet<Actor> actors= new HashSet();
    
    @Value("${actor.lifetime.check.interval:5000}") // Valeur par défaut : 5000 ms
    private long checkInterval;

    public HashSet<Actor> getActors() {
        return actors;
    }

    public boolean addActor(Actor a){
        return this.actors.add(a);
    } 

    /**
     * Find and return the actor according to the ID provied, 
     * null if the actor is not present
     * @param id 
     * @return
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
    public void deleteActor(Actor a){
        this.actors.remove(a);
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
    @Scheduled(fixedRateString = "${actor.lifetime.check.interval}")
    private void checkLifeTime(){
        for(Actor a : this.actors){
            a.setLifetime(a.getLifetime()-checkInterval);
        }

        // remove all expired actors (lifetime <= 0ms)
        actors.removeIf((a) -> {
            return a.getLifetime() <= 0;
        });
    }

    @Override
    public String toString() {
        return "ActorManager [actors=" + actors + ", getActors()=" + getActors() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actors == null) ? 0 : actors.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActorManager other = (ActorManager) obj;
        if (actors == null) {
            if (other.actors != null)
                return false;
        } else if (!actors.equals(other.actors))
            return false;
        return true;
    }

    
}