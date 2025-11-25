package com.msfilm.film;

import java.util.Map;

import com.actor.Actor;

public class Film implements Actor {
    /**
     * Actor ID : used to identify the actor during his lifecycle
     */
    private int idActor;

    /**
     * Movie Score : ratings based on our own data base (not imdb score provided in
     * the data)
     */
    private float score;

    /**
     * Raw movie Data
     */
    private Map<String, Object> moviedata;

    public Film(int idActor, Map<String, Object> movieData) {
        this.idActor = idActor;
        this.moviedata = movieData;
        this.score = 0; // TODO connect h2 data base and update this score according to the database
    }

    private Object getMovieProperty(String nameProperty) {
        return this.moviedata.get(nameProperty);
    }

    @Override
    public boolean createNewSubActor() {
        throw new IllegalCallerException("Sub actor Film is not allowed to create another Sub actor");
    }

    @Override
    public boolean deleteSubActor(int arg0) {
        throw new IllegalCallerException("Sub actor Film is not allowed to delete another sub actor");
    }

    @Override
    public int getId() {
        return this.idActor;
    }

    @Override
    public void receiveWithActor(String arg0, Actor arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveWithActor'");
    }

    @Override
    public void receiveWithId(String arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveWithId'");
    }

    @Override
    public void sendWithActor(String arg0, Actor arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendWithActor'");
    }

    @Override
    public void sendWithId(String arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendWithId'");
    }

    @Override
    public boolean thisSubActorExist(int arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'thisSubActorExist'");
    }

    @Override
    public String toString() {
        return "Film id: " + this.idActor + "; \nTitle: " + getMovieProperty("Title") + "; \nDesc.: "
                + getMovieProperty("Description") + "; Score : " + this.score;
    }

}